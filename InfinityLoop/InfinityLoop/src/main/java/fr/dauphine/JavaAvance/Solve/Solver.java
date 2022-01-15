package main.java.fr.dauphine.JavaAvance.Solve;
import main.java.fr.dauphine.JavaAvance.Components.*;
import main.java.fr.dauphine.JavaAvance.GUI.GameControler;

import java.util.ArrayList;

public class Solver {
	public static Grid solverGraphic(Grid grid, GameControler gameControler){

		// On fixe toute les pièces qui sont au bord du plateau et qui ont qu'une seul orientation possible
		for (int i = 0; i < grid.getHeight(); i++){
			for (int j = 0; j < grid.getWidth(); j++){
				if (grid.isCorner(i, j) && grid.getPiece(i, j).getType() == PieceType.LTYPE){
					if (i == 0 && j == 0){
						while (grid.getPiece(i, j).getOrientation() != Orientation.EAST){
							if(gameControler.turnPiece(i,j)) {
								grid.getPiece(i, j).turn();
							}
						}
						grid.getPiece(i, j).setFixed(true);
					}
					else if(i == 0 && j == grid.getWidth() - 1){
						while (grid.getPiece(i, j).getOrientation() != Orientation.SOUTH){
							if(gameControler.turnPiece(i,j)) {
								grid.getPiece(i, j).turn();
							}
						}
						grid.getPiece(i, j).setFixed(true);
					}
					else if (i == grid.getHeight() - 1 && j == 0){
						while (grid.getPiece(i, j).getOrientation() != Orientation.NORTH){
							if(gameControler.turnPiece(i,j)) {
								grid.getPiece(i, j).turn();
							}
						}
						grid.getPiece(i, j).setFixed(true);
					}
					else if (i == grid.getHeight() - 1 && j == grid.getWidth() - 1){
						while (grid.getPiece(i, j).getOrientation() != Orientation.WEST){
							if(gameControler.turnPiece(i,j)) {
								grid.getPiece(i, j).turn();
							}
						}
						grid.getPiece(i, j).setFixed(true);
					}
				}
				if (grid.isBorderColumn(i, j) && grid.getPiece(i, j).getType() == PieceType.BAR){
					while (grid.getPiece(i, j).getOrientation() != Orientation.NORTH && grid.getPiece(i, j).getOrientation() != Orientation.SOUTH){
						if(gameControler.turnPiece(i,j)){
							grid.getPiece(i, j).turn();
						}
					}
					grid.getPiece(i, j).setFixed(true);
				}
				else if (grid.isBorderLine(i, j) && grid.getPiece(i, j).getType() == PieceType.BAR) {
					while (grid.getPiece(i, j).getOrientation() != Orientation.EAST && grid.getPiece(i, j).getOrientation() != Orientation.WEST){
						if(gameControler.turnPiece(i,j)){
							grid.getPiece(i, j).turn();
						}
					}
					grid.getPiece(i, j).setFixed(true);
				}
				else if (i == 0 && grid.getPiece(i, j).getType() == PieceType.TTYPE){
					while (grid.getPiece(i, j).getOrientation() != Orientation.SOUTH){
						if(gameControler.turnPiece(i,j)){
							grid.getPiece(i, j).turn();
						}
					}
					grid.getPiece(i, j).setFixed(true);
				}
				else if (i == grid.getHeight() - 1 && grid.getPiece(i, j).getType() == PieceType.TTYPE){
					while (grid.getPiece(i, j).getOrientation() != Orientation.NORTH){
						gameControler.turnPiece(i,j);
						grid.getPiece(i, j).turn();
					}
					grid.getPiece(i, j).setFixed(true);
				}
				else if (j == grid.getWidth() - 1 && grid.getPiece(i, j).getType() == PieceType.TTYPE){
					while (grid.getPiece(i, j).getOrientation() != Orientation.WEST){
						if(gameControler.turnPiece(i,j)){
							grid.getPiece(i, j).turn();
						}
					}
					grid.getPiece(i, j).setFixed(true);
				}
				else if (j == 0 && grid.getPiece(i, j).getType() == PieceType.TTYPE){
					while (grid.getPiece(i, j).getOrientation() != Orientation.EAST){
						if(gameControler.turnPiece(i,j)){
							grid.getPiece(i, j).turn();
						}
					}
					grid.getPiece(i, j).setFixed(true);
				}
				// On fixe tout les void et les fourconn qui n'ont pas de sens a être tourné
				else if (grid.getPiece(i, j).getType() == PieceType.VOID || grid.getPiece(i, j).getType() == PieceType.FOURCONN){
					grid.getPiece(i, j).setFixed(true);
				}
			}
		}
		boolean change = true;
		while (change){
			change = false;
			// Avec les nouvelle piece fixé on regarde si on peut en fixer d'autre plus en profondeur et on repète tant qu'une nouvelle pièce est fixé.
			for (int i = 0; i < grid.getHeight(); i++){
				for (int j = 0; j < grid.getWidth(); j++) {
					if (!grid.getPiece(i, j).isFixed()) {
						boolean north = false;
						boolean east = false;
						boolean south = false;
						boolean west = false;

						boolean unnorth = false;
						boolean uneast = false;
						boolean unsouth = false;
						boolean unwest = false;
						Piece piece = grid.getPiece(i, j);
						north = (i != 0 && (grid.getPiece(i - 1,j).isFixed() && grid.getPiece(i - 1,j).hasBottomConnector()));
						east = (j < grid.getWidth() - 1 && (grid.getPiece(i,j + 1).isFixed() && grid.getPiece(i,j + 1).hasLeftConnector()));
						south = (i < grid.getHeight() - 1 && (grid.getPiece(i + 1,j).isFixed() && grid.getPiece(i + 1,j).hasTopConnector()));
						west = (j != 0 && (grid.getPiece(i,j - 1).isFixed() && grid.getPiece(i,j - 1).hasRightConnector()));
						unnorth = (i == 0 || (grid.getPiece(i-1,j).isFixed() && !grid.getPiece(i-1,j).hasBottomConnector()));
						uneast = (j == grid.getWidth() - 1 || (grid.getPiece(i,j + 1).isFixed() && !grid.getPiece(i,j + 1).hasLeftConnector()));
						unsouth = (i == grid.getHeight() - 1 || (grid.getPiece(i + 1,j).isFixed() && !grid.getPiece(i + 1,j).hasTopConnector()));
						unwest = (j == 0 || (grid.getPiece(i,j - 1).isFixed() && !grid.getPiece(i,j - 1).hasRightConnector()));
						if (grid.getPiece(i, j).getType() == PieceType.ONECONN) {
							if (north || (uneast && unsouth && unwest)){
								while (piece.getOrientation() != Orientation.NORTH){
									gameControler.turnPiece(i,j);
									piece.turn();
								}
								piece.setFixed(true);
								change = true;
							}
							if (east || (unnorth && unsouth && unwest)){
								while (piece.getOrientation() != Orientation.EAST){
									gameControler.turnPiece(i,j);
									piece.turn();
								}
								piece.setFixed(true);
								change = true;
							}
							if (south || (unnorth && uneast && unwest)){
								while (piece.getOrientation() != Orientation.SOUTH){
									gameControler.turnPiece(i,j);
									piece.turn();
								}
								piece.setFixed(true);
								change = true;
							}
							if (west || (unnorth && uneast && unsouth)){
								while (piece.getOrientation() != Orientation.WEST){
									gameControler.turnPiece(i,j);
									piece.turn();
								}
								piece.setFixed(true);
								change = true;
							}
						}
						if (grid.getPiece(i, j).getType() == PieceType.LTYPE) {
							if ((north || unsouth) && (east || unwest)){
								while (piece.getOrientation() != Orientation.NORTH){
									gameControler.turnPiece(i,j);
									piece.turn();
								}
								piece.setFixed(true);
								change = true;
							}
							if ((east || unwest) && (south || unnorth)){
								while (piece.getOrientation() != Orientation.EAST){
									gameControler.turnPiece(i,j);
									piece.turn();
								}
								piece.setFixed(true);
								change = true;
							}
							if ((south || unnorth) && (west || uneast)){
								while (piece.getOrientation() != Orientation.SOUTH){
									gameControler.turnPiece(i,j);
									piece.turn();
								}
								piece.setFixed(true);
								change = true;
							}
							if ((west || uneast) && (north || unsouth)){
								while (piece.getOrientation() != Orientation.WEST){
									gameControler.turnPiece(i,j);
									piece.turn();
								}
								piece.setFixed(true);
								change = true;
							}
						}
						if (grid.getPiece(i, j).getType() == PieceType.BAR) {
							if (north || south || (uneast || unwest)){
								while (piece.getOrientation() != Orientation.NORTH && piece.getOrientation() != Orientation.SOUTH){
									gameControler.turnPiece(i,j);
									piece.turn();
								}
								piece.setFixed(true);
								change = true;
							}
							if (east || west || (unnorth || unsouth)){
								while (piece.getOrientation() != Orientation.EAST && piece.getOrientation() != Orientation.WEST){
									gameControler.turnPiece(i,j);
									piece.turn();
								}
								piece.setFixed(true);
								change = true;
							}
						}
						if (grid.getPiece(i, j).getType() == PieceType.TTYPE){
							if (unsouth || (north && east && west)){
								while (piece.getOrientation() != Orientation.NORTH){
									gameControler.turnPiece(i,j);
									piece.turn();
								}
								piece.setFixed(true);
								change = true;
							}
							if (unwest || (north && east && south)){
								while (piece.getOrientation() != Orientation.EAST){
									gameControler.turnPiece(i,j);
									piece.turn();
								}
								piece.setFixed(true);
								change = true;
							}
							if (unnorth || (east && south && west)){
								while (piece.getOrientation() != Orientation.SOUTH){
									gameControler.turnPiece(i,j);
									piece.turn();
								}
								piece.setFixed(true);
								change = true;
							}
							if (uneast || (north && south && west)){
								while (piece.getOrientation() != Orientation.WEST){
									gameControler.turnPiece(i,j);
									piece.turn();
								}
								piece.setFixed(true);
								change = true;
							}
						}
					}
				}
			}
		}
		ArrayList<Piece> unFixedPiece = new ArrayList<>();
		for (int i = 0; i < grid.getHeight(); i++){
			for (int j = 0; j < grid.getWidth(); j++) {
				if (!grid.getPiece(i, j).isFixed()){
					unFixedPiece.add(grid.getPiece(i, j));
				}
			}
		}
		if (unFixedPiece.size() == 0){
			return grid;
		}
		Grid solvedgrid = solverRecu2(gameControler, grid, unFixedPiece, 0, gameControler.open);
		return solvedgrid;
	}


