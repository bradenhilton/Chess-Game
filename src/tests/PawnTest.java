package tests;

import java.util.Scanner;

import game.Player;
import pieces.Pawn;
import pieces.King;
import pieces.Piece;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class PawnTest {
    private static int ranks = 8;
    private static int files = 8;
    private static Piece[][] boardArray = new Piece[ranks][files];
    private static TestBoard board;

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
        System.out.println("8) En passant");
        System.out.println("9) Promotion");
        System.out.println("10) Check test");
        System.out.println("11) AI En passant");
        // System.out.println("");

        String input;
        while (test == 0) {
            input = selection.nextLine();
            if (!(input.matches("[1-9][0-9]*"))) {
                System.out.println("Please enter a number");
            } else {
                test = Integer.parseInt(input);
            }
        }

        switch (test) {
        case 1:
            boardArray[6][3] = new Pawn(Player.WHITE);
            boardArray[1][5] = new Pawn(Player.BLACK);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 2:
            boardArray[6][5] = new Pawn(Player.WHITE);
            boardArray[1][5] = new Pawn(Player.BLACK);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 3:
            boardArray[6][5] = new Pawn(Player.WHITE);
            boardArray[1][5] = new Pawn(Player.BLACK);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 4:
            boardArray[6][5] = new Pawn(Player.WHITE);
            boardArray[5][5] = new Pawn(Player.WHITE);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 5:
            boardArray[6][5] = new Pawn(Player.WHITE);
            boardArray[5][5] = new Pawn(Player.BLACK);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 6:
            boardArray[6][5] = new Pawn(Player.WHITE);
            boardArray[5][4] = new Pawn(Player.BLACK);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 7:
            boardArray[6][5] = new Pawn(Player.WHITE);
            boardArray[5][6] = new Pawn(Player.BLACK);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 8:
            boardArray[4][4] = new Pawn(Player.WHITE);
            boardArray[1][3] = new Pawn(Player.BLACK);

            // boardArray[6][4] = new Pawn(Player.WHITE);
            // boardArray[4][3] = new Pawn(Player.BLACK);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 9:
            boardArray[1][3] = new Pawn(Player.WHITE);
            boardArray[6][5] = new Pawn(Player.BLACK);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 10:
            boardArray[7][5] = new Pawn(Player.WHITE);
            boardArray[5][4] = new King(Player.BLACK);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 11:
            boardArray[1][4] = new Pawn(Player.BLACK);
            boardArray[3][5] = new Pawn(Player.WHITE);
            board = new TestBoard(boardArray);
            new TestGame(board, 1);
            break;
        }

        selection.close();
    }
}