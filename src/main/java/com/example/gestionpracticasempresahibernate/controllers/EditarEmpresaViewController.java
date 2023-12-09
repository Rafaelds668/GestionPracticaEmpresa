package com.example.gestionpracticasempresahibernate.controllers;

import com.example.gestionpracticasempresahibernate.Main;
import com.example.gestionpracticasempresahibernate.domain.Session;
import com.example.gestionpracticasempresahibernate.domain.company.Company;
import com.example.gestionpracticasempresahibernate.domain.company.CompanyDAOImp;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de edición de empresas.
 */
public class EditarEmpresaViewController implements Initializable {
    @javafx.fxml.FXML
    private MenuItem menuLogout;
    @javafx.fxml.FXML
    private TextField txtNombre;
    @javafx.fxml.FXML
    private TextField txtEmail;
    @javafx.fxml.FXML
    private TextField txtTelefono;
    @javafx.fxml.FXML
    private TextField txtEncargado;
    @javafx.fxml.FXML
    private TextField txtIncidentes;
    @javafx.fxml.FXML
    private Button btnEliminar;
    @javafx.fxml.FXML
    private Button btnEditar;
    @javafx.fxml.FXML
    private Button btnVolver;

    CompanyDAOImp companyDAOImp;


    /**
     * Inicializa la vista con los datos de la empresa seleccionada.
     *
     * @param url             Ubicación relativa del recurso
     * @param resourceBundle  Recurso localizable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        companyDAOImp = new CompanyDAOImp();
        Company company = Session.getCurrentCompany();
        txtNombre.setText(company.getCompany_name());
        txtEmail.setText(company.getEmail());
        txtEncargado.setText(company.getCompany_contact());
        txtTelefono.setText(company.getPhone_number());
        txtIncidentes.setText(company.getIncidents());
    }


    @javafx.fxml.FXML
    public void eliminar(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("¿Deseas borrar a la empresa " + Session.getCurrentCompany().getCompany_name() + "?");
        var salida = alert.showAndWait().get();
        if (salida.getButtonData() == ButtonBar.ButtonData.OK_DONE){
            companyDAOImp.delete(Session.getCurrentCompany());
            Main.loadFXML("main-empresa-view.fxml", "Listado de empresas");
        }
    }

    @javafx.fxml.FXML
    public void editar(ActionEvent actionEvent) {
        Company company = Session.getCurrentCompany();

        company.setCompany_name(txtNombre.getText());
        company.setCompany_contact(txtEncargado.getText());
        company.setEmail(txtEmail.getText());
        company.setPhone_number(txtTelefono.getText());
        company.setIncidents(txtIncidentes.getText());
        companyDAOImp.updateCompany(company);
        volver();

    }

    @javafx.fxml.FXML
    public void volver() {
        Main.loadFXML("main-empresa-view.fxml", "Listado de empresas");
    }

    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        Session.setCurrentTeacher(null);
        Main.loadLogin("login-view.fxml");
    }
}
