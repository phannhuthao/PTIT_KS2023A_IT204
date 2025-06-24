package org.example.ptit_ks2023a_projectit204.ra.edu.service;

import org.example.ptit_ks2023a_projectit204.ra.edu.dao.CourseDao;
import org.example.ptit_ks2023a_projectit204.ra.edu.dao.EnrollmentDao;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Course;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Enrollment;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private EnrollmentDao enrollmentDao;

    @Transactional
    public List<Course> getAllCourses() {
        return courseDao.findAll();
    }

    @Transactional
    public void addCourse(Course course) {
        courseDao.save(course);
    }

    @Transactional
    public void updateCourse(Course course) {
        courseDao.edit(course);
    }


    @Transactional
    public void deleteCourseById(int id) {
        courseDao.deleteById(id);
    }

    @Transactional
    public Course getCourseById(int id) {
        return courseDao.findById(id);
    }

    public List<Course> searchCourse(String name) {
        return courseDao.searchByName(name);
    }

@Transactional
    public void registerStudentToCourse(int studentId, int courseId) {
        Course course = courseDao.findById(courseId);
        Students student = courseDao.find(Students.class, studentId);

        if (enrollmentDao.isStudentEnrolled(studentId, courseId)) {
            return;
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setRegistered_at(new Date());
        enrollment.setStatus(Enrollment.EnrollmentStatus.WAITING);

        courseDao.persist(enrollment);
    }


}

