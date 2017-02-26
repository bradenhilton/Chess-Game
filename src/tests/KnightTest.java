package tests;

import java.util.Scanner;

import game.Player;
import pieces.Knight;
import pieces.Piece;

public class KnightTest {
	private static int ranks = 8;
	private static int files = 8;
	private static Piece[][] boardArray = new Piece[ranks][files];
	
	public static void main(String[] args) {
		int test = 0;
		Scanner selection = new Scanner(System.in);
		
		System.out.println("Which test would you like to do?");
		System.out.println("1) List possible moves");
		System.out.println("2) Test jump collision");
		System.out.println("3) Test capture");
		
		try {
			test = Integer.parseInt(selection.nextLine());
		} catch (NumberFormatException nfe) {
			System.err.println(nfe);
		}
		
		switch (test) {
		case 1: 
			boardArray[4][3] = new Knight(Player.WHITE);
			boardArray[6][3] = new Knight(Player.WHITE);
			boardArray[5][5] = new Knight(Player.WHITE);
			
			//boardArray[4][3] = new Knight(Player.BLACK);
			//boardArray[6][3] = new Knight(Player.BLACK);
			//boardArray[5][5] = new Knight(Player.BLACK);
			TestBoard board1 = new TestBoard(boardArray);
			new TestLoop(board1);
			break;
		case 2:
			boardArray[4][3] = new Knight(Player.WHITE);
			boardArray[3][4] = new Knight(Player.WHITE);
			boardArray[3][2] = new Knight(Player.WHITE);
			boardArray[5][4] = new Knight(Player.WHITE);
			boardArray[5][2] = new Knight(Player.WHITE);
			
			//boardArray[4][3] = new Knight(Player.BLACK);
			//boardArray[3][4] = new Knight(Player.BLACK);
			//boardArray[3][2] = new Knight(Player.BLACK);
			//boardArray[5][4] = new Knight(Player.BLACK);
			//boardArray[5][2] = new Knight(Player.BLACK);
			TestBoard board2 = new TestBoard(boardArray);
			new TestLoop(board2);
			break;
		case 3:
			boardArray[4][3] = new Knight(Player.WHITE);
			boardArray[4][4] = new Knight(Player.WHITE);
			boardArray[4][2] = new Knight(Player.WHITE);
			boardArray[3][3] = new Knight(Player.WHITE);
			boardArray[5][3] = new Knight(Player.WHITE);
			
			//boardArray[4][3] = new Knight(Player.BLACK);
			//boardArray[4][4] = new Knight(Player.BLACK);
			//boardArray[4][2] = new Knight(Player.BLACK);
			//boardArray[3][3] = new Knight(Player.BLACK);
			//boardArray[5][3] = new Knight(Player.BLACK);
			TestBoard board3 = new TestBoard(boardArray);
			new TestLoop(board3);
			break;
		}
		
		selection.close();
	}
}