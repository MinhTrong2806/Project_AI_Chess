package player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import board.Board;
import board.Tile;
import move.CastlingMove;
import move.Move;
import piece.Piece;
import piece.Rook;

public class BlackPlayer extends Player {

	public BlackPlayer(Board board, Collection<Move> legalBlackMove, Collection<Move> legalWhiteMove,
			String PlayerType) {
		super(board, legalBlackMove, legalWhiteMove, PlayerType);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}

	@Override
	public Player getOpponent() {
		return this.board.getWhitePlayer();
	}

	@Override
	public String getColorAlliance() {
		return "black";
	}

	@Override
	public String getPlayerType() {
		return this.PlayerType;
	}

	@Override
	public boolean isAI() {
		return true;
	}

	@Override
	protected Collection<Move> culRookKing(Collection<Move> playerLegals, Collection<Move> opponentsLegals) {
		List<Move> kingRookMoves = new ArrayList<Move>();
		Rook rook = null;
		
		if (playerKing.isFirstMove() && !this.isInCheck()) {
			if (!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
				Tile rookTile = this.board.getTile(7);
				if(rookTile.isTileOccupied()) {
					if(rookTile.getPiece().getPieceType()=="R") {
						 rook = (Rook) rookTile.getPiece();
					}
				}
				if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && rook != null) { 
					if(Player.culAttackOnPiece(5, opponentsLegals).isEmpty() && Player.culAttackOnPiece(6, opponentsLegals).isEmpty()) {
						kingRookMoves.add(new CastlingMove(this.playerKing, this.board, 6, rook, rookTile.getViTriTile(), 5));
					}
				}
			}
			Rook rookSideQueen = null;
			if(!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() && !this.board.getTile(3).isTileOccupied()) {
				Tile rookTile = this.board.getTile(0);
				if(rookTile.isTileOccupied()) {
					if(rookTile.getPiece().getPieceType()=="R") {
						rookSideQueen = (Rook) rookTile.getPiece();
					}
				}
				if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && rookSideQueen != null) {
					kingRookMoves.add(new CastlingMove(this.playerKing, this.board, 2, rookSideQueen, rookTile.getViTriTile(), 3));
				}
			}
			
		}
		return kingRookMoves;
	}

}
