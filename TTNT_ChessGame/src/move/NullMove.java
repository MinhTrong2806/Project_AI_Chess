package move;

import board.Board;
import piece.Piece;

public class NullMove extends Move{
	public NullMove() {
		super(null, null, -1);
	}
	@Override
	public Board execute() {
		throw new Error("Error Null Move");
	}
	@Override
	public boolean isAttack() {
		return false;
	}

}
