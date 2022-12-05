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

public class King extends Piece {
	private final static int[] move = { -9, -8, -7, -1, 1, 7, 8, 9 };
	private boolean canCastling;
	public King(int piecePos, String color) {
		super("K",piecePos, color,true);
		this.canCastling = false;
		this.valuePiece = 10000;
	}
	
	public King(int piecePos, String color, boolean isFirstMove) {
		super("K",piecePos, color, isFirstMove);
		this.canCastling = false;
		this.valuePiece = 10000;
	}
	
	public King(int piecePos, boolean castling, String color, boolean isFirstMove) {
		super("K",piecePos, color, isFirstMove);
		this.canCastling = castling;
		this.valuePiece = 10000;
	}
	


	@Override
	public List<Move> legalMoves(Board board) {
		List<Move> legalMoves = new ArrayList<Move>();
		for(int destinationCoordinates : move) {
			int destination = this.positionPiece;
				if (checkFirstCol(this.positionPiece, destinationCoordinates) || checkEighthCol(this.positionPiece, destinationCoordinates)) continue;
					destination += destinationCoordinates;
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
					}
				}
		}
		return legalMoves;
	}
	private boolean checkFirstCol(int positionPiece, int destinationCoordinates) {
		return ValidBoard.FIRST_COL[positionPiece] && (destinationCoordinates == -9 || destinationCoordinates== 7 || destinationCoordinates == -1);
	}
	
	private boolean checkEighthCol(int positionPiece, int destinationCoordinates) {
		return ValidBoard.EIGHTH_COL[positionPiece] && (destinationCoordinates == -7 || destinationCoordinates==9 || destinationCoordinates == 1);
	}
	@Override
	public String toString() {
		return this.pieceType;
	}

	@Override
	public Piece getPieceMove(Move move) {
		return new King(move.getDestinationCoordinates(), move.isCastlingMove(), this.color, false);
	}
	
	public boolean isCastling() {
		return this.canCastling;
	}

	@Override
	public int locationScore() {
		return this.color == "white" ? PositionPieceValue.WHITE_KING_PREFERRED_COORDINATES[this.positionPiece] : PositionPieceValue.BLACK_KING_PREFERRED_COORDINATES[this.positionPiece];
	}
}
