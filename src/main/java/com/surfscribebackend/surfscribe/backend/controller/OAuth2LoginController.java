package com.surfscribebackend.surfscribe.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2LoginController {

    @GetMapping("/login")
    public String login() {
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/login/oauth2/code/google")
    public String loginSuccess() {
        return "redirect:/"; // Redirect to the home page after successful login
    }

}
