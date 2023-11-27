package com.example.gestionpracticasempresahibernate.controllers;

import com.example.gestionpracticasempresahibernate.Main;
import com.example.gestionpracticasempresahibernate.domain.Session;
import com.example.gestionpracticasempresahibernate.domain.activity.Activity;
import com.example.gestionpracticasempresahibernate.domain.activity.ActivityDAOImp;
import com.example.gestionpracticasempresahibernate.domain.student.Student;
import com.example.gestionpracticasempresahibernate.domain.student.StudentDAOImp;
import com.example.gestionpracticasempresahibernate.domain.teacher.Teacher;
import com.example.gestionpracticasempresahibernate.domain.teacher.TeacherDAOImp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewAlumnoController implements Initializable {

    @javafx.fxml.FXML
    private ComboBox comboEditarTarea;
    @javafx.fxml.FXML
    private TextField txtActividad;
    @javafx.fxml.FXML
    private TextField txtObservacion;
    @javafx.fxml.FXML
    private DatePicker dateFecha;
    @javafx.fxml.FXML
    private Spinner spTotal;
    @javafx.fxml.FXML
    private RadioButton rbFCT;
    @javafx.fxml.FXML
    private RadioButton rbDual;
    @javafx.fxml.FXML
    private Button btnAñadir;
    @javafx.fxml.FXML
    private TableView <Activity> tvActividad;
    @javafx.fxml.FXML
    private TableColumn <Activity, String> cActividad;
    @javafx.fxml.FXML
    private TableColumn <Activity, String> cObservacion;
    @javafx.fxml.FXML
    private TableColumn <Activity, String> cFecha;
    @javafx.fxml.FXML
    private TableColumn <Activity, String> cHora;
    @javafx.fxml.FXML
    private TableColumn <Activity, String> cPractica;
    @javafx.fxml.FXML
    private Label infoHorasDual;
    @javafx.fxml.FXML
    private Label infoHorasFCT;


    StudentDAOImp studentDAOImp;
    ActivityDAOImp activityDAOImp;

    public MainViewAlumnoController(){}


    public void logout(ActionEvent actionEvent){
        Session.setCurrentStudent(null);
        Main.loadLogin("login-view.fxml");
    }

    public void informacionEmpresa(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacion Adicional");
        alert.setHeaderText("Empresa");
        alert.setContentText("Nombre: " + Session.getCurrentStudent().getCompany().getCompany_name() +
                " Email: " + Session.getCurrentStudent().getCompany().getEmail() +
                " Responsable: " + Session.getCurrentStudent().getCompany().getCompany_contact());
        alert.showAndWait();
    }

    public void informacionTutor (ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacion Adicional");
        alert.setHeaderText("Tutor/a");
        alert.setContentText("Nombre: " + Session.getCurrentStudent().getTutor().getFirst_name() +
                " Apellidos: " + Session.getCurrentStudent().getTutor().getLast_name() +
                " Email: " + Session.getCurrentStudent().getTutor().getEmail());
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentDAOImp = new StudentDAOImp();
        activityDAOImp = new ActivityDAOImp();
        cargarTabla();


    }

    private void cargarTabla(){
        Student student = Session.getCurrentStudent();
        StudentDAOImp studentDAOImp = new StudentDAOImp();
    }

}
