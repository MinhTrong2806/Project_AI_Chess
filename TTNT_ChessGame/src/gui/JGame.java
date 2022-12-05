package gui;
import javax.swing.*;

import ai.AIGame;
import board.Board;
import move.Move;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
public class JGame extends Observable{
	private JFrame gameFrame;
	private BoardGame boardChess;
	public Board board;
	private Move AIMove;
	private Dimension sizeGame = new Dimension(700, 700);
	private Dimension sizeBoardPanel = new Dimension(600, 600);
	private static JGame game = new JGame();
	private JMenuBar menuBar; // more
	private JMenu menuGame;
	private JMenu menuDepth;
	private JMenuItem menuItemNewGame;
	private JMenuItem menuItemDepth1;
	private JMenuItem menuItemDepth2;
	private JMenuItem menuItemDepth3;
	private JMenuItem menuItemDepth4;
	private JMenuItem menuItemDepth5;
	private JMenuItem menuItemDepth6;
	private ImageIcon newGameIcon;
	private ImageIcon DepthIcon;

	public JGame() {
		this.gameFrame = new JFrame();
		this.gameFrame.setTitle("Chess Game");
		this.gameFrame.getContentPane().setLayout(new BorderLayout());
		this.menuBar = new JMenuBar();
		this.gameFrame.setJMenuBar(menuBar);
		this.menuGame = new JMenu();
		menuGame.setFont(new Font("Cambria Math", Font.BOLD, 14));
		this.menuItemNewGame = new JMenuItem("    New Game");
		menuItemNewGame.setFont(new Font("Cambria Math", Font.BOLD, 16));
		menuItemNewGame.setPreferredSize(new Dimension(112, 25));
		this.menuGame.add(menuItemNewGame);
		this.menuItemNewGame.addActionListener(action());
		this.menuDepth = new JMenu();	
		
		this.menuItemDepth1 = new JMenuItem();
		menuItemDepth1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardChess.setDepth(1);
			}
		});
		
		menuItemDepth1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuItemDepth1.setText("         1");
		menuItemDepth1.setPreferredSize(new Dimension(95, menuItemDepth1.getPreferredSize().height));
		menuItemDepth1.setHorizontalTextPosition(SwingConstants.CENTER);
		menuItemDepth1.setSelected(true);
		
		this.menuItemDepth2 = new JMenuItem();
		menuItemDepth2.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuItemDepth2.setText("         2");
		menuItemDepth2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardChess.setDepth(2);
			}
		});
			
		this.menuItemDepth3 = new JMenuItem("         3");
		menuItemDepth3.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuItemDepth3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardChess.setDepth(3);
			}
		});
		
		this.menuItemDepth4 = new JMenuItem("         4");
		menuItemDepth4.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuItemDepth4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardChess.setDepth(4);
			}
		});
		
		this.menuItemDepth5 = new JMenuItem("         5");
		menuItemDepth5.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuItemDepth5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardChess.setDepth(5);
			}
		});
		
		this.menuItemDepth6 = new JMenuItem("         6");
		menuItemDepth6.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuItemDepth6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardChess.setDepth(6);
			}
		});
		
		this.menuDepth.add(menuItemDepth1);
		this.menuDepth.add(menuItemDepth2);
		this.menuDepth.add(menuItemDepth3);
		this.menuDepth.add(menuItemDepth4);
		this.menuDepth.add(menuItemDepth5);
		this.menuDepth.add(menuItemDepth6);
		
		this.menuBar.add(menuGame);
		this.menuBar.add(menuDepth);
		
		this.newGameIcon = new ImageIcon("D:\\Workspace\\TTNT_ChessGame\\Image\\menugame.png");
		this.DepthIcon = new ImageIcon("D:\\Workspace\\TTNT_ChessGame\\Image\\menudepth.png");
		this.menuGame.setIcon(newGameIcon);
		this.menuDepth.setIcon(DepthIcon);
		this.board = Board.createStartBoard();
		this.boardChess = new BoardGame(board, 1 , sizeBoardPanel);
		this.addObserver(new AIGame(boardChess));
		this.gameFrame.getContentPane().add(boardChess, BorderLayout.CENTER);
		this.gameFrame.setSize(this.sizeGame);
		this.gameFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - (this.gameFrame.size().width))/2, (Toolkit.getDefaultToolkit().getScreenSize().height - (this.gameFrame.size().height))/2);
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameFrame.setVisible(true);
	}
	
	public ActionListener action() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameStart();
			}
		};
		
	}
	
	public void moveMadeUpdate(String string) {
		setChanged();
		notifyObservers(string);
	}
	public static JGame setGame() {
		return game;
	}
	public BoardGame getBoardGame(BoardGame board) {
		return board;
	}
	public Board getBoard(Board board) {
		return board;
	}
	public Board getBoard() {
		return board;
	}
	public void newGame() {
		this.boardChess.drawBoard(JGame.setGame().getBoard());
	}
	public void GameStart() {
		this.gameFrame.setVisible(false);// more exit
		game = new JGame();
	}
	public void updateBoard(Board board) {
		this.board = board;
	}
	public void updateAIMove(Move move) {
		this.AIMove = move;
	}
}
