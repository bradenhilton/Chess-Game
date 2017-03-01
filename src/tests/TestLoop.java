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
	private TestBoard tempBoard;
	
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
				board.printBoard(board.boardArray);
			}
		
//			isCheck(board);
			
			if (whiteTurn) {
				if (!moveHistory.isEmpty()) {
					System.out.println(moveHistory.get(moveHistory.size() - 1));
				}
				
				System.out.println("White to move (e.g. d2d4)");
				move = input.nextLine();
				
				if (move.equalsIgnoreCase("list")) {
					String piece = input.nextLine();
					
					if (!piece.matches("[a-hA-H][1-8]")) {
						System.out.println("Please enter the location of a piece");
						break;
					} else if (piece.equalsIgnoreCase("q") || piece.equalsIgnoreCase("quit")) {
						break;
					} else {
						listPossibleMoves(board, piece);
					}
				} else if (move.equalsIgnoreCase("castle")) {
					String kingAndRook = input.nextLine();
					
					if (!kingAndRook.matches("[a-hA-H][1-8][a-hA-H][1-8]")) {
						System.out.println("Please enter the location of the king and the rook to castle");
						break;
					} else if (kingAndRook.equalsIgnoreCase("q") || kingAndRook.equalsIgnoreCase("quit")) {
						break;
					} else {
						parseMoveTest(board, kingAndRook, whiteTurn, true);
					}
				} else {
					parseMoveTest(board, move, whiteTurn, false);
				}
			} else {
				if (!moveHistory.isEmpty()) {
					System.out.println(moveHistory.get(moveHistory.size() - 1));
				}
				
				System.out.println("Black to move (e.g. d2d4)");
				move = input.nextLine();

				if (move.equalsIgnoreCase("list")) {
					String piece = input.nextLine();
					
					if (!piece.matches("[a-hA-H][1-8]")) {
						System.out.println("Please enter the location of a piece");
						break;
					} else if (piece.equalsIgnoreCase("q") || piece.equalsIgnoreCase("quit")) {
						break;
					} else {
						listPossibleMoves(board, piece);
					}
				} else if (move.equalsIgnoreCase("castle")) {
					String kingAndRook = input.nextLine();
					
					if (!kingAndRook.matches("[a-hA-H][1-8][a-hA-H][1-8]")) {
						System.out.println("Please enter the location of the king and the rook to castle");
						break;
					} else if (kingAndRook.equalsIgnoreCase("q") || kingAndRook.equalsIgnoreCase("quit")) {
						break;
					} else {
						parseMoveTest(board, move, whiteTurn, true);
					}
				} else {
					parseMoveTest(board, move, whiteTurn, false);
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
	public void parseMoveTest(TestBoard board, String move, boolean whiteTurn, boolean castle) {
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
						if (isLegalMove(board, startRank, startFile, newRank, newFile)) {
							if (castle == true) {
								castle(board, startRank, startFile, newRank, newFile);
								
								moveHistory.add("White " + board.boardArray[newRank][newFile].getPieceType() + " " + move.toLowerCase() + " castle");
								legalMove = true;
								whiteTurn = false;
								testLoop(board, whiteTurn);
							} else {
								movePiece(board, startRank, startFile, newRank, newFile);
								
								moveHistory.add("White " + board.boardArray[newRank][newFile].getPieceType() + " " + move.toLowerCase());
								legalMove = true;
								whiteTurn = false;
								testLoop(board, whiteTurn);
							}
						} else {
							System.err.println("Error: Invalid move");
						}
					}
				} else {
					if (!(board.boardArray[startRank][startFile].getPlayer() == Player.BLACK)) {
						System.err.println("Error: Invalid move, Black cannot move White");
					} else {
						if (isLegalMove(board, startRank, startFile, newRank, newFile)) {
							if (castle == true) {
								castle(board, startRank, startFile, newRank, newFile);
								
								moveHistory.add("Black " + board.boardArray[newRank][newFile].getPieceType() + " " + move.toLowerCase() + " castle");
								legalMove = true;
								whiteTurn = true;
								testLoop(board, whiteTurn);
							} else {
								movePiece(board, startRank, startFile, newRank, newFile);
								
								moveHistory.add("Black " + board.boardArray[newRank][newFile].getPieceType() + " " + move.toLowerCase());
								legalMove = true;
								whiteTurn = true;
								testLoop(board, whiteTurn);
							}
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
	 * @return 
	 */
	public List<String> listPossibleMoves(TestBoard board, String piece) {
		int startRank, startFile, destRank, newRank, newFile;
		char destFile;
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
				
				if (allPossibleMoves.isEmpty()) {
					System.out.println("None!");
				} else {
					for (String move: allPossibleMoves) {
						destRank = 8 - Integer.parseInt(move.substring(2, 3));
						destFile = reverseCharMap.get(Integer.parseInt(move.substring(3, 4)));
						
						newRank = Integer.parseInt(move.substring(2, 3));
						newFile = Integer.parseInt(move.substring(3, 4));
						board.boardArray[newRank][newFile] = new Pawn(Player.BLACK);
						System.out.println(destFile + "" + destRank + move.substring(4));
					}
				}
				
				System.out.println();
				board.printBoard(board.boardArray);
			}
		}
		return (List<String>) allPossibleMoves;
	} // listPossibleMoves
	
	/**
	 * Castles a King and a Rook.
	 * 
	 * @param kingAndRook       String representation of the king and rook locations.
	 */
	public void castle(TestBoard board, int piece1Rank, int piece1File, int piece2Rank, int piece2File) {
		// piece1 = king
		// piece2 = rook
		if (board.boardArray[piece1Rank][piece1File].getPieceType() == PieceType.KING
				&& board.boardArray[piece2Rank][piece2File].getPieceType() == PieceType.ROOK) {
			if (board.boardArray[piece1Rank][piece1File].getMoved() == false
					&& board.boardArray[piece2Rank][piece2File].getMoved() == false) {
				
				if (piece1File > piece2File) {
					board.boardArray[piece1Rank][piece1File - 2] = board.boardArray[piece1Rank][piece1File];
					board.boardArray[piece2Rank][piece1File - 1] = board.boardArray[piece2Rank][piece2File];
					
					board.boardArray[piece1Rank][piece1File] = null;
					board.boardArray[piece2Rank][piece2File] = null;
					
					board.boardArray[piece1Rank][piece1File - 2].setMoved();
					board.boardArray[piece2Rank][piece1File - 1].setMoved();
				} else {
					board.boardArray[piece1Rank][piece1File + 2] = board.boardArray[piece1Rank][piece1File];
					board.boardArray[piece2Rank][piece1File + 1] = board.boardArray[piece2Rank][piece2File];
					
					board.boardArray[piece1Rank][piece1File] = null;
					board.boardArray[piece2Rank][piece2File] = null;
					
					board.boardArray[piece1Rank][piece1File + 2].setMoved();
					board.boardArray[piece2Rank][piece1File + 1].setMoved();
				}
			}
		// piece 1 = rook
		// piece 2 = king
		} else if (board.boardArray[piece1Rank][piece1File].getPieceType() == PieceType.ROOK
				&& board.boardArray[piece2Rank][piece2File].getPieceType() == PieceType.KING) {
			if (board.boardArray[piece1Rank][piece1File].getMoved() == false
					&& board.boardArray[piece2Rank][piece2File].getMoved() == false) {
				
				if (piece1File < piece2File) {
					board.boardArray[piece1Rank][piece1File - 2] = board.boardArray[piece1Rank][piece1File];
					board.boardArray[piece2Rank][piece1File - 1] = board.boardArray[piece2Rank][piece2File];
					
					board.boardArray[piece1Rank][piece1File] = null;
					board.boardArray[piece2Rank][piece2File] = null;
					
					board.boardArray[piece1Rank][piece1File - 2].setMoved();
					board.boardArray[piece2Rank][piece1File - 1].setMoved();
				} else {
					board.boardArray[piece1Rank][piece1File + 2] = board.boardArray[piece1Rank][piece1File];
					board.boardArray[piece2Rank][piece1File + 1] = board.boardArray[piece2Rank][piece2File];
					
					board.boardArray[piece1Rank][piece1File] = null;
					board.boardArray[piece2Rank][piece2File] = null;
					
					board.boardArray[piece1Rank][piece1File + 2].setMoved();
					board.boardArray[piece2Rank][piece1File + 1].setMoved();
				}
			}
		}
	} // castle
	
	/**
	 * Captures a pawn piece en passant.
	 * 
	 * @param pawns             String representation of both Pawn pieces.
	 */
	public void enPassant(TestBoard board, String pawns) {
		int attackerRank = Integer.parseInt(pawns.substring(1, 2));
		int attackerFile = charMap.get(pawns.charAt(0));
		
		int enemyRank = Integer.parseInt(pawns.substring(3, 4));
		int enemyFile = charMap.get(pawns.charAt(2));
		
		if (board.boardArray[attackerRank][attackerFile].getPlayer() == Player.BLACK) {
			board.boardArray[enemyRank][enemyFile] = null;
			board.boardArray[enemyRank + 1][enemyFile] = board.boardArray[attackerRank][attackerFile];
			board.boardArray[attackerRank][attackerFile] = null;
			board.boardArray[enemyRank + 1][enemyFile].setMoved();
		} else {
			board.boardArray[enemyRank][enemyFile] = null;
			board.boardArray[enemyRank - 1][enemyFile] = board.boardArray[attackerRank][attackerFile];
			board.boardArray[attackerRank][attackerFile] = null;
			board.boardArray[enemyRank - 1][enemyFile].setMoved();
		}
		
	} // enPassant

	/**
	 * Moves a piece on the game board.
	 * 
	 * @param board             Custom game board.
	 * @param startRank         Starting Rank (Piece to be moved).
	 * @param startFile         Starting File (Piece to be moved).
	 * @param destRank          Destination Rank.
	 * @param destFile          Destination File.   
	 */
	public void movePiece(TestBoard board, int startRank,  int startFile, int destRank, int destFile) {
		board.boardArray[destRank][destFile] = null;
		board.boardArray[destRank][destFile] = board.boardArray[startRank][startFile];
		board.boardArray[startRank][startFile] = null;
		board.boardArray[destRank][destFile].setMoved();
		board.boardArray[destRank][destFile].setMovedTwo(true);
		
		//lastMove(board, startRank, startFile, destRank, destFile);
	} // movePiece
	
	public void lastMove(TestBoard board, int startRank, int startFile, int destRank, int destFile) {
		
	}
	
	/**
	 * Checks move is legal.
	 * <p>
	 * Tries to match the chosen move with all possible moves for the selected piece.
	 * 
	 * @param board             Custom game board.
	 * @param startRank         Starting Rank (Piece to be moved).
	 * @param startFile         Starting File (Piece to be moved).
	 * @param destRank          Destination Rank.
	 * @param destFile          Destination File.
	 * @return                  True for legal move, False for illegal move.
	 */
	public boolean isLegalMove(TestBoard board, int startRank, int startFile, int destRank, int destFile) {
		String destination = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(destRank) + String.valueOf(destFile);
		Collection<String> possibleMoves = board.boardArray[startRank][startFile].generatePossibleMoves(board.boardArray, startRank, startFile);
		
		for (String move : possibleMoves) {
			if (move.substring(0, 4).equals(destination)) {
				return true;
			}
		}
		
		return false;
	} // isLegalMove
}