package org.example.ptit_ks2023a_projectit204.ra.edu.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Students student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private Date registered_at;
    public enum EnrollmentStatus {
        WAITING,
        CONFIRM,
        CANCEL
    }

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;
}
