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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controlador para la vista principal de un profesor en la aplicación de gestión de prácticas empresariales.
 * Permite la gestión de estudiantes, visualización de datos y navegación entre vistas en una aplicación JavaFX.
 */
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
    private MenuItem menuEmpresas;

    /**
     * Inicializa la vista del controlador y carga los datos iniciales.
     *
     * @param url             URL de la ubicación para resolver rutas relativas de recursos.
     * @param resourceBundle  Los recursos localizados.
     */
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

        lblTitulo.setText("Hola, " + Session.getCurrentTeacher().getFirst_name());

    }

    /**
     * Carga los datos de los estudiantes en la tabla de la interfaz gráfica.
     */
    private void cargarTabla() {
        Teacher teacher = Session.getCurrentTeacher();
        //TeacherDAOImp teacherDAOImp1 = new TeacherDAOImp();
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
        cFecha.setCellValueFactory(fila -> {
            LocalDate fechaNac = fila.getValue().getDate_of_birth();
            // Definir el formato deseado para la fecha (día/mes/año)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            // Formatear la fecha al nuevo formato
            String fechaFormateada = fechaNac.format(formatter);
            return new SimpleStringProperty(fechaFormateada);
        });
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
        if(txtNombre.getText().length() < 3){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("El nombre debe tener minimo 3 caracteres");
            alert.show();
        }
        else if(txtApellidos.getText().length() < 3){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "El apellido debe tener minimo 3 caracteres" );
            alert.show();
        }
        else if(passContrasenya.getText().length() < 4){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "La contraseña debe tener minimo 4 caracteres" );
            alert.show();
        }
        else if(!comprobarDNI(txtDNI.getText())){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "Formato del DNI incorrecto" );
            alert.show();
        }
        else if(!comprobarEmail(txtEmail.getText())){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "Formato del Correo electronico incorrecto" );
            alert.show();
        }
        else if(dataFecha.getValue() == null){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "Debe rellenar la fecha de nacimiento" );
            alert.show();
        }
        else if(!comprobarTelefono(txtTelef.getText())){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "Formato del numero de telefono incorrecto" );
            alert.show();
        } else {
            Student student = new Student();
            student.setTutor(Session.getCurrentTeacher());
            student.setFirst_name(txtNombre.getText());
            student.setLast_name(txtApellidos.getText());
            student.setPassword(passContrasenya.getText());
            student.setDni(txtDNI.getText());
            student.setEmail(txtEmail.getText());
            student.setDate_of_birth(dataFecha.getValue());
            student.setContact_phone(txtTelef.getText());
            student.setObservations(txtObservaciones.getText());
            student.setCompany(teacherDAOImp.nombreCompañia((String) comboEmpresa.getValue()));
            student.setDiary_activities(new ArrayList<>());
            student.setTotal_fct_hours(0);
            student.setTotal_dual_hours(0);
            teacherDAOImp.addAlumno(student);
            cargarTabla();
        }

    }

    /**
     * Verifica si el formato del DNI es válido.
     *
     * @param dni El número de identificación a validar.
     * @return    true si el formato del DNI es válido, de lo contrario false.
     */
    private boolean comprobarDNI (String dni){
        boolean salida = true;
        if(dni.length() != 9 || !Character.isLetter(dni.charAt(8))) salida = false;
        else {
            try {
                Integer.parseInt(dni.substring(0,8));
            } catch (Exception e) {
                salida = false;
            }
        }
        return salida;
    }

    /**
     * Verifica si el formato del correo electrónico es válido.
     *
     * @param email El correo electrónico a validar.
     * @return      true si el formato del correo electrónico es válido, de lo contrario false.
     */
    private boolean comprobarEmail( String email ) {
        boolean salida;
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher( email);

        if (mather.find( )) {
            salida = true;
        } else {
            salida = false;
        }
        return salida;
    }

    /**
     * Verifica si el formato del número de teléfono es válido.
     *
     * @param tel El número de teléfono a validar.
     * @return    true si el formato del número de teléfono es válido, de lo contrario false.
     */
    private boolean comprobarTelefono( String tel ) {
        boolean salida = true;
        try {
            Integer.parseInt(tel);
        } catch (NumberFormatException excepcion) {
            salida = false;
        }
        return salida;
    }

    /**
     * RCarga los detalles del alumno.
     *
     * @param actionEvent Evento de acción asociado al menú "Detalles del alumno"
     */
    @javafx.fxml.FXML
    public void detallesAlumno(ActionEvent actionEvent) {
        Student selectedStudent = tvAlumnos.getSelectionModel().getSelectedItem();
        if (selectedStudent != null){
            if(Session.getCurrentStudent() != null) Main.loadFXML("editar-alumno-view.fxml", "Detalles del alumno " + Session.getCurrentStudent().getFirst_name());
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Por favor, selecciona un alumno para modificarlo");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void empresas(ActionEvent actionEvent) {
        Main.loadFXML("main-empresa-view.fxml", "Listado de empresas" );
    }

    /**
     * Realiza el cierre de sesión.
     *
     * @param actionEvent Evento de acción asociado al menú "Cerrar Sesión"
     */
    public void logout(ActionEvent actionEvent){
        Session.setCurrentTeacher(null);
        Main.loadLogin("login-view.fxml");
    }
}
