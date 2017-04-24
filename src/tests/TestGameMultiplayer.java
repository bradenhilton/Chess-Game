package tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import game.Player;
import pieces.Pawn;
import pieces.PieceType;

public class TestGameMultiplayer {
    private TestBoard board;
    private TestBoard resetBoard;
    private boolean legalMove = false;
    private boolean whiteTurn = true;
    private boolean gameOver = false;
    private boolean stalemate = false;

    private String move;
    private Scanner input = new Scanner(System.in);

    private Map<Character, Integer> charMap = new HashMap<Character, Integer>();
    private Map<Integer, Character> reverseCharMap = new HashMap<Integer, Character>();

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

    public TestGameMultiplayer(TestBoard tBoard, Map<Character, Integer> cMap, Map<Integer, Character> reverseCMap,
            boolean turn) {
        board = tBoard;
        charMap = cMap;
        reverseCharMap = reverseCMap;
        whiteTurn = turn;
    }

    public void testGameMultiplayerLoop(TestBoard tBoard, boolean turn) {
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
                        testGameMultiplayerParseMove(board, kingAndRook, whiteTurn, true);
                    }
                } else {
                    testGameMultiplayerParseMove(board, move, whiteTurn, false);
                }
            } else {
                if (!moveHistory.isEmpty()) {
                    System.out.println(moveHistory.get(moveHistory.size() - 1));
                }

                System.out.println("Black to move (e.g. d2d4 or list or castle)");
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
                        testGameMultiplayerParseMove(board, move, whiteTurn, true);
                    }
                } else {
                    testGameMultiplayerParseMove(board, move, whiteTurn, false);
                }
            }
        }
    }

    public void testGameMultiplayerParseMove(TestBoard tBoard, String move, boolean turn, boolean castle) {
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
                                    testGameMultiplayerLoop(board, whiteTurn);
                                } else if (possibleMoves.get(i).substring(4).contains("en passant")) {
                                    enPassant(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("White " + board.boardArray[newRank][newFile].getPieceType() + " "
                                            + move.toLowerCase() + " en passant");
                                    legalMove = true;
                                    whiteTurn = false;
                                    testGameMultiplayerLoop(board, whiteTurn);
                                } else {
                                    movePiece(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("White " + board.boardArray[newRank][newFile].getPieceType() + " "
                                            + move.toLowerCase());
                                    legalMove = true;
                                    whiteTurn = false;
                                    testGameMultiplayerLoop(board, whiteTurn);
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
                                    testGameMultiplayerLoop(board, whiteTurn);
                                } else if (possibleMoves.get(i).substring(4).contains("en passant")) {
                                    enPassant(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("Black " + board.boardArray[newRank][newFile].getPieceType() + " "
                                            + move.toLowerCase() + " en passant");
                                    legalMove = true;
                                    whiteTurn = true;
                                    testGameMultiplayerLoop(board, whiteTurn);
                                } else {
                                    movePiece(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("Black " + board.boardArray[newRank][newFile].getPieceType() + " "
                                            + move.toLowerCase());
                                    legalMove = true;
                                    whiteTurn = true;
                                    testGameMultiplayerLoop(board, whiteTurn);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public List<String> listPossibleMoves(TestBoard board, String piece) {
        int startRank, startFile, destRank, newRank, newFile;
        char destFile;
        Collection<String> allPossibleMoves = new ArrayList<String>();

        if (piece.length() != 2 && !move.matches("[a-hA-H][1-8]")) {
            System.err.println("Error: Invalid piece");
        } else {
            startFile = charMap.get(piece.toLowerCase().charAt(0));
            startRank = 8 - Integer.parseInt(piece.substring(1, 2));

            if (board.boardArray[startRank][startFile] == null) {
                System.err.println("Error: No piece found at " + piece.substring(0, 2));
            } else {
                allPossibleMoves = board.boardArray[startRank][startFile].generatePossibleMoves(board.boardArray,
                        startRank, startFile);

                System.out.println(piece + " selected\nPossible moves:");

                if (allPossibleMoves.isEmpty()) {
                    System.out.println("None!");
                } else {
                    for (String move : allPossibleMoves) {
                        destRank = 8 - Integer.parseInt(move.substring(2, 3));
                        destFile = reverseCharMap.get(Integer.parseInt(move.substring(3, 4)));

                        newRank = Integer.parseInt(move.substring(2, 3));
                        newFile = Integer.parseInt(move.substring(3, 4));
                        board.boardArray[newRank][newFile] = new Pawn(Player.BLACK, newRank, newFile);
                        System.out.println(destFile + "" + destRank + move.substring(4));
                    }
                }

                System.out.println();
                board.printBoard(board.boardArray);
            }
        }
        return (List<String>) allPossibleMoves;
    } // listPossibleMoves

    public void castle(TestBoard board, int piece1Rank, int piece1File, int piece2Rank, int piece2File) {

    }

    public void enPassant(TestBoard board, int startRank, int startFile, int destRank, int destFile) {

    }

    public void movePiece(TestBoard board, int startRank, int startFile, int destRank, int destFile) {

    }

    public void lastMove(TestBoard tBoard, String lastMove) {

    }

    public void isLegalMove(TestBoard board, int startRank, int startFile, int destRank, int destFile) {

    }
}
