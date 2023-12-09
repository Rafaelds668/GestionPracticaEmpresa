package com.example.gestionpracticasempresahibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal que inicia la aplicación JavaFX.
 */
public class Main extends Application {

    private static Stage myStage;

    /**
     * Método principal que inicia la aplicación.
     *
     * @param stage El Stage principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        myStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Gestor de CESUR");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método para cargar la vista de inicio de sesión.
     *
     * @param ruta La ruta del archivo FXML de la vista de inicio de sesión.
     */
    public static void loadLogin(String ruta){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(ruta));
            Scene scene = new Scene(fxmlLoader.load(), 700, 500);
            myStage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para cargar un archivo FXML en la ventana principal.
     *
     * @param fxml    El archivo FXML que se va a cargar.
     * @param titulo  El título de la ventana.
     */
    public static void loadFXML(String fxml, String titulo){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            myStage.setTitle(titulo);
            myStage.setResizable(false);
            myStage.setScene(scene);
            myStage.show();
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo " + fxml);
            throw new RuntimeException(e);
        }
    }

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Los argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {launch();}
}
