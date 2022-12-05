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

public class Bishop extends Piece {
	private final static int[] move = {-9, -7, 7, 9};
	public Bishop(int piecePos, String color) {
		super("B", piecePos, color ,true);
		this.valuePiece = 300;
	}
	
	public Bishop(int piecePos, String color, boolean isFirstMove) {
		super("B", piecePos, color ,isFirstMove);
		this.valuePiece = 300;
	}


	@Override
	public List<Move> legalMoves(Board board) {
		List<Move> legalMoves = new ArrayList<Move>();
		for(int destinationCoordinates : move) {
			int destination =  this.positionPiece;
			while(ValidBoard.isValid(destination)) {
				if(ValidBoard.isValid(destination + destinationCoordinates)) {
					destination += destinationCoordinates;
				}else {
					break;
				}
				if(checkFirstCol(this.positionPiece, destinationCoordinates) || checkEighthCol(this.positionPiece, destinationCoordinates)) break;
				if(destinationCoordinates == 7 && checkEighthColStop(destination)) break;
				if(destinationCoordinates == 9 && checkFirstColStop(destination)) break;
				if(destinationCoordinates == -7 && checkFirstColStop(destination)) break;
				if(destinationCoordinates == -9 && checkEighthColStop(destination)) break;
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
	
	private boolean checkFirstCol(int piecePos, int destinationCoordinates) {
		return ValidBoard.FIRST_COL[piecePos] && (destinationCoordinates==-9 || destinationCoordinates== 7);
	}
	
	private boolean checkEighthCol(int piecePos, int destinationCoordinates) {
		return ValidBoard.EIGHTH_COL[piecePos] && (destinationCoordinates==-7 || destinationCoordinates==9);
	}
	
	
	private boolean checkEighthColStop(int destinationCoordinates) {
		return ValidBoard.EIGHTH_COL[destinationCoordinates];
	}
	
	private boolean checkFirstColStop(int destinationCoordinates) {
		return ValidBoard.FIRST_COL[destinationCoordinates];
	}
	
	@Override
	public String toString() {
		return this.pieceType;
	}

	@Override
	public Piece getPieceMove(Move move) {
		return new Bishop(move.getDestinationCoordinates(), this.color, false);
	}

	@Override
	public int locationScore() {
		return this.color == "white" ? PositionPieceValue.WHITE_BISHOP_PREFERRED_COORDINATES[this.positionPiece] : PositionPieceValue.BLACK_BISHOP_PREFERRED_COORDINATES[this.positionPiece];
	}

}
