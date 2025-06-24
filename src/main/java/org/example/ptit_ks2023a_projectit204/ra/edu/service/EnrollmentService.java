package org.example.ptit_ks2023a_projectit204.ra.edu.service;

import org.example.ptit_ks2023a_projectit204.ra.edu.dao.EnrollmentDao;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    public List<Enrollment> getEnrollmentsPaginated(int page, int size) {
        List<Enrollment> allEnrollments = enrollmentDao.findAllEnrollments();
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, allEnrollments.size());

        if (fromIndex >= allEnrollments.size()) {
            return new ArrayList<>();
        }

        return allEnrollments.subList(fromIndex, toIndex);
    }


    public int getTotalEnrollments() {
        return enrollmentDao.findAllEnrollments().size();
    }

}

