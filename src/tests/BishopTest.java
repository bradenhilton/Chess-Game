package tests;

import java.util.Scanner;

import game.Player;
import pieces.Bishop;
import pieces.Piece;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class BishopTest {
    private static int ranks = 8;
    private static int files = 8;
    private static Piece[][] boardArray = new Piece[ranks][files];
    private static TestBoard board;

    public static void main(String[] args) {
        int test = 0;
        Scanner selection = new Scanner(System.in);

        System.out.println("Which test would you like to do?");
        System.out.println("1) List all possible moves");
        System.out.println("2) Test diagonal moves");
        System.out.println("3) Blocked diagonal moves");
        System.out.println("4) Test diagonal capture");

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
            boardArray[4][3] = new Bishop(Player.WHITE, 4, 3);
            // boardArray[0][4] = new King(Player.BLACK);
            // boardArray[2][3] = new Bishop(Player.BLACK);
            // boardArray[7][4] = new King(Player.WHITE);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 2:
            boardArray[4][3] = new Bishop(Player.WHITE, 4, 3);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 3:
            boardArray[4][3] = new Bishop(Player.WHITE, 4, 3);
            boardArray[3][2] = new Bishop(Player.WHITE, 3, 2);
            boardArray[3][4] = new Bishop(Player.WHITE, 3, 4);
            boardArray[5][2] = new Bishop(Player.WHITE, 5, 2);
            boardArray[5][4] = new Bishop(Player.WHITE, 5, 4);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 4:
            boardArray[6][5] = new Bishop(Player.WHITE, 6, 5);
            boardArray[5][5] = new Bishop(Player.BLACK, 5, 5);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        }

        selection.close();
    }
}