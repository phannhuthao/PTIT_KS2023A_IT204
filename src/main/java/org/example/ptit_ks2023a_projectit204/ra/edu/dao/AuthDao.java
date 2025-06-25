package org.example.ptit_ks2023a_projectit204.ra.edu.dao;

import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class AuthDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Students> findAll() {
        return entityManager.createQuery("from Students", Students.class).getResultList();
    }

    @Transactional
    public void save(Students student) {
        entityManager.merge(student);
    }
}
