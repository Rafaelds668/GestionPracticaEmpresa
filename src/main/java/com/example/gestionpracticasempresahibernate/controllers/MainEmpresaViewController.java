package com.example.gestionpracticasempresahibernate.controllers;

import com.example.gestionpracticasempresahibernate.Main;
import com.example.gestionpracticasempresahibernate.domain.Session;
import com.example.gestionpracticasempresahibernate.domain.company.Company;
import com.example.gestionpracticasempresahibernate.domain.company.CompanyDAOImp;
import com.example.gestionpracticasempresahibernate.domain.student.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainEmpresaViewController implements Initializable {
    @javafx.fxml.FXML
    private TextField txtNombreEmpresa;
    @javafx.fxml.FXML
    private TextField txtEncargado;
    @javafx.fxml.FXML
    private TextField txtEmail;
    @javafx.fxml.FXML
    private TextField txtTelef;
    @javafx.fxml.FXML
    private TextArea txtIncidentes;
    @javafx.fxml.FXML
    private Button btnAñadir;
    @javafx.fxml.FXML
    private TableView<Company> tvEmpresas;
    @javafx.fxml.FXML
    private TableColumn<Company, String> cNombre;
    @javafx.fxml.FXML
    private TableColumn<Company, String> cEncargado;
    @javafx.fxml.FXML
    private TableColumn<Company, String> cEmail;
    @javafx.fxml.FXML
    private TableColumn<Company, String> cTelefono;
    @javafx.fxml.FXML
    private TableColumn<Company, String> cIncidentes;
    @javafx.fxml.FXML
    private Button btnVerEmpresa;
    @javafx.fxml.FXML
    private MenuItem menuAlumnos;
    @javafx.fxml.FXML
    private Label lblTitulo;
    @javafx.fxml.FXML
    private MenuItem menuCerrarSesion;

    private ObservableList<Company> observableList;
    private CompanyDAOImp companyDAOImp = new CompanyDAOImp();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblTitulo.setText("Hola, " + Session.getCurrentTeacher().getFirst_name());
        observableList = FXCollections.observableArrayList();
        cargarTabla();
    }

    private void cargarTabla() {

        cNombre.setCellValueFactory( (fila) -> {
            String nombre = fila.getValue().getCompany_name();
            return  new SimpleStringProperty(nombre);
        });
        cEncargado.setCellValueFactory( (fila) -> {
            String encargado = fila.getValue().getCompany_contact();
            return  new SimpleStringProperty(encargado);
        });
        cEmail.setCellValueFactory( (fila) -> {
            String email = fila.getValue().getEmail();
            return  new SimpleStringProperty(email);
        });
        cTelefono.setCellValueFactory( (fila) -> {
            String telefono = fila.getValue().getPhone_number();
            return  new SimpleStringProperty(telefono);
        });
        cIncidentes.setCellValueFactory( (fila) -> {
            String incidente = fila.getValue().getIncidents();
            return  new SimpleStringProperty(incidente);
        });
    }

    @javafx.fxml.FXML
    public void insertCompany(ActionEvent actionEvent) {
        if(txtNombreEmpresa.getText().length()<2){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("El nombre de la empresa debe tener minimo 2 caracteres");
            alert.show();
        } else if(txtEncargado.getText().length()<2){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("El encargado debe tener minimo 2 caracteres");
            alert.show();
        } else if(!comprobarEmail(txtEmail.getText())){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "Formato del Correo electronico incorrecto" );
            alert.show();
        } else if(!comprobarTelefono(txtTelef.getText())){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "Formato del numero de telefono incorrecto" );
            alert.show();
        } else {
            Company company = new Company();
            company.setCompany_name(txtNombreEmpresa.getText());
            company.setCompany_contact(txtEncargado.getText());
            company.setEmail(txtEmail.getText());
            company.setPhone_number(txtTelef.getText());
            company.setIncidents(txtIncidentes.getText());
        }
    }

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

    private boolean comprobarTelefono( String tel ) {
        boolean salida = true;
        try {
            Integer.parseInt(tel);
        } catch (NumberFormatException excepcion) {
            salida = false;
        }
        return salida;
    }

    @javafx.fxml.FXML
    public void detalleEmpresa(ActionEvent actionEvent) {
        Company selectedCompany = tvEmpresas.getSelectionModel().getSelectedItem();
        if (selectedCompany != null){
            if(Session.getCurrentStudent() != null) Main.loadFXML("editar-empresa-view.fxml", "Detalles de la empresa " + Session.getCurrentCompany().getCompany_name());
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Por favor, selecciona una empresa para modificarlo");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void alumnos(ActionEvent actionEvent) {
        Main.loadFXML("main-view-profesor.fxml", "Listado de empresas" );
    }


    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        Session.setCurrentTeacher(null);
        Main.loadLogin("login-view.fxml");
    }
}
