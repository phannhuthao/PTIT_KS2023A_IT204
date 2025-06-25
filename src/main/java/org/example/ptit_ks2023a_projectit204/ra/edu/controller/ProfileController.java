package org.example.ptit_ks2023a_projectit204.ra.edu.controller;

import org.example.ptit_ks2023a_projectit204.ra.edu.dto.ChangePasswordForm;
import org.example.ptit_ks2023a_projectit204.ra.edu.dto.FormUpdateProfile;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public String showProfile(Model model, HttpSession session) {
        Students currentUser = (Students) session.getAttribute("loggedInUser");

        if (currentUser == null) {
            return "redirect:/login";
        }

        FormUpdateProfile formUpdateProfile =  new FormUpdateProfile();
        formUpdateProfile.setName(currentUser.getName());
        formUpdateProfile.setEmail(currentUser.getEmail());
        formUpdateProfile.setPhone(currentUser.getPhone());
        formUpdateProfile.setDob(currentUser.getDob());
        formUpdateProfile.setSex(currentUser.isSex());

        model.addAttribute("student", formUpdateProfile);
        return "User/profile";
    }

    @PostMapping
    public String updateProfile(@ModelAttribute("student") FormUpdateProfile formUpdateProfile,
                                HttpSession session) {
        Students currentUser = (Students) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        authService.updateProfile(formUpdateProfile, currentUser);
        session.setAttribute("loggedInUser", currentUser);

        return "redirect:/profile?success=true";
    }


    @GetMapping("/confirmPassword")
    public String showChangePasswordPage(Model model, HttpSession session) {
        Students currentUser = (Students) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        return "User/confirmPassword";
    }



    @PostMapping("/change-password")
    public String handleChangePassword(@ModelAttribute @Valid ChangePasswordForm form,
                                       BindingResult bindingResult,
                                       HttpSession session,
                                       Model model) {
        Students currentUser = (Students) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "User/confirmPassword";
        }

        boolean result = authService.changePassword(currentUser, form);
        if (!result) {
            model.addAttribute("error", "Mật khẩu cũ không đúng hoặc mật khẩu xác nhận không khớp");
            return "User/confirmPassword";
        }

        return "redirect:/profile?successPassword=true";
    }

}

