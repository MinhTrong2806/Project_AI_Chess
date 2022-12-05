package board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import move.Move;
import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rook;
import player.BlackPlayer;
import player.Player;
import player.WhitePlayer;
import valid.ValidBoard;

public class Board {
	private List<Tile> gameBoard;
	private Collection<Piece> whitePieces;
	private Collection<Piece> blackPieces;
	private WhitePlayer whitePlayer;
	private BlackPlayer blackPlayer;
	private Player currentPlayer;
	public Collection<Move> whileMoves;
	public Collection<Move> blackMoves;
	private Move transitionMove;
	public Board(Builder builder) {
		this.gameBoard = createGameBoard(builder);
		this.whitePieces = currentNumberPiece(this.gameBoard, "white");
		this.blackPieces = currentNumberPiece(this.gameBoard, "black");
		this.whileMoves = currentNumberMove(whitePieces);
		this.blackMoves = currentNumberMove(blackPieces);
		this.whitePlayer = new WhitePlayer(this, whileMoves, blackMoves,"Human");
		this.blackPlayer = new BlackPlayer(this, blackMoves, whileMoves,"AI");
		this.currentPlayer = builder.choosePlayer(this.whitePlayer, this.blackPlayer);
		this.transitionMove = builder.transitionMove != null ? builder.transitionMove : Move.getNullMove();
	}
	
	public List<Tile> getGameBoard() {
		return gameBoard;
	}

	public Collection<Piece> getWhitePieces() {
		return whitePieces;
	}

	public Collection<Piece> getBlackPieces() {
		return blackPieces;
	}
	
	public WhitePlayer getWhitePlayer() {
		return whitePlayer;
	}

	public BlackPlayer getBlackPlayer() {
		return blackPlayer;
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	private List<Tile> createGameBoard(Builder builder) {
		List<Tile> tiles = new ArrayList<Tile>();
		for(int i = 0; i < ValidBoard.NUM_TILE; i++) {
				tiles.add(i,Tile.creteTile(i, builder.boardConfig.get(i)));
		}
		return tiles;
	}
	
	public Collection<Move> currentNumberMove(final Collection<Piece> pieces){
		List<Move> moves = new ArrayList<Move>();
		if(pieces != null) for(final Piece piece : pieces) {
			if(piece.legalMoves(this)!=null) 
				moves.addAll(piece.legalMoves(this));
		}
		return moves;
	}
	
	public Collection<Piece> currentNumberPiece(List<Tile> gameBoard, String color){
		List<Piece> pieces = new ArrayList<Piece>();
		if(gameBoard != null)
			for(Tile tile : gameBoard) {
				if(tile.isTileOccupied()) {
					if(tile.getPiece().getColor()==color) {
						pieces.add(tile.getPiece());
					}
				}
			
			}
		return pieces;
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(); 
		for (int i = 0; i < ValidBoard.NUM_TILE; i++) {
			String tileText = print(this.gameBoard.get(i)); 
			builder.append(String.format("%10s", tileText));
			if((i+1)%ValidBoard.NUM_TILE_ROW ==0) builder.append("\n\n");
		}
		return builder.toString();
	}

	private String print(Tile tile) {
		if(tile.isTileOccupied()) return tile.getPiece().getColor() == "black" ? tile.toString().toLowerCase() : tile.toString().toUpperCase(); 
		return tile.toString();
	}
	
	public Tile getTile(int diemDen) {
		return this.gameBoard.get(diemDen);
	}
	
	public Collection<Piece> getAllPiece() {
		Collection<Piece> allPiece = new ArrayList<Piece>();
		allPiece.addAll(this.whitePieces);
		allPiece.addAll(this.blackPieces);
		return allPiece;
	}
	
	public Collection<Move> getAllMove() {
		Collection<Move> allMove = new ArrayList<Move>();
		allMove.addAll(this.whitePlayer.getLegalMove());
		allMove.addAll(this.blackPlayer.getLegalMove());
		return allMove;
	}
	public void setFalseFirstMove(Piece pieceSet) {
		for(Piece piece : getAllPiece()) {
			if(piece.equals(pieceSet)) {
				piece.setFirstMove();
			}
		}
	}
	public static Board createStartBoard() {
		Builder builder = new Builder();
		builder.setPiece(new Rook(0,"black"));
		builder.setPiece(new Knight(1,"black"));
		builder.setPiece(new Bishop(2,"black"));
		builder.setPiece(new Queen(3,"black"));
		builder.setPiece(new King(4,"black"));
		builder.setPiece(new Bishop(5,"black"));
		builder.setPiece(new Knight(6,"black"));
		builder.setPiece(new Rook(7,"black"));
		builder.setPiece(new Pawn(8,"black"));
		builder.setPiece(new Pawn(9,"black"));
		builder.setPiece(new Pawn(10,"black"));
		builder.setPiece(new Pawn(11,"black"));
		builder.setPiece(new Pawn(12,"black"));
		builder.setPiece(new Pawn(13,"black"));
		builder.setPiece(new Pawn(14,"black"));
		builder.setPiece(new Pawn(15,"black"));
		
		builder.setPiece(new Pawn(48,"white"));
		builder.setPiece(new Pawn(49,"white"));
		builder.setPiece(new Pawn(50,"white"));
		builder.setPiece(new Pawn(51,"white"));
		builder.setPiece(new Pawn(52,"white"));
		builder.setPiece(new Pawn(53,"white"));
		builder.setPiece(new Pawn(54,"white"));
		builder.setPiece(new Pawn(55,"white"));
		builder.setPiece(new Rook(56,"white"));
		builder.setPiece(new Knight(57,"white"));
		builder.setPiece(new Bishop(58,"white"));
		builder.setPiece(new Queen(59,"white"));
		builder.setPiece(new King(60,"white"));
		builder.setPiece(new Bishop(61,"white"));
		builder.setPiece(new Knight(62,"white"));
		builder.setPiece(new Rook(63,"white"));
		builder.setMoveNext("white");
		return builder.build();
	}

	public Move getTransitionMove() {
		return this.transitionMove;
	}
}
