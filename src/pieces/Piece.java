package pieces;

import java.util.List;

import game.Player;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public abstract class Piece {
	protected boolean hasMoved = false;
	protected boolean enPassant = false;
	protected Player m_player;
	
	/**
	 * 
	 * @param player
	 */
	public Piece(Player player) {
		m_player = player;
	}
	
	@Override
	public abstract String toString();
	
	public Player getPlayer() {
		return m_player;
	}
	
	public void setMoved() {
		this.hasMoved = true;
	}
	
	public boolean getMoved() {
		return hasMoved;
	}
	
	public abstract PieceType getPieceType();
	
	public abstract List<String> generatePossibleMoves(Piece[][] boardArray, int startRank, int startFile);
}