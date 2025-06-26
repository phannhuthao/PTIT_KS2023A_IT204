package org.example.ptit_ks2023a_projectit204.ra.edu.controller;

import org.example.ptit_ks2023a_projectit204.ra.edu.dto.FormLogin;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.serviceImpl.AuthService;
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
        return "Auth/login";
    }

    @PostMapping("/login")
    public String actionLogin(@Valid @ModelAttribute("formLogin") FormLogin formLogin,
                              BindingResult result,
                              Model model,
                              HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("formLogin", formLogin);
            return "Auth/login";
        }

        Students loggedInStudent = authService.login(formLogin.getEmail(), formLogin.getPassword());


        if (loggedInStudent == null) {
            model.addAttribute("error", "Sai email hoặc mật khẩu");
            return "redirect:/login";
        }

        if (!loggedInStudent.isStatus()) {
            model.addAttribute("error", "Tài Khoản Của Bạn Đã Bị Khóa");
            model.addAttribute("formLogin", formLogin);
            return "Auth/login";
        }

        session.setAttribute("loggedInUser", loggedInStudent);

        return loggedInStudent.isRole() ? "redirect:/admin" : "redirect:/home";
    }

    @PostMapping("/logout")
    public String actionLogout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("student", new Students());
        return "Auth/register";
    }

    @PostMapping("/register")
    public String actionRegister(@Valid @ModelAttribute("student") Students student,
                                 BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("student", student);
            return "Auth/register";
        }

        student.setCreate_at(new Date());
        student.setRole(false);
        student.setStatus(true);
        authService.saveStudent(student);

        model.addAttribute("success", "Đăng ký thành công!");
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String showAdmin(Model model, HttpSession session) {
        Students loggedInUser = (Students) session.getAttribute("loggedInUser");

        // Nếu chưa đăng nhập hoặc không phải admin
        if (loggedInUser == null || !loggedInUser.isRole()) {
            model.addAttribute("error", "Bạn cần phải đăng nhập tài khoản admin mới được vào");
            model.addAttribute("formLogin", new FormLogin());
            return "Auth/login";
        }

        model.addAttribute("student", loggedInUser);
        return "Admin/admin";
    }


    @GetMapping("/home")
    public String showHome(Model model) {
        model.addAttribute("student", new Students());
        return "User/home";
    }

}
