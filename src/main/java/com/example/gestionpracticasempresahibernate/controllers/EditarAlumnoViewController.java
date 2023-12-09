package com.example.gestionpracticasempresahibernate.controllers;

import com.example.gestionpracticasempresahibernate.Main;
import com.example.gestionpracticasempresahibernate.domain.Session;
import com.example.gestionpracticasempresahibernate.domain.company.CompanyDAOImp;
import com.example.gestionpracticasempresahibernate.domain.student.Student;
import com.example.gestionpracticasempresahibernate.domain.teacher.TeacherDAOImp;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditarAlumnoViewController implements Initializable {
    @javafx.fxml.FXML
    private TextField txtNombre;
    @javafx.fxml.FXML
    private TextField txtApellido;
    @javafx.fxml.FXML
    private TextField txtDNI;
    @javafx.fxml.FXML
    private DatePicker dateFecha;
    @javafx.fxml.FXML
    private TextArea txtObservacion;
    @javafx.fxml.FXML
    private TextField txtEmail;
    @javafx.fxml.FXML
    private TextField txtTelef;
    @javafx.fxml.FXML
    private Spinner <Integer>spFCT;
    @javafx.fxml.FXML
    private Spinner <Integer> spDual;
    @javafx.fxml.FXML
    private Button btnEditar;
    @javafx.fxml.FXML
    private Label lblTitulo;
    @javafx.fxml.FXML
    private Button btnEliminar;
    @javafx.fxml.FXML
    private Button btnVolver;
    @javafx.fxml.FXML
    private ComboBox comboEmpresa;

    CompanyDAOImp companyDAOImp;
    TeacherDAOImp teacherDAOImp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        companyDAOImp = new CompanyDAOImp();
        teacherDAOImp = new TeacherDAOImp();
        Student alumno = Session.getCurrentStudent();
        txtNombre.setText(alumno.getFirst_name());
        txtApellido.setText(alumno.getLast_name());
        txtDNI.setText(alumno.getDni());
        dateFecha.setValue(alumno.getDate_of_birth());
        txtObservacion.setText(alumno.getObservations());
        txtTelef.setText(alumno.getContact_phone());
        txtEmail.setText(alumno.getEmail());

        //Combo Empresas
        List<String> nombreCompania = new ArrayList<>();
        companyDAOImp.getAll().forEach( s-> nombreCompania.add(s.getCompany_name()));
        comboEmpresa.getItems().addAll(nombreCompania);
        comboEmpresa.setValue(comboEmpresa.getItems().getFirst());

        comboEmpresa.setValue(alumno.getCompany().getCompany_name());

        //Spinners para las horas de la dual
        spDual.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 250, alumno.getTotal_dual_hours(), 1));
        spFCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 250, alumno.getTotal_fct_hours(), 1));

    }

    @javafx.fxml.FXML
    public void eliminar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("¿Deseas borrar al alumno " + Session.getCurrentStudent().getFirst_name() + " " + Session.getCurrentStudent().getLast_name() + "?");
        var salida = alert.showAndWait().get();
        if (salida.getButtonData() == ButtonBar.ButtonData.OK_DONE){
            teacherDAOImp.deleteAlumno(Session.getCurrentStudent());
            atras();
        }
    }

    @javafx.fxml.FXML
    public void editar() {
        Student student = Session.getCurrentStudent();

        student.setFirst_name(txtNombre.getText());
        student.setLast_name(txtApellido.getText());
        student.setDni(txtDNI.getText());
        student.setDate_of_birth(dateFecha.getValue());
        student.setEmail(txtEmail.getText());
        student.setContact_phone(txtTelef.getText());
        student.setTotal_fct_hours(spFCT.getValue());
        student.setTotal_dual_hours(spDual.getValue());
        student.setCompany( teacherDAOImp.nombreCompañia((String) comboEmpresa.getValue()) );
        student.setObservations(txtObservacion.getText());

        teacherDAOImp.updateAlumno(student);
        atras();
    }

    @javafx.fxml.FXML
    public void atras() {
        Main.loadFXML("main-view-profesor.fxml", "Listado Alumnos del profesor " + Session.getCurrentTeacher().getFirst_name() );
    }

    public void logout(ActionEvent actionEvent){
        Session.setCurrentTeacher(null);
        Main.loadLogin("login-view.fxml");
    }
}
