package move;

import board.Board;
import piece.Piece;
import valid.ValidMove;

public class MoveUpdate {
	private Board board;
	private Move move;
	private String moveStatus;
	
	public MoveUpdate(Board updateBoard, Move move, String moveStatus) {
		this.board = updateBoard;
		this.move = move;
		this.moveStatus = moveStatus;
	}
	
	public String getMoveStatus() {
		return this.moveStatus;
	}
	
	public boolean isDone() {
		switch (moveStatus) {
		case "DONE":
			return true;
		case "ILLEGAL":
			return false;
		case "LEAVES_PLAYER_IN_CHECK":
			return false;
		default:
			break;
		}
		return false;
	}

	public Board getBoardUpdate() {
		return this.board;
	}

}
