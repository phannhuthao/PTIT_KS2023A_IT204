package org.example.ptit_ks2023a_projectit204.ra.edu.dao;

import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Enrollment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EnrollmentDao {
    @PersistenceContext
    private EntityManager entityManager;

    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return entityManager.find(entityClass, primaryKey);
    }

    public void remove(Object entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    public List<Enrollment> findByStudentId(int studentId) {
        String jpql = "SELECT e FROM Enrollment e WHERE e.student.id = :id";
        return entityManager.createQuery(jpql, Enrollment.class)
                .setParameter("id", studentId)
                .getResultList();
    }

    public boolean isStudentEnrolled(int studentId, int courseId) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = :courseId AND e.student.id = :studentId", Long.class)
                .setParameter("courseId", courseId)
                .setParameter("studentId", studentId)
                .getSingleResult();
        return count > 0;
    }

    public List<Enrollment> findAllEnrollments() {
        String jpql = "SELECT e FROM Enrollment e";
        return entityManager.createQuery(jpql, Enrollment.class).getResultList();
    }


}
