package tests;

import java.util.*;
import game.*;
import pieces.*;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class BishopTest {
	private static int ranks = 8;
	private static int files = 8;
	private static Piece[][] boardArray = new Piece[ranks][files];
	
	public static void main(String[] args) {
		int test = 0;
		Scanner selection = new Scanner(System.in);
		
		System.out.println("Which test would you like to do?");
		System.out.println("1) List all possible moves");
		System.out.println("2) ");
		System.out.println("3) ");
		System.out.println("4) ");
		System.out.println("5) ");
		System.out.println("6) ");
		System.out.println("7) ");
		try {
			test = Integer.parseInt(selection.nextLine());
		} catch (NumberFormatException nfe) {
			System.err.println(nfe);
		}
		
		switch (test) {
		case 1: boardArray[5][3] = new Bishop(Player.WHITE);
				boardArray[0][4] = new King(Player.BLACK);
				boardArray[2][3] = new Bishop(Player.BLACK);
				boardArray[7][4] = new King(Player.WHITE);
				TestBoard board1 = new TestBoard(boardArray);
				new TestLoop(board1);
				break;
		case 2: boardArray[6][5] = new Bishop(Player.WHITE);
				TestBoard board2 = new TestBoard(boardArray);
				new TestLoop(board2);
				break;
		case 3: boardArray[6][5] = new Bishop(Player.WHITE);
				boardArray[5][5] = new Bishop(Player.WHITE);
				TestBoard board3 = new TestBoard(boardArray);
				new TestLoop(board3);
				break;
		case 4: boardArray[6][5] = new Bishop(Player.WHITE);
				boardArray[5][5] = new Bishop(Player.BLACK);
				TestBoard board4 = new TestBoard(boardArray);
				new TestLoop(board4);
				break;
		case 5: boardArray[6][5] = new Bishop(Player.WHITE);
				boardArray[5][4] = new Bishop(Player.BLACK);
				TestBoard board5 = new TestBoard(boardArray);
				new TestLoop(board5);
				break;
		case 6: boardArray[6][5] = new Bishop(Player.WHITE);
				boardArray[5][6] = new Bishop(Player.BLACK);
				TestBoard board6 = new TestBoard(boardArray);
				new TestLoop(board6);
				break;
		case 7:	boardArray[6][5] = new Bishop(Player.WHITE);
				boardArray[5][6] = new Bishop(Player.BLACK);
				TestBoard board7 = new TestBoard(boardArray);
				new TestLoop(board7);
				break;
	}
	
	selection.close();
	}
}