package tests;

import java.util.*;
import game.*;
import pieces.*;

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
        System.out.println("1) Regular 2 player");
        System.out.println("2) Fool's mate");

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
            boardArray[0][0] = new Rook(Player.BLACK);
            boardArray[0][1] = new Knight(Player.BLACK);
            boardArray[0][2] = new Bishop(Player.BLACK);
            boardArray[0][3] = new Queen(Player.BLACK);
            boardArray[0][4] = new King(Player.BLACK);
            boardArray[0][5] = new Bishop(Player.BLACK);
            boardArray[0][6] = new Knight(Player.BLACK);
            boardArray[0][7] = new Rook(Player.BLACK);

            boardArray[1][0] = new Pawn(Player.BLACK);
            boardArray[1][1] = new Pawn(Player.BLACK);
            boardArray[1][2] = new Pawn(Player.BLACK);
            boardArray[1][3] = new Pawn(Player.BLACK);
            boardArray[1][4] = new Pawn(Player.BLACK);
            boardArray[1][5] = new Pawn(Player.BLACK);
            boardArray[1][6] = new Pawn(Player.BLACK);
            boardArray[1][7] = new Pawn(Player.BLACK);

            boardArray[6][0] = new Pawn(Player.WHITE);
            boardArray[6][1] = new Pawn(Player.WHITE);
            boardArray[6][2] = new Pawn(Player.WHITE);
            boardArray[6][3] = new Pawn(Player.WHITE);
            boardArray[6][4] = new Pawn(Player.WHITE);
            boardArray[6][5] = new Pawn(Player.WHITE);
            boardArray[6][6] = new Pawn(Player.WHITE);
            boardArray[6][7] = new Pawn(Player.WHITE);

            boardArray[7][0] = new Rook(Player.WHITE);
            boardArray[7][1] = new Knight(Player.WHITE);
            boardArray[7][2] = new Bishop(Player.WHITE);
            boardArray[7][3] = new Queen(Player.WHITE);
            boardArray[7][4] = new King(Player.WHITE);
            boardArray[7][5] = new Bishop(Player.WHITE);
            boardArray[7][6] = new Knight(Player.WHITE);
            boardArray[7][7] = new Rook(Player.WHITE);
            board = new TestBoard(boardArray);
            new TestGame(board, 1);
            break;
        case 2:
            boardArray[0][0] = new Rook(Player.BLACK);
            boardArray[0][1] = new Knight(Player.BLACK);
            boardArray[0][2] = new Bishop(Player.BLACK);
            boardArray[0][3] = new Queen(Player.BLACK);
            boardArray[0][4] = new King(Player.BLACK);
            boardArray[0][5] = new Bishop(Player.BLACK);
            boardArray[0][6] = new Knight(Player.BLACK);
            boardArray[0][7] = new Rook(Player.BLACK);

            boardArray[1][0] = new Pawn(Player.BLACK);
            boardArray[1][1] = new Pawn(Player.BLACK);
            boardArray[1][2] = new Pawn(Player.BLACK);
            boardArray[1][3] = new Pawn(Player.BLACK);
            boardArray[1][4] = new Pawn(Player.BLACK);
            boardArray[1][5] = new Pawn(Player.BLACK);
            boardArray[1][6] = new Pawn(Player.BLACK);
            boardArray[1][7] = new Pawn(Player.BLACK);

            boardArray[6][0] = new Pawn(Player.WHITE);
            boardArray[6][1] = new Pawn(Player.WHITE);
            boardArray[6][2] = new Pawn(Player.WHITE);
            boardArray[6][3] = new Pawn(Player.WHITE);
            boardArray[6][4] = new Pawn(Player.WHITE);
            boardArray[6][5] = new Pawn(Player.WHITE);
            boardArray[6][6] = new Pawn(Player.WHITE);
            boardArray[6][7] = new Pawn(Player.WHITE);

            boardArray[7][0] = new Rook(Player.WHITE);
            boardArray[7][1] = new Knight(Player.WHITE);
            boardArray[7][2] = new Bishop(Player.WHITE);
            boardArray[7][3] = new Queen(Player.WHITE);
            boardArray[7][4] = new King(Player.WHITE);
            boardArray[7][5] = new Bishop(Player.WHITE);
            boardArray[7][6] = new Knight(Player.WHITE);
            boardArray[7][7] = new Rook(Player.WHITE);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 3:
            System.out.println("f2f3 e7e5 g2g4 d854 checkmate");
            boardArray[0][0] = new Rook(Player.BLACK);
            boardArray[0][1] = new Knight(Player.BLACK);
            boardArray[0][2] = new Bishop(Player.BLACK);
            boardArray[0][3] = new Queen(Player.BLACK);
            boardArray[0][4] = new King(Player.BLACK);
            boardArray[0][5] = new Bishop(Player.BLACK);
            boardArray[0][6] = new Knight(Player.BLACK);
            boardArray[0][7] = new Rook(Player.BLACK);

            boardArray[1][0] = new Pawn(Player.BLACK);
            boardArray[1][1] = new Pawn(Player.BLACK);
            boardArray[1][2] = new Pawn(Player.BLACK);
            boardArray[1][3] = new Pawn(Player.BLACK);
            boardArray[1][4] = new Pawn(Player.BLACK);
            boardArray[1][5] = new Pawn(Player.BLACK);
            boardArray[1][6] = new Pawn(Player.BLACK);
            boardArray[1][7] = new Pawn(Player.BLACK);

            boardArray[6][0] = new Pawn(Player.WHITE);
            boardArray[6][1] = new Pawn(Player.WHITE);
            boardArray[6][2] = new Pawn(Player.WHITE);
            boardArray[6][3] = new Pawn(Player.WHITE);
            boardArray[6][4] = new Pawn(Player.WHITE);
            boardArray[6][5] = new Pawn(Player.WHITE);
            boardArray[6][6] = new Pawn(Player.WHITE);
            boardArray[6][7] = new Pawn(Player.WHITE);

            boardArray[7][0] = new Rook(Player.WHITE);
            boardArray[7][1] = new Knight(Player.WHITE);
            boardArray[7][2] = new Bishop(Player.WHITE);
            boardArray[7][3] = new Queen(Player.WHITE);
            boardArray[7][4] = new King(Player.WHITE);
            boardArray[7][5] = new Bishop(Player.WHITE);
            boardArray[7][6] = new Knight(Player.WHITE);
            boardArray[7][7] = new Rook(Player.WHITE);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        }

        selection.close();
    }
}