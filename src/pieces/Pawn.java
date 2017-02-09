package pieces;

import java.util.*;
import game.*;

public class Pawn extends Piece {
	protected PieceType m_type;
	
	public Pawn(Player player) {
		super(player);
		m_type = PieceType.PAWN;
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
		List<String> possibleMoves = new ArrayList<String>();
		
		if (m_player == Player.BLACK) {			
				if (boardArray[startRank+1][startFile] == null) {
					possibleMoves.add(String.valueOf(startRank+1) + String.valueOf(startFile));		// move by one
				}
				if ((startFile+1) <= 7 && boardArray[startRank+1][startFile+1] != null && boardArray[startRank+1][startFile+1].m_player != Player.BLACK) {
					possibleMoves.add(String.valueOf(startRank+1) + String.valueOf(startFile+1));	// capture diagonally left
				}
				if ((startFile-1) >= 0 && boardArray[startRank+1][startFile-1] != null && boardArray[startRank+1][startFile-1].m_player != Player.BLACK) {
					possibleMoves.add(String.valueOf(startRank+1) + String.valueOf(startFile-1));	// capture diagonally right
				}
				if (!hasMoved) {
					if (boardArray[startRank+2][startFile] == null) {
						possibleMoves.add(String.valueOf(startRank+2) + String.valueOf(startFile));	// move by two
					}
				}
		} else if (m_player == Player.WHITE) {
			if (boardArray[startRank-1][startFile] == null) {
				possibleMoves.add(String.valueOf(startRank-1) + String.valueOf(startFile));		// move by one
			}
			if ((startFile+1) <= 7 && boardArray[startRank-1][startFile+1] != null && boardArray[startRank-1][startFile+1].m_player != Player.WHITE) {
				possibleMoves.add(String.valueOf(startRank-1) + String.valueOf(startFile+1));	// capture diagonally right
			}
			if ((startFile-1) >= 0 && boardArray[startRank-1][startFile-1] != null && boardArray[startRank-1][startFile-1].m_player != Player.WHITE) {
				possibleMoves.add(String.valueOf(startRank-1) + String.valueOf(startFile-1));	// capture diagonally left
			}
			if (!hasMoved) {
				if (boardArray[startRank-2][startFile] == null) {
					possibleMoves.add(String.valueOf(startRank-2) + String.valueOf(startFile));	// move by two
				}
			}
		}
		
		return possibleMoves;
	}
}