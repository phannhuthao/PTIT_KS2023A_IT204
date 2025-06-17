package org.example.ptit_ks2023a_projectit204.ra.edu.controller;

import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String showLogin(Model model) {
        return "login";
    }

    @PostMapping("/login")
        public String actionLogin(@Valid @ModelAttribute ("student")Students student,
                                  BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "Login";
        }

            return "login";
        }

    @GetMapping("/register")
    public String showRegister(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String actionRegister(@Valid @ModelAttribute ("student") Students student,
                                 BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "register";
        }

        return "register";
    }

}
