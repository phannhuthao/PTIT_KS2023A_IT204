package org.example.ptit_ks2023a_projectit204.ra.edu.controller;

import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Enrollment;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/enrollmentHistory")
    public String enrollmentHistory(HttpSession session, Model model) {
        Students student = (Students) session.getAttribute("loggedInUser");
        if (student != null) {
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(student.getId());
            model.addAttribute("enrollments", enrollments);
            return "User/enrollmentHistory";
        }
        return "redirect:/login";
    }

    @PostMapping("/cancelEnrollment")
    public String cancelEnrollment(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        enrollmentService.cancelEnrollment(id);
        redirectAttributes.addFlashAttribute("successMessage", "Hủy đăng ký thành công.");
        return "redirect:/enrollmentHistory";
    }
}
