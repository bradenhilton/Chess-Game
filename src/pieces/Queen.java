package pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import game.Player;

public class Queen extends Piece {
	protected PieceType m_type;
	
	public Queen(Player player) {
		super(player);
		m_type = PieceType.QUEEN;
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
			blackUp: {
				for (int tempRank = startRank+1; tempRank <= 7; tempRank++) {
					if (boardArray[tempRank][startFile] == null) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile));
						// move by one
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player != Player.BLACK)) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile));
						// capture and stop loop
						break blackUp;
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player == Player.BLACK)) {
						// don't capture black and stop loop
						break blackUp;
					}
				}
			} // blackUp
		
			blackDown: {
				for (int tempRank = startRank-1; tempRank >= 0; tempRank--) {
					if (boardArray[tempRank][startFile] == null) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile));
						// move by one
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player != Player.BLACK)) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile));
						// capture and stop loop
						break blackDown;
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player == Player.BLACK)) {
						// don't capture black and stop loop
						break blackDown;
					}
				}
			} // blackDown
			
			blackLeft: {
				for (int tempFile = startFile-1; tempFile >= 0; tempFile--) {
					if (boardArray[startRank][tempFile] == null) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile));
						// move by one
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player != Player.BLACK)) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile));
						// capture and stop loop
						break blackLeft;
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player == Player.BLACK)) {
						// don't capture black and stop loop
						break blackLeft;
					}
				}
			} // blackLeft
			
			blackRight: {
				for (int tempFile = startFile+1; tempFile <= 7; tempFile++) {
					if (boardArray[startRank][tempFile] == null) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile));
						// move by one
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player != Player.BLACK)) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile));
						// capture and stop loop
						break blackRight;
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player == Player.BLACK)) {
						// don't capture black and stop loop
						break blackRight;
					}
				}
			} // blackRight
			
			blackUpLeft: {
				for (int tempRank = startRank+1; tempRank <= 7; tempRank++) {
					for (int tempFile = startFile-1; tempFile >= 0; tempFile--) {
						if (boardArray[tempRank][tempFile] == null) {
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
							// move by one
						} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player != Player.BLACK)) {
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
							// capture and stop loop
							break blackUpLeft;
						} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player == Player.BLACK)) {
							// don't capture black and stop loop
							break blackUpLeft;
						}
					}
				}
			} // blackUpLeft
			
			blackUpRight: {
				for (int tempRank = startRank+1; tempRank <= 7; tempRank++) {
					for (int tempFile = startFile+1; tempFile <= 7; tempFile++) {
						if (boardArray[tempRank][tempFile] == null) {
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
							// move by one
						} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player != Player.BLACK)) {
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
							// capture and stop loop
							break blackUpRight;
						} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player == Player.BLACK)) {
							// don't capture black and stop loop
							break blackUpRight;
						}
					}
				}
			} // blackUpRight
			
			blackDownLeft: {
				for (int tempRank = startRank-1; tempRank >=0; tempRank--) {
					for (int tempFile = startFile-1; tempFile >= 0; tempFile--) {
						if (boardArray[tempRank][tempFile] == null) {
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
							// move by one
						} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player != Player.BLACK)) {
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
							// capture and stop loop
							break blackDownLeft;
						} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player == Player.BLACK)) {
							// don't capture black and stop loop
							break blackDownLeft;
						}
					}
				}
			} // blackDownLeft
			
			blackDownRight: {
				for (int tempRank = startRank-1; tempRank >= 0; tempRank--) {
					for (int tempFile = startFile-1; tempFile >= 0; tempFile--) {
						if (boardArray[tempRank][tempFile] == null) {
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
							// move by one
						} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player != Player.BLACK)) {
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(tempFile));
							// capture and stop loop
							break blackDownRight;
						} else if ((boardArray[tempRank][tempFile] != null && boardArray[tempRank][tempFile].m_player == Player.BLACK)) {
							// don't capture black and stop loop
							break blackDownRight;
						}
					}
				}
			} // blackDownRight
			
		} else if (m_player == Player.WHITE) {
			whiteUp: {
				for (int tempRank = startRank+1; tempRank <= 7; tempRank++) {
					if (boardArray[tempRank][startFile] == null) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile));		// move by one
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player != Player.WHITE)) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile));		// capture and stop loop
						break whiteUp;
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player == Player.WHITE)) {
						// don't capture white and stop loop
						break whiteUp;
					}
				}
			} // whiteUp
		
			whiteDown: {
				for (int tempRank = startRank-1; tempRank >= 0; tempRank--) {
					if (boardArray[tempRank][startFile] == null) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile));		// move by one
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player != Player.WHITE)) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile));		// capture and stop loop
						break whiteDown;
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player == Player.WHITE)) {
						// don't capture white and stop loop
						break whiteDown;
					}
				}
			} // whiteDown
			
			whiteLeft: {
				for (int tempFile = startFile-1; tempFile >= 0; tempFile--) {
					if (boardArray[startRank][tempFile] == null) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile));
						// move by one
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player != Player.WHITE)) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile));
						// capture and stop loop
						break whiteLeft;
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player == Player.WHITE)) {
						// don't capture black and stop loop
						break whiteLeft;
					}
				}
			} // whiteLeft
			
			whiteRight: {
				for (int tempFile = startFile+1; tempFile <= 7; tempFile++) {
					if (boardArray[startRank][tempFile] == null) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile));
						// move by one
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player != Player.WHITE)) {
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile));
						// capture and stop loop
						break whiteRight;
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player == Player.WHITE)) {
						// don't capture black and stop loop
						break whiteRight;
					}
				}
			} // whiteRight
			
			whiteUpLeft: {
				
			} // whiteUpLeft
			
			whiteUpRight: {
				
			} // whiteUpRight
			
			whiteDownLeft: {
				
			} // whiteDownLeft
			
			whiteDownRight: {
				
			} // whiteDownRight
		}
		
		return possibleMoves;
	}
}