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
	private Map<Integer, Character> reverseCharMap = new HashMap<Integer, Character>();
	private List<String> moveHistory = new ArrayList<String>();
	private Scanner input = new Scanner(System.in);
	/**
	 * Constructor which populates character maps and starts the loop.
	 * 
	 * @param board             Custom game board.
	 */
	public TestLoop(TestBoard board) {
		charMap.put('a', 0);
		charMap.put('b', 1);
		charMap.put('c', 2);
		charMap.put('d', 3);
		charMap.put('e', 4);
		charMap.put('f', 5);
		charMap.put('g', 6);
		charMap.put('h', 7);
		
		reverseCharMap.put(0, 'a');
		reverseCharMap.put(1, 'b');
		reverseCharMap.put(2, 'c');
		reverseCharMap.put(3, 'd');
		reverseCharMap.put(4, 'e');
		reverseCharMap.put(5, 'f');
		reverseCharMap.put(6, 'g');
		reverseCharMap.put(7, 'h');
		
		testLoop(board, whiteTurn);
	}
	
	/**
	 * Game loop for test files.
	 * <p>
	 * Allows user input for moves from both players.
	 * 
	 * @param board             Custom game board.
	 * @param whiteTurn         Boolean which dictates whose turn it is.
	 */
	private void testLoop(TestBoard board, boolean whiteTurn) {
		while (!gameOver) {
			if (legalMove) {
				System.out.println("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
				board.printBoard(board.boardArray);
			}
			
			if (whiteTurn) {
				if (!moveHistory.isEmpty()) {
					System.out.println(moveHistory.get(moveHistory.size() - 1));
				}
				
				System.out.println("White to move (e.g. d2d4)");
				move = input.nextLine();
				
				if (move.contains("list")) {
					String piece = input.nextLine();
					listPossibleMoves(board, piece);
				} else {
					parseMoveTest(board, move, whiteTurn);
				}
			} else {
				if (!moveHistory.isEmpty()) {
					System.out.println(moveHistory.get(moveHistory.size() - 1));
				}
				
				System.out.println("Black to move (e.g. d2d4)");
				move = input.nextLine();

				if (move.contains("list")) {
					String piece = input.nextLine();
					listPossibleMoves(board, piece);
				} else {
					parseMoveTest(board, move, whiteTurn);
				}
			}
		}
	} // testLoop
	
	/**
	 * Parses move input.
	 * <p>
	 * Converts move e.g. d2d4 into start ranks and files and new ranks and files.
	 * 
	 * @param board             Custom game board.
	 * @param move              String representation of move input.
	 * @param whiteTurn         Boolean which dictates whose turn it is. 
	 */
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
	
	/**
	 * Lists all possible moves that can be made by a specified piece.
	 * 
	 * @param board             Custom game board.
	 * @param piece             String representation of a piece on the board.
	 */
	public void listPossibleMoves(TestBoard board, String piece) {
		int startRank, startFile, destRank;
		Character destFile;
		Collection<String> allPossibleMoves = new ArrayList<String>();
		
		if (piece.length() != 2 && !move.matches("[a-hA-H][1-8]")) {
			System.err.println("Error: Invalid piece");
		} else {
			startFile = charMap.get(piece.toLowerCase().charAt(0));
			startRank = 8 - Integer.parseInt(piece.substring(1,2));
			
			if (board.boardArray[startRank][startFile] == null) {
				System.err.println("Error: No piece found at " + piece.substring(0, 2));
			} else {
				allPossibleMoves = board.boardArray[startRank][startFile].generatePossibleMoves(board.boardArray, startRank, startFile);
				
				System.out.println(piece + " selected\nPossible moves:");
				
				for (String move: allPossibleMoves) {
					destRank = 8 - Integer.parseInt(move.substring(2, 3));
					destFile = reverseCharMap.get(Integer.parseInt(move.substring(3, 4)));
					System.out.println(destFile + "" + destRank);
				}
				
				System.out.println();
			}
		}
	}

	/**
	 * Moves a piece on the game board.
	 * 
	 * @param boardArray        Array of Pieces on the Board.
	 * @param startRank         Starting Rank (Piece to be moved).
	 * @param startFile         Starting File (Piece to be moved).
	 * @param destRank          Destination Rank.
	 * @param destFile          Destination File.   
	 */
	public void movePiece(Piece[][] boardArray, int startRank,  int startFile, int destRank, int destFile) {
		boardArray[destRank][destFile] = null;
		boardArray[destRank][destFile] = boardArray[startRank][startFile];
		boardArray[startRank][startFile] = null;
		boardArray[destRank][destFile].setMoved();
	} // movePiece
	
	/**
	 * Checks move is legal.
	 * <p>
	 * Tries to match the chosen move with all possible moves for the selected piece.
	 * 
	 * @param boardArray        Array of Pieces on the Board.
	 * @param startRank         Starting Rank (Piece to be moved).
	 * @param startFile         Starting File (Piece to be moved).
	 * @param destRank          Destination Rank.
	 * @param destFile          Destination File.
	 * @return                  True for legal move, False for illegal move.
	 */
	public boolean isLegalMove(Piece[][] boardArray, int startRank, int startFile, int destRank, int destFile) {
		String destination = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(destRank) + String.valueOf(destFile);
		if (!boardArray[startRank][startFile].generatePossibleMoves(boardArray, startRank, startFile).contains(destination)) {
			return false;
		}
		return true;
	} // isLegalMove
}