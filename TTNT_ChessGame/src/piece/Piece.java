package piece;

import java.util.List;
import java.util.Objects;

import board.Board;
import move.Move;

public abstract class Piece {
	protected String pieceType;
	protected int positionPiece;
	protected String color;
	protected boolean firstMove;
	protected int valuePiece;
	public Piece(String pieceType, int positonPiece, String color) {
		this.pieceType = pieceType;
		this.positionPiece = positonPiece;
		this.color = color;
	}
	public Piece(String pieceType, int positonPiece, String color, boolean fisrtMove) {
		this.pieceType = pieceType;
		this.positionPiece = positonPiece;
		this.color = color;
		this.firstMove = fisrtMove;
	}
	public int getPositonPiece() {
		return positionPiece;
	}

	public void setPositonPiece(int piecePos) {
		this.positionPiece = piecePos;
	}

	public String getColor() {
		return color;
	}
	
	public boolean isBlack() {
		return this.color == "black";
	}
	public String getPieceType() {
		return this.pieceType;
	}
	
	public boolean isKing() {
		if(this.pieceType == "K") return true;
		return false;
	}
	
	public boolean isQueen() {
		if(this.pieceType == "Q") return true;
		return false;
	}
	
	public boolean isPawn() {
		if(this.pieceType == "P") return true;
		return false;
	}
	
	public boolean isRook() {
		if(this.pieceType == "R") return true;
		return false;
	}
	
	public boolean isKnight() {
		if(this.pieceType == "N") return true;
		return false;
	}
	
	public boolean isBishop() {
		if(this.pieceType == "B") return true;
		return false;
	}
	
	public boolean isFirstMove() {
		return this.firstMove;
	}
	
	public void setFirstMove() {
		 this.firstMove = false;
		
	}
	
	public int getValuePiece() {
		return this.valuePiece;
	}
	
	public abstract List<Move> legalMoves(Board board);
	
	public abstract String toString();
	@Override
	public int hashCode() {
		return Objects.hash(color, firstMove, positionPiece, pieceType);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		return Objects.equals(color, other.color) && firstMove == other.firstMove && positionPiece == other.positionPiece
				&& Objects.equals(pieceType, other.pieceType);
	}
	public abstract Piece getPieceMove(Move move);
	
	public boolean getFirstMove() {
		return this.firstMove;
	}
	public abstract int locationScore();
}
