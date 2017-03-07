package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import game.Player;
import pieces.PieceType;

public class TestGameSingleplayer {
    private Map<Character, Integer> charMap = new HashMap<Character, Integer>();
    private Map<Integer, Character> reverseCharMap = new HashMap<Integer, Character>();

    private TestBoard board;
    private TestBoard resetBoard;
    private boolean legalMove = false;
    private boolean whiteTurn = true;
    private boolean gameOver = false;
    private boolean stalemate = false;

    private String move;
    private Scanner input = new Scanner(System.in);

    private List<String> moveHistory = new ArrayList<String>();

    private Player lastPlayer;
    private PieceType lastPiece;
    private int lastPieceOldRank;
    private int lastPieceOldFile;
    private int lastPieceNewRank;
    private int lastPieceNewFile;

    private int blackKingR;
    private int blackKingF;
    private int whiteKingR;
    private int whiteKingF;

    /**
     * 
     * @param tBoard
     * @param cMap
     * @param reverseCMap
     * @param turn
     */
    public TestGameSingleplayer(TestBoard tBoard, Map<Character, Integer> cMap, Map<Integer, Character> reverseCMap,
            boolean turn) {
        board = tBoard;
        resetBoard = tBoard;
        charMap = cMap;
        reverseCharMap = reverseCMap;
        whiteTurn = turn;

        testGameSingleplayerLoop(board, whiteTurn);
    }

    /**
     * 
     * @param tBoard
     * @param turn
     */
    public void testGameSingleplayerLoop(TestBoard tBoard, boolean turn) {
        while (!(gameOver || stalemate)) {
            if (!moveHistory.isEmpty()) {
                lastMove(board, moveHistory.get(moveHistory.size() - 1));

                if (board.boardArray[lastPieceNewRank][lastPieceNewFile].getPieceType() == PieceType.PAWN
                        && (lastPieceNewRank - lastPieceOldRank == 2 || lastPieceNewRank - lastPieceOldRank == -2)) {
                    board.boardArray[lastPieceNewRank][lastPieceNewFile].setMovedTwo(true);
                } else {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (board.boardArray[i][j] != null) {
                                board.boardArray[i][j].setMovedTwo(false);
                            }
                        }
                    }
                }
            }

