package game;

import java.util.*;

import game.Board;
import pieces.Piece;

public class SingleplayerGame {
	private int ranks = 8;
	private int files = 8;
	private String move;
	private String restart;
	private Board chessBoard;
	private Scanner input = new Scanner(System.in);
	private boolean legalMove = false;
	private boolean whiteTurn;
	private boolean gameOver = false;
	private boolean reverse = false;
	private Map<Character, Integer> charMap = new HashMap<Character, Integer>();
	private Map<Integer, Character> reverseCharMap = new HashMap<Integer, Character>();
	private List<String> moveHistory = new ArrayList<String>();
	
	public SingleplayerGame(Board chessBoard, Map<Character, Integer> charMap, Map<Integer, Character>reverseCharMap, boolean whiteTurn) {
		this.chessBoard = chessBoard;
		this.charMap = charMap;
		this.reverseCharMap = reverseCharMap;
		this.whiteTurn = whiteTurn;
		
		singleplayerLoop(whiteTurn);
	}
	
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
				
				if (move.contains("list")) {
					String piece = input.nextLine();
					listPossibleMoves(piece);
				} else {
					parseMoveSingleplayer(move, whiteTurn, reverse);
				}
			} else {
				List<String> allMoves = new ArrayList<String>();
				
				for (int i = 0; i < ranks; i++) {
					for (int j = 0; j < files; j++) {
						if (chessBoard.boardArray[i][j] != null && chessBoard.boardArray[i][j].getPlayer() == Player.BLACK) {
							allMoves.addAll(chessBoard.boardArray[i][j].generatePossibleMoves(chessBoard.boardArray, i, j));
						}
					}
				}
				
				Random rand = new Random();
				int randMove = rand.nextInt(allMoves.size());
				
				move = allMoves.get(randMove).substring(0, 4);
				
				reverse = true;
				
				parseMoveSingleplayer(move, whiteTurn, reverse);
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
	 * @param move			    String representation of move input.
	 * @param whiteTurn		    Boolean which dictates whose turn it is. 
	 */
	public void parseMoveSingleplayer(String move, boolean whiteTurn, boolean reverse) {
		int startRank, startFile, newRank, newFile;
		
		if (reverse == true) {
			startRank = Integer.parseInt(move.substring(0, 1));
			startFile = Integer.parseInt(move.substring(1, 2));
			
			newRank = Integer.parseInt(move.substring(2, 3));
			newFile = Integer.parseInt(move.substring(3, 4));
			
			String moveText = "" + reverseCharMap.get(startFile) + (startRank+1) + reverseCharMap.get(newFile) + (newRank+1);
			
			movePiece(chessBoard.boardArray, startRank, startFile, newFile, newRank);
			
			moveHistory.add("Black " + move.toLowerCase());
			legalMove = true;
			whiteTurn = true;
			singleplayerLoop(whiteTurn);
		} else {
			if (move.length() != 4 && !move.matches("[a-hA-H][1-8][a-hA-H][1-8]")) {
				System.err.println("Error: Invalid move");
			} else {
				startRank = 8 - Integer.parseInt(move.substring(1,2));
				startFile = charMap.get(move.toLowerCase().charAt(0));
				
				newRank = 8 - Integer.parseInt(move.substring(3,4));
				newFile = charMap.get(move.toLowerCase().charAt(2));
				
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
					}
				}
			}
		}
	} // parseMoveSingleplayer
	
	public void listPossibleMoves(String piece) {
		int startRank, startFile, destRank;
		Character destFile;
		List<String> allPossibleMoves = new ArrayList<String>();
		
		if (piece.length() != 2 && !move.matches("[a-hA-H][1-8]")) {
			System.err.println("Error: Invalid piece");
		} else {
			startFile = charMap.get(piece.toLowerCase().charAt(0));
			startRank = 8 - Integer.parseInt(piece.substring(1,2));
			
			if (chessBoard.boardArray[startRank][startFile] == null) {
				System.err.println("Error: No piece found at " + piece.substring(0, 2));
			} else {
				allPossibleMoves = chessBoard.boardArray[startRank][startFile].generatePossibleMoves(chessBoard.boardArray, startRank, startFile);
				
				System.out.println(piece + " selected\nPossible moves:");
				
				for (String move: allPossibleMoves) {
					destRank = 8 - Integer.parseInt(move.substring(2, 3));
					destFile = reverseCharMap.get(Integer.parseInt(move.substring(3, 4)));
					
					System.out.println(destFile + "" + destRank + move.substring(4));
				}
			}
		}
	}
	
	public void movePiece(Piece[][] boardArray, int startRank,  int startFile, int destRank, int destFile) {
		boardArray[destRank][destFile] = null;
		boardArray[destRank][destFile] = boardArray[startRank][startFile];
		boardArray[startRank][startFile] = null;
		boardArray[destRank][destFile].setMoved();
	} // movePiece
	
	public boolean isLegalMove(Piece[][] boardArray, int startRank, int startFile, int destRank, int destFile) {
		String move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(destRank) + String.valueOf(destFile);
		List<String> possibleMoves = boardArray[startRank][startFile].generatePossibleMoves(boardArray, startRank, startFile);
		
		for (String destination : possibleMoves) {
			if (destination.substring(0, 4).equals(move)) {
				return true;
			}
		}
		
		return false;
	} // isLegalMove
}
