package board;

import java.util.HashMap;
import java.util.Map;

import piece.Piece;
import valid.ValidBoard;

public abstract class Tile {
	protected int toaDo;
	public Tile(int toaDo) {
		this.toaDo = toaDo;
	}
	private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllEptyTile();
			
	private static Map<Integer, EmptyTile> createAllEptyTile() {
		Map<Integer, EmptyTile> emptyMap = new HashMap<Integer, EmptyTile>();
		for(int i = 0; i < ValidBoard.NUM_TILE; i++) {
			emptyMap.put(i, new EmptyTile(i));
		}
		return emptyMap;
	}
	public abstract boolean isTileOccupied();

	public abstract Piece getPiece();
	
	public abstract String toString();
	
	public static Tile creteTile(int viTriTile, Piece piece) {
		return piece != null ? new TileOccupied(viTriTile, piece) : EMPTY_TILES.get(viTriTile);
	}
	public int getViTriTile() {
		return this.toaDo;
	}
}
