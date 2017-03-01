package pieces;

import java.util.ArrayList;
import java.util.List;

import game.Player;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
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
	public List<String> generatePossibleMoves(Piece[][] boardArray, int startRank, int startFile) {
		// negative (-) indicates up or left
		// positive (+) indicates down or right
		
		List<String> possibleMoves = new ArrayList<String>();
		
		upLeft: {
			for (int tempRank = startRank - 1; tempRank >= 0; tempRank--) {
				for (int tempFile = startFile - 1; tempFile >= 0; tempFile--) {
					if (Math.abs(tempRank - startRank) == Math.abs(tempFile - startFile)) {
						if (boardArray[tempRank][tempFile] == null) {
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
						} else {
							if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
								if (boardArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " check");
									
									break upLeft;
								} else {
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " capture " + boardArray[tempRank][tempFile].getPieceType().toString());
									
									break upLeft;
								}
							} else {
								break upLeft;
							}
						}
					}
				}
			}
		}
		upRight: {
			for (int tempRank = startRank - 1; tempRank >= 0; tempRank--) {
				for (int tempFile = startFile + 1; tempFile <= 7; tempFile++) {
					if (Math.abs(tempRank - startRank) == Math.abs(tempFile - startFile)) {
						if (boardArray[tempRank][tempFile] == null) {
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
						} else {
							if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
								if (boardArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " check");
									
									break upRight;
								} else {
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " capture " + boardArray[tempRank][tempFile].getPieceType().toString());
									
									break upRight;
								}
							} else {
								break upRight;
							}
						}
					}
				}
			}
		}
		downLeft: {
			for (int tempRank = startRank + 1; tempRank <= 7; tempRank++) {
				for (int tempFile = startFile - 1; tempFile >= 0; tempFile--) {
					if (Math.abs(tempRank - startRank) == Math.abs(tempFile - startFile)) {
						if (boardArray[tempRank][tempFile] == null) {
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
						} else {
							if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
								if (boardArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " check");
									
									break downLeft;
								} else {
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " capture " + boardArray[tempRank][tempFile].getPieceType().toString());
									
									break downLeft;
								}
							} else {
								break downLeft;
							}
						}
					}
				}
			}
		}
		downRight: {
			for (int tempRank = startRank + 1; tempRank <= 7; tempRank++) {
				for (int tempFile = startFile + 1; tempFile <= 7; tempFile++) {
					if (Math.abs(tempRank - startRank) == Math.abs(tempFile - startFile)) {
						if (boardArray[tempRank][tempFile] == null) {
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
						} else {
							if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
								if (boardArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " check");
									
									break downRight;
								} else {
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " capture " + boardArray[tempRank][tempFile].getPieceType().toString());
									
									break downRight;
								}
							} else {
								break downRight;
							}
						}
					}
				}
			}
		}
		
		return possibleMoves;
	}
}