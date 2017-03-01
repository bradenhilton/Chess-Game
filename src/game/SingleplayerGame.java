package game;

import java.util.*;

import game.Board;
import pieces.Pawn;
import pieces.Piece;
import pieces.PieceType;

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
	
	/**
	 * Game loop for one player.
	 * <p>
	 * Does not allow user input for Black turn due to AI.
	 * 
	 * @param whiteTurn		    Boolean which dictates whose turn it is.
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
				
				System.out.println("White to move (e.g. d2d4 or list or castle)");
				move = input.nextLine();
				
				if (move.equalsIgnoreCase("list")) {
					String piece = input.nextLine();
					
					if (!piece.matches("[a-hA-H][1-8]")) {
						System.out.println("Please enter the location of a piece");
						break;
					} else if (piece.equalsIgnoreCase("q") || piece.equalsIgnoreCase("quit")) {
						break;
					} else {
						listPossibleMoves(piece);
					}
				} else if (move.equalsIgnoreCase("castle")) {
					String kingAndRook = input.nextLine();
					
					if (!kingAndRook.matches("[a-hA-H][1-8][a-hA-H][1-8]")) {
						System.out.println("Please enter the location of the king and the rook to castle");
						break;
					} else if (kingAndRook.equalsIgnoreCase("q") || kingAndRook.equalsIgnoreCase("quit")) {
						break;
					} else {
						castle(kingAndRook);
					}
				} else {
					parseMoveSingleplayer(move, whiteTurn);
				}
			} else {
				if (!moveHistory.isEmpty()) {
					System.out.println(moveHistory.get(moveHistory.size() - 1));
				}
				
				System.out.println("Black to move (e.g. g7g6)");
				
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
				
				Character startFile = reverseCharMap.get(Integer.parseInt(move.substring(1, 2)));
				int startRank = 8 - Integer.parseInt(move.substring(0, 1));
				
				Character newFile = reverseCharMap.get(Integer.parseInt(move.substring(3, 4)));
				int newRank = 8 - Integer.parseInt(move.substring(2, 3));
				
				if (move.substring(4).equalsIgnoreCase("castle")) {
					move = startFile + String.valueOf(startRank) + newFile + String.valueOf(newRank);
					castle(move);
				} else {					
					move = startFile + String.valueOf(startRank) + newFile + String.valueOf(newRank);
					parseMoveSingleplayer(move, whiteTurn);
				}
				
			}
		}
		
		System.out.println("New game? (y/n)");
		restart = input.nextLine();
		if (restart.equalsIgnoreCase("y") || restart.equalsIgnoreCase("yes")) {
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
	public void parseMoveSingleplayer(String move, boolean whiteTurn) {
		int startRank, startFile, newRank, newFile;
			
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
				} else {
					if (!(chessBoard.boardArray[startRank][startFile].getPlayer() == Player.BLACK)) {
						System.err.println("Error: Invalid move, Black cannot move White");
					} else {
						if (isLegalMove(chessBoard.boardArray, startRank, startFile, newRank, newFile)) {
							movePiece(chessBoard.boardArray, startRank, startFile, newRank, newFile);
							
							moveHistory.add("Black " + move.toLowerCase());
							legalMove = true;
							whiteTurn = true;
							singleplayerLoop(whiteTurn);
						} else {
							System.err.println("Error: Invalid move");
						}
					}
				}
			}
		}
	} // parseMoveSingleplayer
	
	/**
	 * Lists all possible moves that can be made by a specified piece.
	 * 
	 * @param piece             String representation of a piece on the board.
	 */
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
				
				if (allPossibleMoves.isEmpty()) {
					System.out.println("None!");
				} else {
					for (String move: allPossibleMoves) {
						destRank = 8 - Integer.parseInt(move.substring(2, 3));
						destFile = reverseCharMap.get(Integer.parseInt(move.substring(3, 4)));

						System.out.println(destFile + "" + destRank + move.substring(4));
					}
				}
			}
		}
	} // listPossibleMoves
	
	/**
	 * Castles a King and a Rook.
	 * 
	 * @param kingAndRook       String representation of the king and rook locations.
	 */
	public void castle(String kingAndRook) {
		int piece1Rank = Integer.parseInt(kingAndRook.substring(1, 2));
		int piece1File = Integer.parseInt(kingAndRook.substring(0, 1));
		
		int piece2Rank = Integer.parseInt(kingAndRook.substring(3, 4));
		int piece2File = Integer.parseInt(kingAndRook.substring(2, 3));
		
		// piece1 = king
		// piece2 = rook
		if (chessBoard.boardArray[piece1Rank][piece1File].getPieceType() == PieceType.KING
				&& chessBoard.boardArray[piece2Rank][piece2File].getPieceType() == PieceType.ROOK) {
			if (chessBoard.boardArray[piece1Rank][piece1File].getMoved() == false
					&& chessBoard.boardArray[piece2Rank][piece2File].getMoved() == false) {
				
				if (piece1File > piece2File) {
					chessBoard.boardArray[piece1Rank][piece1File - 2] = chessBoard.boardArray[piece1Rank][piece1File];
					chessBoard.boardArray[piece2Rank][piece1File - 1] = chessBoard.boardArray[piece2Rank][piece2File];
					
					chessBoard.boardArray[piece1Rank][piece1File] = null;
					chessBoard.boardArray[piece2Rank][piece2File] = null;
					
					chessBoard.boardArray[piece1Rank][piece1File - 2].setMoved();
					chessBoard.boardArray[piece2Rank][piece1File - 1].setMoved();
				} else {
					chessBoard.boardArray[piece1Rank][piece1File + 2] = chessBoard.boardArray[piece1Rank][piece1File];
					chessBoard.boardArray[piece2Rank][piece1File + 1] = chessBoard.boardArray[piece2Rank][piece2File];
					
					chessBoard.boardArray[piece1Rank][piece1File] = null;
					chessBoard.boardArray[piece2Rank][piece2File] = null;
					
					chessBoard.boardArray[piece1Rank][piece1File + 2].setMoved();
					chessBoard.boardArray[piece2Rank][piece1File + 1].setMoved();
				}
			}
		// piece 1 = rook
		// piece 2 = king
		} else if (chessBoard.boardArray[piece1Rank][piece1File].getPieceType() == PieceType.ROOK
				&& chessBoard.boardArray[piece2Rank][piece2File].getPieceType() == PieceType.KING) {
			if (chessBoard.boardArray[piece1Rank][piece1File].getMoved() == false
					&& chessBoard.boardArray[piece2Rank][piece2File].getMoved() == false) {
				
				if (piece1File < piece2File) {
					chessBoard.boardArray[piece1Rank][piece1File - 2] = chessBoard.boardArray[piece1Rank][piece1File];
					chessBoard.boardArray[piece2Rank][piece1File - 1] = chessBoard.boardArray[piece2Rank][piece2File];
					
					chessBoard.boardArray[piece1Rank][piece1File] = null;
					chessBoard.boardArray[piece2Rank][piece2File] = null;
					
					chessBoard.boardArray[piece1Rank][piece1File - 2].setMoved();
					chessBoard.boardArray[piece2Rank][piece1File - 1].setMoved();
				} else {
					chessBoard.boardArray[piece1Rank][piece1File + 2] = chessBoard.boardArray[piece1Rank][piece1File];
					chessBoard.boardArray[piece2Rank][piece1File + 1] = chessBoard.boardArray[piece2Rank][piece2File];
					
					chessBoard.boardArray[piece1Rank][piece1File] = null;
					chessBoard.boardArray[piece2Rank][piece2File] = null;
					
					chessBoard.boardArray[piece1Rank][piece1File + 2].setMoved();
					chessBoard.boardArray[piece2Rank][piece1File + 1].setMoved();
				}
			}
		}
	} // castle
	
	/**
	 * Captures a pawn piece en passant.
	 * 
	 * @param pawns             String representation of both Pawn pieces.
	 */
	public void enPassant(String pawns) {
		int attackerRank = Integer.parseInt(pawns.substring(1, 2));
		int attackerFile = Integer.parseInt(pawns.substring(0, 1));
		
		int enemyRank = Integer.parseInt(pawns.substring(3, 4));
		int enemyFile = Integer.parseInt(pawns.substring(2, 3));
		
		chessBoard.boardArray[enemyRank][enemyFile] = null;
		chessBoard.boardArray[enemyRank + 1][enemyFile] = chessBoard.boardArray[attackerRank][attackerFile];
		chessBoard.boardArray[attackerRank][attackerFile] = null;
		chessBoard.boardArray[enemyRank + 1][enemyFile].setMoved();
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
