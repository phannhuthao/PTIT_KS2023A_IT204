package org.example.ptit_ks2023a_projectit204.ra.edu.dao;

import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class StudentDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Students> findAll() {
        return entityManager.createQuery("FROM Students", Students.class).getResultList();
    }

    public void save(Students students) {
        entityManager.persist(students);
    }

    public Students findById(Integer id) {
        return entityManager.find(Students.class, id);
    }

    public void update(Students student) {
        entityManager.merge(student);
    }
}
