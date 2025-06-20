package org.example.ptit_ks2023a_projectit204.ra.edu.controller;

import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Course;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.CloudinaryService;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/course")
    public String showCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
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
        if (result.hasErrors()) {
            model.addAttribute("course", course);
            return "addCourse";
        }

        if (file.isEmpty()) {
            model.addAttribute("error", "Vui lòng chọn hình ảnh.");
            return "addCourse";
        }

        try {
            String imageUrl = cloudinaryService.uploadFile(file);
            course.setImage(imageUrl);
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi upload ảnh: " + e.getMessage());
            e.printStackTrace();
            return "addCourse";
        }

        course.setCreate_at(new java.util.Date());

        try {
            courseService.addCourse(course);
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi lưu khóa học: " + e.getMessage());
            e.printStackTrace();
            return "addCourse";
        }

        return "redirect:/admin/courses";
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
        return "redirect:/admin/courses";
    }

    @GetMapping("/courseDelete/{id}")
    public String deleteCourse(@PathVariable int id) {
        courseService.deleteCourseById(id);
        return "redirect:/admin/courses";
    }

    @GetMapping("/course/search")
    public String searchAndSortCourses(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nameOrder", required = false) String nameOrder,
            @RequestParam(value = "idOrder", required = false) String idOrder,
            Model model) {

        List<Course> courses = courseService.getAllCourses();

        if (name != null && !name.isEmpty()) {
            courses = courseService.searchCourse(name);
        }
        if (nameOrder != null && !nameOrder.isEmpty()) {
            if (nameOrder.equals("asc")) {
                courses.sort(Comparator.comparing(Course::getName));
            } else {
                courses.sort(Comparator.comparing(Course::getName).reversed());
            }
        }

        if (idOrder != null && !idOrder.isEmpty()) {
            if (idOrder.equals("asc")) {
                courses.sort(Comparator.comparingInt(Course::getId));
            } else {
                courses.sort(Comparator.comparingInt(Course::getId).reversed());
            }
        }
        model.addAttribute("courses", courses);
        return "course";
    }

    @PostMapping("/registerCourse")
    public String registerCourse(@RequestParam("id") Integer courseId,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        Students student = (Students) session.getAttribute("loggedInUser");

        if (student == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng đăng nhập trước khi đăng ký.");
            return "redirect:/login";
        }

        courseService.registerStudentToCourse(student.getId(), courseId);
        redirectAttributes.addFlashAttribute("successMessage", "Đăng ký khóa học thành công!");
        return "redirect:/listCourse";
    }

}
