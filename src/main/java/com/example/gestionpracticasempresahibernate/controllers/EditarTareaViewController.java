package com.example.gestionpracticasempresahibernate.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class EditarTareaViewController implements Initializable {
    @javafx.fxml.FXML
    private TextField txtActividad;
    @javafx.fxml.FXML
    private TextField txtObservacion;
    @javafx.fxml.FXML
    private DatePicker dateFecha;
    @javafx.fxml.FXML
    private Spinner spHoras;
    @javafx.fxml.FXML
    private RadioButton rbFCT;
    @javafx.fxml.FXML
    private RadioButton rbDual;
    @javafx.fxml.FXML
    private Button btnEliminar;
    @javafx.fxml.FXML
    private Button btnEditar;
    @javafx.fxml.FXML
    private Button btnVolver;
    @javafx.fxml.FXML
    private MenuItem menuLogout;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void delete(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void editar(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
    }
}
