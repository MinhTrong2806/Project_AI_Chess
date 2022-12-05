package move;

import board.Board;
import board.Builder;
import piece.Piece;

public abstract class Move {
	protected Piece piece;
	protected Board board;
	protected int destinationCoordinates;
	protected boolean isFirstMove;
	
	
	public Move(Piece piece, Board board, int destinationCoordinates) {
		super();
		this.piece = piece;
		this.board = board;
		this.destinationCoordinates = destinationCoordinates;
		if(piece!=null) this.isFirstMove = piece.isFirstMove();
	}
	
	public Move(Board board, int destinationCoordinates) {
		super();
		this.piece = null;
		this.board = board;
		this.destinationCoordinates = destinationCoordinates;
		this.isFirstMove = false;
	}
	
	public Move() {
		super();
		this.piece = null;
		this.isFirstMove = false;
	}
	
	public abstract boolean isAttack();
	
	public int getCurrentPositionPiece() {
		return piece.getPositonPiece();
	}	
	
	public int getDestinationCoordinates() {
		return this.destinationCoordinates;
	}
	
	public Piece getMovePiece() {
		return this.piece;
	}
	
	public Board getBoard() {
		return this.board;
	}

	@Override
	public String toString() {
		return "Move [piece=" + piece + ", diemDen=" + destinationCoordinates + ", isFirstMove=" + isFirstMove
				+ "]";
	}
	
	public Board execute() {
		final Builder builder = new Builder();
		for(Piece piece: this.board.getCurrentPlayer().getActivePieces()) {
			if(!this.piece.equals(piece)) {
				builder.setPiece(piece);
			}
		}
		for(Piece piece: this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
			builder.setPiece(piece);
		}
		builder.setPiece(this.piece.getPieceMove(this)); 
		builder.setMoveNext(this.board.getCurrentPlayer().getOpponent().getColorAlliance());
		builder.setMoveTransition(this);
		return builder.build();
	}

	public static Move createMove(Board board, int positionCurrent, int destinationCoordinates) {
		for(Move move : board.getAllMove()) {
			if(move.getCurrentPositionPiece() == positionCurrent && move.getDestinationCoordinates() == destinationCoordinates) {
				return move;
			}
		}
		return new NullMove();
	}
	
	
	public Piece getAttackedPiece() {
		return null;
	}

	public boolean isCastlingMove() {
		return false;
	}
	
	public static Move getNullMove() {
		return new NullMove();
	}
}
