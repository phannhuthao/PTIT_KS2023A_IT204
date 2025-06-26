package org.example.ptit_ks2023a_projectit204.ra.edu.service.serviceImpl;

import org.example.ptit_ks2023a_projectit204.ra.edu.dao.EnrollmentDao;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentDao enrollmentDao;

    public List<Enrollment> getEnrollmentsByStudent(int id) {
        return enrollmentDao.findByStudentId(id);
    }

    @Transactional
    public void cancelEnrollment(int id) {
        Enrollment enrollment = enrollmentDao.find(Enrollment.class, id);
        if (enrollment != null) {
            enrollmentDao.remove(enrollment);
        }
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentDao.findAllEnrollments();
    }

    @Transactional
    public void updateEnrollmentStatus(int id, Enrollment.EnrollmentStatus status) {
        Enrollment enrollment = enrollmentDao.find(Enrollment.class, id);
        if (enrollment != null) {
            enrollment.setStatus(status);
        }
    }

    public List<Enrollment> getEnrollmentsByStudentAndStatus(int studentId, String status) {
        if (status == null || status.isEmpty()) {
            return getEnrollmentsByStudent(studentId);
        }
        return enrollmentDao.findByStudentIdAndStatus(studentId, status);
    }


    @Transactional
    public List<Enrollment> findAll() {
        return enrollmentDao.findAll();
    }
}

