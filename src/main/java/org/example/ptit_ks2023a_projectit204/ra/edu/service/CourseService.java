package org.example.ptit_ks2023a_projectit204.ra.edu.service;

import org.example.ptit_ks2023a_projectit204.ra.edu.dao.CourseDao;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

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
    public Course getCourseById(int id) {
        return courseDao.findById(id);
    }
}

