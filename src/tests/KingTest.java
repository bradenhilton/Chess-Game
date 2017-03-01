package tests;

import java.util.Scanner;

import game.Player;
import pieces.*;

public class KingTest {
	private static int ranks = 8;
	private static int files = 8;
	private static Piece[][] boardArray = new Piece[ranks][files];

	public static void main(String[] args) {
		int test = 0;
		Scanner selection = new Scanner(System.in);
		
		System.out.println("Which test would you like to do?");
		System.out.println("1) List possible moves");
		System.out.println("2) Test horizontal and vertical moves");
		System.out.println("3) Test diagonal moves");
		System.out.println("4) Capture diagonally");
		System.out.println("5) Capture horizontally and vertically");
		System.out.println("6) Castle");
		System.out.println("7) Blocked Castle");
		
		String input;
		while (test == 0) {			
			input = selection.nextLine();
			if (!(input.matches("[0-9]"))) {
				System.out.println("Please enter a number");
			} else {				
				test = Integer.parseInt(input);
			}
		}
		
		switch (test) {
		case 1: 
			boardArray[4][3] = new King(Player.WHITE);
			TestBoard board1 = new TestBoard(boardArray);
			new TestLoop(board1);
			break;
		case 2:
			boardArray[4][3] = new King(Player.WHITE);
			boardArray[3][4] = new King(Player.WHITE);
			boardArray[3][2] = new King(Player.WHITE);
			boardArray[5][4] = new King(Player.WHITE);
			boardArray[5][2] = new King(Player.WHITE);
			TestBoard board2 = new TestBoard(boardArray);
			new TestLoop(board2);
			break;
		case 3:
			boardArray[4][3] = new King(Player.WHITE);
			boardArray[4][4] = new King(Player.WHITE);
			boardArray[4][2] = new King(Player.WHITE);
			boardArray[3][3] = new King(Player.WHITE);
			boardArray[5][3] = new King(Player.WHITE);
			TestBoard board3 = new TestBoard(boardArray);
			new TestLoop(board3);
			break;
		case 4:
			boardArray[4][3] = new King(Player.WHITE);
			boardArray[3][2] = new Pawn(Player.BLACK);
			boardArray[3][4] = new Pawn(Player.BLACK);
			boardArray[5][2] = new Pawn(Player.BLACK);
			boardArray[5][4] = new Pawn(Player.BLACK);
			TestBoard board4 = new TestBoard(boardArray);
			new TestLoop(board4);
			break;
		case 5:
			boardArray[4][3] = new King(Player.WHITE);
			boardArray[3][3] = new Queen(Player.BLACK);
			boardArray[5][3] = new Queen(Player.BLACK);
			boardArray[4][2] = new Queen(Player.BLACK);
			boardArray[4][4] = new Queen(Player.BLACK);
			TestBoard board5 = new TestBoard(boardArray);
			new TestLoop(board5);
			break;
		case 6:
			boardArray[7][4] = new King(Player.WHITE);
			boardArray[7][0] = new Rook(Player.WHITE);
			boardArray[7][7] = new Rook(Player.WHITE);
			TestBoard board6 = new TestBoard(boardArray);
			new TestLoop(board6);
			break;
		case 7:
			boardArray[7][4] = new King(Player.WHITE);
			boardArray[7][0] = new Rook(Player.WHITE);
			boardArray[7][7] = new Rook(Player.WHITE);
			
			boardArray[1][7] = new Queen(Player.BLACK);
			boardArray[0][3] = new Rook(Player.BLACK);
			
			//boardArray[7][3] = new Queen(Player.WHITE);
			//boardArray[7][5] = new Bishop(Player.WHITE);
			TestBoard board7 = new TestBoard(boardArray);
			new TestLoop(board7);
			break;
		}
		
		selection.close();
	}
}