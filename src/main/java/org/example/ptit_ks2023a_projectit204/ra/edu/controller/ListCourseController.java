package org.example.ptit_ks2023a_projectit204.ra.edu.controller;

import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Course;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ListCourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/listCourse")
    public String listCourse(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Course> courses;
        if (search != null && !search.isEmpty()) {
            courses = courseService.searchCourse(search);
        } else {
            courses = courseService.getAllCourses();
        }
        model.addAttribute("courses", courses);
        return "User/listCourse";
    }
}
