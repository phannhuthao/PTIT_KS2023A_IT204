package org.example.ptit_ks2023a_projectit204.ra.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormUpdateProfile {
    @NotBlank(message = "Tên không được để trống")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email phải định dạng")
    private String email;

    @NotNull(message = "Giới tính không được để trống")
    private boolean sex;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;
}
