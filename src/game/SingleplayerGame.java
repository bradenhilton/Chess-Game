package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import pieces.Piece;
import pieces.PieceType;

public class SingleplayerGame {
    private int ranks = 8;
    private int files = 8;
    private String move;
    private String restart;
    private Board chessBoard;
    private Scanner input = new Scanner(System.in);
    private boolean legalMove = false;
    private boolean whiteTurn;
    private boolean gameOver = false;
    private Map<Character, Integer> charMap = new HashMap<Character, Integer>();
    private Map<Integer, Character> reverseCharMap = new HashMap<Integer, Character>();
    private List<String> moveHistory = new ArrayList<String>();

    private int blackKingR;
    private int blackKingF;
    private int whiteKingR;
    private int whiteKingF;

    public SingleplayerGame(Board chessBoard, Map<Character, Integer> charMap, Map<Integer, Character> reverseCharMap,
            boolean whiteTurn) {
        this.chessBoard = chessBoard;
        this.charMap = charMap;
        this.reverseCharMap = reverseCharMap;
        this.whiteTurn = whiteTurn;

        singleplayerLoop(whiteTurn);
    }

    /**
     * Game loop for one player.
     * <p>
     * Does not allow user input for Black turn due to AI.
     * 
     * @param whiteTurn
     *            Boolean which dictates whose turn it is.
     */
    private void singleplayerLoop(boolean whiteTurn) {
        while (!gameOver) {
            if (legalMove) {
                chessBoard.printBoard(chessBoard.boardArray);
            }

            if (whiteTurn) {
                if (!moveHistory.isEmpty()) {
                    System.out.println(moveHistory.get(moveHistory.size() - 1));
                }

                System.out.println("White to move (e.g. d2d4 or list or castle)");
                move = input.nextLine();

                if (move.equalsIgnoreCase("list")) {
                    System.out.println("Location of piece:");
                    String piece = input.nextLine();

                    if (!piece.matches("[a-hA-H][1-8]")) {
                        System.out.println("Please enter the location of a piece");
                        break;
                    } else if (piece.equalsIgnoreCase("q") || piece.equalsIgnoreCase("quit")) {
                        break;
                    } else {
                        listPossibleMoves(piece);
                    }
                } else if (move.equalsIgnoreCase("castle")) {
                    System.out.println("Location of King and Rook:");
                    String kingAndRook = input.nextLine();

                    if (!kingAndRook.matches("[a-hA-H][1-8][a-hA-H][1-8]")) {
                        System.out.println("Please enter the location of the king and the rook to castle");
                        break;
                    } else if (kingAndRook.equalsIgnoreCase("q") || kingAndRook.equalsIgnoreCase("quit")) {
                        break;
                    } else {
                        parseMoveSingleplayer(kingAndRook, whiteTurn, true);
                    }
                } else {
                    parseMoveSingleplayer(move, whiteTurn, false);
                }
            } else {
                if (!moveHistory.isEmpty()) {
                    System.out.println(moveHistory.get(moveHistory.size() - 1));
                }

                System.out.println("Black to move (e.g. g7g6)");

                List<String> allMoves = new ArrayList<String>();

                for (int i = 0; i < ranks; i++) {
                    for (int j = 0; j < files; j++) {
                        if (chessBoard.boardArray[i][j] != null
                                && chessBoard.boardArray[i][j].getPlayer() == Player.BLACK) {
                            allMoves.addAll(
                                    chessBoard.boardArray[i][j].generatePossibleMoves(chessBoard.boardArray, i, j));
                        }
                    }
                }

                Random rand = new Random();
                int randMove = rand.nextInt(allMoves.size());

                move = allMoves.get(randMove).substring(0, 4);

                Character startFile = reverseCharMap.get(Integer.parseInt(move.substring(1, 2)));
                int startRank = 8 - Integer.parseInt(move.substring(0, 1));

                Character newFile = reverseCharMap.get(Integer.parseInt(move.substring(3, 4)));
                int newRank = 8 - Integer.parseInt(move.substring(2, 3));

                if (move.substring(4).contains("castle")) {
                    move = startFile + String.valueOf(startRank) + newFile + String.valueOf(newRank);
                    parseMoveSingleplayer(move, whiteTurn, true);
                } else {
                    move = startFile + String.valueOf(startRank) + newFile + String.valueOf(newRank);
                    parseMoveSingleplayer(move, whiteTurn, false);
                }

            }
        }

        System.out.println("New game? (y/n)");
        restart = input.nextLine();
        if (restart.equalsIgnoreCase("y") || restart.equalsIgnoreCase("yes")) {
            chessBoard = new Board();
            gameOver = false;
        } else {
            System.out.println("Goodbye!");
            input.close();
            System.exit(0);
        }
    }

