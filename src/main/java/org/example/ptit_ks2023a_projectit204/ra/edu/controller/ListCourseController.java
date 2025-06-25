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
    public String listCourse(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        List<Course> courses;
        if (search != null && !search.isEmpty()) {
            courses = courseService.searchCourse(search);
            model.addAttribute("search", search);
        } else {
            courses = courseService.getAllCourses();
        }

        int totalCourses = courses.size();
        int totalPages = (int) Math.ceil((double) totalCourses / size);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalCourses);

        if (fromIndex > toIndex) {
            fromIndex = 0;
            page = 0;
        }

        List<Course> paginatedCourses = courses.subList(fromIndex, toIndex);

        model.addAttribute("courses", paginatedCourses);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("search", search);

        return "User/listCourse";
    }

}
