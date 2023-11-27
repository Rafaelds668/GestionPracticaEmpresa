package com.example.gestionpracticasempresahibernate.domain.activity;

import com.example.gestionpracticasempresahibernate.domain.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.gestionpracticasempresahibernate.domain.activity.PracticeType;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activity_id;

    private String activity_date;
    @Enumerated(EnumType.STRING)
    private PracticeType practice_type;
    private Integer total_hours;
    private String activity_description;
    private String observations;

    @ManyToOne
    @JoinColumn(name = "student")
    private Student student;
}