    /**
     * Parses move input.
     * <p>
     * Converts move e.g. d2d4 into start ranks and files and new ranks and
     * files.
     * 
     * @param move
     *            String representation of move input.
     * @param whiteTurn
     *            Boolean which dictates whose turn it is.
     */
    public void parseMoveSingleplayer(String move, boolean whiteTurn, boolean castle) {
        int startRank, startFile, newRank, newFile;

        if (move.length() != 4 && !move.matches("[a-hA-H][1-8][a-hA-H][1-8]")) {
            System.err.println("Error: Invalid move");
        } else {
            startRank = 8 - Integer.parseInt(move.substring(1, 2));
            startFile = charMap.get(move.toLowerCase().charAt(0));

            newRank = 8 - Integer.parseInt(move.substring(3, 4));
            newFile = charMap.get(move.toLowerCase().charAt(2));

            if (chessBoard.boardArray[startRank][startFile] == null) {
                System.err.println("Error: Invalid move, no piece found at " + move.substring(0, 2));
            } else if (startRank == newRank && startFile == newFile) {
                System.err.println("Error: Invalid move");
            } else {
                if (whiteTurn) {
                    if (!(chessBoard.boardArray[startRank][startFile].getPlayer() == Player.WHITE)) {
                        System.err.println("Error: Invalid move, White cannot move Black");
                    } else {
                        String destination = String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(newRank) + String.valueOf(newFile);
                        List<String> possibleMoves = chessBoard.boardArray[startRank][startFile]
                                .generatePossibleMoves(chessBoard.boardArray, startRank, startFile);

                        for (int i = 0; i < possibleMoves.size(); i++) {
                            if (i == possibleMoves.size()
                                    && !possibleMoves.get(i).substring(0, 4).contains(destination)) {
                                System.err.println("Error: Invalid move");
                            } else if (!possibleMoves.get(i).substring(0, 4).contains(destination)) {
                                continue;
                            } else {
                                if (castle == true || possibleMoves.get(i).substring(4).contains("castle")) {
                                    castle(startRank, startFile, newRank, newFile);

                                    moveHistory.add("White " + move.toLowerCase() + " castle");
                                    legalMove = true;
                                    whiteTurn = false;
                                    singleplayerLoop(whiteTurn);
                                } else if (possibleMoves.get(i).substring(4).contains("en passant")) {
                                    enPassant(startRank, startFile, newRank, newFile);

                                    moveHistory.add("White " + chessBoard.boardArray[newRank][newFile].getPieceType()
                                            + " " + move.toLowerCase() + " en passant");
                                    legalMove = true;
                                    whiteTurn = false;
                                    singleplayerLoop(whiteTurn);
                                } else {
                                    movePiece(chessBoard.boardArray, startRank, startFile, newRank, newFile);

                                    moveHistory.add("White " + chessBoard.boardArray[newRank][newFile].getPieceType()
                                            + " " + move.toLowerCase());
                                    legalMove = true;
                                    whiteTurn = false;
                                    singleplayerLoop(whiteTurn);
                                }
                            }
                        }
                    }
                } else {
                    if (!(chessBoard.boardArray[startRank][startFile].getPlayer() == Player.BLACK)) {
                        System.err.println("Error: Invalid move, Black cannot move White");
                    } else {
                        String destination = String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(newRank) + String.valueOf(newFile);
                        List<String> possibleMoves = chessBoard.boardArray[startRank][startFile]
                                .generatePossibleMoves(chessBoard.boardArray, startRank, startFile);

                        for (int i = 0; i < possibleMoves.size(); i++) {
                            if (i == possibleMoves.size()
                                    && !possibleMoves.get(i).substring(0, 4).contains(destination)) {
                                System.err.println("Error: Invalid move");
                            } else if (!possibleMoves.get(i).substring(0, 4).contains(destination)) {
                                continue;
                            } else {
                                if (castle == true || possibleMoves.get(i).substring(4).contains("castle")) {
                                    castle(startRank, startFile, newRank, newFile);

                                    moveHistory.add("Black " + move.toLowerCase() + " castle");
                                    legalMove = true;
                                    whiteTurn = true;
                                    singleplayerLoop(whiteTurn);
                                } else if (possibleMoves.get(i).substring(4).contains("en passant")) {
                                    enPassant(startRank, startFile, newRank, newFile);

                                    moveHistory.add("Black " + chessBoard.boardArray[newRank][newFile].getPieceType()
                                            + " " + move.toLowerCase() + " en passant");
                                    legalMove = true;
                                    whiteTurn = true;
                                    singleplayerLoop(whiteTurn);
                                } else {
                                    movePiece(chessBoard.boardArray, startRank, startFile, newRank, newFile);

                                    moveHistory.add("Black " + chessBoard.boardArray[newRank][newFile].getPieceType()
                                            + " " + move.toLowerCase());
                                    legalMove = true;
                                    whiteTurn = true;
                                    singleplayerLoop(whiteTurn);
                                }
                            }
                        }
                    }
                }
            }
        }
    } // parseMoveSingleplayer

