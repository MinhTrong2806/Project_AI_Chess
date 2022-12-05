package piece;

import java.util.ArrayList;
import java.util.List;

import ai.PositionPieceValue;
import board.Board;
import board.Tile;
import move.AttackMove;
import move.Move;
import move.NormalMove;
import valid.ValidBoard;

public class Rook extends Piece {
	private final static int[] move = {-8, -1, 1, 8};
	public Rook(int positionPiece, String color) {
		super("R", positionPiece, color,true);
		this.valuePiece = 500;
	}
	
	public Rook(int positionPiece, String color, boolean isFirstMove) {
		super("R", positionPiece, color, isFirstMove);
		this.valuePiece = 500;
	}
	@Override
	public List<Move> legalMoves(Board board) {
		List<Move> legalMoves = new ArrayList<Move>();
		for(int destinationCoordinates : move) {
			int destination = this.positionPiece;
			while(ValidBoard.isValid(destination)) {
				if(checkFirstCol(this.positionPiece, destinationCoordinates) || checkEighthCol(this.positionPiece, destinationCoordinates)) break;
				if(ValidBoard.isValid(destination + destinationCoordinates)) {
					destination += destinationCoordinates;
				}else {
					break;
				}
				if(destinationCoordinates == 1) if(checkRowStop(destination, destination -1)) break;
				if(destinationCoordinates == -1) if(checkRowStop(destination + 1, destination)) break;
				if(ValidBoard.isValid(destination)) {
					final Tile tileDestination = board.getTile(destination);
					if(!tileDestination.isTileOccupied()) {				
						legalMoves.add(new NormalMove(this, board, destination));
					}else {	
						Piece pieceDestination = tileDestination.getPiece();
						String colorPiece = pieceDestination.getColor();
						if(this.color != colorPiece) {
							legalMoves.add(new AttackMove(this, board, destination, pieceDestination));
						}
						break;
					}
				}
			}
		}	
		return legalMoves;
	}

	private boolean checkFirstCol(int positionPiece, int destinationCoordinates) {
		return ValidBoard.FIRST_COL[positionPiece] && (destinationCoordinates == -1);
	}
	
	private boolean checkEighthCol(int positionPiece, int destinationCoordinates) {
		return ValidBoard.EIGHTH_COL[positionPiece] && (destinationCoordinates == 1);
	}
	
	private boolean checkRowStop(int positionPiece, int destinationCoordinates) {
		if(ValidBoard.FIRST_ROW[positionPiece] && !ValidBoard.FIRST_ROW[destinationCoordinates]) return true;
		if(ValidBoard.SECOND_ROW[positionPiece] && !ValidBoard.SECOND_ROW[destinationCoordinates]) return true;
		if(ValidBoard.THIRD_ROW[positionPiece] && !ValidBoard.THIRD_ROW[destinationCoordinates]) return true;
		if(ValidBoard.FOURTH_ROW[positionPiece] && !ValidBoard.FOURTH_ROW[destinationCoordinates]) return true;
		if(ValidBoard.FIFTH_ROW[positionPiece] && !ValidBoard.FIFTH_ROW[destinationCoordinates]) return true;
		if(ValidBoard.SIXTH_ROW[positionPiece] && !ValidBoard.SIXTH_ROW[destinationCoordinates]) return true;
		if(ValidBoard.SEVENTH_ROW[positionPiece] && !ValidBoard.SEVENTH_ROW[destinationCoordinates]) return true;
		if(ValidBoard.EIGHTH_ROW[positionPiece] && !ValidBoard.EIGHTH_ROW[destinationCoordinates]) return true;
		return false;
	}
	
	@Override
	public String toString() {
		return this.pieceType;
	}

	@Override
	public Piece getPieceMove(Move move) {
		return new Rook(move.getDestinationCoordinates(), this.color, false);
	}


	@Override
	public int locationScore() {
		return this.color == "white" ? PositionPieceValue.WHITE_ROOK_PREFERRED_COORDINATES[this.positionPiece] : PositionPieceValue.BLACK_ROOK_PREFERRED_COORDINATES[this.positionPiece];
	}

}
