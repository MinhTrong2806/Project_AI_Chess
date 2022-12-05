package ai;
import javax.swing.SwingWorker;
import gui.BoardGame;
import gui.JGame;
import move.Move;
import move.MoveUpdate;

public class AIWork extends SwingWorker<Move, String>{
	private BoardGame boardChess;
	public AIWork(BoardGame boardChess) {
		this.boardChess = boardChess;
	}
	@Override
	protected Move doInBackground() throws Exception {
		// chạy thuật toán minimax trả về bestMove
		Move move = null;
		if(boardChess.getBoard().getCurrentPlayer().isAI()) {
			MiniMax minimax = new MiniMax(boardChess.depth);
			move = minimax.perform(boardChess.getBoard());
		}
		return move;
	}
	
	@Override 
	 protected void done() {
         try {
        	 Move move = get();
        	 if(boardChess.getBoard().getCurrentPlayer().isAI()) {
        		 MoveUpdate tranMove =  boardChess.getBoard().getCurrentPlayer().makeMove(move);
        		 if(tranMove.isDone()) {
        			 boardChess.setBoard(tranMove.getBoardUpdate());
        		 }
        		 JGame.setGame().getBoardGame(boardChess).setOffHighLight(boardChess.getBoard());
        		 if(!boardChess.getBoard().getCurrentPlayer().isInCheckMate() && !JGame.setGame().getBoardGame(boardChess).getBoard().getCurrentPlayer().isInStaleMate()) { // more
            		 JGame.setGame().getBoardGame(boardChess).setHightLighTileAI(move.getDestinationCoordinates());
        		 }
                 JGame.setGame().getBoardGame(boardChess).drawBoard(boardChess.getBoard());
                 JGame.setGame().moveMadeUpdate("AI");
        	 }
         } catch (Exception e) {
        	 e.printStackTrace();
         }
     }

}
