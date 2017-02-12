package tests;

import java.util.*;
import game.*;
import pieces.*;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class PawnTest {
	private static int ranks = 8;
	private static int files = 8;
	private static Piece[][] boardArray = new Piece[ranks][files];
	
	public static void main(String[] args) {
		int test = 0;
		Scanner selection = new Scanner(System.in);
		
		System.out.println("Which test would you like to do?");
		System.out.println("1) List possible moves");
		System.out.println("2) Move by One");
		System.out.println("3) Move by Two");
		System.out.println("4) Move by One (Blocked by White Piece)");
		System.out.println("5) Move by One (Blocked by Black Piece)");
		System.out.println("6) Capture Left");
		System.out.println("7) Capture Right");
		System.out.println("8) En-Passant");
		System.out.println("9) Promotion");
		System.out.println("10) Check test");
		
		try {
			test = Integer.parseInt(selection.nextLine());
		} catch (NumberFormatException nfe) {
			System.err.println(nfe);
		}
		
		switch (test) {
			case 1:
				boardArray[6][3] = new Pawn(Player.WHITE);
				boardArray[1][5] = new Pawn(Player.BLACK);
				TestBoard board1 = new TestBoard(boardArray);
				new TestLoop(board1);
				break;
			case 2:
				boardArray[6][5] = new Pawn(Player.WHITE);
				boardArray[1][5] = new Pawn(Player.BLACK);
				TestBoard board2 = new TestBoard(boardArray);
				new TestLoop(board2);
				break;
			case 3:
				boardArray[6][5] = new Pawn(Player.WHITE);
				boardArray[1][5] = new Pawn(Player.BLACK);
				TestBoard board3 = new TestBoard(boardArray);
				new TestLoop(board3);
				break;
			case 4:
				boardArray[6][5] = new Pawn(Player.WHITE);
				boardArray[5][5] = new Pawn(Player.WHITE);
				TestBoard board4 = new TestBoard(boardArray);
				new TestLoop(board4);
				break;
			case 5:
				boardArray[6][5] = new Pawn(Player.WHITE);
				boardArray[5][5] = new Pawn(Player.BLACK);
				TestBoard board5 = new TestBoard(boardArray);
				new TestLoop(board5);
				break;
			case 6:
				boardArray[6][5] = new Pawn(Player.WHITE);
				boardArray[5][4] = new Pawn(Player.BLACK);
				TestBoard board6 = new TestBoard(boardArray);
				new TestLoop(board6);
				break;
			case 7:
				boardArray[6][5] = new Pawn(Player.WHITE);
				boardArray[5][6] = new Pawn(Player.BLACK);
				TestBoard board7 = new TestBoard(boardArray);
				new TestLoop(board7);
				break;
			case 8:
				boardArray[4][4] = new Pawn(Player.WHITE);
				boardArray[1][3] = new Pawn(Player.BLACK);
				TestBoard board8 = new TestBoard(boardArray);
				new TestLoop(board8);
				break;
			case 9:
				boardArray[1][3] = new Pawn(Player.WHITE);
				boardArray[6][5] = new Pawn(Player.BLACK);
				TestBoard board9 = new TestBoard(boardArray);
				new TestLoop(board9);
				break;
			case 10:
				boardArray[7][5] = new Pawn(Player.WHITE);
				boardArray[5][4] = new King(Player.BLACK);
				TestBoard board10 = new TestBoard(boardArray);
				new TestLoop(board10);
				break;
		}
		
		selection.close();
	}
}