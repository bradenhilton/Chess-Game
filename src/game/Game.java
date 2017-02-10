package game;

import java.util.*;
import pieces.*;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class Game {
	private String move;
	private String restart;
	private Board chessBoard = new Board();
	private Scanner input = new Scanner(System.in);
	private boolean legalMove = false;
	private boolean whiteTurn = true;
	private boolean gameOver = false;
	private Map<Character, Integer> charMap = new HashMap<Character, Integer>();
	private List<String> moveHistory = new ArrayList<String>();
	
	/**
	 * Starts the game.
	 * <p>
	 * Adds characters to the character map, then starts the appropriate game loop depending on number of players.
	 * 
	 * @param numPlayers	Number of players.
	 */
	public void start(int numPlayers) {
		charMap.put('a', 0);
		charMap.put('b', 1);
		charMap.put('c', 2);
		charMap.put('d', 3);
		charMap.put('e', 4);
		charMap.put('f', 5);
		charMap.put('g', 6);
		charMap.put('h', 7);
		
		if (numPlayers == 1) {
			singleplayerLoop(whiteTurn);
		}
		
		if (numPlayers == 2) {
			multiplayerLoop(whiteTurn);
		}
	}
	
	/**
	 * Game loop for one player.
	 * <p>
	 * Does not allow user input for Black turn due to AI.
	 * 
	 * @param whiteTurn		Boolean which dictates whose turn it is.
	 */
	private void singleplayerLoop(boolean whiteTurn) {
		while (!gameOver) {
			if (legalMove) {
				chessBoard.printBoard(chessBoard.boardArray);
			}
			
			if (whiteTurn) {
				if (!moveHistory.isEmpty()) {
					System.out.println(moveHistory.get(moveHistory.size() - 1));
				}
				
				System.out.println("White to move (e.g. d2d4)");
				move = input.nextLine();
				parseMoveSingleplayer(move, whiteTurn);
			} else {
				// TODO Minimax/Alpha-Beta Pruning
				
				// Placeholder move
				System.out.println("Generating move...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				parseMoveSingleplayer("g7g6", whiteTurn);
			}
		}
		
		System.out.println("New game? (y/n)");
		restart = input.nextLine();
		if (restart.toLowerCase().equals("y") || restart.toLowerCase().equals("yes")) {
			chessBoard = new Board();
			gameOver = false;
		} else {
			System.out.println("Goodbye!");
			input.close();
			System.exit(0);
		}
	}

	/**
	 * Parses move input.
	 * <p>
	 * Converts move e.g. d2d4 into start ranks and files and new ranks and files.
	 * 
	 * @param move			String representation of move input.
	 * @param whiteTurn		Boolean which dictates whose turn it is. 
	 */
	public void parseMoveSingleplayer(String move, boolean whiteTurn) {
		int startRank, startFile, newRank, newFile;
		
		if (move.length() != 4 && !move.matches("[a-hA-H][1-8][a-hA-H][1-8]")) {
			System.err.println("Error: Invalid move");
		} else {
			startFile = charMap.get(move.toLowerCase().charAt(0));
			startRank = 8 - Integer.parseInt(move.substring(1,2));
			
			newFile = charMap.get(move.toLowerCase().charAt(2));
			newRank = 8 - Integer.parseInt(move.substring(3,4));
			
			if (chessBoard.boardArray[startRank][startFile] == null) {
				System.err.println("Error: Invalid move, no piece found at " + move.substring(0, 2));
			} else if (startRank == newRank && startFile == newFile) {
				System.err.println("Error: Invalid move");
			} else {
				if (whiteTurn) {				
					if (!(chessBoard.boardArray[startRank][startFile].getPlayer() == Player.WHITE)) {
						System.err.println("Error: Invalid move, White cannot move Black");
					} else {
						if (isLegalMove(chessBoard.boardArray, startRank, startFile, newRank, newFile)) {
							movePiece(chessBoard.boardArray, startRank, startFile, newRank, newFile);
							
							moveHistory.add("White " + move.toLowerCase());
							legalMove = true;
							whiteTurn = false;
							singleplayerLoop(whiteTurn);
						} else {
							System.err.println("Error: Invalid move");
						}
					}
				} else {
					if (!(chessBoard.boardArray[startRank][startFile].getPlayer() == Player.BLACK)) {
						System.err.println("Error: Invalid move, Black cannot move White");
					} else {
						chessBoard.boardArray[startRank][startFile].generatePossibleMoves(chessBoard.boardArray, startRank, startFile);
						movePiece(chessBoard.boardArray, startRank, startFile, newFile, newRank);
						
						moveHistory.add("Black " + move.toLowerCase());
						legalMove = true;
						whiteTurn = true;
						singleplayerLoop(whiteTurn);
					}
				}
			}
		}
	} // parseMoveSingleplayer
	
	/**
	 * Game loop for two players.
	 * <p>
	 * Allows user input for moves from both players.
	 * 
	 * @param whiteTurn		Boolean which dictates whose turn it is.
	 */
	private void multiplayerLoop(boolean whiteTurn) {
		while (!gameOver) {
			if (legalMove) {
				chessBoard.printBoard(chessBoard.boardArray);
			}
			
			if (whiteTurn) {
				if (!moveHistory.isEmpty()) {
					System.out.println(moveHistory.get(moveHistory.size() - 1));
				}
				
				System.out.println("White to move (e.g. d2d4)");
				move = input.nextLine();
				parseMoveMultiplayer(move, whiteTurn);
			} else {
				if (!moveHistory.isEmpty()) {
					System.out.println(moveHistory.get(moveHistory.size() - 1));
				}
				
				System.out.println("Black to move (e.g. g7g6)");
				move = input.nextLine();
				parseMoveMultiplayer(move, whiteTurn);
			}
		}
		
		System.out.println("New game? (y/n)");
		restart = input.nextLine();
		if (restart.toLowerCase().equals("y") || restart.toLowerCase().equals("yes")) {
			chessBoard = new Board();
			gameOver = false;
		} else {
			System.out.println("Goodbye!");
			input.close();
			System.exit(0);
		}
	}
	
	/**
	 * Parses move input.
	 * <p>
	 * Converts move e.g. d2d4 into start ranks and files and new ranks and files.
	 * 
	 * @param move			String representation of move input.
	 * @param whiteTurn		Boolean which dictates whose turn it is. 
	 */
	public void parseMoveMultiplayer(String move, boolean whiteTurn) {
		int startRank, startFile, newRank, newFile;
			
		if (move.length() != 4 && !move.matches("[a-hA-H][1-8][a-hA-H][1-8]")) {
			System.err.println("Error: Invalid move");
		} else {
			startFile = charMap.get(move.toLowerCase().charAt(0));
			startRank = 8 - Integer.parseInt(move.substring(1,2));
			
			newFile = charMap.get(move.toLowerCase().charAt(2));
			newRank = 8 - Integer.parseInt(move.substring(3,4));
			
			if (chessBoard.boardArray[startRank][startFile] == null) {
				System.err.println("Error: Invalid move, no piece found at " + move.substring(0, 2));
			} else if (startRank == newRank && startFile == newFile) {
				System.err.println("Error: Invalid move");
			} else {
				if (whiteTurn) {				
					if (!(chessBoard.boardArray[startRank][startFile].getPlayer() == Player.WHITE)) {
						System.err.println("Error: Invalid move, White cannot move Black");
					} else {
						if (isLegalMove(chessBoard.boardArray, startRank, startFile, newRank, newFile)) {
							movePiece(chessBoard.boardArray, startRank, startFile, newRank, newFile);
							
							moveHistory.add("White " + move.toLowerCase());
							legalMove = true;
							whiteTurn = false;
							multiplayerLoop(whiteTurn);
						} else {
							System.err.println("Error: Invalid move");
						}
					}
				} else {
					if (!(chessBoard.boardArray[startRank][startFile].getPlayer() == Player.BLACK)) {
						System.err.println("Error: Invalid move, Black cannot move White");
					} else {
						if (isLegalMove(chessBoard.boardArray, startRank, startFile, newRank, newFile)) {
							movePiece(chessBoard.boardArray, startRank, startFile, newRank, newFile);
							
							moveHistory.add("Black " + move.toLowerCase());
							legalMove = true;
							whiteTurn = true;
							multiplayerLoop(whiteTurn);
						}
					}
				}
			}
		}
	} // parseMoveMultiplayer
	
	/**
	 * Moves a piece on the game board.
	 * 
	 * @param boardArray    Array of Pieces on the Board.
	 * @param startRank     Starting Rank (Piece to be moved).
	 * @param startFile     Starting File (Piece to be moved).
	 * @param destRank      Destination Rank.
	 * @param destFile      Destination File.   
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
	 * @param boardArray    Array of Pieces on the Board.
	 * @param startRank     Starting Rank (Piece to be moved).
	 * @param startFile     Starting File (Piece to be moved).
	 * @param destRank      Destination Rank.
	 * @param destFile      Destination File.
	 * @return              True for legal move, False for illegal move.
	 */
	public boolean isLegalMove(Piece[][] boardArray, int startRank, int startFile, int destRank, int destFile) {
		String move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(destRank) + String.valueOf(destFile);
		if (!boardArray[startRank][startFile].generatePossibleMoves(boardArray, startRank, startFile).contains(move)) {
			return false;
		}
		return true;
	} // isLegalMove
} // Game