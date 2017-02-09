package pieces;

import java.util.*;
import game.*;

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
		Collection<String> possibleMoves = new ArrayList<String>();
		return possibleMoves;
	}
}