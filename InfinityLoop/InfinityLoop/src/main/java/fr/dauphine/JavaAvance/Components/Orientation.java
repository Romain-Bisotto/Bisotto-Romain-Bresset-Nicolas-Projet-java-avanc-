package main.java.fr.dauphine.JavaAvance.Components;
import java.util.HashMap;

/**
 * 
 * Orientation of the piece enum
 * 
 */
public enum Orientation {
	/* Implement all the possible orientations and 
	 *  required methods to rotate
	 */
	NORTH,EAST,SOUTH,WEST;
	private static Orientation[] orientations = values();
	public static Orientation getOrifromValue(int orientationValue) {
		return orientations[orientationValue];
	}

	public Orientation turn90() {
		return orientations[(this.ordinal()+1) % orientations.length];
	}


	public int[] getOpposedPieceCoordinates(Piece p) {
		int[] coordonnee = new int[2];
		coordonnee[0] = p.getPosY();
		coordonnee[1] = p.getPosX();
		if (this == NORTH){
			coordonnee[1] --;
		}
		if (this == WEST){
			coordonnee[0] --;
		}
		if (this == SOUTH){
			coordonnee[1] ++;
		}
		if (this == EAST){
			coordonnee[0] ++;
		}
		return coordonnee;
	}


	public Orientation getOpposedOrientation() {
		 return this.turn90().turn90();
	}
}
