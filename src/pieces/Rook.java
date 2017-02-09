package pieces;

import java.util.*;
import game.*;

public class Rook extends Piece {
	protected PieceType m_type;
	
	public Rook(Player player) {
		super(player);
		m_type = PieceType.ROOK;
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