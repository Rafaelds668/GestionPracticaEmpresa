package com.example.gestionpracticasempresahibernate.domain;

import com.example.gestionpracticasempresahibernate.domain.company.Company;
import com.example.gestionpracticasempresahibernate.domain.student.Student;
import com.example.gestionpracticasempresahibernate.domain.teacher.Teacher;
import lombok.Getter;
import lombok.Setter;

public class Session {

    @Getter
    @Setter
    private static Teacher currentTeacher;

    @Setter
    @Getter
    private static Student currentStudent;

    @Getter
    @Setter
    private static Company currentCompany;

}
