package com.example.gestionpracticasempresahibernate.controllers;

import com.example.gestionpracticasempresahibernate.Main;
import com.example.gestionpracticasempresahibernate.domain.Session;
import com.example.gestionpracticasempresahibernate.domain.activity.Activity;
import com.example.gestionpracticasempresahibernate.domain.activity.ActivityDAOImp;
import com.example.gestionpracticasempresahibernate.domain.activity.PracticeType;
import com.example.gestionpracticasempresahibernate.domain.company.Company;
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
import javafx.scene.input.MouseButton;

import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewAlumnoController implements Initializable {

    @javafx.fxml.FXML
    private TextField txtActividad;
    @javafx.fxml.FXML
    private TextField txtObservacion;
    @javafx.fxml.FXML
    private DatePicker dateFecha;
    @javafx.fxml.FXML
    private Spinner <Integer> spTotal;
    @javafx.fxml.FXML
   private ComboBox <PracticeType> comboPractica;
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
    @javafx.fxml.FXML
    private Label lblTitulo;

    private StudentDAOImp studentDAOImp = new StudentDAOImp();

   private ActivityDAOImp activityDAOImp = new ActivityDAOImp();

   private  ObservableList<Activity> activityObservableList;

    @javafx.fxml.FXML
    private Button btnEditarTarea;


    public MainViewAlumnoController(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblTitulo.setText("Hola, " + Session.getCurrentStudent().getFirst_name());
        infoHorasDual.setText("Horas realizadas dual: " + Session.getCurrentStudent().getTotal_dual_hours()+" Horas restantes dual: " + (100 -Session.getCurrentStudent().getTotal_dual_hours()));
        infoHorasFCT.setText("Horas realizadas dual: " + Session.getCurrentStudent().getTotal_fct_hours()+" Horas restantes FCT: " + (50 -Session.getCurrentStudent().getTotal_fct_hours()));

        List<Activity> activityList = activityDAOImp.getAll();


        cActividad.setCellValueFactory((fila) ->{
            String actividad = String.valueOf(fila.getValue().getActivity_description());
            return new SimpleStringProperty(actividad);
        });
        cObservacion.setCellValueFactory((fila)->{
            String observacion = String.valueOf(fila.getValue().getObservations());
            return new SimpleStringProperty(observacion);
        });
        cFecha.setCellValueFactory((fila)->{
            LocalDate fecha = fila.getValue().getActivity_date();
            // Definir el formato deseado para la fecha (día/mes/año)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            // Formatear la fecha al nuevo formato
            String fechaFormateada = fecha.format(formatter);
            return new SimpleStringProperty(fechaFormateada);
        });
        cHora.setCellValueFactory((fila)->{
            String horasTotales = String.valueOf(fila.getValue().getTotal_hours());
            return new SimpleStringProperty(horasTotales);
        });
        cPractica.setCellValueFactory((fila)->{
            String tipoPractica = String.valueOf(fila.getValue().getPractice_type());
            return new SimpleStringProperty(tipoPractica);
        });


        //Obtengo el valor de la fecha actual
       dateFecha.getValue();

       //creo una lista para contener los Activity
        activityObservableList = FXCollections.observableArrayList();

        //Le paso al observable toda la instancia del ActivityDAOImp
        activityObservableList.setAll(activityDAOImp.getAll());

        //Obtengo los valores enumerados Practyce
        ObservableList<PracticeType> practiceTypeObservableList = FXCollections.observableArrayList();
        practiceTypeObservableList.setAll(PracticeType.DUAL, PracticeType.FCT);

        //configuro el combobox
        comboPractica.setItems(practiceTypeObservableList);
        comboPractica.getSelectionModel().selectFirst();

        //Configuro el Spinner
        spTotal.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0, 1));

        // Establezco la lista observable como el conjunto de tareas que se mostrarán en la tabla
        tvActividad.setItems(activityObservableList);

        // Cuando se selecciona una fila, verifica que el nuevo elemento (t1) no sea nulo y, en ese caso, establece la atarea pulsada en la clase Sesion con la actividad asociada a la fila seleccionada
        tvActividad.getSelectionModel().selectedItemProperty().addListener((observableValue, tarea, t1) -> {
            if (t1 != null) Session.setCurrentActivity(t1);
        });

        //Ponemos un mouseClick para que al pulsar dos veces nos vaya a otra pagina
        tvActividad.setOnMouseClicked(event ->{
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                Activity selectedPedido = tvActividad.getSelectionModel().getSelectedItem();
                if(selectedPedido != null){
                    Session.setCurrentActivity(selectedPedido);
                    Main.loadFXML("editar-tarea-view.fxml", "Editar Tarea");
                }
            }
        });
        btnAñadir.setOnAction(this::onAñadirButtonClick);

    }

    public void onAñadirButtonClick(ActionEvent actionEvent) {
        // Obtener datos de los controles de la interfaz de usuario
        String actividad = txtActividad.getText();
        String observacion = txtObservacion.getText();
        LocalDate fecha = dateFecha.getValue();
        int totalHoras = spTotal.getValue();
        PracticeType practiceType = comboPractica.getValue();

        // Validar que los campos obligatorios no estén vacíos
        if (actividad.isEmpty() || fecha == null || practiceType == null) {
            mostrarAlerta("Error", "Por favor, completa todos los campos obligatorios.");
            return;
        }

        // Crear una nueva instancia de Activity con los datos obtenidos
        Activity nuevaActividad = new Activity();
        nuevaActividad.setActivity_description(actividad);
        nuevaActividad.setObservations(observacion);
        nuevaActividad.setActivity_date(fecha);
        nuevaActividad.setTotal_hours(totalHoras);
        nuevaActividad.setPractice_type(practiceType);
        nuevaActividad.setStudent(Session.getCurrentStudent());

        // Guardar la nueva actividad en la base de datos
        activityDAOImp.save(nuevaActividad);

        // Actualizar la lista observable de actividades
        activityObservableList.add(nuevaActividad);

        // Limpiar los campos después de agregar la actividad
        limpiarCampos();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        txtActividad.clear();
        txtObservacion.clear();
        dateFecha.setValue(null);
        spTotal.getValueFactory().setValue(0);
        comboPractica.getSelectionModel().selectFirst();
    }


    public void logout(ActionEvent actionEvent){
        Session.setCurrentStudent(null);
        Main.loadLogin("login-view.fxml");
    }

    public void informacionEmpresa(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacion Adicional");
        alert.setHeaderText("Empresa");
        alert.setContentText("Nombre: " + Session.getCurrentStudent().getCompany().getCompany_name() +
                "\nEmail: " + Session.getCurrentStudent().getCompany().getEmail() +
                "\nResponsable: " + Session.getCurrentStudent().getCompany().getCompany_contact());
        alert.showAndWait();
    }

    public void informacionTutor (ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacion Adicional");
        alert.setHeaderText("Tutor/a");
        alert.setContentText("Nombre: " + Session.getCurrentStudent().getTutor().getFirst_name() +
                "\nApellidos: " + Session.getCurrentStudent().getTutor().getLast_name() +
                "\nEmail: " + Session.getCurrentStudent().getTutor().getEmail());
        alert.showAndWait();
    }






    @javafx.fxml.FXML
    public void editarTarea(ActionEvent actionEvent) {
        Activity selectedActivity = tvActividad.getSelectionModel().getSelectedItem();
        if (selectedActivity != null){
            if(Session.getCurrentStudent() != null) Main.loadFXML("editar-tarea-view.fxml", "Detalles de la tarea de " + Session.getCurrentStudent().getFirst_name());
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Por favor, selecciona una tarea para modificarla");
            alert.showAndWait();
        }
    }
}
