package player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import board.Board;
import board.Tile;
import move.CastlingMove;
import move.Move;
import piece.King;
import piece.Piece;
import piece.Rook;

public class WhitePlayer extends Player {

	public WhitePlayer(Board board, Collection<Move> legalWhiteMove, Collection<Move> legalBlackMove, String PlayerType) {
		super(board, legalWhiteMove, legalBlackMove,PlayerType);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}

	@Override
	public Player getOpponent() {
		return this.board.getBlackPlayer();
	}
	
	@Override
	public String getColorAlliance() {
		return "white";
	}

	@Override
	public String getPlayerType() {
		return this.PlayerType;
	}

	@Override
	public boolean isAI() {
		return false;
	}
	@Override
	protected Collection<Move> culRookKing(Collection<Move> playerLegals, Collection<Move> opponentsLegals) {
		List<Move> kingRookMoves = new ArrayList<Move>();
		Rook rook = null;
		if (playerKing.isFirstMove() && !this.isInCheck()) {
			if (!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
				Tile rookTile = this.board.getTile(63);
				if(rookTile.isTileOccupied()) {
					if(rookTile.getPiece().getPieceType()=="R") {
						 rook = (Rook) rookTile.getPiece();
					}	
				}
				if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && rook != null) {
					if(Player.culAttackOnPiece(61, opponentsLegals).isEmpty() && Player.culAttackOnPiece(61, opponentsLegals).isEmpty()) {
						kingRookMoves.add(new CastlingMove(this.playerKing, this.board, 62, rook, rookTile.getViTriTile(), 61));
					}
				}
			}
			Rook rookSideQueen = null;
			if(!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() && !this.board.getTile(57).isTileOccupied()) {
				Tile rookTile = this.board.getTile(56);
				
				if(rookTile.isTileOccupied()) {
					if(rookTile.getPiece().getPieceType()=="R") {
						rookSideQueen = (Rook) rookTile.getPiece();
					}
				}
				if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && rookSideQueen != null) {
					kingRookMoves.add(new CastlingMove(this.playerKing, this.board, 58, rookSideQueen, rookTile.getViTriTile(), 59));
				}
			}
			
		}
		
		return kingRookMoves;
	}
}
