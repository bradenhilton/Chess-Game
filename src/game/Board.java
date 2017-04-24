package game;

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
public class Board {
    private int ranks = 8;
    private int files = 8;
    private String spacer = "   ";
    protected Piece[][] boardArray = new Piece[ranks][files];

    /**
     * Creates the game board.
     */
    public Board() {
        resetBoard(boardArray);
        printBoard(boardArray);
    }

    /**
     * Prints the board to the console.
     * 
     * @param board
     *            The game board.
     */
    public void printBoard(Piece[][] board) {
        System.out.print("\n " + spacer + " a " + spacer + " b " + spacer + " c " + spacer + " d " + spacer + " e "
                + spacer + " f " + spacer + " g " + spacer + " h \n");
        for (int i = 0; i < ranks; i++) {
            System.out.print("  -------------------------------------------------\n");
            System.out.print((8 - i) + " ");
            for (int j = 0; j < files; j++) {
                System.out.print("| ");
                if (board[i][j] == null) {
                    System.out.print(spacer);
                } else {
                    System.out.print(board[i][j].toString());
                }
                System.out.print(" ");
            }
            System.out.print("|");
            System.out.print(" " + (8 - i) + "\n");
        }
        System.out.print("  -------------------------------------------------\n");
        System.out.print(" " + spacer + " a " + spacer + " b " + spacer + " c " + spacer + " d " + spacer + " e "
                + spacer + " f " + spacer + " g " + spacer + " h\n");
    } // printBoard

    /**
     * Resets the board at the start of a game.
     * 
     * @param board
     *            The game board.
     */
    public void resetBoard(Piece[][] board) {
        board[0][0] = new Rook(Player.BLACK, 0, 0);
        board[0][1] = new Knight(Player.BLACK, 0, 1);
        board[0][2] = new Bishop(Player.BLACK, 0, 2);
        board[0][3] = new Queen(Player.BLACK, 0, 3);
        board[0][4] = new King(Player.BLACK, 0, 4);
        board[0][5] = new Bishop(Player.BLACK, 0, 5);
        board[0][6] = new Knight(Player.BLACK, 0, 6);
        board[0][7] = new Rook(Player.BLACK, 0, 7);

        board[1][0] = new Pawn(Player.BLACK, 1, 0);
        board[1][1] = new Pawn(Player.BLACK, 1, 1);
        board[1][2] = new Pawn(Player.BLACK, 1, 2);
        board[1][3] = new Pawn(Player.BLACK, 1, 3);
        board[1][4] = new Pawn(Player.BLACK, 1, 4);
        board[1][5] = new Pawn(Player.BLACK, 1, 5);
        board[1][6] = new Pawn(Player.BLACK, 1, 6);
        board[1][7] = new Pawn(Player.BLACK, 1, 7);

        board[6][0] = new Pawn(Player.WHITE, 6, 0);
        board[6][1] = new Pawn(Player.WHITE, 6, 1);
        board[6][2] = new Pawn(Player.WHITE, 6, 2);
        board[6][3] = new Pawn(Player.WHITE, 6, 3);
        board[6][4] = new Pawn(Player.WHITE, 6, 4);
        board[6][5] = new Pawn(Player.WHITE, 6, 5);
        board[6][6] = new Pawn(Player.WHITE, 6, 6);
        board[6][7] = new Pawn(Player.WHITE, 6, 7);

        board[7][0] = new Rook(Player.WHITE, 7, 0);
        board[7][1] = new Knight(Player.WHITE, 7, 1);
        board[7][2] = new Bishop(Player.WHITE, 7, 2);
        board[7][3] = new Queen(Player.WHITE, 7, 3);
        board[7][4] = new King(Player.WHITE, 7, 4);
        board[7][5] = new Bishop(Player.WHITE, 7, 5);
        board[7][6] = new Knight(Player.WHITE, 7, 6);
        board[7][7] = new Rook(Player.WHITE, 7, 7);
    } // resetBoard
} // Board