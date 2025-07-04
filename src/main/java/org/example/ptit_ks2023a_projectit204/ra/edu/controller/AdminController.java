package org.example.ptit_ks2023a_projectit204.ra.edu.controller;

import org.example.ptit_ks2023a_projectit204.ra.edu.dto.CourseStatisticForm;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Course;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Enrollment;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.serviceImpl.CourseService;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.serviceImpl.EnrollmentService;
import org.example.ptit_ks2023a_projectit204.ra.edu.service.serviceImpl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;
    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        // tạo biến đếm để đếm tổng số sinh viên, khóa học và lượt đăng ký từ service tương ứng
        int totalStudents = studentService.findAll().size();
        int totalCourses = courseService.findAll().size();
        int totalEnrollments = enrollmentService.findAll().size();

        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("totalEnrollments", totalEnrollments);

        // Danh sách thống kê sinh viên theo từng khóa học
        List<Course> allCourses = courseService.getAllCourses();
        // Tính số sinh viên đã đăng ký cho từng khóa học và đóng gói vào CourseStatisticForm
        List<CourseStatisticForm> courseStats = allCourses.stream().map(course -> {
            // Đếm số lượt đăng ký có courseId trùng với khóa học hiện tại
            long count = enrollmentService.findAll().stream()
                    .filter(enrollment -> enrollment.getCourse().getId().equals(course.getId()))
                    .count();
            return new CourseStatisticForm(course, (int) count);
        }).collect(Collectors.toList());

        model.addAttribute("courseStats", courseStats);

        // Top 5 khóa học có nhiều sinh viên nhất
        // Sắp xếp danh sách trên theo số lượng sinh viên đăng ký giảm dần, lấy top 5
        List<CourseStatisticForm> topCourses = courseStats.stream()
                .sorted(Comparator.comparingInt(CourseStatisticForm::getStudentCount).reversed())
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("topCourses", topCourses);

        return "Admin/dashboard";
    }

    @GetMapping("/admin/courses")
    public String showCourses(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nameOrder", required = false) String nameOrder,
            @RequestParam(value = "idOrder", required = false) String idOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        List<Course> courses = courseService.getAllCourses();

        if (name != null && !name.trim().isEmpty()) {
            courses = courses.stream()
                    .filter(course -> course.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Sort by name or ID
        if ("asc".equalsIgnoreCase(nameOrder)) {
            courses.sort(Comparator.comparing(Course::getName));
        } else if ("desc".equalsIgnoreCase(nameOrder)) {
            courses.sort(Comparator.comparing(Course::getName).reversed());
        }

        if ("asc".equalsIgnoreCase(idOrder)) {
            courses.sort(Comparator.comparing(Course::getId));
        } else if ("desc".equalsIgnoreCase(idOrder)) {
            courses.sort(Comparator.comparing(Course::getId).reversed());
        }

        // Pagination
        int totalCourses = courses.size();
        int totalPages = (int) Math.ceil((double) totalCourses / size);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalCourses);

        if (fromIndex > toIndex) {
            fromIndex = 0;
            page = 0;
        }

        List<Course> paginatedCourses = courses.subList(fromIndex, toIndex);

        // Add attributes to model
        model.addAttribute("courses", paginatedCourses);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("name", name);
        model.addAttribute("nameOrder", nameOrder);
        model.addAttribute("idOrder", idOrder);

        return "Admin/course";
    }


    @GetMapping("/admin/enrollments")
    public String showEnrollments(
            @RequestParam(value = "sortByStatusOrder", required = false) String sortByStatusOrder,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        // Lấy toàn bộ danh sách
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();

        // Lọc theo tên khóa học
        if (name != null && !name.isEmpty()) {
            enrollments = enrollments.stream()
                    .filter(e -> e.getCourse().getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Lọc theo trạng thái
        if ("waiting".equalsIgnoreCase(sortByStatusOrder)) {
            enrollments = enrollments.stream()
                    .filter(e -> e.getStatus() == Enrollment.EnrollmentStatus.WAITING)
                    .collect(Collectors.toList());
        } else if ("confirm".equalsIgnoreCase(sortByStatusOrder)) {
            enrollments = enrollments.stream()
                    .filter(e -> e.getStatus() == Enrollment.EnrollmentStatus.CONFIRM)
                    .collect(Collectors.toList());
        } else if ("denied".equalsIgnoreCase(sortByStatusOrder)) {
            enrollments = enrollments.stream()
                    .filter(e -> e.getStatus() == Enrollment.EnrollmentStatus.DENIED)
                    .collect(Collectors.toList());
        }

        // Tính tổng số trang sau khi lọc
        int totalEnrollments = enrollments.size();
        int totalPages = (int) Math.ceil((double) totalEnrollments / size);

        // Áp dụng phân trang thủ công sau khi lọc
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalEnrollments);
        List<Enrollment> paginatedEnrollments = enrollments.subList(fromIndex, toIndex);

        model.addAttribute("enrollments", paginatedEnrollments);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "Admin/enrollment";
    }


    @PostMapping("/admin/enrollments")
    public String actionStatusEnrollment(@RequestParam("id") int id,
                                         @RequestParam("action") String action,
                                         RedirectAttributes redirectAttributes) {
        if ("confirm".equalsIgnoreCase(action)) {
            enrollmentService.updateEnrollmentStatus(id, Enrollment.EnrollmentStatus.CONFIRM);
            redirectAttributes.addFlashAttribute("successMessage", "Đã duyệt đơn đăng ký.");
        } else if ("deny".equalsIgnoreCase(action)) {
            enrollmentService.updateEnrollmentStatus(id, Enrollment.EnrollmentStatus.DENIED);
            redirectAttributes.addFlashAttribute("errorMessage", "Đã từ chối đơn đăng ký.");
        }
        return "redirect:/admin/enrollments";
    }

    @GetMapping("/admin/students")
    public String showStudent(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(value = "search", required = false) String search,
            Model model) {

        // Lấy danh sách tất cả học sinh không phải admin
        List<Students> students = studentService.getAllStudents()
                .stream()
                .filter(s -> !s.isRole())
                .collect(Collectors.toList());

        // Lọc theo search nếu có
        if (search != null && !search.isEmpty()) {
            students = students.stream()
                    .filter(s -> s.getName().toLowerCase().contains(search.toLowerCase())
                            || s.getEmail().toLowerCase().contains(search.toLowerCase())
                            || String.valueOf(s.getId()).contains(search))
                    .collect(Collectors.toList());
        }

        // Tổng số trang
        int totalStudents = students.size();
        int totalPages = (int) Math.ceil((double) totalStudents / size);

        // Lấy danh sách cho trang hiện tại
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalStudents);
        List<Students> pageStudents = students.subList(fromIndex, toIndex);

        // Truyền dữ liệu sang view
        model.addAttribute("students", pageStudents);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("search", search);

        return "Admin/student";
    }
}
