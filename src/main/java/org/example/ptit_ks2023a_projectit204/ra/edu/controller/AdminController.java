package org.example.ptit_ks2023a_projectit204.ra.edu.controller;

import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Course;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.CourseService;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/admin/courses")
    public String showCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("course", new Course());
        return "course";
    }


    @GetMapping("/admin/students")
    public String showStudent(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nameOrder", required = false) String nameOrder,
            @RequestParam(value = "idOrder", required = false) String idOrder,
            Model model) {

        List<Students> students = studentService.getAllStudents()
                .stream()
                .filter(s -> !s.isRole())
                .collect(Collectors.toList());

        if (name != null && !name.isEmpty()) {
            students = students.stream()
                    .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase())
                            || s.getEmail().toLowerCase().contains(name.toLowerCase())
                            || String.valueOf(s.getId()).contains(name))
                    .collect(Collectors.toList());
        }

        if ("asc".equalsIgnoreCase(nameOrder)) {
            students.sort(Comparator.comparing(Students::getName));
        } else if ("desc".equalsIgnoreCase(nameOrder)) {
            students.sort(Comparator.comparing(Students::getName).reversed());
        }

        if ("asc".equalsIgnoreCase(idOrder)) {
            students.sort(Comparator.comparingInt(Students::getId));
        } else if ("desc".equalsIgnoreCase(idOrder)) {
            students.sort(Comparator.comparingInt(Students::getId).reversed());
        }

        model.addAttribute("students", students);
        return "student";
    }

}
