package org.example.ptit_ks2023a_projectit204.ra.edu.controller;

import org.example.ptit_ks2023a_projectit204.ra.edu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/student")
    public String showStudent(Model model) {
        model.addAttribute("students",
                studentService.getAllStudents()
                        .stream()
                        .filter(student -> !student.isRole())
                        .collect(Collectors.toList())
        );
        return "Admin/student";
    }
    @PostMapping("/student/toggleStatus")
    public String toggleStudentStatus(@RequestParam("id") Integer id, Model model) {
        studentService.toggleStudentStatus(id);
        model.addAttribute("students", studentService.getAllStudents());
        return "redirect:/admin/students";
    }

}
