package org.example.ptit_ks2023a_projectit204.ra.edu.service;

import org.example.ptit_ks2023a_projectit204.ra.edu.dao.EnrollmentDao;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Course;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Enrollment;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentDao enrollmentDao;


    public List<Enrollment> getEnrollmentsByStudent(int id) {
        return enrollmentDao.findByStudentId(id);
    }


    public void cancelEnrollment(int id) {
        Enrollment enrollment = enrollmentDao.find(Enrollment.class, id);
        if (enrollment != null) {
            enrollmentDao.remove(enrollment);
        }
    }
}

