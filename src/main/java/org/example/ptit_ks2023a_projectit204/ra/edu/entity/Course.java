package org.example.ptit_ks2023a_projectit204.ra.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Tên không được để trống")
    private String name;

    @Min(value = 1, message = "Thời lượng phải lớn hơn 0")
    private int duration;

    @NotBlank(message = "Giảng viên không được để trống")
    private String instructor;

    private Date create_at;

    private String image;

    @OneToMany(mappedBy = "course")
    private java.util.List<Enrollment> enrollments;
}

