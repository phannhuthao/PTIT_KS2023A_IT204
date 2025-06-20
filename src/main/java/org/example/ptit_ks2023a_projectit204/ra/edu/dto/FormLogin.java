package org.example.ptit_ks2023a_projectit204.ra.edu.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class FormLogin {
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email phải định dạng")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
}
