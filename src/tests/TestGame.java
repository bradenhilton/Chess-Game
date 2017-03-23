package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import game.Player;
import pieces.PieceType;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class TestGame {
    private boolean legalMove = false;
    private boolean whiteTurn = true;
    private boolean gameOver = false;
    private boolean stalemate = false;

    private String move;

    private Map<Character, Integer> charMap = new HashMap<Character, Integer>();
    private Map<Integer, Character> reverseCharMap = new HashMap<Integer, Character>();
    private Map<PieceType, Integer> pieceScores = new HashMap<PieceType, Integer>();

    private List<String> moveHistory = new ArrayList<String>();

    private Scanner input = new Scanner(System.in);

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

    private int players;

    /**
     * Constructor which populates character maps and starts the loop.
     * 
     * @param board
     *            Custom game board.
     */
    public TestGame(TestBoard board, int numPlayers) {
        charMap.put('a', 0);
        charMap.put('b', 1);
        charMap.put('c', 2);
        charMap.put('d', 3);
        charMap.put('e', 4);
        charMap.put('f', 5);
        charMap.put('g', 6);
        charMap.put('h', 7);

        reverseCharMap.put(0, 'a');
        reverseCharMap.put(1, 'b');
        reverseCharMap.put(2, 'c');
        reverseCharMap.put(3, 'd');
        reverseCharMap.put(4, 'e');
        reverseCharMap.put(5, 'f');
        reverseCharMap.put(6, 'g');
        reverseCharMap.put(7, 'h');

        pieceScores.put(PieceType.PAWN, 1);
        pieceScores.put(PieceType.KNIGHT, 3);
        pieceScores.put(PieceType.BISHOP, 3);
        pieceScores.put(PieceType.ROOK, 5);
        pieceScores.put(PieceType.QUEEN, 9);

        players = numPlayers;

        // if (players == 2) {
        // new TestGameSingleplayer(board, charMap, reverseCharMap, whiteTurn);
        // } else if (players == 1) {
        // new TestGameMultiplayer(board, charMap, reverseCharMap, whiteTurn);
        // }

        testGame(board, whiteTurn, players);
    }

    /**
     * Game loop for test files.
     * <p>
     * Allows user input for moves from both players.
     * 
     * @param board
     *            Custom game board.
     * @param whiteTurn
     *            Boolean which dictates whose turn it is.
     */
    private void testGame(TestBoard board, boolean whiteTurn, int numPlayers) {
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

            // multiplayer loop
            if (players == 2) {
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
                            parseMoveTest(board, kingAndRook, whiteTurn, true);
                        }
                    } else {
                        parseMoveTest(board, move, whiteTurn, false);
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
                            parseMoveTest(board, move, whiteTurn, true);
                        }
                    } else {
                        parseMoveTest(board, move, whiteTurn, false);
                    }
                }
            } else {
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
                            parseMoveTest(board, kingAndRook, whiteTurn, true);
                        }
                    } else {
                        parseMoveTest(board, move, whiteTurn, false);
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

                    // used for forcing test cases
                    // for (int i = 0; i < allMoves.size(); i++) {
                    // force castle
                    // if (allMoves.get(i).substring(4).contains("castle")) {
                    // move = allMoves.get(i).substring(0, 4);
                    // }
                    // force en passant
                    // if (allMoves.get(i).substring(4).contains("en passant"))
                    // {
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
                        parseMoveTest(board, move, whiteTurn, true);
                    } else {
                        move = startFile + String.valueOf(startRank) + newFile + String.valueOf(newRank);
                        parseMoveTest(board, move, whiteTurn, false);
                    }
                }
            }
        }

        if (gameOver) {
            System.out.println("Play again?");
            String restart = input.nextLine();

            if (restart.equalsIgnoreCase("y") || restart.equalsIgnoreCase("yes")) {
                testGame(board, whiteTurn, players);
            } else {
                System.out.println("Goodbye!");
                System.exit(0);
            }
        }
    } // testLoop

    /**
     * Parses move input.
     * <p>
     * Converts move e.g. d2d4 into start ranks and files and new ranks and
     * files.
     * 
     * @param board
     *            Custom game board.
     * @param move
     *            String representation of move input.
     * @param whiteTurn
     *            Boolean which dictates whose turn it is.
     * @param castle
     *            Boolean which dictates if the move is a castle.
     */
    public void parseMoveTest(TestBoard board, String move, boolean whiteTurn, boolean castle) {
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
                                    testGame(board, whiteTurn, players);
                                } else if (possibleMoves.get(i).substring(4).contains("en passant")) {
                                    enPassant(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("White " + board.boardArray[newRank][newFile].getPieceType() + " "
                                            + move.toLowerCase() + " en passant");
                                    legalMove = true;
                                    whiteTurn = false;
                                    testGame(board, whiteTurn, players);
                                } else if (possibleMoves.get(i).substring(4).contains("check")) {
                                    movePiece(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("White " + board.boardArray[newRank][newFile].getPieceType() + " "
                                            + move.toLowerCase() + " check");
                                    legalMove = true;
                                    whiteTurn = false;
                                    testGame(board, whiteTurn, players);
                                } else {
                                    movePiece(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("White " + board.boardArray[newRank][newFile].getPieceType() + " "
                                            + move.toLowerCase());
                                    legalMove = true;
                                    whiteTurn = false;
                                    testGame(board, whiteTurn, players);
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
                                    testGame(board, whiteTurn, players);
                                } else if (possibleMoves.get(i).substring(4).contains("en passant")) {
                                    enPassant(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("Black " + board.boardArray[newRank][newFile].getPieceType() + " "
                                            + move.toLowerCase() + " en passant");
                                    legalMove = true;
                                    whiteTurn = true;
                                    testGame(board, whiteTurn, players);
                                } else if (possibleMoves.get(i).substring(4).contains("check")) {
                                    movePiece(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("Black " + board.boardArray[newRank][newFile].getPieceType() + " "
                                            + move.toLowerCase() + " check");
                                    legalMove = true;
                                    whiteTurn = false;
                                    testGame(board, whiteTurn, players);
                                } else {
                                    movePiece(board, startRank, startFile, newRank, newFile);

                                    moveHistory.add("Black " + board.boardArray[newRank][newFile].getPieceType() + " "
                                            + move.toLowerCase());
                                    legalMove = true;
                                    whiteTurn = true;
                                    testGame(board, whiteTurn, players);
                                }
                            }
                        }
                    }
                }
            }
        }
    } // parseMoveTest

    /**
     * Lists all possible moves that can be made by a specified piece.
     * 
     * @param board
     *            Custom game board.
     * @param piece
     *            String representation of a piece on the board.
     * @return A list of all possible moves for the piece.
     */
    public List<String> listPossibleMoves(TestBoard board, String piece) {
        int startRank, startFile, destRank;
        char destFile;
        List<String> allPossibleMoves = new ArrayList<String>();

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

                        // visualise possible moves with black pawns
                        // newRank = Integer.parseInt(move.substring(2, 3));
                        // newFile = Integer.parseInt(move.substring(3, 4));
                        // board.boardArray[newRank][newFile] = new
                        // Pawn(Player.BLACK);
                        System.out.println(String.valueOf(destFile) + String.valueOf(destRank) + move.substring(4));
                    }
                }

                System.out.println();
                board.printBoard(board.boardArray);
            }
        }
        return allPossibleMoves;
    } // listPossibleMoves

    /**
     * Castles a King and a Rook.
     * 
     * @param kingAndRook
     *            String representation of the king and rook locations.
     */
    public void castle(TestBoard board, int piece1Rank, int piece1File, int piece2Rank, int piece2File) {
        int kingRank = 0;
        int kingFile = 0;
        int rookRank = 0;
        int rookFile = 0;

        // piece1 = king
        // piece2 = rook
        if (board.boardArray[piece1Rank][piece1File].getPieceType() == PieceType.KING
                && board.boardArray[piece2Rank][piece2File].getPieceType() == PieceType.ROOK) {
            if (board.boardArray[piece1Rank][piece1File].getMoved() == false
                    && board.boardArray[piece2Rank][piece2File].getMoved() == false) {

                kingRank = piece1Rank;
                kingFile = piece1File;

                rookRank = piece2Rank;
                rookFile = piece2File;
            }
            // piece 1 = rook
            // piece 2 = king
        } else if (board.boardArray[piece1Rank][piece1File].getPieceType() == PieceType.ROOK
                && board.boardArray[piece2Rank][piece2File].getPieceType() == PieceType.KING) {
            if (board.boardArray[piece1Rank][piece1File].getMoved() == false
                    && board.boardArray[piece2Rank][piece2File].getMoved() == false) {

                kingRank = piece2Rank;
                kingFile = piece2File;

                rookRank = piece1Rank;
                rookFile = piece1File;
            }
        }

        if (kingFile > rookFile) { // left
            board.boardArray[kingRank][kingFile - 2] = board.boardArray[kingRank][kingFile]; // king
            board.boardArray[rookRank][kingFile - 1] = board.boardArray[rookRank][rookFile]; // rook

            board.boardArray[kingRank][kingFile] = null;
            board.boardArray[rookRank][rookFile] = null;

            board.boardArray[kingRank][kingFile - 2].setMoved();
            if (board.boardArray[kingRank][kingFile - 2].getPlayer() == Player.BLACK) {
                blackKingR = kingRank;
                blackKingF = kingFile - 2;
            } else {
                whiteKingR = kingRank;
                whiteKingF = kingFile - 2;
            }
            board.boardArray[rookRank][kingFile - 1].setMoved();
        } else { // right
            board.boardArray[kingRank][kingFile + 2] = board.boardArray[kingRank][kingFile]; // king
            board.boardArray[rookRank][kingFile + 1] = board.boardArray[rookRank][rookFile]; // rook

            board.boardArray[kingRank][kingFile] = null;
            board.boardArray[rookRank][rookFile] = null;

            board.boardArray[kingRank][kingFile + 2].setMoved();
            if (board.boardArray[kingRank][kingFile + 2].getPlayer() == Player.BLACK) {
                blackKingR = kingRank;
                blackKingF = kingFile + 2;
            } else {
                whiteKingR = kingRank;
                whiteKingF = kingFile + 2;
            }
            board.boardArray[rookRank][kingFile + 1].setMoved();
        }
    } // castle

    /**
     * Captures a pawn piece en passant.
     * 
     * @param pawns
     *            String representation of both Pawn pieces.
     */
    public void enPassant(TestBoard board, int startRank, int startFile, int destRank, int destFile) {
        int attackerRank = startRank;
        int attackerFile = startFile;

        int enemyRank;
        int enemyFile = destFile;

        if (board.boardArray[attackerRank][attackerFile].getPlayer() == Player.BLACK) {
            enemyRank = destRank - 1;
            board.boardArray[enemyRank][enemyFile] = null;
            board.boardArray[enemyRank + 1][enemyFile] = board.boardArray[attackerRank][attackerFile];
            board.boardArray[attackerRank][attackerFile] = null;
            board.boardArray[enemyRank + 1][enemyFile].setMoved();
        } else {
            enemyRank = destRank + 1;
            board.boardArray[enemyRank][enemyFile] = null;
            board.boardArray[enemyRank - 1][enemyFile] = board.boardArray[attackerRank][attackerFile];
            board.boardArray[attackerRank][attackerFile] = null;
            board.boardArray[enemyRank - 1][enemyFile].setMoved();
        }
    } // enPassant

    /**
     * Moves a piece on the game board.
     * 
     * @param board
     *            Custom game board.
     * @param startRank
     *            Starting Rank (Piece to be moved).
     * @param startFile
     *            Starting File (Piece to be moved).
     * @param destRank
     *            Destination Rank.
     * @param destFile
     *            Destination File.
     */
    public void movePiece(TestBoard board, int startRank, int startFile, int destRank, int destFile) {
        board.boardArray[destRank][destFile] = null;
        board.boardArray[destRank][destFile] = board.boardArray[startRank][startFile];
        board.boardArray[startRank][startFile] = null;
        board.boardArray[destRank][destFile].setMoved();
        board.boardArray[destRank][destFile].setMovedTwo(true);

        // lastMove(board, startRank, startFile, destRank, destFile);
    } // movePiece

    public void lastMove(TestBoard board, String lastMove) {
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

        lastPiece = board.boardArray[lastPieceNewRank][lastPieceNewFile].getPieceType();
    }

    /**
     * Checks move is legal.
     * <p>
     * Tries to match the chosen move with all possible moves for the selected
     * piece.
     * 
     * @param board
     *            Custom game board.
     * @param startRank
     *            Starting Rank (Piece to be moved).
     * @param startFile
     *            Starting File (Piece to be moved).
     * @param destRank
     *            Destination Rank.
     * @param destFile
     *            Destination File.
     * @return True for legal move, False for illegal move.
     */
    public boolean isLegalMove(TestBoard board, int startRank, int startFile, int destRank, int destFile) {
        String destination = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(destRank)
                + String.valueOf(destFile);
        List<String> possibleMoves = board.boardArray[startRank][startFile].generatePossibleMoves(board.boardArray,
                startRank, startFile);

        for (String move : possibleMoves) {
            if (move.substring(0, 4).equals(destination)) {
                return true;
            }
        }

        return false;
    } // isLegalMove
}