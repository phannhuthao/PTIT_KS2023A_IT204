package org.example.ptit_ks2023a_projectit204.ra.edu.service;

import org.example.ptit_ks2023a_projectit204.ra.edu.dao.AuthDao;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AuthService {
    @Autowired
    private AuthDao authDao;

    @Transactional
    public List<Students> getAllStudents() {
        return authDao.findAll();
    }

    public Students findByEmail(String email) {
        return authDao.findByEmail(email);
    }

    public Students findByPhone(String phone) {
        return authDao.findByPhone(phone);
    }

    public void saveStudent(Students student) {
        authDao.save(student);
    }

}
