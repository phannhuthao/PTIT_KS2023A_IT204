package org.example.ptit_ks2023a_projectit204.ra.edu.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Enrollment {
    @Id
    private int id;
    private int student_id;
    private int course_id;
    private Date registered_at;
    private boolean status;
}
