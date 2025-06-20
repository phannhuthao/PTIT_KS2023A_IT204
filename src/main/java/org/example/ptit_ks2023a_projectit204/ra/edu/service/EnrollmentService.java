package org.example.ptit_ks2023a_projectit204.ra.edu.service;

import org.example.ptit_ks2023a_projectit204.ra.edu.dao.EnrollmentDao;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Course;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Enrollment;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EntityManager entityManager;

    public void enrollStudent(Course course, int studentId) {
        Students student = entityManager.find(Students.class, studentId);

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setRegistered_at(new Date());
        enrollment.setStatus(true);

        entityManager.persist(enrollment);
    }

    public List<Enrollment> getEnrollmentsByStudent(int id) {
        return entityManager.createQuery("SELECT e FROM Enrollment e WHERE e.student.id = :id", Enrollment.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void cancelEnrollment(int id) {
        Enrollment enrollment = entityManager.find(Enrollment.class, id);
        if (enrollment != null) {
            entityManager.remove(enrollment);
        }
    }
}

