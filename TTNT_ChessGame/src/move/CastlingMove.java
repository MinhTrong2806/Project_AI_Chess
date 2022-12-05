package move;

import board.Board;
import board.Builder;
import piece.Piece;
import piece.Rook;

public class CastlingMove extends Move{
	private Rook castlingRook;
	private int rookStart;
	private int rookDestination;
	
	public CastlingMove(Piece piece, Board board, int destinationCoordinates, Rook rook, int rookStart,int rookDestination) {
		super(piece, board, destinationCoordinates);
		this.castlingRook = rook;
		this.rookStart = rookStart;
		this.rookDestination = rookDestination;
	}
	
	public Rook getRook() {
		return this.castlingRook;
	}
	
	public boolean isCastlingRook() {
		return true;
	}
	
	@Override
	public Board execute() {
		final Builder builder = new Builder();
		for(Piece piece: this.board.getCurrentPlayer().getActivePieces()) {
			if(!this.piece.equals(piece) && !this.castlingRook.equals(piece)) {
				builder.setPiece(piece);
			}
		}
		
		for(Piece piece: this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
			builder.setPiece(piece);
		}
		builder.setPiece(piece.getPieceMove(this));
		builder.setPiece(new Rook(this.rookDestination, this.castlingRook.getColor(), false));
		builder.setMoveNext(this.board.getCurrentPlayer().getOpponent().getColorAlliance());
		builder.setMoveTransition(this);
		return builder.build();
	}

	@Override
	public boolean isAttack() {
		return false;
	}
	
	public boolean isCastlingMove() {
		return true;
	}

}
