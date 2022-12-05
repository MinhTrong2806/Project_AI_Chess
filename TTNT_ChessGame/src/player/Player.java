package player;

import java.util.ArrayList;
import java.util.Collection;

import piece.Queen;
import valid.ValidMove;
import board.Board;
import move.Move;
import move.MoveUpdate;
import move.NullMove;
import piece.King;
import piece.Piece;

public abstract class Player {
	protected Board board;
	protected King playerKing;
	protected Collection<Move> legalMoves;
	protected boolean isInCheck;
	protected String PlayerType;
	protected Collection<Move> castlingMoves;
	public Player(Board board, Collection<Move> legalMoves, Collection<Move> opponenMoves, String PlayerType) {
		this.board = board;
		this.PlayerType = PlayerType;
		this.playerKing = establishKing();
		this.legalMoves = getAllLegalMove(legalMoves,opponenMoves);
		this.castlingMoves = culRookKing(legalMoves, opponenMoves);
		this.isInCheck = !Player.culAttackOnPiece(this.playerKing.getPositonPiece(), opponenMoves).isEmpty();
	}
	
	private Collection<Move> getAllLegalMove(Collection<Move> legalMoves, Collection<Move> opponenMoves) {
		Collection<Move> allMove = new ArrayList<Move>();
		allMove.addAll(legalMoves);
		if(culRookKing(legalMoves, opponenMoves)!=null) allMove.addAll(culRookKing(legalMoves, opponenMoves));
		return allMove;
	}

	private King establishKing() {
		if(getActivePieces()!=null) 
			for(Piece piece: getActivePieces()) {
				if(piece.isKing()) return (King) piece;
			}
		return null;
	}
	
	protected boolean hasEscapeMove() {
		if(legalMoves !=null) 
			for(Move move : legalMoves) {
				MoveUpdate transition = makeMove(move);
				if(transition.isDone()) {
					return true;
				}
			}
	    return false;
	}
	
	public King getPlayerKing() {
		return this.playerKing;
	}
	
	public Collection<Move> getLegalMove(){
		return this.legalMoves;
	}
	
	public boolean isLegalMove(Move moveCheck) {
		return legalMoves.contains(moveCheck);
	}
	
	public boolean isInCheck() { // Chiếu
		return this.isInCheck;
	}
	
	public boolean isInCheckMate() { // Chiếu hết
		return this.isInCheck && !hasEscapeMove();
	}
	
	public boolean isInStaleMate() { // hết nước đi - không bị chiếu cũng k còn ô an toàn để đi tới
		return !this.isInCheck && !hasEscapeMove();
	}
	
	public boolean isCastling() {
		return playerKing.isCastling();
	}
	
	public MoveUpdate makeMove(Move move) {
		if(!isLegalMove(move)) {
			return new MoveUpdate(board, move, ValidMove.ILLEGAL);
		}
		if(move instanceof NullMove) {
			return new MoveUpdate(board, move, ValidMove.ILLEGAL);
		}
		Board transitionBoard = move.execute();
		if(transitionBoard.getCurrentPlayer().getOpponent().isInCheck()) {
			return new MoveUpdate(this.board, move, ValidMove.LEAVES_PLAYER_IN_CHECK);//ko bi chieu
		}
		return new MoveUpdate(transitionBoard, move, ValidMove.DONE);
	}
	
	public abstract Collection<Piece> getActivePieces();

	public static Collection<Move> culAttackOnPiece(int piecePosition, Collection<Move> opponenMoves) {
		Collection<Move> attackMoves = new ArrayList<Move>(); 
		if(opponenMoves != null) 
			for(Move move : opponenMoves) {
				if(piecePosition == move.getDestinationCoordinates()) {
					attackMoves.add(move);
				}
			}
		return attackMoves;
	}
	
	@Override
	public String toString() {
		return "Player [PlayerType=" + PlayerType + "]";
	}

	public abstract Player getOpponent();
	
	public abstract String getColorAlliance();
	
	public abstract String getPlayerType();
	
	public abstract boolean isAI();
	
	protected abstract Collection<Move> culRookKing(Collection<Move> playerLegals, Collection<Move> opponentsLegals);
	
	public Collection<Move> getCastlingMoves(){
		return this.castlingMoves;
	}
	
	public Collection<Piece> getPieceActiveForPiece(String typePiece) {
		Collection<Piece> listPiece = new ArrayList<Piece>();
		if(getActivePieces() != null) 
			for(Piece piece: getActivePieces()) {
				if(piece.getPieceType() == typePiece) {
					listPiece.add(piece);
				};
		}
		return listPiece;
	}

	public Queen getQueen() {
		if(getActivePieces()!=null) 
			for(Piece piece: getActivePieces()) {
				if(piece.getPieceType() == "Q") {
					return (Queen) piece;
				};
		}
		return null;
	}

}
