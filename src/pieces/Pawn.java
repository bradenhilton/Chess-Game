package pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import game.Player;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
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
		// negative (-) indicates up or left
		// positive (+) indicates down or right
		
		List<String> possibleMoves = new ArrayList<String>();
		
		if (m_player == Player.BLACK) {			
			if (boardArray[startRank+1][startFile] == null) {
				// move by one
				possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank+1) + String.valueOf(startFile));
			}
			
			if ((startFile+1) <= 7 && boardArray[startRank+1][startFile+1] != null && boardArray[startRank+1][startFile+1].m_player != Player.BLACK) {
				if (boardArray[startRank+1][startFile+1].getPieceType() == PieceType.KING) {
					// check
					possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank+1) + String.valueOf(startFile+1)
					+ " check");	
				} else {
					// capture diagonally left
					possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank+1) + String.valueOf(startFile+1)
					+ " c" + boardArray[startRank+1][startFile+1].getPieceType().toString());
				}
			}
			
			if ((startFile-1) >= 0 && boardArray[startRank+1][startFile-1] != null && boardArray[startRank+1][startFile-1].m_player != Player.BLACK) {
				if (boardArray[startRank+1][startFile-1].getPieceType() == PieceType.KING) {
					// check
					possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank+1) + String.valueOf(startFile-1)
					+ " check");	
				} else {
					// capture diagonally right
					possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank+1) + String.valueOf(startFile-1)
					+ " c" + boardArray[startRank+1][startFile-1].getPieceType().toString());
				}
			}
			
			if (!hasMoved) {
				if (boardArray[startRank+2][startFile] == null) {
					// move by two
					possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank+2) + String.valueOf(startFile));
				}
			}
		} else if (m_player == Player.WHITE) {
			if (boardArray[startRank-1][startFile] == null) {
				// move by one
				possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank-1) + String.valueOf(startFile));
			}
			
			if ((startFile+1) <= 7 && boardArray[startRank-1][startFile+1] != null && boardArray[startRank-1][startFile+1].m_player != Player.WHITE) {
				if (boardArray[startRank-1][startFile+1].getPieceType() == PieceType.KING) {
					// check
					possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank-1) + String.valueOf(startFile+1)
					+ " check");	
				} else {
					// capture diagonally left
					possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank-1) + String.valueOf(startFile+1)
					+ " c" + boardArray[startRank-1][startFile+1].getPieceType().toString());
				}
			}
			
			if ((startFile-1) >= 0 && boardArray[startRank-1][startFile-1] != null && boardArray[startRank-1][startFile-1].m_player != Player.WHITE) {
				if (boardArray[startRank-1][startFile-1].getPieceType() == PieceType.KING) {
					// check
					possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank-1) + String.valueOf(startFile-1)
					+ " check");	
				} else {
					// capture diagonally right
					possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank-1) + String.valueOf(startFile-1)
					+ " c" + boardArray[startRank-1][startFile-1].getPieceType().toString());
				}
			}
			
			if (!hasMoved) {
				if (boardArray[startRank-2][startFile] == null) {
					// move by two
					possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank-2) + String.valueOf(startFile));
				}
			}
		}
		
		return possibleMoves;
	}
}