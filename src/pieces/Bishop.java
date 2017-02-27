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
		
		if (m_player == Player.BLACK) {
			blackUpLeft: {
				for (int tempRank = startRank-1; tempRank >= 0; tempRank--) {
					for (int tempFile = startFile-1; tempFile >= 0; tempFile--) {
						if (Math.abs(startRank - tempRank) == Math.abs(startFile - tempFile)) {
							if (boardArray[tempRank][tempFile] == null) {
								// move up and left
								possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
								
								break;
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player != Player.BLACK)) {
								if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
									// check
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " check");
									
									break blackUpLeft;
								} else {
									// capture and stop loop
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " c" + boardArray[tempRank][tempFile].getPieceType().toString());
									
									break blackUpLeft;
								}
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player == Player.BLACK)) {
								// don't capture black and stop loop
								break blackUpLeft;
							}
						}
					}
				}
			} // blackUpLeft
			
			blackUpRight: {
				for (int tempRank = startRank-1; tempRank >= 0; tempRank--) {
					for (int tempFile = startFile+1; tempFile <= 7; tempFile++) {
						if (Math.abs(startRank - tempRank) == Math.abs(startFile - tempFile)) {
							if (boardArray[tempRank][tempFile] == null) {
								// move up and left
								possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
								
								break;
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player != Player.BLACK)) {
								if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
									// check
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " check");
									
									break blackUpRight;
								} else {
									// capture and stop loop
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " c" + boardArray[tempRank][tempFile].getPieceType().toString());
									
									break blackUpRight;
								}
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player == Player.BLACK)) {
								// don't capture black and stop loop
								break blackUpRight;
							}
						}
					}
				}
			} // blackUpRight
			
			blackDownLeft: {
				for (int tempRank = startRank+1; tempRank <= 7; tempRank++) {
					for (int tempFile = startFile-1; tempFile >= 0; tempFile--) {
						if (Math.abs(startRank - tempRank) == Math.abs(startFile - tempFile)) {
							if (boardArray[tempRank][tempFile] == null) {
								// move up and left
								possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
								
								break;
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player != Player.BLACK)) {
								if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
									// check
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " check");
									
									break blackDownLeft;
								} else {
									// capture and stop loop
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " c" + boardArray[tempRank][tempFile].getPieceType().toString());
									
									break blackDownLeft;
								}
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player == Player.BLACK)) {
								// don't capture black and stop loop
								break blackDownLeft;
							}
						}
					}
				}
			} // blackDownLeft
			
			blackDownRight: {
				for (int tempRank = startRank+1; tempRank <= 7; tempRank++) {
					for (int tempFile = startFile+1; tempFile <= 7; tempFile++) {
						if (Math.abs(startRank - tempRank) == Math.abs(startFile - tempFile)) {
							if (boardArray[tempRank][tempFile] == null) {
								// move up and left
								possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
								
								break;
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player != Player.BLACK)) {
								if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
									// check
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " check");
									
									break blackDownRight;
								} else {
									// capture and stop loop
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " c" + boardArray[tempRank][tempFile].getPieceType().toString());
									
									break blackDownRight;
								}
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player == Player.BLACK)) {
								// don't capture black and stop loop
								break blackDownRight;
							}
						}
					}
				}
			} // blackDownRight
			
		} else if (m_player == Player.WHITE) {
			whiteUpLeft: {
				for (int tempRank = startRank-1; tempRank >= 0; tempRank--) {
					for (int tempFile = startFile-1; tempFile >= 0; tempFile--) {
						if (Math.abs(startRank - tempRank) == Math.abs(startFile - tempFile)) {
							if (boardArray[tempRank][tempFile] == null) {
								// move up and left
								possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
								
								break;
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player != Player.WHITE)) {
								if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
									// check
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " check");
									
									break whiteUpLeft;
								} else {
									// capture and stop loop
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " c" + boardArray[tempRank][tempFile].getPieceType().toString());
									
									break whiteUpLeft;
								}
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player == Player.WHITE)) {
								// don't capture white and stop loop
								break whiteUpLeft;
							}
						}
					}
				}
			} // whiteUpLeft
			
			whiteUpRight: {
				for (int tempRank = startRank-1; tempRank >= 0; tempRank--) {
					for (int tempFile = startFile+1; tempFile <= 7; tempFile++) {
						if (Math.abs(startRank - tempRank) == Math.abs(startFile - tempFile)) {
							if (boardArray[tempRank][tempFile] == null) {
								// move up and left
								possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
								
								break;
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player != Player.WHITE)) {
								if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
									// check
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " check");
									
									break whiteUpRight;
								} else {
									// capture and stop loop
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " c" + boardArray[tempRank][tempFile].getPieceType().toString());
									
									break whiteUpRight;
								}
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player == Player.WHITE)) {
								// don't capture white and stop loop
								break whiteUpRight;
							}
						}
					}
				}
			} // whiteUpRight
			
			whiteDownLeft: {
				for (int tempRank = startRank+1; tempRank <= 7; tempRank++) {
					for (int tempFile = startFile-1; tempFile >= 0; tempFile--) {
						if (Math.abs(startRank - tempRank) == Math.abs(startFile - tempFile)) {
							if (boardArray[tempRank][tempFile] == null) {
								// move up and left
								possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
								
								break;
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player != Player.WHITE)) {
								if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
									// check
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " check");
									
									break whiteDownLeft;
								} else {
									// capture and stop loop
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " c" + boardArray[tempRank][tempFile].getPieceType().toString());
									
									break whiteDownLeft;
								}
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player == Player.WHITE)) {
								// don't capture white and stop loop
								break whiteDownLeft;
							}
						}
					}
				}
			} // whiteDownLeft
			
			whiteDownRight: {
				for (int tempRank = startRank+1; tempRank <= 7; tempRank++) {
					for (int tempFile = startFile+1; tempFile <= 7; tempFile++) {
						if (Math.abs(startRank - tempRank) == Math.abs(startFile - tempFile)) {
							if (boardArray[tempRank][tempFile] == null) {
								// move up and left
								possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
								
								break;
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player != Player.WHITE)) {
								if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
									// check
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " check");
									
									break whiteDownRight;
								} else {
									// capture and stop loop
									possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile)
									+ " c" + boardArray[tempRank][tempFile].getPieceType().toString());
									
									break whiteDownRight;
								}
							} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player == Player.WHITE)) {
								// don't capture white and stop loop
								break whiteDownRight;
							}
						}
					}
				}
			} // whiteDownRight
		}
		
		return possibleMoves;
	}
}