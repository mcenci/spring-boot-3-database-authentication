package com.cenci.security.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/")
    String home(Model model) {
        return "redirect:login";
    }
	
    @GetMapping("/login")
    String loginPage(Model model) {
        return "login";
    }

}