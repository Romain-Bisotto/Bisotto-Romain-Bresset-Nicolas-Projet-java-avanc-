package main.java.fr.dauphine.JavaAvance.Solve;

import main.java.fr.dauphine.JavaAvance.Components.Orientation;
import main.java.fr.dauphine.JavaAvance.Components.Piece;
import main.java.fr.dauphine.JavaAvance.Components.PieceType;
import main.java.fr.dauphine.JavaAvance.GUI.DisplayUnicode;
import main.java.fr.dauphine.JavaAvance.Components.Grid;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Generate a solution, number of connexe composant is not finished
 *
 */

public class Generator {

	private static Grid filledGrid;

	/**
	 *            file name
	 * @throws IOException
	 *             - if an I/O error occurs.
	 * @return a File that contains a grid filled with pieces (a level)
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static Grid generateLevel(Grid inputGrid){
		return generateLevel(inputGrid, 0.5);
	}
	public static Grid generateLevel(Grid inputGrid, double pc) {
		int x = 0;
		int y = 0;
		ArrayList<Boolean> combinaison = new ArrayList<>(Arrays.asList(false,false,false,false));
		//On défini 4 variables booléennes pour les orientation, le haut et la gauche sont défini par les voisins, la droite et le bas sont défini au hasard selon un paramêtre
		for (Piece[] ligne : inputGrid.getAllPieces()) {
			for (Piece piece : ligne) {
				if (x == 0){
					combinaison.set(0,false);
				}
				else if(inputGrid.getPiece(x-1,y).hasBottomConnector()){
					combinaison.set(0,true);
				}
				else{
					combinaison.set(0,false);
				}
				if (y == inputGrid.getWidth()-1){
					combinaison.set(1,false);
				}
				else {
					combinaison.set(1,Math.random()<pc);
				}
				if(x == inputGrid.getHeight()-1){
					combinaison.set(2,false);
				}
				else {
					combinaison.set(2,Math.random()<pc);
				}
				if (y == 0){
					combinaison.set(3,false);
				}
				else if(inputGrid.getPiece(x,y-1).hasRightConnector()){
					combinaison.set(3,true);
				}
				else{
					combinaison.set(3,false);
				}

				inputGrid.setPiece(x,y,new Piece(y, x, Piece.FINDYOURPIECE.get(combinaison).getKey(),Piece.FINDYOURPIECE.get(combinaison).getValue()));
				combinaison = new ArrayList<>(Arrays.asList(false,false,false,false));
				y++;
			}
			y = 0;
			x++;
		}
		for (Piece[] ligne : inputGrid.getAllPieces()) {
			for (Piece piece : ligne) {
				piece.setOrientation((int)(Math.random()*4));
			}
		}
		return inputGrid;
	}
	public static int[] copyGrid(Grid filledGrid, Grid inputGrid, int i, int j) {
		Piece p;
		int hmax = inputGrid.getHeight();
		int wmax = inputGrid.getWidth();

		if (inputGrid.getHeight() != filledGrid.getHeight())
			hmax = filledGrid.getHeight() + i; // we must adjust hmax to have the height of the original grid
		if (inputGrid.getWidth() != filledGrid.getWidth())
			wmax = filledGrid.getWidth() + j;

		int tmpi = 0;// temporary variable to stock the last index
		int tmpj = 0;

		// DEBUG System.out.println("copyGrid : i =" + i + " & j = " + j);
		// DEBUG System.out.println("hmax = " + hmax + " - wmax = " + wmax);
		for (int x = i; x < hmax; x++) {
			for (int y = j; y < wmax; y++) {
				// DEBUG System.out.println("x = " + x + " - y = " + y);
				p = filledGrid.getPiece(x - i, y - j);
				// DEBUG System.out.println("x = " + x + " - y = " +
				// y);System.out.println(p);
				inputGrid.setPiece(x, y, new Piece(x, y, p.getType(), p.getOrientation()));
				// DEBUG System.out.println("x = " + x + " - y = " +
				// y);System.out.println(inputGrid.getPiece(x, y));
				tmpj = y;
			}
			tmpi = x;
		}
		//DEBUGSystem.out.println("tmpi =" + tmpi + " & tmpj = " + tmpj);
		return new int[] { tmpi, tmpj };
	}


	public static Grid generateLevelFromFiles(File file) {
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			StringBuffer sb = new StringBuffer();
			String line;
			ArrayList<ArrayList<String>> stringFile = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				ArrayList<String> ligne = new ArrayList<>();
				for (char element: line.toCharArray()) {
					ligne.add(Character.toString(element));
				}
				stringFile.add(ligne);
			}
			fr.close();
			Grid voidGrid = new Grid(stringFile.get(0).size(), stringFile.size());
			for (int i = 0; i < stringFile.size(); i++) {
				for (int j = 0; j < stringFile.get(i).size(); j++) {
					for (PieceType pieceType: PieceType.values()){
						for (Orientation ori: Orientation.values()){
						if (DisplayUnicode.getUnicodeOfPiece(pieceType, ori).equals(stringFile.get(i).get(j))){
							voidGrid.setPiece(i, j, new Piece(i, j, pieceType, ori));
						}
						}
					}
				}
			}
			return voidGrid;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Grid(0,0);
	}

}