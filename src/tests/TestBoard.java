package tests;

import pieces.*;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class TestBoard {
	private int ranks = 8;
	private int files = 8;
	private String spacer = "   ";
	protected Piece[][] boardArray = new Piece[ranks][files];
	
	/**
	 * 
	 * @param customBoardArray
	 */
	public TestBoard(Piece[][] customBoardArray) {
		boardArray = customBoardArray;
		printBoard(boardArray);
	}
	
	/**
	 * Prints the board to the console
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
} // TestBoard