package tests;

import java.util.*;
import game.*;
import pieces.*;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class TestLoop {
	private boolean legalMove = false;
	private boolean whiteTurn = true;
	private boolean gameOver = false;
	private String move;
	private Map<Character, Integer> charMap = new HashMap<Character, Integer>();
	private List<String> moveHistory = new ArrayList<String>();
	private Scanner input = new Scanner(System.in);
	
	public TestLoop(TestBoard board) {
		charMap.put('a', 0);
		charMap.put('b', 1);
		charMap.put('c', 2);
		charMap.put('d', 3);
		charMap.put('e', 4);
		charMap.put('f', 5);
		charMap.put('g', 6);
		charMap.put('h', 7);
		
		testLoop(board, whiteTurn);
	}
	
	private void testLoop(TestBoard board, boolean whiteTurn) {
		while (!gameOver) {
			if (legalMove) {
				board.printBoard(board.boardArray);
			}
			
			if (whiteTurn) {
				if (!moveHistory.isEmpty()) {
					System.out.println(moveHistory.get(moveHistory.size() - 1));
				}
				
				System.out.println("White to move (e.g. d2d4)");
				move = input.nextLine();
				parseMoveTest(board, move, whiteTurn);
			} else {
				if (!moveHistory.isEmpty()) {
					System.out.println(moveHistory.get(moveHistory.size() - 1));
				}
				
				System.out.println("Black to move (e.g. d2d4)");
				move = input.nextLine();
				parseMoveTest(board, move, whiteTurn);
			}
		}
	} // testLoop
	
	public void parseMoveTest(TestBoard board, String move, boolean whiteTurn) {
		int startRank, startFile, newRank, newFile;
			
		if (move.length() != 4 && !move.matches("[a-hA-H][1-8][a-hA-H][1-8]")) {
			System.err.println("Error: Invalid move");
		} else {
			startFile = charMap.get(move.toLowerCase().charAt(0));
			startRank = 8 - Integer.parseInt(move.substring(1,2));
			
			newFile = charMap.get(move.toLowerCase().charAt(2));
			newRank = 8 - Integer.parseInt(move.substring(3,4));
			
			if (board.boardArray[startRank][startFile] == null) {
				System.err.println("Error: Invalid move, no piece found at " + move.substring(0, 2));
			} else if (startRank == newRank && startFile == newFile) {
				System.err.println("Error: Invalid move");
			} else {
				if (whiteTurn) {				
					if (!(board.boardArray[startRank][startFile].getPlayer() == Player.WHITE)) {
						System.err.println("Error: Invalid move, White cannot move Black");
					} else {
						if (isLegalMove(board.boardArray, startRank, startFile, newRank, newFile)) {
							movePiece(board.boardArray, startRank, startFile, newRank, newFile);
							
							moveHistory.add("White " + move.toLowerCase());
							legalMove = true;
							whiteTurn = false;
							testLoop(board, whiteTurn);
						} else {
							System.err.println("Error: Invalid move");
						}
					}
				} else {
					if (!(board.boardArray[startRank][startFile].getPlayer() == Player.BLACK)) {
						System.err.println("Error: Invalid move, Black cannot move White");
					} else {
						if (isLegalMove(board.boardArray, startRank, startFile, newRank, newFile)) {
							movePiece(board.boardArray, startRank, startFile, newRank, newFile);
							
							moveHistory.add("Black " + move.toLowerCase());
							legalMove = true;
							whiteTurn = true;
							testLoop(board, whiteTurn);
						}
					}
				}
			}
		}
	} // parseMoveTest

	public void movePiece(Piece[][] boardArray, int startRank,  int startFile, int destRank, int destFile) {
		boardArray[destRank][destFile] = null;
		boardArray[destRank][destFile] = boardArray[startRank][startFile];
		boardArray[startRank][startFile] = null;
		boardArray[destRank][destFile].setMoved();
	} // movePiece
	
	public boolean isLegalMove(Piece[][] boardArray, int startRank, int startFile, int destRank, int destFile) {
		String destination = String.valueOf(destRank) + String.valueOf(destFile);
		if (!boardArray[startRank][startFile].generatePossibleMoves(boardArray, startRank, startFile).contains(destination)) {
			return false;
		}
		return true;
	} // isLegalMove
}