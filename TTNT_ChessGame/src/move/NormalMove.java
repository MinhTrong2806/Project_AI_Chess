package move;

import board.Board;
import board.Builder;
import piece.Piece;

public class NormalMove extends Move {

	public NormalMove(Piece piece, Board board, int destinationCoordinates) {
		super(piece, board, destinationCoordinates);
	}

	@Override
	public String toString() {
		return "NormalMove: " + piece.toString()+", " + destinationCoordinates;
	}

	@Override
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

	@Override
	public boolean isAttack() {
		return false;
	}
}
