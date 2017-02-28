package pieces;

import java.util.ArrayList;
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
	public List<String> generatePossibleMoves(Piece[][] boardArray, int startRank, int startFile) {
		// negative (-) indicates up or left
		// positive (+) indicates down or right
		
List<String> possibleMoves = new ArrayList<String>();
		
		int[][] offsets = null;
		
		if (boardArray[startRank][startFile].m_player == Player.BLACK) {
			if (!boardArray[startRank][startFile].hasMoved) {
				int[][] o = {
						{1, 0},
						{2, 0},
						{1, -1},
						{1, 1}
				};
				offsets = o;
			} else {
				int[][] o = {
						{1, 0},
						{1, -1},
						{1, 1}
				};
				offsets = o;
			}
		} else if (boardArray[startRank][startFile].m_player == Player.WHITE){
			if (!boardArray[startRank][startFile].hasMoved) {
				int[][] o = {
						{-1, 0},
						{-2, 0},
						{-1, -1},
						{-1, 1}
				};
				offsets = o;
			} else {
				int[][] o = {
						{-1, 0},
						{-1, -1},
						{-1, 1}
				};
				offsets = o;
			}
		}
		
		for (int[] o : offsets) {
			int tempRank = startRank + o[0];
			int tempFile = startFile + o[1];
			
			if ((tempRank >= 0 && tempRank <= 7) && (tempFile >= 0 && tempFile <= 7)) {
				if (tempFile != startFile) {
					if ((boardArray[tempRank][tempFile] != null) && (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player)) {
						if (boardArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
							+ " check");
						} else {
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
							+ " c" + boardArray[tempRank][tempFile].getPieceType());
						}
					}
				} else {
					if (boardArray[tempRank][tempFile] == null) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
					} else {
						break;
					}
				}
			}
		}
		
		return possibleMoves;
	}
}