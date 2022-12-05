package board;

import java.util.HashMap;
import java.util.Map;

import move.Move;
import piece.Piece;
import player.BlackPlayer;
import player.Player;
import player.WhitePlayer;

public class Builder {
	public Map<Integer,Piece> boardConfig;
	private String color;
	public Move transitionMove;
	
	public Builder() {
		this.boardConfig = new HashMap<Integer,Piece>();
	}

	public Builder(Map<Integer, Piece> boardConfig, String color) {
		super();
		this.boardConfig = boardConfig;
		this.color = color;
	}
	
	public Builder setMoveNext(String color) {
		this.color = color;
		return this;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return this.color;
	}
	public Builder setPiece(Piece piece) {
		boardConfig.put(piece.getPositonPiece(), piece);
		return this;
	}
	
	public Builder setPiece(String color) {
		this.color = color;
		return this;
	}
	
	public Builder setMoveTransition(Move transionMove) {
		this.transitionMove =  transionMove;
		return this;
	}
	public Board build() {
		return new Board(this);
	}

	public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
		if(this.color == "white") return whitePlayer;
		return blackPlayer;
	}

}
