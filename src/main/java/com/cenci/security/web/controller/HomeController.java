package com.cenci.security.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cenci.security.configuration.security.CustomUserDetails;

@Controller
public class HomeController {

    @GetMapping("/home")
    String homePage(Authentication authentication, Model model) {
    	CustomUserDetails principal = (CustomUserDetails)authentication.getPrincipal();
    	model.addAttribute("fullName", principal.getFullName());
        return "home";
    }

}
