package org.example.ptit_ks2023a_projectit204.ra.edu.dao;

import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Course;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CourseDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Course> findAll() {
        return entityManager.createQuery("FROM Course", Course.class).getResultList();
    }

    public void save(Course course) {
        entityManager.persist(course);
    }

    public void edit(Course course) {
        entityManager.merge(course);
    }

    public void deleteById(int id) {
        Course course = entityManager.find(Course.class, id);
        if (course != null) {
            entityManager.remove(course);
        }
    }
    public List<Course> searchByName(String name) {
        String query = "SELECT c FROM Course c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))";
        return entityManager.createQuery(query, Course.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Course findById(int id) {
        return entityManager.find(Course.class, id);
    }

}

