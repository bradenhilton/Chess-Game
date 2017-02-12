package pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import game.Player;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class Knight extends Piece {
	protected PieceType m_type;
	
	public Knight(Player player) {
		super(player);
		m_type = PieceType.KNIGHT;
	}
	
	public PieceType getPieceType() {
		return m_type;
	}

	@Override
	public String toString() {
		return m_player.toString().toLowerCase().substring(0, 1) + m_type.toString().substring(0, 2);
	}

	@Override
	public Collection<String> generatePossibleMoves(Piece[][] boardArray, int startRank, int startFile) {
		// negative (-) indicates up or left
		// positive (+) indicates down or right
		
		List<String> possibleMoves = new ArrayList<String>();
		
		if (m_player == Player.BLACK) {
			
		} else if (m_player == Player.WHITE) {
			
		}
		
		return possibleMoves;
	}
}