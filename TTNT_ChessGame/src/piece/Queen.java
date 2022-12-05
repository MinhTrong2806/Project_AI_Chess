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

public class Queen extends Piece {
	private final static int[] move = { -9, -8, -7, -1, 1, 7, 8, 9 };

	public Queen(int positionPiece, String color) {
		super("Q", positionPiece, color, true);
		this.valuePiece = 1000;
	}

	public Queen(int positionPiece, String color, boolean isFirstMove) {
		super("Q", positionPiece, color, isFirstMove);
		this.valuePiece = 1000;
	}

	@Override
	public List<Move> legalMoves(Board board) {
		List<Move> legalMoves = new ArrayList<Move>();
		for (int destinationCoordinates : move) {
			int destination = this.positionPiece;
			while (ValidBoard.isValid(destination)) {
				if (checkFirstCol(this.positionPiece, destinationCoordinates) || checkEighthCol(this.positionPiece, destinationCoordinates)) break;

				if (ValidBoard.isValid(destination + destinationCoordinates)) {
					destination += destinationCoordinates;
				} else {
					break;
				}

				if (destinationCoordinates == 7 && checkEighthColStop(destination)) break;
				
				if (destinationCoordinates == 9 && checkFirstColStop(destination)) break;
				
				if (destinationCoordinates == -7 && checkFirstColStop(destination)) break;
				
				if (destinationCoordinates == -9 && checkEighthColStop(destination)) break;
				
				if (destinationCoordinates == 1) if (checkRowStop(destination, destination - 1)) break;
				
				if (destinationCoordinates == -1) if (checkRowStop(destination + 1, destination)) break;
				
				if (ValidBoard.isValid(destination)) {
					final Tile tileDestination = board.getTile(destination);
					if (!tileDestination.isTileOccupied()) {
						legalMoves.add(new NormalMove(this, board, destination));
					} else {
						Piece pieceDestination = tileDestination.getPiece();
						String colorPiece = pieceDestination.getColor();
						if (this.color != colorPiece) {
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
		return ValidBoard.FIRST_COL[positionPiece] && (destinationCoordinates == -1 || destinationCoordinates == -9 || destinationCoordinates == 7);
	}

	private boolean checkEighthCol(int positionPiece, int destinationCoordinates) {
		return ValidBoard.EIGHTH_COL[positionPiece] && (destinationCoordinates == 1 || destinationCoordinates == -7 || destinationCoordinates == 9);
	}

	private boolean checkEighthColStop(int destinationCoordinates) {
		return ValidBoard.EIGHTH_COL[destinationCoordinates];
	}

	private boolean checkFirstColStop(int destinationCoordinates) {
		return ValidBoard.FIRST_COL[destinationCoordinates];
	}

	private boolean checkRowStop(int positionPiece, int destinationCoordinates) {
		if (ValidBoard.FIRST_ROW[positionPiece] && !ValidBoard.FIRST_ROW[destinationCoordinates]) return true;
		if (ValidBoard.SECOND_ROW[positionPiece] && !ValidBoard.SECOND_ROW[destinationCoordinates]) return true;
		if (ValidBoard.THIRD_ROW[positionPiece] && !ValidBoard.THIRD_ROW[destinationCoordinates]) return true;
		if (ValidBoard.FOURTH_ROW[positionPiece] && !ValidBoard.FOURTH_ROW[destinationCoordinates]) return true;
		if (ValidBoard.FIFTH_ROW[positionPiece] && !ValidBoard.FIFTH_ROW[destinationCoordinates]) return true;
		if (ValidBoard.SIXTH_ROW[positionPiece] && !ValidBoard.SIXTH_ROW[destinationCoordinates]) return true;
		if (ValidBoard.SEVENTH_ROW[positionPiece] && !ValidBoard.SEVENTH_ROW[destinationCoordinates]) return true;
		if (ValidBoard.EIGHTH_ROW[positionPiece] && !ValidBoard.EIGHTH_ROW[destinationCoordinates]) return true;
		return false;
	}

	@Override
	public String toString() {
		return this.pieceType;
	}

	@Override
	public Piece getPieceMove(Move move) {
		return new Queen(move.getDestinationCoordinates(), this.color, false);
	}

	@Override
	public int locationScore() {
		return this.color == "white" ? PositionPieceValue.WHITE_QUEEN_PREFERRED_COORDINATES[this.positionPiece] : PositionPieceValue.BLACK_QUEEN_PREFERRED_COORDINATES[this.positionPiece];
	}
}
