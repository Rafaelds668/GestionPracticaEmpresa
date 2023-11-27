module com.example.gestionpracticasempresahibernate {
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;

    opens com.example.gestionpracticasempresahibernate.domain.student;
    opens com.example.gestionpracticasempresahibernate.domain.company;
    opens com.example.gestionpracticasempresahibernate.domain.teacher;
    opens com.example.gestionpracticasempresahibernate.domain.activity;

    opens com.example.gestionpracticasempresahibernate to javafx.fxml;
    opens com.example.gestionpracticasempresahibernate.controllers to javafx.fxml;



    exports com.example.gestionpracticasempresahibernate;
    exports com.example.gestionpracticasempresahibernate.controllers;
}