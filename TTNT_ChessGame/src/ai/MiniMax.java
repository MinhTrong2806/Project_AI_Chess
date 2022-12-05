
package ai;
import board.Board;
import move.Move;
import move.MoveUpdate;

public class MiniMax {
	
	private BoardEvaluation boardEvaluation;
	private int depth; 
	
	public MiniMax(int depth) {	
		this.boardEvaluation = new ScoreBoardEvaluation();
		this.depth = depth;
	}
	
	public Move perform(Board board) {
		long startTime = System.nanoTime();
		Move bestMove = null;
		int minValue = Integer.MAX_VALUE;
		int currentValue;
		int alpha = Integer.MIN_VALUE;
		int beta =  Integer.MAX_VALUE;
		//AI Max, Human Min
		for(Move move : board.getCurrentPlayer().getLegalMove()) {
			MoveUpdate moveUpdate = board.getCurrentPlayer().makeMove(move);
			if(moveUpdate.isDone()) {
				 currentValue = max(moveUpdate.getBoardUpdate(), this.depth - 1, alpha, beta);
				 beta = Math.min(beta,currentValue);
				 if(currentValue < minValue){
					minValue = currentValue;
					bestMove = move;
					if(moveUpdate.getBoardUpdate().getWhitePlayer().isInCheckMate()) {
						break;
					}
				}
				 
			}
		}
		long stopTime = System.nanoTime();
		System.out.println("Time: "+(stopTime-startTime)/(1e9)+ " second(s)");	
		return bestMove;
	}
	
	public int min(Board board, int depth, int alpha, int beta) {
		if(depth == 0 || depth < 0 || isEndGame(board)) {
			return boardEvaluation.heuristic(board);
		}
		int minValue = Integer.MAX_VALUE;
		for(Move move : board.getCurrentPlayer().getLegalMove()) {
			MoveUpdate transitionMove = board.getCurrentPlayer().makeMove(move);
			if(transitionMove.isDone()) {
				minValue = Math.min(minValue, max(transitionMove.getBoardUpdate(), depth - 1, alpha, beta));
				beta = Math.min(beta,minValue);
				if(alpha >= beta){
					return minValue;
				}
			}
		}
		return minValue;
	}
	
	public int max(Board board, int depth, int alpha, int beta) {
		if(depth == 0 || depth < 0 || isEndGame(board)) {
			return this.boardEvaluation.heuristic(board); 
		}
		int maxValue = Integer.MIN_VALUE;
		for(Move move : board.getCurrentPlayer().getLegalMove()) {
			MoveUpdate transitionMove = board.getCurrentPlayer().makeMove(move);
			if(transitionMove.isDone()) {
				maxValue = Math.max(maxValue, min(transitionMove.getBoardUpdate(), depth - 1, alpha, beta));
				alpha = Math.max(alpha, maxValue);
				if(alpha >= beta){
					return maxValue;
				}
			}
		}
		return maxValue;
	}

	public boolean isEndGame(Board board) {
		if(board.getCurrentPlayer().isInCheckMate() || board.getCurrentPlayer().isInStaleMate()) return true;
		return false;
	}
}
