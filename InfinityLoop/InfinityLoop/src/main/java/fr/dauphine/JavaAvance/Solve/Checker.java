package main.java.fr.dauphine.JavaAvance.Solve;

import main.java.fr.dauphine.JavaAvance.Components.Grid;
import main.java.fr.dauphine.JavaAvance.Components.Piece;
import main.java.fr.dauphine.JavaAvance.GUI.GameControler;

public class Checker {

    public static boolean isSolve(Grid grid, GameControler gameControler){
        for (Piece[] line: grid.getAllPieces()) {
            for (Piece piece: line){
                if(!grid.isTotallyConnected(piece, gameControler.open)){
                    return false;
                }
            }
        }
        return true;
    }
}