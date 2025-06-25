package org.example.ptit_ks2023a_projectit204.ra.edu.service;

import org.example.ptit_ks2023a_projectit204.ra.edu.dao.AuthDao;
import org.example.ptit_ks2023a_projectit204.ra.edu.dto.ChangePasswordForm;
import org.example.ptit_ks2023a_projectit204.ra.edu.dto.FormUpdateProfile;
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

    public Students login(String email, String password) {
        List<Students> all = authDao.findAll();
        for (Students s : all) {
            if (s.getEmail().equals(email) && s.getPassword().equals(password)) {
                return s;
            }
        }
        return null;
    }

    public void saveStudent(Students student) {
        authDao.save(student);
    }

    public void updateProfile(FormUpdateProfile formUpdateProfile, Students currentUser) {
        currentUser.setName(formUpdateProfile.getName());
        currentUser.setEmail(formUpdateProfile.getEmail());
        currentUser.setPhone(formUpdateProfile.getPhone());
        currentUser.setDob(formUpdateProfile.getDob());
        currentUser.setSex(formUpdateProfile.isSex());

        authDao.save(currentUser);
    }

    public boolean changePassword(Students student, ChangePasswordForm form) {
        // Kiểm tra mật khẩu cũ
        if (!student.getPassword().equals(form.getOldPassword())) {
            return false; // Mật khẩu cũ không đúng
        }

        // Kiểm tra xác nhận mật khẩu mới
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            return false; // Mật khẩu xác nhận không khớp
        }

        // Cập nhật mật khẩu mới
        student.setPassword(form.getNewPassword());
        authDao.save(student);

        return true;
    }

}
