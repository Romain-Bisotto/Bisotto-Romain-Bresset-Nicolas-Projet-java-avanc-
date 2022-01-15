package main.java.fr.dauphine.JavaAvance.GUI;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import main.java.fr.dauphine.JavaAvance.Components.Grid;
import main.java.fr.dauphine.JavaAvance.Components.PieceType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.util.HashMap;

import static main.java.fr.dauphine.JavaAvance.Components.PieceType.*;


public class GameControler {

    public boolean open;

    @FXML
    private Button solveButton;

    @FXML
    private Button saveButton;

    @FXML
    private GridPane plateauGP;

    @FXML
    private Label labelVictoire;

    @FXML
    private Label labelImpossible;

    private Grid grid;

    private boolean canChange = true;

    private static final HashMap<PieceType, String> MAPIMAGE = new HashMap<>(){{
        put(VOID,".\\out\\ressource\\background.png");
        put(ONECONN, ".\\out\\ressource\\1.png");
        put(BAR, ".\\out\\ressource\\5.png");
        put(TTYPE, ".\\out\\ressource\\7.png");
        put(FOURCONN, ".\\out\\ressource\\11.png");
        put(LTYPE, ".\\out\\ressource\\12.png");
    }};

    @FXML
    public void initialize(Grid grid, Boolean open) throws FileNotFoundException {
        this.open = open;
        this.grid = grid;
        solveButton.setOnAction(actionEvent -> {
            Grid.solveGraphic(this, grid);
        });
        saveButton.setOnAction(actionEvent -> {
            saveGridOnFile();
        });
        for (int j =  0; j < this.grid.getHeight(); ++j) {
            for (int i = 0; i < this.grid.getWidth(); ++i) {
                Button star = new Button();
                ImageView imageView = new ImageView(getImageFromPieceType(this.grid.getPiece(j,i).getType()));
                star.setPadding(new Insets(0));
                double prefSize = Math.min(plateauGP.getHeight()/grid.getHeight(),plateauGP.getWidth()/grid.getWidth());
                plateauGP.setPrefHeight(prefSize*grid.getHeight());
                plateauGP.setPrefWidth(prefSize*grid.getWidth());
                star.setPrefSize(prefSize,prefSize);
                imageView.setFitHeight(prefSize);
                imageView.setFitWidth(prefSize);
                star.setGraphic(imageView);
                for (int r = grid.getPiece(j,i).getOrientation().ordinal(); r > 0; r--){
                    star.setRotate(star.getRotate() + 90);
                }
                star.setStyle("-fx-background-radius: 50; -fx-background-color: rgb(232,209,218)");
                star.setOnAction(actionEvent -> {
                    Node clickedNode = (Node) actionEvent.getSource();
                    int colIndex = GridPane.getColumnIndex(clickedNode);
                    int rowIndex = GridPane.getRowIndex(clickedNode);
                    if(canChange) {
                        grid.turnPiece(rowIndex, colIndex, grid ,this);
                        star.setDisable(true);
                        star.setOpacity(1);
                        RotateTransition rt = new RotateTransition(Duration.millis(250), star);
                        rt.setToAngle(star.getRotate() + 90);
                        rt.play();
                        rt.setOnFinished(actionEvent1 -> {
                            star.setDisable(false);
                        });
                    }
                });
                plateauGP.add(star,i,j);
            }
        }

    }

    public void victoire(){
        canChange = false;
        labelVictoire.setVisible(true);
    }

    public void impossible(){
        labelImpossible.setVisible(true);
    }

    private Image getImageFromPieceType(PieceType p) throws FileNotFoundException {
        return new Image(new FileInputStream(MAPIMAGE.get(p)));
    }

    public Boolean turnPiece(int i, int j){
        Node node = plateauGP.getChildren().get((i * grid.getWidth()) + j);
        node.setRotate(node.getRotate() + 90);
        return true;
    }

    private void saveGridOnFile(){
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(".\\out"));
        File file = fileChooser.showSaveDialog(MainGUI.getWindow());
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(grid.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}