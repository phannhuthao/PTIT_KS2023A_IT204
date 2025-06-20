package org.example.ptit_ks2023a_projectit204.ra.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {
    @GetMapping("/student")
    public String showStudent(Model model) {
        return "student";
    }
}
