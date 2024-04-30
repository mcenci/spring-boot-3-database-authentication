package com.cenci.security.web.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.cenci.security.configuration.security.CustomUserDetails;
import com.cenci.security.service.UserService;
import com.cenci.security.web.model.ProfileUserFormData;
import com.cenci.security.web.model.RegisterUserFormData;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "/users")
public class UsersController {

	private UserService userService;

	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String listUsers(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
			Model model) {
		
		var page = userService.findAll(pageNo - 1, Sort.by(Direction.ASC, "lastName"));

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("usersList", page.getContent());
		
		return "list-users";
	}

	@GetMapping(path = "/register")
	public String registerUser(Model model) {

		model.addAttribute("formData", new RegisterUserFormData());
		return "register-user";
	}

	@PostMapping(path = "/register")
	public String registerUserSubmit(@Valid @ModelAttribute("formData") RegisterUserFormData formData, 
			BindingResult bindingResult,
			Model model) {
		if(bindingResult.hasErrors()) {
			return "register-user";
		}

		return "redirect:/users";
	}


	@GetMapping(path = "/detail/{userId}")
	public String detailUser(@PathVariable(name = "userId") Long userId, 
			Authentication authentication,
			Model model) {

		boolean isAdmin = authentication.getAuthorities()
				.stream()
				.filter(t -> t.getAuthority().equals("ROLE_ADMIN"))
				.findFirst()
				.isPresent();
		Long callerUserId = ((CustomUserDetails)authentication.getPrincipal()).getUser().getId();
		 
		if(!isAdmin && callerUserId != userId) {
			throw new AccessDeniedException("Invalid Request");
		}
		
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(getCurrentHttpRequest().get());
		if(inputFlashMap != null 
				&& !inputFlashMap.isEmpty()) {
			model.addAttribute("operationResult", inputFlashMap.get("operationResult"));
		}

		var user = userService.retrieve(userId);

		model.addAttribute("formData", ProfileUserFormData
				.builder()
				.userId(user.getId())
				.username(user.getUsername())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.phone(user.getPhone())
				.enable2FA(Boolean.TRUE)
				.build());
		return "user-detail";
	}

	@PostMapping(path = "/detail")
	public String  updateUserSubmit(@Valid @ModelAttribute("formData") ProfileUserFormData formData, 
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model) {
		if(bindingResult.hasErrors()) {
			return "user-detail";
		}

		var user = userService.retrieve(formData.getUserId());
		user.fromProfileUser(formData);

		userService.update(user);

		redirectAttributes.addFlashAttribute("operationResult", "complete");
		return "redirect:/users/detail/" + user.getId();
	}

	@GetMapping(path = "/me")
	public String profileUser(
			Authentication authentication,
			Model model) {
		var me = ((CustomUserDetails)authentication.getPrincipal()).getUser();
		model.addAttribute("formData", ProfileUserFormData
				.builder()
				.userId(me.getId())
				.username(me.getUsername())
				.firstName(me.getFirstName())
				.lastName(me.getLastName())
				.phone(me.getPhone())
				.enable2FA(Boolean.FALSE)
				.build());
		return "user-detail";
	}
	
	public static Optional<HttpServletRequest> getCurrentHttpRequest() {
	    return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
	        .filter(ServletRequestAttributes.class::isInstance)
	        .map(ServletRequestAttributes.class::cast)
	        .map(ServletRequestAttributes::getRequest);
	}
}
