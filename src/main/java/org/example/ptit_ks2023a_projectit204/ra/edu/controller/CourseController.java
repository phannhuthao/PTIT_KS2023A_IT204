package org.example.ptit_ks2023a_projectit204.ra.edu.controller;

import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Course;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.CloudinaryService;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/course")
    public String showCourses(Model model) {
        model.addAttribute("coures", courseService.getAllCourses());
        model.addAttribute("course", new Course());
        return "course";
    }
    @GetMapping("/courseAdd")
    public String showAddCourse(Model model) {
        model.addAttribute("course", new Course());
        return "addCourse";
    }

    @PostMapping("/courseAdd")
    public String addCourse(@Valid @ModelAttribute("course") Course course,
                            BindingResult result,
                            @RequestParam("imageFile") MultipartFile file,
                            Model model) {
        // Validate file format
        if (!file.isEmpty() && !file.getContentType().matches("image/(jpeg|jpg|png|gif)")) {
            model.addAttribute("imageError", "Định dạng ảnh không hợp lệ. Chỉ chấp nhận jpeg, jpg, png, gif.");
            return "addCourse";
        }

        if (result.hasErrors()) {
            return "addCourse";
        }

        try {
            String imageUrl = cloudinaryService.uploadFile(file);
            course.setImage(imageUrl);
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi upload ảnh: " + e.getMessage());
            return "addCourse";
        }

        course.setCreate_at(new java.util.Date());
        courseService.addCourse(course);
        return "redirect:/course";
    }


    @GetMapping("/courseEdit")
    public String showEditCourse(@RequestParam("id") int id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "editCourse";
    }

    @PostMapping("/courseEdit")
    public String editCourse(@Valid @ModelAttribute("course") Course course,
                             BindingResult result,
                             @RequestParam("imageFile") MultipartFile file,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("course", course);
            return "editCourse";
        }

        try {
            if (!file.isEmpty()) {
                String imageUrl = cloudinaryService.uploadFile(file);
                course.setImage(imageUrl);
            } else {
                Course old = courseService.getCourseById(course.getId());
                course.setImage(old.getImage());
                course.setCreate_at(old.getCreate_at());
            }
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi upload ảnh: " + e.getMessage());
            return "editCourse";
        }

        courseService.updateCourse(course);
        return "redirect:/course";
    }


}
