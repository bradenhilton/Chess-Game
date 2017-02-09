package pieces;

import java.util.*;
import game.*;

public class Bishop extends Piece {
	protected PieceType m_type;
	
	public Bishop(Player player) {
		super(player);
		m_type = PieceType.BISHOP;
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
		//int deltaRank = Math.abs(destRank - startRank);
		//int deltaFile = Math.abs(destFile - startFile);
		
		//return deltaRank == deltaFile;
		Collection<String> possibleMoves = new ArrayList<String>();
		return possibleMoves;
	}
}