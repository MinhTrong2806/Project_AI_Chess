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

public class Knight extends Piece {

	private final static int[] move = {-17,-15,-10,-6,6,10,15,17};
	
	public Knight(int positionPiece, String color) {
		super("N", positionPiece, color, true);
		this.valuePiece = 300;
	}

	public Knight(int positionPiece, String color, boolean isFirstMove) {
		super("N", positionPiece, color, isFirstMove);
		this.valuePiece = 300;
	}
	
	@Override
	public List<Move> legalMoves(Board board) {
		int destination;
		List<Move> legalMoves = new ArrayList<>();
		for(final int destinationCoordinates : move) {
			destination = this.positionPiece + destinationCoordinates;
			if(ValidBoard.isValid(destination)) {
				final Tile tileDestination = board.getTile(destination);
				
				if(checkFirstCol(this.positionPiece, destinationCoordinates) || checkSecondCol(this.positionPiece, destinationCoordinates) 
				|| checkSeventhCol(this.positionPiece, destinationCoordinates) ||checkEighthCol(this.positionPiece, destinationCoordinates)) continue;
				
				if(!tileDestination.isTileOccupied()) {
					legalMoves.add(new NormalMove(this, board, destination));
				}else {	
					Piece pieceDestination = tileDestination.getPiece();
					String colorPiece = pieceDestination.getColor();
					if(this.color != colorPiece) {
						legalMoves.add(new AttackMove(this, board, destination, pieceDestination));
					}
				}
			}
		}
		return legalMoves;
	}

	private boolean checkFirstCol(int positionPiece, int destinationCoordinates) {
		return ValidBoard.FIRST_COL[positionPiece] && (destinationCoordinates==-17 || destinationCoordinates==-10|| destinationCoordinates==6 || destinationCoordinates==15);
	}
	
	private boolean checkSecondCol(int positionPiece, int destinationCoordinates) {
		return ValidBoard.SECOND_COL[positionPiece] && (destinationCoordinates== -10 || destinationCoordinates== 6);
	}
	
	private boolean checkSeventhCol(int positionPiece, int destinationCoordinates) {
		return ValidBoard.SEVENTH_COL[positionPiece] && (destinationCoordinates== -6 || destinationCoordinates== 10);
	}
	private boolean checkEighthCol(int positionPiece, int destinationCoordinates) {
		return ValidBoard.EIGHTH_COL[positionPiece] && (destinationCoordinates== -15 || destinationCoordinates== -6 || destinationCoordinates== 10|| destinationCoordinates== 17);
	}
	@Override
	public String toString() {
		return this.pieceType;
	}

	@Override
	public Piece getPieceMove(Move move) {
		return new Knight(move.getDestinationCoordinates(), this.color, false);
	}

	@Override
	public int locationScore() {
		return this.color == "white" ? PositionPieceValue.WHITE_KNIGHT_PREFERRED_COORDINATES[this.positionPiece] : PositionPieceValue.BLACK_KNIGHT_PREFERRED_COORDINATES[this.positionPiece];
	}
}