	private static Grid solverRecu2(GameControler gameControler, Grid grid, ArrayList<Piece> unFixedPiece, int pieceID, boolean reverse) {
		//On parcoure toute les possibilité avec un arbre et a chaque noeud on regarde si notre chemin a une chance d'être résolu, si non on le clos est on passe au suivant, l'algorytme de parcours est de type Depht-First.
		//On l'implemente avec une méthode récursive.
		Piece piece = unFixedPiece.get(pieceID);
		int i = piece.getPosX();
		int j = piece.getPosY();
		if (reverse){
			i = piece.getPosY();
			j = piece.getPosX();
		}

		ArrayList<Orientation> oriList = new ArrayList<>();
		oriList.add(Orientation.NORTH);
		oriList.add(Orientation.EAST);
		oriList.add(Orientation.SOUTH);
		oriList.add(Orientation.WEST);

		if (piece.getType() == PieceType.ONECONN) {
			if (i == 0 || (grid.getPiece(i - 1, j).isFixed() && !grid.getPiece(i - 1, j).hasBottomConnector())) {
				oriList.remove(Orientation.NORTH);
			}
			if (j == grid.getWidth() - 1 || (grid.getPiece(i, j + 1).isFixed() && !grid.getPiece(i, j + 1).hasLeftConnector())) {
				oriList.remove(Orientation.EAST);
			}
			if (i == grid.getHeight() - 1 || (grid.getPiece(i + 1, j).isFixed() && !grid.getPiece(i + 1, j).hasTopConnector())) {
				oriList.remove(Orientation.SOUTH);
			}
			if (j == 0 || (grid.getPiece(i, j - 1).isFixed() && !grid.getPiece(i, j - 1).hasRightConnector())) {
				oriList.remove(Orientation.WEST);
			}
			if (i != 0 && (grid.getPiece(i - 1, j).isFixed() && grid.getPiece(i - 1, j).hasBottomConnector())) {
				oriList.remove(Orientation.EAST);
				oriList.remove(Orientation.SOUTH);
				oriList.remove(Orientation.WEST);
			}
			if (j < grid.getWidth() - 1 && (grid.getPiece(i, j + 1).isFixed() && grid.getPiece(i, j + 1).hasLeftConnector())) {
				oriList.remove(Orientation.NORTH);
				oriList.remove(Orientation.SOUTH);
				oriList.remove(Orientation.WEST);
			}
			if (i < grid.getHeight() - 1 && (grid.getPiece(i + 1, j).isFixed() && grid.getPiece(i + 1, j).hasTopConnector())) {
				oriList.remove(Orientation.NORTH);
				oriList.remove(Orientation.EAST);
				oriList.remove(Orientation.WEST);
			}
			if (j != 0 && (grid.getPiece(i, j - 1).isFixed() && grid.getPiece(i, j - 1).hasRightConnector())) {
				oriList.remove(Orientation.NORTH);
				oriList.remove(Orientation.SOUTH);
				oriList.remove(Orientation.EAST);
			}
		}
		if (piece.getType() == PieceType.LTYPE) {
			if ((i != 0 && (grid.getPiece(i - 1, j).isFixed() && grid.getPiece(i - 1, j).hasBottomConnector())) || (i == grid.getHeight() - 1 || (grid.getPiece(i + 1, j).isFixed() && !grid.getPiece(i + 1, j).hasTopConnector()))) {
				oriList.remove(Orientation.EAST);
				oriList.remove(Orientation.SOUTH);
			}
			if ((j < grid.getWidth() - 1 && (grid.getPiece(i, j + 1).isFixed() && grid.getPiece(i, j + 1).hasLeftConnector())) || (j == 0 || (grid.getPiece(i, j - 1).isFixed() && !grid.getPiece(i, j - 1).hasRightConnector()))) {
				oriList.remove(Orientation.SOUTH);
				oriList.remove(Orientation.WEST);
			}
			if ((i < grid.getHeight() - 1 && (grid.getPiece(i + 1, j).isFixed() && grid.getPiece(i + 1, j).hasTopConnector())) || (i == 0 || (grid.getPiece(i - 1, j).isFixed() && !grid.getPiece(i - 1, j).hasBottomConnector()))) {
				oriList.remove(Orientation.WEST);
				oriList.remove(Orientation.NORTH);
			}
			if ((j != 0 && (grid.getPiece(i, j - 1).isFixed() && grid.getPiece(i, j - 1).hasRightConnector())) || (j == grid.getWidth() - 1 || (grid.getPiece(i, j + 1).isFixed() && !grid.getPiece(i, j + 1).hasLeftConnector()))) {
				oriList.remove(Orientation.NORTH);
				oriList.remove(Orientation.EAST);
			}
		}
		if (piece.getType() == PieceType.BAR) {
			oriList.remove(Orientation.SOUTH);
			oriList.remove(Orientation.WEST);
			if ((i != 0 && (grid.getPiece(i - 1, j).isFixed() && grid.getPiece(i - 1, j).hasBottomConnector())) || (j == 0 || (grid.getPiece(i, j - 1).isFixed() && !grid.getPiece(i, j - 1).hasRightConnector()))) {
				oriList.remove(Orientation.EAST);
			}
			if ((j != 0 && (grid.getPiece(i, j - 1).isFixed() && grid.getPiece(i, j - 1).hasRightConnector())) || (i == 0 || (grid.getPiece(i - 1, j).isFixed() && !grid.getPiece(i - 1, j).hasBottomConnector()))) {
				oriList.remove(Orientation.NORTH);
			}
		}
		if (piece.getType() == PieceType.TTYPE) {
			if (i != 0 && (grid.getPiece(i - 1, j).isFixed() && grid.getPiece(i - 1, j).hasBottomConnector())) {
				oriList.remove(Orientation.SOUTH);
			}
			if (j < grid.getWidth() - 1 && (grid.getPiece(i, j + 1).isFixed() && grid.getPiece(i, j + 1).hasLeftConnector())) {
				oriList.remove(Orientation.WEST);
			}
			if (i < grid.getHeight() - 1 && (grid.getPiece(i + 1, j).isFixed() && grid.getPiece(i + 1, j).hasTopConnector())) {
				oriList.remove(Orientation.NORTH);
			}
			if (j != 0 && (grid.getPiece(i, j - 1).isFixed() && grid.getPiece(i, j - 1).hasRightConnector())) {
				oriList.remove(Orientation.EAST);
			}
			if (i == 0 || (grid.getPiece(i - 1, j).isFixed() && !grid.getPiece(i - 1, j).hasBottomConnector())) {
				oriList.remove(Orientation.NORTH);
				oriList.remove(Orientation.EAST);
				oriList.remove(Orientation.WEST);
			}
			if (j == grid.getWidth() - 1 || (grid.getPiece(i, j + 1).isFixed() && !grid.getPiece(i, j + 1).hasLeftConnector())) {
				oriList.remove(Orientation.NORTH);
				oriList.remove(Orientation.EAST);
				oriList.remove(Orientation.SOUTH);
			}
			if (i == grid.getHeight() - 1 || (grid.getPiece(i + 1, j).isFixed() && !grid.getPiece(i + 1, j).hasTopConnector())) {
				oriList.remove(Orientation.WEST);
				oriList.remove(Orientation.EAST);
				oriList.remove(Orientation.SOUTH);
			}
			if (j == 0 || (grid.getPiece(i, j - 1).isFixed() && !grid.getPiece(i, j - 1).hasRightConnector())) {
				oriList.remove(Orientation.NORTH);
				oriList.remove(Orientation.WEST);
				oriList.remove(Orientation.SOUTH);
			}
		}
		if(pieceID == unFixedPiece.size() - 1){
			for (int ori = 0; ori < oriList.size(); ori++) {
				while (piece.getOrientation() != oriList.get(ori)) {
					gameControler.turnPiece(i, j);
					piece.turn();
				}
				if (grid.isTotallyConnected(piece, reverse)){
					return grid;
				}
			}
			return null;
		}
		piece.setFixed(true);
		for (int ori = 0; ori < oriList.size(); ori++) {
			while (piece.getOrientation() != oriList.get(ori)) {
				gameControler.turnPiece(i, j);
				piece.turn();
			}
			Grid solution = solverRecu2(gameControler, grid, unFixedPiece, pieceID + 1, reverse);
			if (solution != null) {
				return solution;
			}
		}
		piece.setFixed(false);
		// On retourne nul si aucune combinaison n'est trouvé, comme l'algorythme est complet ça signifie qu'aucune solution existe
		return null;
	}
}
