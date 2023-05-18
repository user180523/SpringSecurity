package com.example.springsecurity.controllers;

import com.example.springsecurity.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AuthenticationController {
    @GetMapping("/authentication")
    public String login(){
        return "authentication";
    }


}
