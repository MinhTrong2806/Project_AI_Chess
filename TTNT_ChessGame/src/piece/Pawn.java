package piece;

import java.util.ArrayList;
import java.util.List;

import ai.PositionPieceValue;
import board.Board;
import move.AttackMove;
import move.Move;
import move.NormalMove;
import move.PawnPromotionMove;
import valid.ValidBoard;

public class Pawn extends Piece {
	private final static int[] move = { 8, 16, 7, 9};

	public Pawn(int piecePos, String color) {
		super("P", piecePos, color, true);
		this.valuePiece = 100;
	}
	
	public Pawn(int piecePos, String color, boolean isFirstMove) {
		super("P", piecePos, color, isFirstMove);
		this.valuePiece = 100;
	}
	
	@Override
	public List<Move> legalMoves(Board board) {
		List<Move> legalMoves = new ArrayList<Move>();
		for (int destinationCoordinates : move) {
			int destination = this.positionPiece + (checkColorChess() * destinationCoordinates);
			if (!ValidBoard.isValid(destination)) {
				continue;
			}
			if (destinationCoordinates == 8 && !board.getTile(destination).isTileOccupied()) {
				if(isPromotion(destination)) {
					legalMoves.add(new PawnPromotionMove(new NormalMove(this, board, destination),new Queen(destination, this.color, false)));
				}else {
					legalMoves.add(new NormalMove(this, board, destination));
				}
			} else if (destinationCoordinates == 16 && this.isFirstMove() && ((ValidBoard.SEVENTH_ROW[this.positionPiece] && this.color.equals("white")) || (ValidBoard.SECOND_ROW[this.positionPiece]))) {
				int previousCoordinates = this.positionPiece + (checkColorChess() * 8);
				if (!board.getTile(previousCoordinates).isTileOccupied() && !board.getTile(destination).isTileOccupied()) {
					legalMoves.add(new NormalMove(this, board, destination));
				}
			} else if (destinationCoordinates == 7 && !((ValidBoard.EIGHTH_COL[positionPiece] && this.color == "white")||(ValidBoard.FIRST_COL[positionPiece]&& this.color=="black"))) {
				Piece pieceKilled = board.getTile(destination).getPiece();
				if (board.getTile(destination).isTileOccupied()) {
					if (this.getColor() != pieceKilled.getColor()) {
						if(isPromotion(destination)) {
							legalMoves.add(new PawnPromotionMove(new AttackMove(this, board, destination, pieceKilled),new Queen(destination, this.color, false)));
						}else {
							legalMoves.add(new AttackMove(this, board, destination, pieceKilled));
						}
					}
				}
			} else if (destinationCoordinates == 9 && !((ValidBoard.FIRST_COL[positionPiece] && this.color=="white") || (ValidBoard.EIGHTH_COL[positionPiece]&& this.color=="black"))) {
				Piece pieceKilled = board.getTile(destination).getPiece();
				if (board.getTile(destination).isTileOccupied()) {
					if (this.getColor() != pieceKilled.getColor()) {
						if(isPromotion(destination)) {
							legalMoves.add(new PawnPromotionMove(new AttackMove(this, board, destination, pieceKilled), new Queen(destination, this.color, false)));
						}else {
							legalMoves.add(new AttackMove(this, board, destination, pieceKilled));
						}
					}
				}
			}

		}
		return legalMoves;
	}
	
	private boolean isPromotion(int posPiece) {
		if(ValidBoard.FIRST_ROW[posPiece]) return true;
		if(ValidBoard.EIGHTH_ROW[posPiece]) return true;
		return false;
	}
	
	private boolean checkFirstCol(int positionPiece, String color, int destinationCoordinates) {
		return ValidBoard.FIRST_COL[positionPiece] && (destinationCoordinates==7 && color == "black") || (destinationCoordinates==9 && color == "white");
	}
	
	private boolean checkSeventhCol(int positionPiece,String color, int destinationCoordinates) {
		return ValidBoard.EIGHTH_COL[positionPiece] && (destinationCoordinates== 9 && color == "black") || (destinationCoordinates== 7 && color == "white");
	}
	
	public int checkColorChess() {
		if (this.getColor() == "white") return -1;
		return 1;
	}

	@Override
	public String toString() {
		return this.pieceType;
	}

	@Override
	public Piece getPieceMove(Move move) {
		return new Pawn(move.getDestinationCoordinates(), this.color, false);
	}

	public Piece getPiecePromotion() {
		return new Queen(positionPiece, color, false);
	}

	@Override
	public int locationScore() {
		return this.color == "white" ? PositionPieceValue.WHITE_PAWN_PREFERRED_COORDINATES[this.positionPiece] : PositionPieceValue.BLACK_PAWN_PREFERRED_COORDINATES[this.positionPiece];
	}
}
