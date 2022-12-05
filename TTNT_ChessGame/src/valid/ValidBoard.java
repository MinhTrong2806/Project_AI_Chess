package valid;

import java.util.ArrayList;
import java.util.List;

public class ValidBoard {
	public static final int NUM_TILE = 64;
	public static final int NUM_TILE_ROW = 8;
	
	public static final boolean[] FIRST_COL = initCol(0);
	public static final boolean[] SECOND_COL = initCol(1);
	public static final boolean[] THIRD_COL = initCol(2);
	public static final boolean[] FOURTH_COL = initCol(3);
	public static final boolean[] FIFTH_COL = initCol(4);
	public static final boolean[] SIXTH_COL = initCol(5);
	public static final boolean[] SEVENTH_COL = initCol(6);
	public static final boolean[] EIGHTH_COL = initCol(7);
	
	public static final boolean[] FIRST_ROW = initRow(0);
	public static final boolean[] SECOND_ROW = initRow(8);
	public static final boolean[] THIRD_ROW = initRow(16);
	public static final boolean[] FOURTH_ROW = initRow(24);
	public static final boolean[] FIFTH_ROW = initRow(32);
	public static final boolean[] SIXTH_ROW = initRow(40);
	public static final boolean[] SEVENTH_ROW = initRow(48);
	public static final boolean[] EIGHTH_ROW = initRow(56);
	public static final List<String> POSITION_AI = positionAI();
	private static boolean[] initCol(int colNumber) {
		final boolean[] column = new boolean[64];
		do {
			column[colNumber] = true;
			colNumber += 8;
		}while(colNumber < NUM_TILE);
		return column;
	}
	
	private static boolean[] initRow(int colNumber) {
		final boolean[] column = new boolean[64];
		int count = 0;
		do {
			column[colNumber] = true;
			colNumber += 1;
			count += 1;
		}while(count < NUM_TILE_ROW);
		return column;
	}
	
	public static int getPositionOnRow(int Pos) {
		if(FIRST_ROW[Pos]) return 8-Pos-1;
		if(SECOND_ROW[Pos]) return 16-Pos-1;
		if(THIRD_ROW[Pos]) return 24-Pos-1;
		if(FOURTH_ROW[Pos]) return 32-Pos-1;
		if(FIFTH_ROW[Pos]) return 40-Pos-1;
		if(SIXTH_ROW[Pos]) return 48-Pos-1;
		if(SEVENTH_ROW[Pos]) return 56-Pos-1;
		if(EIGHTH_ROW[Pos]) return 64-Pos-1;
		return 0;
	}
	
	public static List<String> positionAI(){
		List<String> position = new ArrayList<>();
		position.add("a8");position.add("b8");position.add("c8");position.add("d8");position.add("e8");position.add("f8");position.add("g8");position.add("h8");
		position.add("a7");position.add("b7");position.add("c7");position.add("d7");position.add("e7");position.add("f7");position.add("g7");position.add("h7");
		position.add("a6");position.add("b6");position.add("c6");position.add("d6");position.add("e6");position.add("f6");position.add("g6");position.add("h6");
		position.add("a5");position.add("b5");position.add("c5");position.add("d5");position.add("e5");position.add("f5");position.add("g5");position.add("h5");
		position.add("a4");position.add("b4");position.add("c4");position.add("d4");position.add("e4");position.add("f4");position.add("g4");position.add("h4");
		position.add("a3");position.add("b3");position.add("c3");position.add("d3");position.add("e3");position.add("f3");position.add("g3");position.add("h3");
		position.add("a2");position.add("b2");position.add("c2");position.add("d2");position.add("e2");position.add("f2");position.add("g2");position.add("h2");
		position.add("a1");position.add("b1");position.add("c1");position.add("d1");position.add("e1");position.add("f1");position.add("g1");position.add("h1");
		return position;
	}
	
	public static int getCoordinateWithPosition(String position) {
		return POSITION_AI.indexOf(position);
	}
	
	public static String getPositionWithCoordinate(int coordinate) {
		return POSITION_AI.get(coordinate);
	}
	
	public static boolean isValid(int toaDo) {
		return toaDo < 64 && toaDo >= 0;
	}
}
