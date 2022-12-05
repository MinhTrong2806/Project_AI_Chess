package ai;

import java.util.ArrayList;
import java.util.Collection;
import board.Board;
import move.Move;
import piece.Piece;
import player.Player;

public class ScoreBoardEvaluation implements BoardEvaluation {
	private final static int CHECK_SCORE = 50;
	private final static int CHECK_MATE_SCORE = 10000;
	private final static int CASTLING_SCORE = 25;
	private final static int TWO_ROOK_SCORE = 30;
	private final static int TWO_BISHOPS_SCORE = 25;
	private final static int TWO_KNIGHT_SCORE = 25;
	private final static int ATTACK_SCORE = 10;
	private final static int FORK_SCORE = 25;

	public int heuristic(Board board) {
		return scorePlayer(board.getWhitePlayer(), board) - scorePlayer(board.getBlackPlayer(), board);
	}

	private int scorePlayer(Player player, Board board) {
		return caculFavorable(player) 
				+ kingThreats(player) 
				+ checkMate(player) 
				+ attacks(player) 
				+ casling(player)
				+ pieceEvaluations(player) 
				+ fork(player, board);
	}

	private int pieceEvaluations(Player player) {
		int pieceValuationScore = 0;
		int numBishops = 0;
		int numRooks = 0;
		int numKnights = 0;
		for (final Piece piece : player.getActivePieces()) {
			pieceValuationScore += piece.getValuePiece() + piece.locationScore();
			if (piece.getPieceType() == "B") {
				numBishops++;
			}
			if (piece.getPieceType() == "R") {
				numRooks++;
			}
			if (piece.getPieceType() == "N") {
				numKnights++;
			}
		}
		return pieceValuationScore + (numBishops == 2 ? TWO_BISHOPS_SCORE : 0) + (numRooks == 2 ? TWO_ROOK_SCORE : 0)
				+ (numKnights == 2 ? TWO_KNIGHT_SCORE : 0);
	}

	private int attacks(Player player) {
		int attackScore = 0;
		Piece movedPiece = null;
		Piece attackedPiece = null;
		if (player.getLegalMove() != null)
			for (Move move : player.getLegalMove()) {
				if (move.isAttack()) {
					if (move.getMovePiece() != null)
						movedPiece = move.getMovePiece();
					if (move.getAttackedPiece() != null)
						attackedPiece = move.getAttackedPiece();
					if (movedPiece != null && attackedPiece != null) {
						if (movedPiece.getValuePiece() <= attackedPiece.getValuePiece()) {
							attackScore++;
						}
					}
				}
			}
		return attackScore * ATTACK_SCORE;
	}

	public int fork(Player player, Board board) {
		int forkScore = 0;
		if (!player.getActivePieces().isEmpty() || player.getActivePieces()!= null)
			for (Piece piece : player.getActivePieces()) {
				int countPieceAttacked = 0;
				for(Move move : piece.legalMoves(board)) {
					if(move.isAttack()) {
						countPieceAttacked++;
					}
				}
				if(countPieceAttacked >= 2) forkScore++;
			}
		return forkScore * FORK_SCORE;
	}

	private int checkMate(Player player) {
		return player.getOpponent().isInCheckMate() ? CHECK_MATE_SCORE : 0;
	}

	private int kingThreats(Player player) {
		return player.getOpponent().isInCheckMate() ? CHECK_MATE_SCORE : check(player);
	}

	private static int check(final Player player) {
		return player.getOpponent().isInCheck() ? CHECK_SCORE : 0;
	}

	private int caculFavorable(Player player) {
		return ((int) player.getLegalMove().size() / player.getOpponent().getLegalMove().size()) * 10;
	}

	private int casling(Player player) {
		return player.isCastling() ? CASTLING_SCORE : 0;
	}
}
