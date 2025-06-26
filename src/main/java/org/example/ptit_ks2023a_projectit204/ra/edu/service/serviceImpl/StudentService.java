package org.example.ptit_ks2023a_projectit204.ra.edu.service.serviceImpl;

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

    @Transactional
    public void toggleStudentStatus(Integer id) {
        Students student = studentDao.findById(id);
        if (student != null) {
            // đảo trạng thái active và inactive
            student.setStatus(!student.isStatus());
            // cập nhật lại DB
            studentDao.update(student);
        }
    }
    @Transactional
    public List<Students> findAll() {
        return studentDao.findAll();
    }


}
