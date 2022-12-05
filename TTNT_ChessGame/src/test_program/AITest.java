package test_program;

import ai.MiniMax;
import board.Board;
import junit.framework.TestCase;
import move.Move;
import move.MoveUpdate;
import valid.ValidBoard;

public class AITest extends TestCase {
	public void test() {
		Board board = Board.createStartBoard();
		MoveUpdate t1 = board.getCurrentPlayer().makeMove(Move.createMove(board, ValidBoard.getCoordinateWithPosition("f2"), 
		ValidBoard.getCoordinateWithPosition("f3")));
		assertTrue(t1.isDone());

		MoveUpdate t2 = t1.getBoardUpdate().getCurrentPlayer().makeMove(Move.createMove(t1.getBoardUpdate(), ValidBoard.getCoordinateWithPosition("e7"), 
		ValidBoard.getCoordinateWithPosition("e5")));
		assertTrue(t2.isDone());
		
		MoveUpdate t3 = t2.getBoardUpdate().getCurrentPlayer().makeMove(Move.createMove(t2.getBoardUpdate(), ValidBoard.getCoordinateWithPosition("g2"), 
		ValidBoard.getCoordinateWithPosition("g4")));
		assertTrue(t3.isDone());
		
		MiniMax miniMax = new MiniMax(4);
		Move aiMove = miniMax.perform(t3.getBoardUpdate());
		Move bestMove = Move.createMove(t3.getBoardUpdate(),  ValidBoard.getCoordinateWithPosition("d8"),  ValidBoard.getCoordinateWithPosition("h4"));
		assertEquals(aiMove, bestMove);
	}
}
