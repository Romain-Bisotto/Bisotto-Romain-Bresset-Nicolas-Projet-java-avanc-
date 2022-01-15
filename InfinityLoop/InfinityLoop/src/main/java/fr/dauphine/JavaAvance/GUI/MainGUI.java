package main.java.fr.dauphine.JavaAvance.GUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import main.java.fr.dauphine.JavaAvance.Components.Grid;

import java.io.IOException;

import static javafx.application.Application.launch;

public class MainGUI extends Application {
    private static Grid grid;

    private static  Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        try {
            this.window = window;
            FXMLLoader loader = new FXMLLoader(GUI.class.getResource("Home.fxml"));
            Pane page = (Pane) loader.load();
            Scene fenetre = new Scene(page,600,400);
            window.setScene(fenetre);
            window.setResizable(false);
            window.show();
            HomeControler homeControler = loader.<HomeControler>getController();
            homeControler.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setGrid(Grid inputgrid){
        grid = inputgrid;
    }

    public static Stage getWindow() {
        return window;
    }
}
