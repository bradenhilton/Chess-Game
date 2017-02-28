package pieces;

import java.util.ArrayList;
import java.util.List;

import game.Player;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class King extends Piece {
	protected PieceType m_type;
	
	public King(Player player) {
		super(player);
		m_type = PieceType.KING;
	}
	
	public PieceType getPieceType() {
		return m_type;
	}

	@Override
	public String toString() {
		return m_player.toString().toLowerCase().substring(0, 1) + m_type.toString().substring(0, 2);
	}
	
	@Override
	public List<String> generatePossibleMoves(Piece[][] boardArray, int startRank, int startFile) {
		// negative (-) indicates up or left
		// positive (+) indicates down or right
		
		List<String> attackerMoves = new ArrayList<String>();
		List<String> possibleMoves = new ArrayList<String>();
		
		int[][] offsets = {
	            {-1, 0},
	            {-1, 1},
	            {-1, -1},
	            {0, -1},
	            {0, 1},
	            {1, 0},
	            {1, 1},
	            {1, -1}
	        };
		
		// generates temporary moves
		for (int[] o : offsets) {
			int tempRank = startRank + o[0];
			int tempFile = startFile + o[1];
			
			if ((tempRank >= 0 && tempRank <= 7) && (tempFile >= 0 && tempFile <= 7)) {
				if (boardArray[tempRank][tempFile] == null) {
					possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
				} else if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
					if (boardArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
						+ " check");
					} else {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
						+ " c" + boardArray[tempRank][tempFile].getPieceType());
					}
				}
			}
		}
		
		// checks for attacking pieces on temporary moves
		for (int ranks = 0; ranks < 8; ranks++) {
			for (int files = 0; files < 8; files++) {
				if (boardArray[ranks][files] != null) {
					if (boardArray[ranks][files].m_player != boardArray[startRank][startFile].m_player) {
						attackerMoves.addAll(boardArray[ranks][files].generatePossibleMoves(boardArray, ranks, files));
					}
				}
			}
		}
		
		// cross checks attacker moves with possible moves
		for (int i = 0; i < attackerMoves.size(); i++) {
			for (int j = 0; j < possibleMoves.size(); j++) {
				if (attackerMoves.get(i).substring(2, 4).equals(possibleMoves.get(j).substring(2, 4))){
					possibleMoves.remove(j);
				}
			}
		}
		
		return possibleMoves;
	}
}