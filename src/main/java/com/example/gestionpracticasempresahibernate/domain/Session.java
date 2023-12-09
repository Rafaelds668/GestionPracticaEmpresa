package com.example.gestionpracticasempresahibernate.domain;

import com.example.gestionpracticasempresahibernate.domain.activity.Activity;
import com.example.gestionpracticasempresahibernate.domain.company.Company;
import com.example.gestionpracticasempresahibernate.domain.student.Student;
import com.example.gestionpracticasempresahibernate.domain.teacher.Teacher;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa una sesión de la aplicación.
 * Contiene información sobre el usuario actual y su contexto de sesión.
 */
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

    @Getter
    @Setter
    private static Activity currentActivity;

}