    /**
     * Lists all possible moves that can be made by a specified piece.
     * 
     * @param piece
     *            String representation of a piece on the board.
     */
    public void listPossibleMoves(String piece) {
        int startRank, startFile, destRank;
        Character destFile;
        List<String> allPossibleMoves = new ArrayList<String>();

        if (piece.length() != 2 && !move.matches("[a-hA-H][1-8]")) {
            System.err.println("Error: Invalid piece");
        } else {
            startFile = charMap.get(piece.toLowerCase().charAt(0));
            startRank = 8 - Integer.parseInt(piece.substring(1, 2));

            if (chessBoard.boardArray[startRank][startFile] == null) {
                System.err.println("Error: No piece found at " + piece.substring(0, 2));
            } else {
                allPossibleMoves = chessBoard.boardArray[startRank][startFile]
                        .generatePossibleMoves(chessBoard.boardArray, startRank, startFile);

                System.out.println(piece + " selected\nPossible moves:");

                if (allPossibleMoves.isEmpty()) {
                    System.out.println("None!");
                } else {
                    for (String move : allPossibleMoves) {
                        destRank = 8 - Integer.parseInt(move.substring(2, 3));
                        destFile = reverseCharMap.get(Integer.parseInt(move.substring(3, 4)));

                        System.out.println(destFile + "" + destRank + move.substring(4));
                    }
                }
            }
        }
    } // listPossibleMoves

