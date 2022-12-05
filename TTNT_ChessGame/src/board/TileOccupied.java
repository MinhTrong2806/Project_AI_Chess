package board;

import piece.Piece;

public class TileOccupied extends Tile {
	private Piece pieceOnTile;
	public TileOccupied(int toaDo, Piece piece) {
		super(toaDo);
		this.pieceOnTile = piece;
	}

	@Override
	public boolean isTileOccupied() {
		return true;
	}

	@Override
	public Piece getPiece() {
		return pieceOnTile;
	}

	@Override
	public String toString() {
		if(pieceOnTile.getColor() == "black") return pieceOnTile.getPieceType().toLowerCase();
		return pieceOnTile.getPieceType().toString();
	}

}
