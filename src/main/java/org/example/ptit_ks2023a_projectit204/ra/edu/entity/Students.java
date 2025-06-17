package org.example.ptit_ks2023a_projectit204.ra.edu.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    private Date dob;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email phải định dạng")
    @Column(unique = true)
    private String email;

    private boolean sex;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Column(unique = true)
    private String phone;

    private Date create_at;

    private boolean role;
}
