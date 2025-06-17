package org.example.ptit_ks2023a_projectit204.ra.edu.controller;

import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("student", new Students());
        return "login";
    }

    @PostMapping("/login")
    public String actionLogin(@ModelAttribute("student") Students student,
                              BindingResult result, Model model) {
        if (student.getEmail().isEmpty() || student.getPassword().isEmpty()) {
            model.addAttribute("error", "Không được để trống email hoặc mật khẩu.");
            return "login";
        }

        Students userDb = authService.findByEmail(student.getEmail());
        if (userDb == null || !userDb.getPassword().equals(student.getPassword())) {
            model.addAttribute("error", "Tài khoản hoặc mật khẩu không đúng!");
            return "login";
        }

        if (userDb.isRole()) {
            return "redirect:/admin";
        } else {
            return "redirect:/home";
        }

    }


    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("student", new Students());
        return "register";
    }

    @PostMapping("/register")
    public String actionRegister(@Valid @ModelAttribute("student") Students student,
                                 BindingResult result, Model model) {
        // Kiểm tra email và phone đã tồn tại chưa
        if (authService.findByEmail(student.getEmail()) != null) {
            result.rejectValue("email", "error.email", "Email đã tồn tại!");
        }
        if (student.getPhone() != null && authService.findByPhone(student.getPhone()) != null) {
            result.rejectValue("phone", "error.phone", "Số điện thoại đã tồn tại!");
        }

        if (result.hasErrors()) {
            return "register";
        }

        student.setCreate_at(new Date());
        student.setRole(false); // là Student
        authService.saveStudent(student);

        model.addAttribute("success", "Đăng ký thành công!");
        return "register";
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
