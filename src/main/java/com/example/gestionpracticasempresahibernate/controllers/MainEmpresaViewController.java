package com.example.gestionpracticasempresahibernate.controllers;

import com.example.gestionpracticasempresahibernate.Main;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

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
    private TableView tvAlumnos;
    @javafx.fxml.FXML
    private TableColumn cNombre;
    @javafx.fxml.FXML
    private TableColumn cEncargado;
    @javafx.fxml.FXML
    private TableColumn cEmail;
    @javafx.fxml.FXML
    private TableColumn cTelefono;
    @javafx.fxml.FXML
    private TableColumn cIncidentes;
    @javafx.fxml.FXML
    private Button btnVerEmpresa;
    @javafx.fxml.FXML
    private MenuItem menuAlumnos;
    @javafx.fxml.FXML
    private Label lblTitulo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void insertCompany(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void detalleEmpresa(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void alumnos(ActionEvent actionEvent) {
        Main.loadFXML("main-view-profesor.fxml", "Listado de empresas" );
    }


}
