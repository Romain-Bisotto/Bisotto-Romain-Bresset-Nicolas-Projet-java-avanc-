package main.java.fr.dauphine.JavaAvance.Components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 * Type of the piece enum
 * 
 */
public enum PieceType {
	VOID,ONECONN,BAR,TTYPE,FOURCONN,LTYPE;
	private static PieceType[] pieceTypes = values();
	private final static HashMap<PieceType, ArrayList<Orientation>> LISTCONNECTORS= new HashMap<>(){{
		put(VOID,new ArrayList<>());
		put(ONECONN, new ArrayList<>(Arrays.asList(Orientation.NORTH)));
		put(BAR, new ArrayList<>(Arrays.asList(Orientation.NORTH, Orientation.SOUTH)));
		put(TTYPE, new ArrayList<>(Arrays.asList(Orientation.WEST, Orientation.NORTH, Orientation.EAST)));
		put(FOURCONN, new ArrayList<>(Arrays.asList(Orientation.NORTH, Orientation.EAST, Orientation.SOUTH, Orientation.WEST)));
		put(LTYPE, new ArrayList<>(Arrays.asList(Orientation.NORTH, Orientation.EAST)));
	}};


	public static PieceType getTypefromValue(int typeValue) {
		return pieceTypes[typeValue];
	}


	public Orientation getOrientation(Orientation orifromValue) {
		return orifromValue;
	}


	public LinkedList<Orientation> setConnectorsList(Orientation orientation) {
		ArrayList<Orientation> connectors = LISTCONNECTORS.get(this);
		LinkedList<Orientation>  oriList = new LinkedList();
		for (Orientation connector : connectors){
			for (int i = 0; i < orientation.ordinal(); i++){
				connector = connector.turn90();
			}
			oriList.add(connector);
		}
		return oriList;
	}

	/* à faire */
	public ArrayList<Orientation> getListOfPossibleOri() {
		ArrayList<Orientation>  oriList= null;
		return oriList;
	}

	public int getNbConnectors() {
		return LISTCONNECTORS.get(this).size();
	}
}
