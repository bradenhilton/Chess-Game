package tests;

import java.util.Scanner;

import game.Player;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class CheckmateTest {
    private static int ranks = 8;
    private static int files = 8;
    private static Piece[][] boardArray = new Piece[ranks][files];
    private static TestBoard board;

    public static void main(String[] args) {
        int test = 0;
        Scanner selection = new Scanner(System.in);

        System.out.println("Which test would you like to do?");
        System.out.println("1) Regular 1 player");
        System.out.println("2) Regular 2 player");
        System.out.println("3) Regular 0 player");
        System.out.println("4) Fool's mate");

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
            boardArray[0][0] = new Rook(Player.BLACK, 0, 0);
            boardArray[0][1] = new Knight(Player.BLACK, 0, 1);
            boardArray[0][2] = new Bishop(Player.BLACK, 0, 2);
            boardArray[0][3] = new Queen(Player.BLACK, 0, 3);
            boardArray[0][4] = new King(Player.BLACK, 0, 4);
            boardArray[0][5] = new Bishop(Player.BLACK, 0, 5);
            boardArray[0][6] = new Knight(Player.BLACK, 0, 6);
            boardArray[0][7] = new Rook(Player.BLACK, 0, 7);

            boardArray[1][0] = new Pawn(Player.BLACK, 1, 0);
            boardArray[1][1] = new Pawn(Player.BLACK, 1, 1);
            boardArray[1][2] = new Pawn(Player.BLACK, 1, 2);
            boardArray[1][3] = new Pawn(Player.BLACK, 1, 3);
            boardArray[1][4] = new Pawn(Player.BLACK, 1, 4);
            boardArray[1][5] = new Pawn(Player.BLACK, 1, 5);
            boardArray[1][6] = new Pawn(Player.BLACK, 1, 6);
            boardArray[1][7] = new Pawn(Player.BLACK, 1, 7);

            boardArray[6][0] = new Pawn(Player.WHITE, 6, 0);
            boardArray[6][1] = new Pawn(Player.WHITE, 6, 1);
            boardArray[6][2] = new Pawn(Player.WHITE, 6, 2);
            boardArray[6][3] = new Pawn(Player.WHITE, 6, 3);
            boardArray[6][4] = new Pawn(Player.WHITE, 6, 4);
            boardArray[6][5] = new Pawn(Player.WHITE, 6, 5);
            boardArray[6][6] = new Pawn(Player.WHITE, 6, 6);
            boardArray[6][7] = new Pawn(Player.WHITE, 6, 7);

            boardArray[7][0] = new Rook(Player.WHITE, 7, 0);
            boardArray[7][1] = new Knight(Player.WHITE, 7, 1);
            boardArray[7][2] = new Bishop(Player.WHITE, 7, 2);
            boardArray[7][3] = new Queen(Player.WHITE, 7, 3);
            boardArray[7][4] = new King(Player.WHITE, 7, 4);
            boardArray[7][5] = new Bishop(Player.WHITE, 7, 5);
            boardArray[7][6] = new Knight(Player.WHITE, 7, 6);
            boardArray[7][7] = new Rook(Player.WHITE, 7, 7);
            board = new TestBoard(boardArray);
            new TestGame(board, 1);
            break;
        case 2:
            boardArray[0][0] = new Rook(Player.BLACK, 0, 0);
            boardArray[0][1] = new Knight(Player.BLACK, 0, 1);
            boardArray[0][2] = new Bishop(Player.BLACK, 0, 2);
            boardArray[0][3] = new Queen(Player.BLACK, 0, 3);
            boardArray[0][4] = new King(Player.BLACK, 0, 4);
            boardArray[0][5] = new Bishop(Player.BLACK, 0, 5);
            boardArray[0][6] = new Knight(Player.BLACK, 0, 6);
            boardArray[0][7] = new Rook(Player.BLACK, 0, 7);

            boardArray[1][0] = new Pawn(Player.BLACK, 1, 0);
            boardArray[1][1] = new Pawn(Player.BLACK, 1, 1);
            boardArray[1][2] = new Pawn(Player.BLACK, 1, 2);
            boardArray[1][3] = new Pawn(Player.BLACK, 1, 3);
            boardArray[1][4] = new Pawn(Player.BLACK, 1, 4);
            boardArray[1][5] = new Pawn(Player.BLACK, 1, 5);
            boardArray[1][6] = new Pawn(Player.BLACK, 1, 6);
            boardArray[1][7] = new Pawn(Player.BLACK, 1, 7);

            boardArray[6][0] = new Pawn(Player.WHITE, 6, 0);
            boardArray[6][1] = new Pawn(Player.WHITE, 6, 1);
            boardArray[6][2] = new Pawn(Player.WHITE, 6, 2);
            boardArray[6][3] = new Pawn(Player.WHITE, 6, 3);
            boardArray[6][4] = new Pawn(Player.WHITE, 6, 4);
            boardArray[6][5] = new Pawn(Player.WHITE, 6, 5);
            boardArray[6][6] = new Pawn(Player.WHITE, 6, 6);
            boardArray[6][7] = new Pawn(Player.WHITE, 6, 7);

            boardArray[7][0] = new Rook(Player.WHITE, 7, 0);
            boardArray[7][1] = new Knight(Player.WHITE, 7, 1);
            boardArray[7][2] = new Bishop(Player.WHITE, 7, 2);
            boardArray[7][3] = new Queen(Player.WHITE, 7, 3);
            boardArray[7][4] = new King(Player.WHITE, 7, 4);
            boardArray[7][5] = new Bishop(Player.WHITE, 7, 5);
            boardArray[7][6] = new Knight(Player.WHITE, 7, 6);
            boardArray[7][7] = new Rook(Player.WHITE, 7, 7);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 3:
            boardArray[0][0] = new Rook(Player.BLACK, 0, 0);
            boardArray[0][1] = new Knight(Player.BLACK, 0, 1);
            boardArray[0][2] = new Bishop(Player.BLACK, 0, 2);
            boardArray[0][3] = new Queen(Player.BLACK, 0, 3);
            boardArray[0][4] = new King(Player.BLACK, 0, 4);
            boardArray[0][5] = new Bishop(Player.BLACK, 0, 5);
            boardArray[0][6] = new Knight(Player.BLACK, 0, 6);
            boardArray[0][7] = new Rook(Player.BLACK, 0, 7);

            boardArray[1][0] = new Pawn(Player.BLACK, 1, 0);
            boardArray[1][1] = new Pawn(Player.BLACK, 1, 1);
            boardArray[1][2] = new Pawn(Player.BLACK, 1, 2);
            boardArray[1][3] = new Pawn(Player.BLACK, 1, 3);
            boardArray[1][4] = new Pawn(Player.BLACK, 1, 4);
            boardArray[1][5] = new Pawn(Player.BLACK, 1, 5);
            boardArray[1][6] = new Pawn(Player.BLACK, 1, 6);
            boardArray[1][7] = new Pawn(Player.BLACK, 1, 7);

            boardArray[6][0] = new Pawn(Player.WHITE, 6, 0);
            boardArray[6][1] = new Pawn(Player.WHITE, 6, 1);
            boardArray[6][2] = new Pawn(Player.WHITE, 6, 2);
            boardArray[6][3] = new Pawn(Player.WHITE, 6, 3);
            boardArray[6][4] = new Pawn(Player.WHITE, 6, 4);
            boardArray[6][5] = new Pawn(Player.WHITE, 6, 5);
            boardArray[6][6] = new Pawn(Player.WHITE, 6, 6);
            boardArray[6][7] = new Pawn(Player.WHITE, 6, 7);

            boardArray[7][0] = new Rook(Player.WHITE, 7, 0);
            boardArray[7][1] = new Knight(Player.WHITE, 7, 1);
            boardArray[7][2] = new Bishop(Player.WHITE, 7, 2);
            boardArray[7][3] = new Queen(Player.WHITE, 7, 3);
            boardArray[7][4] = new King(Player.WHITE, 7, 4);
            boardArray[7][5] = new Bishop(Player.WHITE, 7, 5);
            boardArray[7][6] = new Knight(Player.WHITE, 7, 6);
            boardArray[7][7] = new Rook(Player.WHITE, 7, 7);
            board = new TestBoard(boardArray);
            new TestGame(board, 0);
            break;
        case 4:
            System.out.println("f2f3 e7e5 g2g4 d854 checkmate");
            boardArray[0][0] = new Rook(Player.BLACK, 0, 0);
            boardArray[0][1] = new Knight(Player.BLACK, 0, 1);
            boardArray[0][2] = new Bishop(Player.BLACK, 0, 2);
            boardArray[0][3] = new Queen(Player.BLACK, 0, 3);
            boardArray[0][4] = new King(Player.BLACK, 0, 4);
            boardArray[0][5] = new Bishop(Player.BLACK, 0, 5);
            boardArray[0][6] = new Knight(Player.BLACK, 0, 6);
            boardArray[0][7] = new Rook(Player.BLACK, 0, 7);

            boardArray[1][0] = new Pawn(Player.BLACK, 1, 0);
            boardArray[1][1] = new Pawn(Player.BLACK, 1, 1);
            boardArray[1][2] = new Pawn(Player.BLACK, 1, 2);
            boardArray[1][3] = new Pawn(Player.BLACK, 1, 3);
            boardArray[1][4] = new Pawn(Player.BLACK, 1, 4);
            boardArray[1][5] = new Pawn(Player.BLACK, 1, 5);
            boardArray[1][6] = new Pawn(Player.BLACK, 1, 6);
            boardArray[1][7] = new Pawn(Player.BLACK, 1, 7);

            boardArray[6][0] = new Pawn(Player.WHITE, 6, 0);
            boardArray[6][1] = new Pawn(Player.WHITE, 6, 1);
            boardArray[6][2] = new Pawn(Player.WHITE, 6, 2);
            boardArray[6][3] = new Pawn(Player.WHITE, 6, 3);
            boardArray[6][4] = new Pawn(Player.WHITE, 6, 4);
            boardArray[6][5] = new Pawn(Player.WHITE, 6, 5);
            boardArray[6][6] = new Pawn(Player.WHITE, 6, 6);
            boardArray[6][7] = new Pawn(Player.WHITE, 6, 7);

            boardArray[7][0] = new Rook(Player.WHITE, 7, 0);
            boardArray[7][1] = new Knight(Player.WHITE, 7, 1);
            boardArray[7][2] = new Bishop(Player.WHITE, 7, 2);
            boardArray[7][3] = new Queen(Player.WHITE, 7, 3);
            boardArray[7][4] = new King(Player.WHITE, 7, 4);
            boardArray[7][5] = new Bishop(Player.WHITE, 7, 5);
            boardArray[7][6] = new Knight(Player.WHITE, 7, 6);
            boardArray[7][7] = new Rook(Player.WHITE, 7, 7);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        }

        selection.close();
    }
}