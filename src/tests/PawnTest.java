package tests;

import java.util.Scanner;

import game.Player;
import pieces.King;
import pieces.Pawn;
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
        System.out.println("10) Promote capture");
        System.out.println("11) Check test");
        System.out.println("12) AI En passant");
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
            boardArray[6][3] = new Pawn(Player.WHITE, 6, 3);
            boardArray[1][5] = new Pawn(Player.BLACK, 1, 5);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 2:
            boardArray[6][5] = new Pawn(Player.WHITE, 6, 5);
            boardArray[1][5] = new Pawn(Player.BLACK, 1, 5);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 3:
            boardArray[6][5] = new Pawn(Player.WHITE, 6, 5);
            boardArray[1][5] = new Pawn(Player.BLACK, 1, 5);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 4:
            boardArray[6][5] = new Pawn(Player.WHITE, 6, 5);
            boardArray[5][5] = new Pawn(Player.WHITE, 5, 5);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 5:
            boardArray[6][5] = new Pawn(Player.WHITE, 6, 5);
            boardArray[5][5] = new Pawn(Player.BLACK, 5, 5);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 6:
            boardArray[6][5] = new Pawn(Player.WHITE, 6, 5);
            boardArray[5][4] = new Pawn(Player.BLACK, 5, 4);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 7:
            boardArray[6][5] = new Pawn(Player.WHITE, 6, 5);
            boardArray[5][6] = new Pawn(Player.BLACK, 5, 6);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 8:
            // boardArray[4][4] = new Pawn(Player.WHITE, 4, 4);
            // boardArray[2][3] = new Pawn(Player.BLACK, 1, 3);

            boardArray[6][1] = new Pawn(Player.WHITE, 6, 1);
            boardArray[6][4] = new Pawn(Player.WHITE, 6, 4);
            boardArray[4][3] = new Pawn(Player.BLACK, 4, 3);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 9:
            boardArray[1][3] = new Pawn(Player.WHITE, 1, 3);
            boardArray[6][5] = new Pawn(Player.BLACK, 6, 5);
            board = new TestBoard(boardArray);
            new TestGame(board, 1);
            break;
        case 10:
            boardArray[1][3] = new Pawn(Player.WHITE, 1, 3);
            boardArray[0][2] = new Pawn(Player.BLACK, 0, 2);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 11:
            boardArray[7][5] = new Pawn(Player.WHITE, 7, 5);
            boardArray[5][4] = new King(Player.BLACK, 5, 4);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 12:
            boardArray[1][4] = new Pawn(Player.BLACK, 1, 4);
            boardArray[3][5] = new Pawn(Player.WHITE, 3, 5);
            board = new TestBoard(boardArray);
            new TestGame(board, 1);
            break;
        }

        selection.close();
    }
}