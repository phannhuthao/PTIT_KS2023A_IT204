package org.example.ptit_ks2023a_projectit204.ra.edu.service;

import org.example.ptit_ks2023a_projectit204.ra.edu.dao.StudentDao;
import org.example.ptit_ks2023a_projectit204.ra.edu.entity.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    @Transactional
    public List<Students> getAllStudents() {
        return studentDao.findAll();
    }
}
