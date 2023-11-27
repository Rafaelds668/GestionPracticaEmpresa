package com.example.gestionpracticasempresahibernate.controllers;

import com.example.gestionpracticasempresahibernate.Main;
import com.example.gestionpracticasempresahibernate.domain.Session;
import com.example.gestionpracticasempresahibernate.domain.company.Company;
import com.example.gestionpracticasempresahibernate.domain.company.CompanyDAOImp;
import com.example.gestionpracticasempresahibernate.domain.student.Student;
import com.example.gestionpracticasempresahibernate.domain.teacher.Teacher;
import com.example.gestionpracticasempresahibernate.domain.teacher.TeacherDAOImp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewProfesorController implements Initializable {

    TeacherDAOImp teacherDAOImp;
    CompanyDAOImp companyDAOImp;

    @javafx.fxml.FXML
    private Label lblTitulo;
    @javafx.fxml.FXML
    private TextField txtNombre;
    @javafx.fxml.FXML
    private TextField txtApellidos;
    @javafx.fxml.FXML
    private PasswordField passContrasenya;
    @javafx.fxml.FXML
    private TextField txtDNI;
    @javafx.fxml.FXML
    private TextField txtEmail;
    @javafx.fxml.FXML
    private DatePicker dataFecha;
    @javafx.fxml.FXML
    private TextField txtTelef;
    @javafx.fxml.FXML
    private ComboBox comboEmpresa;
    @javafx.fxml.FXML
    private TextArea txtObservaciones;
    @javafx.fxml.FXML
    private TableView <Student> tvAlumnos;
    @javafx.fxml.FXML
    private TableColumn <Student, String> cNombre;
    @javafx.fxml.FXML
    private TableColumn <Student, String> cApellidos;
    @javafx.fxml.FXML
    private TableColumn<Student, String> cDNI;
    @javafx.fxml.FXML
    private TableColumn <Student, String> cFecha;
    @javafx.fxml.FXML
    private TableColumn <Student, String> cEmail;
    @javafx.fxml.FXML
    private TableColumn <Student, String> cEmpresa;
    @javafx.fxml.FXML
    private TableColumn <Student, String> cDual;
    @javafx.fxml.FXML
    private TableColumn <Student, String> cFCT;
    @javafx.fxml.FXML
    private TableColumn <Student, String> cObservaciones;
    @javafx.fxml.FXML
    private Button btnAñadir;
    @javafx.fxml.FXML
    private Button btnVerAlumno;
    @javafx.fxml.FXML
    private Button btnVerEmpresas;


    public void logout(ActionEvent actionEvent){
        Session.setCurrentTeacher(null);
        Main.loadLogin("login-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teacherDAOImp = new TeacherDAOImp();
        companyDAOImp = new CompanyDAOImp();

        cargarTabla();

        List<String> nombreCompañias = new ArrayList<>();
        companyDAOImp.getAll().forEach(s-> nombreCompañias.add(s.getCompany_name()));
        comboEmpresa.getItems().addAll(nombreCompañias);
        comboEmpresa.setValue(comboEmpresa.getItems().getFirst());

        tvAlumnos.getSelectionModel().selectedItemProperty().addListener((observableValue, producto, t1) -> {
            Student student = teacherDAOImp.alumnoPorId(t1.getStudent_id());
            Session.setCurrentStudent(student);
        });

        lblTitulo.setText("Hola " + Session.getCurrentTeacher().getFirst_name());

    }

    private void cargarTabla() {
        Teacher teacher = Session.getCurrentTeacher();
        TeacherDAOImp teacherDAOImp1 = new TeacherDAOImp();
        List<Student> alumnosDeProfesor = teacherDAOImp.alumnoDeProfesor(teacher);

        cNombre.setCellValueFactory( (fila) -> {
            String nombre = fila.getValue().getFirst_name();
            return  new SimpleStringProperty(nombre);
        });
        cApellidos.setCellValueFactory( ( fila ) -> {
            String apellido = fila.getValue( ).getLast_name();
            return new SimpleStringProperty( apellido );
        } );
        cDNI.setCellValueFactory( ( fila ) -> {
            String dni = fila.getValue( ).getDni();
            return new SimpleStringProperty( dni );
        } );
        cFecha.setCellValueFactory( ( fila ) -> {
            String fechaNac = String.valueOf(fila.getValue( ).getDate_of_birth());
            return new SimpleStringProperty( fechaNac );
        } );
        cEmail.setCellValueFactory( ( fila ) -> {
            String email = fila.getValue( ).getEmail();
            return new SimpleStringProperty( email );
        } );
        cEmpresa.setCellValueFactory( ( fila ) -> {
            Company company = fila.getValue( ).getCompany();
            if (company != null) return new SimpleStringProperty( company.getCompany_name( ) );
            else return new SimpleStringProperty("");
        } );
        cDual.setCellValueFactory( ( fila ) -> {
            Integer dual = fila.getValue( ).getTotal_dual_hours();
            if (dual != null) return new SimpleStringProperty( dual.toString() );
            else return new SimpleStringProperty("");
        } );
        cFCT.setCellValueFactory( ( fila ) -> {
            Integer fct = fila.getValue( ).getTotal_fct_hours();
            if (fct != null) return new SimpleStringProperty( fct.toString() );
            else return new SimpleStringProperty("");
        } );
        cObservaciones.setCellValueFactory( ( fila ) -> {
            String observations = fila.getValue( ).getObservations();
            return new SimpleStringProperty( observations );
        } );

        ObservableList<Student> observableList = FXCollections.observableArrayList();
        observableList.addAll(alumnosDeProfesor);
        tvAlumnos.setItems(observableList);
    }

    @javafx.fxml.FXML
    public void insertStudent(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void detallesAlumno(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void empresas(ActionEvent actionEvent) {
    }


}