            if (legalMove) {
                board.printBoard(board.boardArray);
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
                        listPossibleMoves(board, piece);
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
                        testGameSingleplayerParseMove(board, kingAndRook, whiteTurn, true);
                    }
                } else {
                    testGameSingleplayerParseMove(board, move, whiteTurn, false);
                }
            } else {
                if (!moveHistory.isEmpty()) {
                    System.out.println(moveHistory.get(moveHistory.size() - 1));
                }

                System.out.println("Black to move (e.g. g7g6)");

                List<String> allMoves = new ArrayList<String>();

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board.boardArray[i][j] != null && board.boardArray[i][j].getPlayer() == Player.BLACK) {
                            allMoves.addAll(board.boardArray[i][j].generatePossibleMoves(board.boardArray, i, j));
                        }
                    }
                }

                // // used for forcing test cases
                // for (int i = 0; i < allMoves.size(); i++) {
                // // force castle
                // if (allMoves.get(i).substring(4).contains("castle")) {
                // move = allMoves.get(i).substring(0, 4);
                // }
                // // force en passant
                // if (allMoves.get(i).substring(4).contains("en passant")) {
                // move = allMoves.get(i).substring(0, 4);
                // }
                // }

                Random rand = new Random();
                int randMove = rand.nextInt(allMoves.size());

                move = allMoves.get(randMove).substring(0, 4);

                Character startFile = reverseCharMap.get(Integer.parseInt(move.substring(1, 2)));
                int startRank = 8 - Integer.parseInt(move.substring(0, 1));

                Character newFile = reverseCharMap.get(Integer.parseInt(move.substring(3, 4)));
                int newRank = 8 - Integer.parseInt(move.substring(2, 3));

                if (move.substring(4).contains("castle")) {
                    move = startFile + String.valueOf(startRank) + newFile + String.valueOf(newRank);
                    testGameSingleplayerParseMove(board, move, whiteTurn, true);
                } else {
                    move = startFile + String.valueOf(startRank) + newFile + String.valueOf(newRank);
                    testGameSingleplayerParseMove(board, move, whiteTurn, false);
                }
            }
        }

        if (gameOver) {
            System.out.println("Play again?");
            String restart = input.nextLine();

            if (restart.equalsIgnoreCase("y") || restart.equalsIgnoreCase("yes")) {
                testGameSingleplayerLoop(resetBoard, whiteTurn);
            } else {
                System.out.println("Goodbye!");
                System.exit(0);
            }
        }
    }

    /**
     * 
     * @param tBoard
     * @param move
     * @param turn
     * @param castle
     */
    public void testGameSingleplayerParseMove(TestBoard tBoard, String move, boolean turn, boolean castle) {
        int startRank, startFile, newRank, newFile;

        if (move.length() != 4 && !move.matches("[a-hA-H][1-8][a-hA-H][1-8]")) {
            System.err.println("Error: Invalid move");
        } else {
            startFile = charMap.get(move.toLowerCase().charAt(0));
            startRank = 8 - Integer.parseInt(move.substring(1, 2));

            newFile = charMap.get(move.toLowerCase().charAt(2));
            newRank = 8 - Integer.parseInt(move.substring(3, 4));

            if (board.boardArray[startRank][startFile] == null) {
                System.err.println("Error: Invalid move, no piece found at " + move.substring(0, 2));
            } else if (startRank == newRank && startFile == newFile) {
                System.err.println("Error: Invalid move");
            } else {
                if (whiteTurn) {
                    if (!(board.boardArray[startRank][startFile].getPlayer() == Player.WHITE)) {
                        System.err.println("Error: Invalid move, White cannot move Black");
                    } else {
                        String destination = String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(newRank) + String.valueOf(newFile);
                        List<String> possibleMoves = board.boardArray[startRank][startFile]
                                .generatePossibleMoves(board.boardArray, startRank, startFile);

                        for (int i = 0; i < possibleMoves.size(); i++) {
                            if (i + 1 == possibleMoves.size()
                                    && !possibleMoves.get(i).substring(0, 4).contains(destination)) {
                                System.err.println("Error: Invalid move");
                            } else if (!possibleMoves.get(i).substring(0, 4).contains(destination)) {
                                continue;
                            } else {
                                if (castle == true || possibleMoves.get(i).substring(4).contains("castle")) {
                                    castle(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("White " + move.toLowerCase() + " castle");
                                    legalMove = true;
                                    whiteTurn = false;
                                    testGameSingleplayerLoop(board, whiteTurn);
                                } else if (possibleMoves.get(i).substring(4).contains("en passant")) {
                                    enPassant(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("White " + board.boardArray[newRank][newFile].getPieceType() + " "
                                            + move.toLowerCase() + " en passant");
                                    legalMove = true;
                                    whiteTurn = false;
                                    testGameSingleplayerLoop(board, whiteTurn);
                                } else {
                                    movePiece(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("White " + board.boardArray[newRank][newFile].getPieceType() + " "
                                            + move.toLowerCase());
                                    legalMove = true;
                                    whiteTurn = false;
                                    testGameSingleplayerLoop(board, whiteTurn);
                                }
                            }
                        }
                    }
                } else {
                    if (!(board.boardArray[startRank][startFile].getPlayer() == Player.BLACK)) {
                        System.err.println("Error: Invalid move, Black cannot move White");
                    } else {
                        String destination = String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(newRank) + String.valueOf(newFile);
                        List<String> possibleMoves = board.boardArray[startRank][startFile]
                                .generatePossibleMoves(board.boardArray, startRank, startFile);

                        for (int i = 0; i < possibleMoves.size(); i++) {
                            if (i + 1 == possibleMoves.size()
                                    && !possibleMoves.get(i).substring(0, 4).contains(destination)) {
                                System.err.println("Error: Invalid move");
                            } else if (!possibleMoves.get(i).substring(0, 4).contains(destination)) {
                                continue;
                            } else {
                                if (castle == true || possibleMoves.get(i).substring(4).contains("castle")) {
                                    castle(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("Black " + move.toLowerCase() + " castle");
                                    legalMove = true;
                                    whiteTurn = true;
                                    testGameSingleplayerLoop(board, whiteTurn);
                                } else if (possibleMoves.get(i).substring(4).contains("en passant")) {
                                    enPassant(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("Black " + board.boardArray[newRank][newFile].getPieceType() + " "
                                            + move.toLowerCase() + " en passant");
                                    legalMove = true;
                                    whiteTurn = true;
                                    testGameSingleplayerLoop(board, whiteTurn);
                                } else {
                                    movePiece(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("Black " + board.boardArray[newRank][newFile].getPieceType() + " "
                                            + move.toLowerCase());
                                    legalMove = true;
                                    whiteTurn = true;
                                    testGameSingleplayerLoop(board, whiteTurn);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @param tBoard
     * @param piece
     * @return
     */
    public List<String> listPossibleMoves(TestBoard tBoard, String piece) {
        int startRank, startFile, destRank;
        char destFile;
        List<String> allPossibleMoves = new ArrayList<String>();

        if (piece.length() != 2 && !move.matches("[a-hA-H][1-8]")) {
            System.err.println("Error: Invalid piece");
        } else {
            startFile = charMap.get(piece.toLowerCase().charAt(0));
            startRank = 8 - Integer.parseInt(piece.substring(1, 2));

            if (tBoard.boardArray[startRank][startFile] == null) {
                System.err.println("Error: No piece found at " + piece.substring(0, 2));
            } else {
                allPossibleMoves = tBoard.boardArray[startRank][startFile].generatePossibleMoves(tBoard.boardArray,
                        startRank, startFile);

                System.out.println(piece + " selected\nPossible moves:");

                if (allPossibleMoves.isEmpty()) {
                    System.out.println("None!");
                } else {
                    for (String move : allPossibleMoves) {
                        destRank = 8 - Integer.parseInt(move.substring(2, 3));
                        destFile = reverseCharMap.get(Integer.parseInt(move.substring(3, 4)));

                        // visualise possible moves with black pawns
                        // newRank = Integer.parseInt(move.substring(2, 3));
                        // newFile = Integer.parseInt(move.substring(3, 4));
                        // tBoard.boardArray[newRank][newFile] = new
                        // Pawn(Player.BLACK);
                        System.out.println(String.valueOf(destFile) + String.valueOf(destRank) + move.substring(4));
                    }
                }

                System.out.println();
                tBoard.printBoard(tBoard.boardArray);
            }
        }
        return allPossibleMoves;
    } // listPossibleMoves

    /**
     * 
     * @param tBoard
     * @param piece1Rank
     * @param piece1File
     * @param piece2Rank
     * @param piece2File
     */
    public void castle(TestBoard tBoard, int piece1Rank, int piece1File, int piece2Rank, int piece2File) {
        int kingRank = 0;
        int kingFile = 0;
        int rookRank = 0;
        int rookFile = 0;

        // piece1 = king
        // piece2 = rook
        if (tBoard.boardArray[piece1Rank][piece1File].getPieceType() == PieceType.KING
                && tBoard.boardArray[piece2Rank][piece2File].getPieceType() == PieceType.ROOK) {
            if (tBoard.boardArray[piece1Rank][piece1File].getMoved() == false
                    && tBoard.boardArray[piece2Rank][piece2File].getMoved() == false) {

                kingRank = piece1Rank;
                kingFile = piece1File;

                rookRank = piece2Rank;
                rookFile = piece2File;
            }
            // piece 1 = rook
            // piece 2 = king
        } else if (tBoard.boardArray[piece1Rank][piece1File].getPieceType() == PieceType.ROOK
                && tBoard.boardArray[piece2Rank][piece2File].getPieceType() == PieceType.KING) {
            if (tBoard.boardArray[piece1Rank][piece1File].getMoved() == false
                    && tBoard.boardArray[piece2Rank][piece2File].getMoved() == false) {

                kingRank = piece2Rank;
                kingFile = piece2File;

                rookRank = piece1Rank;
                rookFile = piece1File;
            }
        }

        if (kingFile > rookFile) { // left
            tBoard.boardArray[kingRank][kingFile - 2] = tBoard.boardArray[kingRank][kingFile]; // king
            tBoard.boardArray[rookRank][kingFile - 1] = tBoard.boardArray[rookRank][rookFile]; // rook

            tBoard.boardArray[kingRank][kingFile] = null;
            tBoard.boardArray[rookRank][rookFile] = null;

            tBoard.boardArray[kingRank][kingFile - 2].setMoved();
            if (tBoard.boardArray[kingRank][kingFile - 2].getPlayer() == Player.BLACK) {
                blackKingR = kingRank;
                blackKingF = kingFile - 2;
            } else {
                whiteKingR = kingRank;
                whiteKingF = kingFile - 2;
            }
            tBoard.boardArray[rookRank][kingFile - 1].setMoved();
        } else { // right
            tBoard.boardArray[kingRank][kingFile + 2] = tBoard.boardArray[kingRank][kingFile]; // king
            tBoard.boardArray[rookRank][kingFile + 1] = tBoard.boardArray[rookRank][rookFile]; // rook

            tBoard.boardArray[kingRank][kingFile] = null;
            tBoard.boardArray[rookRank][rookFile] = null;

            tBoard.boardArray[kingRank][kingFile + 2].setMoved();
            if (tBoard.boardArray[kingRank][kingFile + 2].getPlayer() == Player.BLACK) {
                blackKingR = kingRank;
                blackKingF = kingFile + 2;
            } else {
                whiteKingR = kingRank;
                whiteKingF = kingFile + 2;
            }
            tBoard.boardArray[rookRank][kingFile + 1].setMoved();
        }
    } // castle

    /**
     * 
     * @param tBoard
     * @param startRank
     * @param startFile
     * @param destRank
     * @param destFile
     */
    public void enPassant(TestBoard tBoard, int startRank, int startFile, int destRank, int destFile) {
        int attackerRank = startRank;
        int attackerFile = startFile;

        int enemyRank;
        int enemyFile = destFile;

        if (tBoard.boardArray[attackerRank][attackerFile].getPlayer() == Player.BLACK) {
            enemyRank = destRank - 1;
            tBoard.boardArray[enemyRank][enemyFile] = null;
            tBoard.boardArray[enemyRank + 1][enemyFile] = tBoard.boardArray[attackerRank][attackerFile];
            tBoard.boardArray[attackerRank][attackerFile] = null;
            tBoard.boardArray[enemyRank + 1][enemyFile].setMoved();
        } else {
            enemyRank = destRank + 1;
            tBoard.boardArray[enemyRank][enemyFile] = null;
            tBoard.boardArray[enemyRank - 1][enemyFile] = tBoard.boardArray[attackerRank][attackerFile];
            tBoard.boardArray[attackerRank][attackerFile] = null;
            tBoard.boardArray[enemyRank - 1][enemyFile].setMoved();
        }
    } // enPassant

    /**
     * 
     * @param tBoard
     * @param startRank
     * @param startFile
     * @param destRank
     * @param destFile
     */
    public void movePiece(TestBoard tBoard, int startRank, int startFile, int destRank, int destFile) {
        tBoard.boardArray[destRank][destFile] = null;
        tBoard.boardArray[destRank][destFile] = tBoard.boardArray[startRank][startFile];
        tBoard.boardArray[startRank][startFile] = null;
        tBoard.boardArray[destRank][destFile].setMoved();
        tBoard.boardArray[destRank][destFile].setMovedTwo(true);
    } // movePiece

    /**
     * 
     * @param tBoard
     * @param lastMove
     */
    public void lastMove(TestBoard tBoard, String lastMove) {
        if (lastMove.substring(0, 5).equalsIgnoreCase("White")) {
            lastPlayer = Player.WHITE;
        } else {
            lastPlayer = Player.BLACK;
        }

        if (lastMove.substring(11, 15).matches("[a-hA-H][1-8][a-hA-H][1-8]")) {
            lastPieceOldRank = 8 - Integer.parseInt(lastMove.substring(12, 13));
            lastPieceOldFile = charMap.get(lastMove.charAt(11));
            lastPieceNewRank = 8 - Integer.parseInt(lastMove.substring(14, 15));
            lastPieceNewFile = charMap.get(lastMove.charAt(13));
        }

        lastPiece = tBoard.boardArray[lastPieceNewRank][lastPieceNewFile].getPieceType();
    } // lastMove

    /**
     * 
     * @param tBoard
     * @param startRank
     * @param startFile
     * @param destRank
     * @param destFile
     */
    public boolean isLegalMove(TestBoard tBoard, int startRank, int startFile, int destRank, int destFile) {
        String destination = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(destRank)
                + String.valueOf(destFile);
        List<String> possibleMoves = tBoard.boardArray[startRank][startFile].generatePossibleMoves(tBoard.boardArray,
                startRank, startFile);

        for (String move : possibleMoves) {
            if (move.substring(0, 4).equals(destination)) {
                return true;
            }
        }

        return false;
    } // isLegalMove
}
