package org.example.ptit_ks2023a_projectit204.ra.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Students {
    @Id
    private int id;
    @NotBlank(message = "Tên không được để trống")
    private String name;
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
    private Date dob;
    private String email;
    private boolean sex;
    private String phone;
    private Date create_at;
    private boolean status;
}
