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

    public Students findByEmail(String email) {
        List<Students> list = entityManager
                .createQuery("SELECT s FROM Students s WHERE s.email = :email", Students.class)
                .setParameter("email", email)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public Students findByPhone(String phone) {
        List<Students> list = entityManager
                .createQuery("SELECT s FROM Students s WHERE s.phone = :phone", Students.class)
                .setParameter("phone", phone)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Transactional
    public void save(Students student) {
        entityManager.persist(student);
    }

}
