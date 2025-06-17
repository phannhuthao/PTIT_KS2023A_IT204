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
public class Course {
    @Id
    private int id;
    private String name;
    private int duration;
    private String instructor;
    private Date create_at;
    private String image;
}
