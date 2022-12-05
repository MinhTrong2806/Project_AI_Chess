package board;

import piece.Piece;

public class EmptyTile extends Tile {

	public EmptyTile(int toaDo) {
		super(toaDo);
	}

	@Override
	public boolean isTileOccupied() {
		return false;
	}

	@Override
	public Piece getPiece() {
		return null;
	}


	@Override
	public String toString() {
		return "-";
	}
}
