package pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import game.Player;

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
		// negative (-) indicates up or left
		// positive (+) indicates down or right
		
		List<String> possibleMoves = new ArrayList<String>();
		
		if (m_player == Player.BLACK) {
			blackUp: {
				for (int tempRank = startRank-1; tempRank >= 0; tempRank--) {
					if (boardArray[tempRank][startFile] == null) {
						// move up
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile));
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player != Player.BLACK)) {
						if (boardArray[tempRank][startFile].getPieceType() == PieceType.KING) {
							// check
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile)
							+ " check");
							
							break blackUp;
						} else {
							// capture and stop loop
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile)
							+ " c" + boardArray[tempRank][startFile].getPieceType().toString());
							
							break blackUp;
						}
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player == Player.BLACK)) {
						// don't capture black and stop loop
						break blackUp;
					}
				}
			} // blackUp
		
			blackDown: {
				for (int tempRank = startRank+1; tempRank <= 7; tempRank++) {
					if (boardArray[tempRank][startFile] == null) {
						// move down
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile));
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player != Player.BLACK)) {
						if (boardArray[tempRank][startFile].getPieceType() == PieceType.KING) {
							// check
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile)
							+ " check");
							
							break blackDown;
						} else {
							// capture and stop loop
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile)
							+ " c" + boardArray[tempRank][startFile].getPieceType().toString());
							
							break blackDown;
						}
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player == Player.BLACK)) {
						// don't capture black and stop loop
						break blackDown;
					}
				}
			} // blackDown
			
			blackLeft: {
				for (int tempFile = startFile-1; tempFile >= 0; tempFile--) {
					if (boardArray[startRank][tempFile] == null) {
						// move left
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile));
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player != Player.BLACK)) {
						// capture and stop loop
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile)
						+ " c" + boardArray[startRank][tempFile].getPieceType().toString());
						
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
						// move right
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile));
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player != Player.BLACK)) {
						// capture and stop loop
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile)
						+ " c" + boardArray[startRank][tempFile].getPieceType().toString());
						
						break blackRight;
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player == Player.BLACK)) {
						// don't capture black and stop loop
						break blackRight;
					}
				}
			} // blackRight
			
		} else if (m_player == Player.WHITE) {
			whiteUp: {
				for (int tempRank = startRank-1; tempRank >= 0; tempRank--) {
					if (boardArray[tempRank][startFile] == null) {
						// move up
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile));
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player != Player.WHITE)) {
						if (boardArray[tempRank][startFile].getPieceType() == PieceType.KING) {
							// check
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile)
							+ " check");
							
							break whiteUp;
						} else {
							// capture and stop loop
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile)
							+ " c" + boardArray[tempRank][startFile].getPieceType().toString());
							
							break whiteUp;
						}
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player == Player.WHITE)) {
						// don't capture white and stop loop
						break whiteUp;
					}
				}
			} // whiteUp
		
			whiteDown: {
				for (int tempRank = startRank+1; tempRank <= 7; tempRank++) {
					if (boardArray[tempRank][startFile] == null) {
						// move down
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile));
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player != Player.WHITE)) {
						if (boardArray[tempRank][startFile].getPieceType() == PieceType.KING) {
							// check
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile)
							+ " check");
							
							break whiteDown;
						} else {
							// capture and stop loop
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank) + String.valueOf(startFile)
							+ " c" + boardArray[tempRank][startFile].getPieceType().toString());
							
							break whiteDown;
						}
					} else if ((boardArray[tempRank][startFile] != null && boardArray[tempRank][startFile].m_player == Player.WHITE)) {
						// don't capture white and stop loop
						break whiteDown;
					}
				}
			} // whiteDown
			
			whiteLeft: {
				for (int tempFile = startFile-1; tempFile >= 0; tempFile--) {
					if (boardArray[startRank][tempFile] == null) {
						// move left
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile));
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player != Player.WHITE)) {
						if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
							// check
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile)
							+ " check");
							
							break whiteLeft;
						} else {
							// capture and stop loop
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile)
							+ " c" + boardArray[startRank][tempFile].getPieceType().toString());
							
							break whiteLeft;
						}
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player == Player.WHITE)) {
						// don't capture white and stop loop
						break whiteLeft;
					}
				}
			} // whiteLeft
			
			whiteRight: {
				for (int tempFile = startFile+1; tempFile <= 7; tempFile++) {
					if (boardArray[startRank][tempFile] == null) {
						// move right
						possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile));
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player != Player.WHITE)) {
						if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
							// check
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile)
							+ " check");
							
							break whiteRight;
						} else {
							// capture and stop loop
							possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank) + String.valueOf(tempFile)
							+ " c" + boardArray[startRank][tempFile].getPieceType().toString());
							
							break whiteRight;
						}
					} else if ((boardArray[startRank][tempFile] != null && boardArray[startRank][tempFile].m_player == Player.WHITE)) {
						// don't capture white and stop loop
						break whiteRight;
					}
				}
			} // whiteRight
		}
		
		return possibleMoves;
	}
}