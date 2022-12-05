package move;

import board.Board;
import board.Builder;
import piece.Piece;

public class AttackMove extends Move {
	private Piece attackPiece;
	public AttackMove(Piece piece, Board board, int destinationCoordinates, Piece attackPiece) {
		super(piece, board, destinationCoordinates);
		this.attackPiece = attackPiece;
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
			if(!this.piece.equals(attackPiece)) {
				builder.setPiece(piece);
			}
		}
		
		builder.setPiece(this.piece.getPieceMove(this));

		builder.setMoveNext(this.board.getCurrentPlayer().getOpponent().getColorAlliance());
		
		builder.setMoveTransition(this);
		
		return builder.build();
	}
	@Override
	public boolean isAttack() {
		return true;
	}
	
	public Piece getAttackedPiece() {
		return this.attackPiece;
	}
}
