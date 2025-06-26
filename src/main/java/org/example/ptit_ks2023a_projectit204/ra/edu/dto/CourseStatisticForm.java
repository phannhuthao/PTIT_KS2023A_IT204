package org.example.ptit_ks2023a_projectit204.ra.edu.dto;

import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Course;

public class CourseStatisticForm {
    private Course course;
    private int studentCount;

    public CourseStatisticForm(Course course, int studentCount) {
        this.course = course;
        this.studentCount = studentCount;
    }
    public Course getCourse() {
        return course;
    }

    public int getStudentCount() {
        return studentCount;
    }
}
