package main.java.fr.dauphine.JavaAvance.Components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Handling of pieces with general functions
 */
public class Piece {
	private int posX;// j
	private int posY;// i
	private PieceType type;
	private Orientation orientation;
	private LinkedList<Orientation> connectors;
	private ArrayList<Orientation> possibleOrientations;

	private boolean isFixed;


	public final static HashMap<ArrayList<Boolean>, Pair<PieceType,Orientation>> FINDYOURPIECE = new HashMap<>(){{
		put(new ArrayList<>(Arrays.asList(false,false,false,false)), new Pair<>(PieceType.VOID,Orientation.NORTH));
		put(new ArrayList<>(Arrays.asList(false,false,false,true)), new Pair<>(PieceType.ONECONN,Orientation.WEST));
		put(new ArrayList<>(Arrays.asList(false,false,true,false)), new Pair<>(PieceType.ONECONN,Orientation.SOUTH));
		put(new ArrayList<>(Arrays.asList(false,false,true,true)), new Pair<>(PieceType.LTYPE,Orientation.SOUTH));
		put(new ArrayList<>(Arrays.asList(false,true,false,false)), new Pair<>(PieceType.ONECONN,Orientation.EAST));
		put(new ArrayList<>(Arrays.asList(false,true,false,true)), new Pair<>(PieceType.BAR,Orientation.EAST));
		put(new ArrayList<>(Arrays.asList(false,true,true,false)), new Pair<>(PieceType.LTYPE,Orientation.EAST));
		put(new ArrayList<>(Arrays.asList(false,true,true,true)), new Pair<>(PieceType.TTYPE,Orientation.SOUTH));
		put(new ArrayList<>(Arrays.asList(true,false,false,false)), new Pair<>(PieceType.ONECONN,Orientation.NORTH));
		put(new ArrayList<>(Arrays.asList(true,false,false,true)), new Pair<>(PieceType.LTYPE,Orientation.WEST));
		put(new ArrayList<>(Arrays.asList(true,false,true,false)), new Pair<>(PieceType.BAR,Orientation.NORTH));
		put(new ArrayList<>(Arrays.asList(true,false,true,true)), new Pair<>(PieceType.TTYPE,Orientation.WEST));
		put(new ArrayList<>(Arrays.asList(true,true,false,false)), new Pair<>(PieceType.LTYPE,Orientation.NORTH));
		put(new ArrayList<>(Arrays.asList(true,true,false,true)), new Pair<>(PieceType.TTYPE,Orientation.NORTH));
		put(new ArrayList<>(Arrays.asList(true,true,true,false)), new Pair<>(PieceType.TTYPE,Orientation.EAST));
		put(new ArrayList<>(Arrays.asList(true,true,true,true)), new Pair<>(PieceType.FOURCONN,Orientation.NORTH));
	}
	};


	public Piece(int posY, int posX) {
		this.posX = posX;
		this.posY = posY;
		this.type = PieceType.VOID;
		this.orientation = type.getOrientation(Orientation.NORTH);
		this.connectors = type.setConnectorsList(Orientation.NORTH);
		this.isFixed = false; // Is there any orientation for the piece
		this.possibleOrientations = type.getListOfPossibleOri();
	}

	public Piece(int posY, int posX, PieceType type, Orientation orientation) {
		this.posX = posX;
		this.posY = posY;
		this.type = type;
		this.orientation = type.getOrientation(orientation);
		this.connectors = type.setConnectorsList(orientation);
		this.isFixed = false;
		this.possibleOrientations = type.getListOfPossibleOri();
	}

	public Piece(int posY, int posX, int typeValue, int orientationValue) {
		this.posX = posX;
		this.posY = posY;
		this.type = PieceType.getTypefromValue(typeValue);
		this.orientation = type.getOrientation(Orientation.getOrifromValue(orientationValue));
		this.connectors = type.setConnectorsList(Orientation.getOrifromValue(orientationValue));
		this.isFixed = false;
		this.possibleOrientations = type.getListOfPossibleOri();
	}

	public void setPossibleOrientations(ArrayList<Orientation> possibleOrientations) {
		this.possibleOrientations = possibleOrientations;
	}

	public ArrayList<Orientation> getPossibleOrientations() {
		return this.possibleOrientations;
	}

	public LinkedList<Orientation> getInvPossibleOrientation() {
		LinkedList<Orientation> invPossibleOrientations = new LinkedList<>();
		for (Orientation ori : this.getPossibleOrientations()) {
			invPossibleOrientations.addFirst(ori);
		}
		return invPossibleOrientations;
	}

	public void deleteFromPossibleOrientation(Orientation ori) {
		if (this.possibleOrientations.contains(ori)) {
			this.possibleOrientations.remove(ori);
		}
	}

	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}

	public boolean isFixed() {
		return isFixed;
	}

	public int getPosX() { // get j
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() { // get i
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public PieceType getType() {
		return type;
	}

	public void setType(PieceType type) {
		this.type = type;
	}

	public void setOrientation(int orientationValue) {
		this.orientation = type.getOrientation(Orientation.getOrifromValue(orientationValue));
		this.connectors = type.setConnectorsList(this.orientation);
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public LinkedList<Orientation> getConnectors() {
		return connectors;
	}

	public boolean hasTopConnector() {
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.NORTH) {
				return true;
			}
		}
		return false;
	}

	public boolean hasRightConnector() {
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.EAST) {
				return true;
			}
		}
		return false;
	}

	public boolean hasBottomConnector() {
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.SOUTH) {
				return true;
			}
		}
		return false;
	}

	public boolean hasLeftConnector() {
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.WEST) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Turn the piece 90° on the right and redefine the connectors's position
	 */
	public void turn() {
		this.orientation = type.getOrientation(orientation.turn90());
		this.connectors = type.setConnectorsList(orientation);
	}

	@Override
	public String toString() {
		String s = "[" + this.getPosY() + ", " + this.getPosX() + "] " + this.getType() + " ";
		for (Orientation ori : this.getConnectors()) {
			s += " " + ori.toString();
		}
		s += " Orientation / " + this.getOrientation();
		return s;
	}

}
