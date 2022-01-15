package main.java.fr.dauphine.JavaAvance.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.fr.dauphine.JavaAvance.Components.Grid;
import main.java.fr.dauphine.JavaAvance.Solve.Generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class HomeControler {

    @FXML
    private Button openButton;

    @FXML
    private Button generateButton;

    @FXML
    private Spinner nbColumnSpinner;

    @FXML
    private Spinner nbRowSpinner;

    @FXML
    private Slider cpSlider;

    @FXML
    private Label cpLabel;

    @FXML
    public  void initialize(){
        SpinnerValueFactory.IntegerSpinnerValueFactory slt = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 50, 10, 1);
        SpinnerValueFactory.IntegerSpinnerValueFactory slt2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 50, 10, 1);
        nbColumnSpinner.setValueFactory(slt);
        nbRowSpinner.setValueFactory(slt2);
        int value;
        //cpSlider.valueProperty().addListener(new ChangeListener<Number>() {
            //public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            //    value = (int) cpSlider.getValue();
            //    cpLabel.setText(Integer.toString(value));
            //}
        //});
        generateButton.setOnAction(actionEvent -> {
            try {
                generate();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        openButton.setOnAction(actionEvent -> {
            try {
                open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void open() throws IOException{
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./out"));
        File file = fileChooser.showOpenDialog(MainGUI.getWindow());
        Grid grid = Generator.generateLevelFromFiles(file);
        FXMLLoader loader = new FXMLLoader(GUI.class.getResource("Game.fxml"));
        Stage window = MainGUI.getWindow();
        Pane page = (Pane) loader.load();
        Scene fenetre = new Scene(page,900,750);
        window.setScene(fenetre);
        window.centerOnScreen();
        window.setResizable(false);
        window.show();
        GameControler gameControler = loader.<GameControler>getController();
        gameControler.initialize(grid, true);
    }

    private void generate() throws IOException {
        double cp = cpSlider.getValue();
        int nbRow = (int)nbRowSpinner.getValue();
        int nbColumn = (int)nbColumnSpinner.getValue();
        Grid grid;
        grid = Generator.generateLevel(new Grid(nbColumn, nbRow), cp);

        FXMLLoader loader = new FXMLLoader(GUI.class.getResource("Game.fxml"));
        Stage window = MainGUI.getWindow();
        Pane page = (Pane) loader.load();
        Scene fenetre = new Scene(page,900,750);
        window.setScene(fenetre);
        window.centerOnScreen();
        window.setResizable(false);
        window.show();
        GameControler gameControler = loader.<GameControler>getController();
        gameControler.initialize(grid,false);
    }
}
