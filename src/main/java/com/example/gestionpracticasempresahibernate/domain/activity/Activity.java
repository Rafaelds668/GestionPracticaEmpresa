package com.example.gestionpracticasempresahibernate.domain.activity;

import com.example.gestionpracticasempresahibernate.domain.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.gestionpracticasempresahibernate.domain.activity.PracticeType;

import java.time.LocalDate;
import java.util.Date;

/**
 * Clase que representa una actividad relacionada con un estudiante en la gestión de prácticas empresariales.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activity_id;

    private LocalDate activity_date;
    @Enumerated(EnumType.STRING)
    private PracticeType practice_type;
    private Integer total_hours;
    private String activity_description;
    private String observations;

    @ManyToOne
    @JoinColumn(name = "student")
    private Student student;
}
