package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import board.Board;
import move.Move;
import move.MoveUpdate;

public class TilePanel extends JPanel {
	private int idTile;
	private Dimension sizeTile = new Dimension(75, 75);
	private String path = "D:\\Workspace\\TTNT_ChessGame\\Image\\";
	private BoardGame boardGame;
	public TilePanel(BoardGame boardGame, int id) {
		this.idTile = id;
		this.boardGame = boardGame;
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(sizeTile);
		setIconTile(boardGame.getBoard());
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				
				if(boardGame.getBoard().getCurrentPlayer().isAI()) {
					return;
				}
				
				if (SwingUtilities.isRightMouseButton(e)) {
					boardGame.tileTemp = null;
					boardGame.pieceMoveTemp = null;
					boardGame.destinationCoordinatesTemp = null;
					boardGame.setOffHighLight(boardGame.getBoard());
					boardGame.drawBoard(boardGame.getBoard());
				} else if (SwingUtilities.isLeftMouseButton(e)) {
				
					if (boardGame.tileTemp == null) {
						// Chon co
						boardGame.tileTemp = boardGame.getBoard().getTile(id);
						boardGame.pieceMoveTemp = boardGame.tileTemp.getPiece();
						if (boardGame.pieceMoveTemp  == null) {
							boardGame.tileTemp = null;
						}
					} else {
						// Tha co			
						boardGame.destinationCoordinatesTemp = boardGame.getBoard().getTile(id);
						if(!boardGame.destinationCoordinatesTemp.isTileOccupied() || !(boardGame.destinationCoordinatesTemp.getPiece().getColor() == boardGame.getBoard().getCurrentPlayer().getColorAlliance())) {
							Move move = Move.createMove(boardGame.getBoard(),boardGame.pieceMoveTemp.getPositonPiece(), boardGame.destinationCoordinatesTemp.getViTriTile());
							MoveUpdate transition = boardGame.getBoard().getCurrentPlayer().makeMove(move);
							if(transition.isDone()) {
								boardGame.setBoard(transition.getBoardUpdate());
							}else {
								JOptionPane.showMessageDialog(null, "Nước đi không hợp lệ!");
							}
							boardGame.setOffHighLight(boardGame.getBoard());
							boardGame.tileTemp = null;
							boardGame.pieceMoveTemp = null;
							boardGame.destinationCoordinatesTemp = null;
							
						}else {
							boardGame.tileTemp = boardGame.getBoard().getTile(id);
							boardGame.pieceMoveTemp = boardGame.tileTemp.getPiece();
							if (boardGame.pieceMoveTemp  == null) {
								boardGame.tileTemp = null;
							}
							boardGame.setOffHighLight(boardGame.getBoard());
							boardGame.drawBoard(boardGame.getBoard());
						}
					}
					SwingUtilities.invokeLater(() -> {
						boardGame.drawBoard(boardGame.getBoard());
                        if (boardGame.getBoard().getCurrentPlayer().isAI()) {
                            JGame.setGame().moveMadeUpdate("Human");
                        }
                    });
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		validate();
	}
	public int getIdTile() {
		return idTile;
	}
	private void setIconTile(Board board) {	
		if (board.getTile(this.idTile).isTileOccupied()) add(new JLabel(new ImageIcon(path + board.getTile(idTile).getPiece().getColor().substring(0, 1)+ board.getTile(idTile).getPiece().toString() + ".png")));
	}

	private void setHighLightTile(Board board) {
		if(true) {
		if(pieceLegalMove(board)!=null)	for(Move move : pieceLegalMove(board)) {
				if(move.getDestinationCoordinates() == this.idTile) {
					if(!board.getTile(move.getDestinationCoordinates()).isTileOccupied()) {
						this.add(new JLabel(new ImageIcon(path + "dot_green.png")));
					}else {
						this.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
					}
				}
			}
		}
	}
	// Nhap thanh
	
	private void setHighLightTileCastling(Board board) {
		if(pieceCastlingMove(board)!=null)	for(Move move : pieceCastlingMove(board)) {
			if(move.getDestinationCoordinates() == this.idTile) {
				if(!board.getTile(move.getDestinationCoordinates()).isTileOccupied()) {
					this.add(new JLabel(new ImageIcon(path + "dot_cyan.png")));
				}
			}
		}
	}
	
	private Collection<Move> pieceCastlingMove(Board board) {
		if(boardGame.pieceMoveTemp != null &&  boardGame.pieceMoveTemp.getColor() == boardGame.getBoard().getCurrentPlayer().getColorAlliance()) {
			if(boardGame.pieceMoveTemp.isKing()) {
				return this.boardGame.getBoard().getCurrentPlayer().getCastlingMoves();
			}
		}
		return null;
	}
	// End nhap thanh
	
	
	public void setHighLightBorderTileMove(Board board) {
		if(boardGame.pieceMoveTemp != null && boardGame.pieceMoveTemp.getColor() == board.getCurrentPlayer().getColorAlliance() && boardGame.pieceMoveTemp.getPositonPiece() == this.idTile) {
			this.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		}
	}
	
	public void setHighLightBorderGreen() {
			this.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
	}
	
	public void setHighLightBorderRed() {
		this.setBorder(BorderFactory.createLineBorder(Color.red, 3));
	}
	public void setOffHighLightBorderTile(Board board) {
		this.setBorder(null);
		repaint();
	}
	
	private Collection<Move> pieceLegalMove(Board board) {
		if(boardGame.pieceMoveTemp != null &&  boardGame.pieceMoveTemp.getColor() == boardGame.getBoard().getCurrentPlayer().getColorAlliance())
		return boardGame.pieceMoveTemp.legalMoves(board);
		return null;
	}

	public void setRepaintIcon(Board boardRepanit) {
		removeAll();
		setHighLightTile(boardRepanit);
		setHighLightTileCastling(boardRepanit);
		setHighLightBorderTileMove(boardRepanit);
		setIconTile(boardRepanit);
	}

}
