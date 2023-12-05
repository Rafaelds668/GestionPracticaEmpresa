package com.example.gestionpracticasempresahibernate.controllers;

import com.example.gestionpracticasempresahibernate.Main;
import com.example.gestionpracticasempresahibernate.domain.Session;
import com.example.gestionpracticasempresahibernate.domain.student.Student;
import com.example.gestionpracticasempresahibernate.domain.student.StudentDAOImp;
import com.example.gestionpracticasempresahibernate.domain.teacher.Teacher;
import com.example.gestionpracticasempresahibernate.domain.teacher.TeacherDAOImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField pswdField;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Label info;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
    @FXML
    public void login(ActionEvent actionEvent) {
        String user = txtUsuario.getText();
        String pass = pswdField.getText();

        if (user.length() < 4 || pass.length() < 4) {
            info.setText("Introduce los datos");
            info.setStyle("-fx-text-fill: red;");

        } else {
            // ACCESO A BASE DE DATOS PARA LA VALIDACION

            Teacher teacher = (new TeacherDAOImp()).validateUser(user, pass);
            Student student = (new StudentDAOImp()).validateUser(user, pass);
            if (teacher == null && student == null) {
                info.setText("Error, datos incorrectos");
                info.setStyle("-fx-text-fill: red;");
            } else {
                if(teacher != null){
                    Session.setCurrentTeacher(teacher);
                    Main.loadFXML("main-view-profesor.fxml", "Datos Profesor");
                }else {
                    Session.setCurrentStudent(student);
                    Main.loadFXML("main-view-alumno.fxml", "Tareas del alumno " + Session.getCurrentStudent().getFirst_name());
                }
            }
        }
    }
}