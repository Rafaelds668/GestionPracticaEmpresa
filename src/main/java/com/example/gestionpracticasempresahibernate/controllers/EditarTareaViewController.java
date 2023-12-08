package com.example.gestionpracticasempresahibernate.controllers;

import com.example.gestionpracticasempresahibernate.Main;
import com.example.gestionpracticasempresahibernate.domain.Session;
import com.example.gestionpracticasempresahibernate.domain.activity.Activity;
import com.example.gestionpracticasempresahibernate.domain.activity.ActivityDAOImp;
import com.example.gestionpracticasempresahibernate.domain.activity.PracticeType;
import com.example.gestionpracticasempresahibernate.domain.student.Student;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditarTareaViewController implements Initializable {
    @javafx.fxml.FXML
    private TextField txtActividad;
    @javafx.fxml.FXML
    private TextField txtObservacion;
    @javafx.fxml.FXML
    private DatePicker dateFecha;
    @javafx.fxml.FXML
    private Spinner<Integer> spHoras;
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

    ActivityDAOImp activityDAOImp;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activityDAOImp = new ActivityDAOImp();
        Activity activity = Session.getCurrentActivity();
        Student student = Session.getCurrentStudent();

        txtActividad.setText(activity.getActivity_description());
        txtObservacion.setText(activity.getObservations());
        dateFecha.setValue(activity.getActivity_date());
        if (activity.getPractice_type() == PracticeType.DUAL){
            spHoras.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, student.getTotal_dual_hours(), activity.getTotal_hours(), 1));
        }else {
            spHoras.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, student.getTotal_fct_hours(), activity.getTotal_hours(), 1));
        }

        //TODO Aun hay que terminar: radioButtons y hacer el editar: valor de los spinner y radioButtons
    }

    @javafx.fxml.FXML
    public void delete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("¿Deseas borrar la tarea " + Session.getCurrentActivity().getActivity_description() + "?");
        var salida = alert.showAndWait().get();
        if (salida.getButtonData() == ButtonBar.ButtonData.OK_DONE){
            activityDAOImp.delete(Session.getCurrentActivity());
            Main.loadFXML("main-view-alumno.fxml", "Tareas del alumno " + Session.getCurrentStudent().getFirst_name());
        }
    }

    @javafx.fxml.FXML
    public void editar(ActionEvent actionEvent) {
        Activity activity = Session.getCurrentActivity();

        activity.setActivity_description(txtActividad.getText());
        activity.setObservations(txtObservacion.getText());
        activity.setActivity_date(dateFecha.getValue());
    }

    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
        Main.loadFXML("main-view-alumno.fxml", "Tareas del alumno " + Session.getCurrentStudent().getFirst_name());
    }

    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        Session.setCurrentTeacher(null);
        Main.loadLogin("login-view.fxml");
    }
}
