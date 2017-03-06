package tests;

import java.util.Scanner;

import game.Player;
import pieces.*;

public class KingTest {
    private static int ranks = 8;
    private static int files = 8;
    private static Piece[][] boardArray = new Piece[ranks][files];
    private static TestBoard board;

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
        System.out.println("8) AI Castle");
        System.out.println("9) Blocked AI Castle");
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
            boardArray[4][3] = new King(Player.WHITE);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 2:
            boardArray[4][3] = new King(Player.WHITE);
            boardArray[3][4] = new King(Player.WHITE);
            boardArray[3][2] = new King(Player.WHITE);
            boardArray[5][4] = new King(Player.WHITE);
            boardArray[5][2] = new King(Player.WHITE);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 3:
            boardArray[4][3] = new King(Player.WHITE);
            boardArray[4][4] = new King(Player.WHITE);
            boardArray[4][2] = new King(Player.WHITE);
            boardArray[3][3] = new King(Player.WHITE);
            boardArray[5][3] = new King(Player.WHITE);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 4:
            boardArray[4][3] = new King(Player.WHITE);
            boardArray[3][2] = new Pawn(Player.BLACK);
            boardArray[3][4] = new Pawn(Player.BLACK);
            boardArray[5][2] = new Pawn(Player.BLACK);
            boardArray[5][4] = new Pawn(Player.BLACK);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 5:
            boardArray[4][3] = new King(Player.WHITE);
            boardArray[3][3] = new Queen(Player.BLACK);
            boardArray[5][3] = new Queen(Player.BLACK);
            boardArray[4][2] = new Queen(Player.BLACK);
            boardArray[4][4] = new Queen(Player.BLACK);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 6:
            boardArray[7][4] = new King(Player.WHITE);
            boardArray[7][0] = new Rook(Player.WHITE);
            boardArray[7][7] = new Rook(Player.WHITE);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 7:
            boardArray[7][4] = new King(Player.WHITE);
            boardArray[7][0] = new Rook(Player.WHITE);
            boardArray[7][7] = new Rook(Player.WHITE);

            boardArray[1][7] = new Queen(Player.BLACK);
            boardArray[0][3] = new Rook(Player.BLACK);

            // boardArray[7][3] = new Queen(Player.WHITE);
            // boardArray[7][5] = new Bishop(Player.WHITE);
            board = new TestBoard(boardArray);
            new TestGame(board, 2);
            break;
        case 8:
            boardArray[0][4] = new King(Player.BLACK);
            boardArray[0][0] = new Rook(Player.BLACK);
            boardArray[0][7] = new Rook(Player.BLACK);

            boardArray[6][3] = new Pawn(Player.WHITE);
            board = new TestBoard(boardArray);
            new TestGame(board, 1);
            break;
        case 9:
            boardArray[0][4] = new King(Player.BLACK);
            boardArray[0][0] = new Rook(Player.BLACK);
            boardArray[0][7] = new Rook(Player.BLACK);

            boardArray[7][5] = new Rook(Player.WHITE);
            // boardArray[0][3] = new Rook(Player.BLACK);

            // boardArray[7][3] = new Queen(Player.WHITE);
            // boardArray[7][5] = new Bishop(Player.WHITE);
            board = new TestBoard(boardArray);
            new TestGame(board, 1);
            break;
        }

        selection.close();
    }
}