    /**
     * Castles a King and a Rook.
     * 
     * @param piece1Rank
     *            Starting Rank (Piece to be moved).
     * @param piece1File
     *            Starting File (Piece to be moved).
     * @param piece2Rank
     *            Destination Rank.
     * @param piece2File
     *            Destination File.
     */
    public void castle(int piece1Rank, int piece1File, int piece2Rank, int piece2File) {
        int kingRank = 0;
        int kingFile = 0;
        int rookRank = 0;
        int rookFile = 0;

        // piece1 = king
        // piece2 = rook
        if (chessBoard.boardArray[piece1Rank][piece1File].getPieceType() == PieceType.KING
                && chessBoard.boardArray[piece2Rank][piece2File].getPieceType() == PieceType.ROOK) {
            if (chessBoard.boardArray[piece1Rank][piece1File].getMoved() == false
                    && chessBoard.boardArray[piece2Rank][piece2File].getMoved() == false) {

                kingRank = piece1Rank;
                kingFile = piece1File;

                rookRank = piece2Rank;
                rookFile = piece2File;
            }
            // piece 1 = rook
            // piece 2 = king
        } else if (chessBoard.boardArray[piece1Rank][piece1File].getPieceType() == PieceType.ROOK
                && chessBoard.boardArray[piece2Rank][piece2File].getPieceType() == PieceType.KING) {
            if (chessBoard.boardArray[piece1Rank][piece1File].getMoved() == false
                    && chessBoard.boardArray[piece2Rank][piece2File].getMoved() == false) {

                kingRank = piece2Rank;
                kingFile = piece2File;

                rookRank = piece1Rank;
                rookFile = piece1File;
            }
        }

        if (kingFile > rookFile) { // left
            chessBoard.boardArray[kingRank][kingFile - 2] = chessBoard.boardArray[kingRank][kingFile]; // king
            chessBoard.boardArray[rookRank][kingFile - 1] = chessBoard.boardArray[rookRank][rookFile]; // rook

            chessBoard.boardArray[kingRank][kingFile] = null;
            chessBoard.boardArray[rookRank][rookFile] = null;

            chessBoard.boardArray[kingRank][kingFile - 2].setMoved(true);
            if (chessBoard.boardArray[kingRank][kingFile - 2].getPlayer() == Player.BLACK) {
                blackKingR = kingRank;
                blackKingF = kingFile - 2;
            } else {
                whiteKingR = kingRank;
                whiteKingF = kingFile - 2;
            }
            chessBoard.boardArray[rookRank][kingFile - 1].setMoved(true);
        } else { // right
            chessBoard.boardArray[kingRank][kingFile + 2] = chessBoard.boardArray[kingRank][kingFile]; // king
            chessBoard.boardArray[rookRank][kingFile + 1] = chessBoard.boardArray[rookRank][rookFile]; // rook

            chessBoard.boardArray[kingRank][kingFile] = null;
            chessBoard.boardArray[rookRank][rookFile] = null;

            chessBoard.boardArray[kingRank][kingFile + 2].setMoved(true);
            if (chessBoard.boardArray[kingRank][kingFile + 2].getPlayer() == Player.BLACK) {
                blackKingR = kingRank;
                blackKingF = kingFile + 2;
            } else {
                whiteKingR = kingRank;
                whiteKingF = kingFile + 2;
            }
            chessBoard.boardArray[rookRank][kingFile + 1].setMoved(true);
        }
    } // castle

    /**
     * Captures a pawn piece en passant.
     * 
     * @param startRank
     *            Starting Rank (Piece to be moved).
     * @param startFile
     *            Starting File (Piece to be moved).
     * @param destRank
     *            Destination Rank.
     * @param destFile
     *            Destination File.
     */
    public void enPassant(int startRank, int startFile, int destRank, int destFile) {
        int attackerRank = startRank;
        int attackerFile = startFile;

        int enemyRank;
        int enemyFile = destFile;

        if (chessBoard.boardArray[attackerRank][attackerFile].getPlayer() == Player.BLACK) {
            enemyRank = destRank - 1;
            chessBoard.boardArray[enemyRank][enemyFile] = null;
            chessBoard.boardArray[enemyRank + 1][enemyFile] = chessBoard.boardArray[attackerRank][attackerFile];
            chessBoard.boardArray[attackerRank][attackerFile] = null;
            chessBoard.boardArray[enemyRank + 1][enemyFile].setMoved(true);
        } else {
            enemyRank = destRank + 1;
            chessBoard.boardArray[enemyRank][enemyFile] = null;
            chessBoard.boardArray[enemyRank - 1][enemyFile] = chessBoard.boardArray[attackerRank][attackerFile];
            chessBoard.boardArray[attackerRank][attackerFile] = null;
            chessBoard.boardArray[enemyRank - 1][enemyFile].setMoved(true);
        }
    }

    /**
     * Moves a piece on the game board.
     * 
     * @param boardArray
     *            Array of Pieces on the Board.
     * @param startRank
     *            Starting Rank (Piece to be moved).
     * @param startFile
     *            Starting File (Piece to be moved).
     * @param destRank
     *            Destination Rank.
     * @param destFile
     *            Destination File.
     */
    public void movePiece(Piece[][] boardArray, int startRank, int startFile, int destRank, int destFile) {
        boardArray[destRank][destFile] = null;
        boardArray[destRank][destFile] = boardArray[startRank][startFile];
        boardArray[startRank][startFile] = null;
        boardArray[destRank][destFile].setMoved(true);
    } // movePiece
}