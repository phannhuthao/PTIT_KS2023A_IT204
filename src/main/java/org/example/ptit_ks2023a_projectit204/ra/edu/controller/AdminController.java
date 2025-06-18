package org.example.ptit_ks2023a_projectit204.ra.edu.controller;

import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Course;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/admin/courses")
    public String showCourses(Model model) {
        model.addAttribute("coures", courseService.getAllCourses());
        model.addAttribute("course", new Course());
        return "course";
    }
}
