package game;

import pieces.*;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class Board {
	private int ranks = 8;
	private int files = 8;
	private String spacer = "   ";
	protected Piece[][] boardArray = new Piece[ranks][files];
	
	/**
	 * 
	 */
	public Board() {
		resetBoard(boardArray);
		printBoard(boardArray);
	}
	
	/**
	 * Prints the board to the console.
	 * 
	 * @param board
	 */
	public void printBoard(Piece[][] board) {
		System.out.println("\n " + spacer + " a " + spacer + " b " + spacer + " c " + spacer + " d " + spacer + " e " + spacer + " f " + spacer + " g " + spacer + " h ");
		for (int i = 0; i < ranks; i++) {
			System.out.println("  -------------------------------------------------");
			System.out.print((8 - i) + " ");
			for (int j = 0; j < files; j++) {
				System.out.print("| ");
				if (board[i][j] == null) {
					System.out.print(spacer);
				} else {
					System.out.print(board[i][j].toString());
				}
				System.out.print(" ");
			}
			System.out.print("|");
			System.out.println(" " + (8 - i));
		}
		System.out.println("  -------------------------------------------------");
		System.out.println(" " + spacer + " a " + spacer + " b " + spacer + " c " + spacer + " d " + spacer + " e " + spacer + " f " + spacer + " g " + spacer + " h\n");
	}
	
	/**
	 * Resets the board at the start of a game.
	 * 
	 *  @param board
	 */
	public void resetBoard(Piece[][] board) {
		board[0][0] = new Rook(Player.BLACK);
		board[0][1] = new Knight(Player.BLACK);
		board[0][2] = new Bishop(Player.BLACK);
		board[0][3] = new Queen(Player.BLACK);
		board[0][4] = new King(Player.BLACK);
		board[0][5] = new Bishop(Player.BLACK);
		board[0][6] = new Knight(Player.BLACK);
		board[0][7] = new Rook(Player.BLACK);
		
		board[1][0] = new Pawn(Player.BLACK);
		board[1][1] = new Pawn(Player.BLACK);
		board[1][2] = new Pawn(Player.BLACK);
		board[1][3] = new Pawn(Player.BLACK);
		board[1][4] = new Pawn(Player.BLACK);
		board[1][5] = new Pawn(Player.BLACK);
		board[1][6] = new Pawn(Player.BLACK);
		board[1][7] = new Pawn(Player.BLACK);
		
		board[6][0] = new Pawn(Player.WHITE);
		board[6][1] = new Pawn(Player.WHITE);
		board[6][2] = new Pawn(Player.WHITE);
		board[6][3] = new Pawn(Player.WHITE);
		board[6][4] = new Pawn(Player.WHITE);
		board[6][5] = new Pawn(Player.WHITE);
		board[6][6] = new Pawn(Player.WHITE);
		board[6][7] = new Pawn(Player.WHITE);
		
		board[7][0] = new Rook(Player.WHITE);
		board[7][1] = new Knight(Player.WHITE);
		board[7][2] = new Bishop(Player.WHITE);
		board[7][3] = new Queen(Player.WHITE);
		board[7][4] = new King(Player.WHITE);
		board[7][5] = new Bishop(Player.WHITE);
		board[7][6] = new Knight(Player.WHITE);
		board[7][7] = new Rook(Player.WHITE);
	}
} // Board