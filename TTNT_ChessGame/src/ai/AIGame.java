package ai;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import gui.BoardGame;
import gui.JGame;
public class AIGame implements Observer{
	private BoardGame boardChess;
	private AIWork ai;
	public AIGame(BoardGame boardChess) {
		this.boardChess = boardChess;
	}
	@Override
	public void update(Observable o, Object arg) {
		if(arg=="AI") {
			if(boardChess.getBoard().getCurrentPlayer().isInCheckMate()) { // human thua se chay dong nay
				System.out.println("Game over Human: " + JGame.setGame().getBoard().getCurrentPlayer().getColorAlliance()); 
				JOptionPane.showMessageDialog(null, "Bạn đã thua vì bị chiếu hết!");
			}
			if(boardChess.getBoard().getCurrentPlayer().isInStaleMate()) {
				JOptionPane.showMessageDialog(null, "Bạn đã thua vì không còn nước đi!");
				System.out.println("Game over2"); //  human thua se chay dong nay
			}
			return;
		}
		ai = new AIWork(boardChess);
		if(arg=="Human") {
			if(boardChess.getBoard().getCurrentPlayer().isAI() && !boardChess.getBoard().getCurrentPlayer().isInCheckMate()) {
				ai.execute();
			}
			if(boardChess.getBoard().getCurrentPlayer().isInCheckMate()) { // ai thua se chay dong nay
				System.out.println("Game over" + JGame.setGame().getBoard().getCurrentPlayer().getColorAlliance()); 
				JOptionPane.showMessageDialog(null, "Bạn đã thắng vì đối phương bị chiếu hết"); 
			}
			if(boardChess.getBoard().getCurrentPlayer().isInStaleMate()) {
				JOptionPane.showMessageDialog(null, "Bạn đã thắng vì dối phương hết nước đi"); 
				System.out.println("Game over"); //  ai thua se chay dong nay
			}
		}


	}

}
