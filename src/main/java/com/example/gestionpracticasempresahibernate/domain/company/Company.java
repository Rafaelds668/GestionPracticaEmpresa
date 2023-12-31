package com.example.gestionpracticasempresahibernate.domain.company;

import com.example.gestionpracticasempresahibernate.domain.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Clase que representa a una compañía en el sistema.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long company_id;
    private String company_name;
    private String phone_number;
    private String email;
    private String company_contact;
    private String incidents;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Student> student;

}
