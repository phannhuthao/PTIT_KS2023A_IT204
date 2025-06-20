package org.example.ptit_ks2023a_projectit204.ra.edu.controller;

import org.example.ptit_ks2023a_projectit204.ra.edu.dto.FormLogin;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

@Controller
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("formLogin", new FormLogin());
        return "login";
    }

    @PostMapping("/login")
    public String actionLogin(@Valid @ModelAttribute("student") FormLogin formLogin,
                              BindingResult result,
                              Model model,
                              HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("student", formLogin);
            return "login";
        }

        Students loggedInStudent = authService.login(formLogin.getEmail(), formLogin.getPassword());


        if (loggedInStudent == null) {
            model.addAttribute("error", "Sai email hoặc mật khẩu");
            return "login";
        }

        session.setAttribute("loggedInUser", loggedInStudent);

        return loggedInStudent.isRole() ? "redirect:/admin" : "redirect:/home";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("student", new Students());
        return "register";
    }

    @PostMapping("/register")
    public String actionRegister(@Valid @ModelAttribute("student") Students student,
                                 BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("student", student);
            return "register";
        }

        student.setCreate_at(new Date());
        student.setRole(false);
        student.setStatus(true);
        authService.saveStudent(student);

        model.addAttribute("success", "Đăng ký thành công!");
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String showAdmin(Model model) {
        model.addAttribute("student", new Students());
        return "admin";
    }

    @GetMapping("/home")
    public String showHome(Model model) {
        model.addAttribute("student", new Students());
        return "home";
    }

}
