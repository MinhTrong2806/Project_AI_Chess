package move;

import board.Board;
import board.Builder;
import piece.Pawn;
import piece.Piece;
import piece.Queen;

public class PawnPromotionMove extends Move {
	private Move promotionMove;
	private Pawn promotionPiece;
	private Queen pieceQueen;
	public PawnPromotionMove(Move promotionMove, Piece pieceNew) {
		super(promotionMove.piece, promotionMove.board, promotionMove.destinationCoordinates);
		this.promotionMove = promotionMove;
		this.promotionPiece =(Pawn) promotionMove.getMovePiece();
		this.pieceQueen = (Queen) pieceNew;
	}
	@Override
	public Board execute() {
		Board board = promotionMove.execute();
		Builder builder = new Builder();
		for(Piece piece : board.getCurrentPlayer().getActivePieces()) {
			if(!piece.equals(promotionPiece)) {
				builder.setPiece(piece);
			}
		}
		for(Piece piece : board.getCurrentPlayer().getOpponent().getActivePieces()) {
				builder.setPiece(piece);
		}
		
		builder.setPiece(this.pieceQueen);
		
		builder.setMoveNext(this.board.getCurrentPlayer().getOpponent().getColorAlliance());
		
		builder.setMoveTransition(this);
	
		return builder.build();
	}
	@Override
	public boolean isAttack() {
		return promotionMove.isAttack();
	}
	
}
