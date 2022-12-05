package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import board.Board;
import board.Tile;
import piece.Piece;
import valid.ValidBoard;

public class BoardGame extends JPanel{
	private List<TilePanel> tiles;
	private Board board;
	public int depth;
	public Tile tileTemp;
	public Piece pieceMoveTemp;
	public Tile destinationCoordinatesTemp;

	public BoardGame(Board board, int depth , Dimension sizeBoardPanel ) {
		this.board = board;
		this.depth = depth;
		this.tiles = new ArrayList<TilePanel>();
		this.setLayout(new GridLayout(8, 8));
		for(int i = 0; i < ValidBoard.NUM_TILE;i++) {
			TilePanel tile = new TilePanel(this,i);
			tiles.add(tile);
			this.add(tile);
		}
		setColorTile();
		this.setSize(sizeBoardPanel);
		validate();

	}
	
	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	private void setColorTile() {
		for (int i = 0; i < ValidBoard.NUM_TILE; i++)
        {
            int row = (i / 8) % 2;
            if (row == 0)
                this.tiles.get(i).setBackground( i % 2 == 0 ? new Color(209, 139, 71) : new Color(255, 206, 158) );
            else
            	this.tiles.get(i).setBackground( i % 2 == 0 ? new Color(255, 206, 158) : new Color(209, 139, 71));
        }
		
	}
	
	public void drawBoard(Board boardRepaint) {
		removeAll();
		for(TilePanel tile : tiles) {
			tile.setRepaintIcon(boardRepaint);
			add(tile);
		}
		setColorTile();
		setHightLightInCheck();
		validate();
		repaint();
	}

	public void setOffHighLight(Board boardReapaint) {
		for(TilePanel tile : tiles) {
			tile.setOffHighLightBorderTile(boardReapaint);
		}
		validate();
		repaint();
	}
	
	public Board getBoard() {
		return this.board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setHightLighTileAI(int diemDen) {
		for(TilePanel tile : tiles) {
			if(tile.getIdTile()== diemDen) {
				tile.setHighLightBorderGreen();
			}
		}
	}
	
	public void setHightLightInCheck() {
		if(board.getCurrentPlayer().isInCheck()) {
			for(TilePanel tile : tiles) {
				if(tile.getIdTile() == board.getCurrentPlayer().getPlayerKing().getPositonPiece()) {
					tile.setHighLightBorderRed();
				}
			}
		}
	}
	
	public TilePanel getTilePanel(int id) {
		for(TilePanel tile : tiles) {
			if(tile.getIdTile()== id) {
				return tile;
			}
		}
		return null;
	}
	
}
