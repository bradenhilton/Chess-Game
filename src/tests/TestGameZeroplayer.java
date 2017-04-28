package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import game.Player;
import pieces.Bishop;
import pieces.Knight;
import pieces.Piece;
import pieces.PieceType;
import pieces.Queen;
import pieces.Rook;

public class TestGameZeroplayer {
    private boolean legalMove = false;
    private boolean whiteTurn = true;
    private boolean gameOver = false;
    private boolean stalemate = false;
    private boolean inCheck = false;

    private String move;

    private Map<Character, Integer> charMap = new HashMap<Character, Integer>();
    private Map<Integer, Character> reverseCharMap = new HashMap<Integer, Character>();

    private List<String> moveHistory = new ArrayList<String>();
    private List<Piece> capturedPieces = new ArrayList<Piece>();
    private List<Piece> promotedPieces = new ArrayList<Piece>();

    private List<String> allPossibleMoves = new ArrayList<String>();
    private List<String> allPossibleFriendlyMoves = new ArrayList<String>();
    private List<String> allPossibleEnemyMoves = new ArrayList<String>();

    private Scanner input = new Scanner(System.in);

    private Player currentPlayer = Player.WHITE;
    private Player lastPlayer = Player.BLACK;
    private PieceType lastPiece;
    private int lastPieceOldRank = 0;
    private int lastPieceOldFile = 0;
    private int lastPieceNewRank = 0;
    private int lastPieceNewFile = 0;

    private int blackKingR;
    private int blackKingF;
    private int whiteKingR;
    private int whiteKingF;

    private TestBoard board;
    private int players = 0;
    private int globalDepthMax = 4;
    private int globalDepthMin = globalDepthMax / 2;

    public TestGameZeroplayer(TestBoard tBoard, Map<Character, Integer> cMap, Map<Integer, Character> reverseCMap,
            boolean turn) {
        board = tBoard;
        charMap = cMap;
        reverseCharMap = reverseCMap;
        whiteTurn = turn;

        testGameZeroplayerLoop(board);
    }

    public void testGameZeroplayerLoop(TestBoard tBoard) {
        while (!(gameOver || stalemate)) {
            if (!moveHistory.isEmpty()) {
                if (tBoard.boardArray[lastPieceNewRank][lastPieceNewFile].getPieceType() == PieceType.PAWN
                        && (Math.abs(lastPieceNewRank - lastPieceOldRank) == 2
                                && lastPieceNewFile == lastPieceOldFile)) {
                    tBoard.boardArray[lastPieceNewRank][lastPieceNewFile].setMovedTwo(true);
                } else {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (tBoard.boardArray[i][j] != null) {
                                tBoard.boardArray[i][j].setMovedTwo(false);
                            }
                        }
                    }
                }
            }

            if (legalMove) {
                tBoard.printBoard(tBoard.boardArray);
            }

            if (whiteTurn) {
                currentPlayer = Player.WHITE;
                lastPlayer = Player.BLACK;
            } else {
                currentPlayer = Player.BLACK;
                lastPlayer = Player.WHITE;
            }

            // generates legal moves
            allPossibleMoves.clear();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (tBoard.boardArray[i][j] != null) {
                        if (tBoard.boardArray[i][j].getPlayer() == currentPlayer) {
                            allPossibleFriendlyMoves
                                    .addAll(tBoard.boardArray[i][j].generatePossibleMoves(tBoard.boardArray, i, j));
                        } else {
                            allPossibleEnemyMoves
                                    .addAll(tBoard.boardArray[i][j].generatePossibleMoves(tBoard.boardArray, i, j));
                        }
                    }
                }
            }

            allPossibleMoves.addAll(allPossibleFriendlyMoves);
            allPossibleMoves.addAll(allPossibleEnemyMoves);

            if (isCheckmate(tBoard, allPossibleEnemyMoves)) {
                gameOver = true;
                break;
            }

            if (!moveHistory.isEmpty()) {
                System.out.println(moveHistory.get(moveHistory.size() - 1));
            }

            System.out.println(currentPlayer.toString() + " to move");

            TestBoard tempBoard = tBoard;

            long startTime = System.currentTimeMillis();
            if (currentPlayer == Player.WHITE) {
                move = alphaBetaWhite(tempBoard, globalDepthMax, Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0);
            } else {
                move = alphaBetaBlack(tempBoard, globalDepthMin, Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("alpha-beta finished with score of: " + move.substring(28));
            System.out.println("That took " + (endTime - startTime) + " milliseconds");
            move = move.substring(0, 4);

            Character startFile = reverseCharMap.get(Integer.parseInt(move.substring(1, 2)));
            int startRank = 8 - Integer.parseInt(move.substring(0, 1));
            Character destFile = reverseCharMap.get(Integer.parseInt(move.substring(3, 4)));
            int destRank = 8 - Integer.parseInt(move.substring(2, 3));

            if (move.substring(4).contains("castle")) {
                move = String.valueOf(startFile)
                        + String.valueOf(startRank + String.valueOf(destFile) + String.valueOf(destRank));
                testGameZeroplayerParseMove(tBoard, move, true);
            } else {
                move = String.valueOf(startFile)
                        + String.valueOf(startRank + String.valueOf(destFile) + String.valueOf(destRank));
                testGameZeroplayerParseMove(tBoard, move, false);
            }
        }

        if (gameOver) {
            System.out.println(lastPlayer.toString().toUpperCase() + "WINS, GAME OVER!");
            System.exit(0);
        }
    }

    public void testGameZeroplayerParseMove(TestBoard tBoard, String move, boolean castle) {
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
                System.err.println("Error: Invalid move, start square = destination square");
            } else {
                if (board.boardArray[startRank][startFile].getPlayer() != currentPlayer) {
                    System.err.println("Error: Invalid move, " + currentPlayer + " cannot move " + lastPlayer);
                } else {
                    String destination = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(newRank)
                            + String.valueOf(newFile);

                    for (int i = 0; i < allPossibleMoves.size(); i++) {
                        if (i + 1 == allPossibleMoves.size()
                                && !allPossibleMoves.get(i).substring(0, 4).contains(destination)) {
                            System.err.println("Error: Invalid move");
                        } else if (allPossibleMoves.get(i).substring(0, 4).contains(destination)) {
                            if (castle == true || allPossibleMoves.get(i).substring(4).contains("castle")) {
                                castle(tBoard, startRank, startFile, newRank, newFile);
                                legalMove = true;
                                whiteTurn = !whiteTurn;
                                testGameZeroplayerLoop(tBoard);
                            } else if (allPossibleMoves.get(i).substring(4).contains("en passant")) {
                                enPassant(tBoard, startRank, startFile, newRank, newFile);
                                legalMove = true;
                                whiteTurn = !whiteTurn;
                                testGameZeroplayerLoop(tBoard);
                            } else if (allPossibleMoves.get(i).substring(4).contains("check")) {
                                makeMove(tBoard, startRank, startFile, newRank, newFile);
                                legalMove = true;
                                whiteTurn = !whiteTurn;
                                testGameZeroplayerLoop(tBoard);
                            } else if (allPossibleMoves.get(i).substring(4).contains("capture")) {
                                makeMove(tBoard, startRank, startFile, newRank, newFile);
                                legalMove = true;
                                whiteTurn = !whiteTurn;
                                testGameZeroplayerLoop(tBoard);
                            } else {
                                makeMove(tBoard, startRank, startFile, newRank, newFile);
                                legalMove = true;
                                whiteTurn = !whiteTurn;
                                testGameZeroplayerLoop(tBoard);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isCheck(TestBoard tBoard, List<String> possibleMoves) {
        if ((tBoard.boardArray[blackKingR][blackKingF] != null
                && tBoard.boardArray[blackKingR][blackKingF].getPieceType() != PieceType.KING
                && tBoard.boardArray[blackKingR][blackKingF].getPlayer() != Player.BLACK)
                && (tBoard.boardArray[whiteKingR][whiteKingF] != null
                        && tBoard.boardArray[whiteKingR][whiteKingF].getPieceType() != PieceType.KING
                        && tBoard.boardArray[whiteKingR][whiteKingF].getPlayer() != Player.WHITE)) { // if no kings
            return false;
        } else if (tBoard.boardArray[blackKingR][blackKingF] != null
                && tBoard.boardArray[blackKingR][blackKingF].getPieceType() != PieceType.KING
                && tBoard.boardArray[blackKingR][blackKingF].getPlayer() != Player.BLACK) { // else if no black king
            return false;
        } else if (tBoard.boardArray[whiteKingR][whiteKingF] != null
                && tBoard.boardArray[whiteKingR][whiteKingF].getPieceType() != PieceType.KING
                && tBoard.boardArray[whiteKingR][whiteKingF].getPlayer() != Player.WHITE) { // else if no white king
            return false;
        } else { // else both kings
            String blackKing = String.valueOf(blackKingR) + String.valueOf(blackKingF);
            String whiteKing = String.valueOf(whiteKingR) + String.valueOf(whiteKingF);

            for (String move : possibleMoves) {
                if (move.substring(2, 4).equalsIgnoreCase(blackKing)) {
                    System.out.println("BLACK IS IN CHECK");
                    return true;
                } else if (move.substring(2, 4).equalsIgnoreCase(whiteKing)) {
                    System.out.println("WHITE IS IN CHECK");
                    return true;
                }
            }

            return false;
        }
    }

    public boolean isCheckmate(TestBoard tBoard, List<String> possibleMoves) {
        List<String> newMoves = new ArrayList<String>();
        for (String m : possibleMoves) {
            if (m.contains("check")) {
                int rank = Integer.parseInt(m.substring(2, 3));
                int file = Integer.parseInt(m.substring(3, 4));

                newMoves = tBoard.boardArray[rank][file].generatePossibleMoves(tBoard.boardArray, rank, file);

                if (newMoves.isEmpty()) {
                    return true;
                } else {
                    inCheck = true;
                    return false;
                }
            }
        }
        return false;
    }

    public String alphaBetaWhite(TestBoard tBoard, int depth, int beta, int alpha, String move, int playerNum) {
        List<String> allMoves = new ArrayList<String>();
        int startRank, startFile, destRank, destFile;
        int value;
        Player player;

        if (playerNum == 0) {
            player = Player.WHITE;
        } else {
            player = Player.BLACK;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tBoard.boardArray[i][j] != null && tBoard.boardArray[i][j].getPlayer() == player) {
                    allMoves.addAll(tBoard.boardArray[i][j].generatePossibleMoves(tBoard.boardArray, i, j));
                }
            }
        }

        allMoves = sortMoves(tBoard, allMoves);

        if (depth == 0 || allMoves.isEmpty()) {
            return move + String.valueOf(TestRating.rating(tBoard, allMoves, depth) * (playerNum * 2 - 1));
        }

        playerNum = 1 - playerNum;
        for (String m : allMoves) {
            startRank = Integer.parseInt(m.substring(0, 1));
            startFile = Integer.parseInt(m.substring(1, 2));
            destRank = Integer.parseInt(m.substring(2, 3));
            destFile = Integer.parseInt(m.substring(3, 4));

            makeMove(tBoard, startRank, startFile, destRank, destFile);

            String returnString = alphaBetaWhite(tBoard, depth - 1, beta, alpha, m, playerNum);
            value = Integer.parseInt(returnString.substring(28));

            undoMove(tBoard, moveHistory, capturedPieces, promotedPieces);

            if (playerNum == 0) {
                if (value <= beta) {
                    beta = value;

                    if (depth == globalDepthMax && currentPlayer == Player.WHITE) {
                        move = returnString.substring(0, 28);
                    }
                }
            } else {
                if (value > alpha) {
                    alpha = value;

                    if (depth == globalDepthMax && currentPlayer == Player.WHITE) {
                        move = returnString.substring(0, 28);
                    }
                }
            }

            if (alpha >= beta) {
                if (playerNum == 0) {
                    return move + String.valueOf(beta);
                } else {
                    return move + String.valueOf(alpha);
                }
            }
        }

        if (playerNum == 0) {
            return move + String.valueOf(beta);
        } else {
            return move + String.valueOf(alpha);
        }
    }

    public String alphaBetaBlack(TestBoard tBoard, int depth, int beta, int alpha, String move, int playerNum) {
        List<String> allMoves = new ArrayList<String>();
        int startRank, startFile, destRank, destFile;
        int value;
        Player player;

        if (playerNum == 0) {
            player = Player.BLACK;
        } else {
            player = Player.WHITE;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tBoard.boardArray[i][j] != null && tBoard.boardArray[i][j].getPlayer() == player) {
                    allMoves.addAll(tBoard.boardArray[i][j].generatePossibleMoves(tBoard.boardArray, i, j));
                }
            }
        }

        allMoves = sortMoves(tBoard, allMoves);

        if (depth == 0 || allMoves.isEmpty()) {
            return move + String.valueOf(TestRating.rating(tBoard, allMoves, depth) * (playerNum * 2 - 1));
        }

        playerNum = 1 - playerNum;
        for (String m : allMoves) {
            startRank = Integer.parseInt(m.substring(0, 1));
            startFile = Integer.parseInt(m.substring(1, 2));
            destRank = Integer.parseInt(m.substring(2, 3));
            destFile = Integer.parseInt(m.substring(3, 4));

            makeMove(tBoard, startRank, startFile, destRank, destFile);

            String returnString = alphaBetaBlack(tBoard, depth - 1, beta, alpha, m, playerNum);
            value = Integer.parseInt(returnString.substring(28));

            undoMove(tBoard, moveHistory, capturedPieces, promotedPieces);

            if (playerNum == 0) {
                if (value <= beta) {
                    beta = value;

                    if (depth == globalDepthMin && currentPlayer == Player.BLACK) {
                        move = returnString.substring(0, 28);
                    }
                }
            } else {
                if (value > alpha) {
                    alpha = value;

                    if (depth == globalDepthMin && currentPlayer == Player.BLACK) {
                        move = returnString.substring(0, 28);
                    }
                }
            }

            if (alpha >= beta) {
                if (playerNum == 0) {
                    return move + String.valueOf(beta);
                } else {
                    return move + String.valueOf(alpha);
                }
            }
        }

        if (playerNum == 0) {
            return move + String.valueOf(beta);
        } else {
            return move + String.valueOf(alpha);
        }
    }

    /**
     * Castles a King and a Rook.
     * 
     * @param kingAndRook
     *            String representation of the king and rook locations.
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

            tBoard.boardArray[kingRank][kingFile - 2].setMoved(true);
            if (tBoard.boardArray[kingRank][kingFile - 2].getPlayer() == Player.BLACK) {
                blackKingR = kingRank;
                blackKingF = kingFile - 2;
            } else {
                whiteKingR = kingRank;
                whiteKingF = kingFile - 2;
            }
            tBoard.boardArray[rookRank][kingFile - 1].setMoved(true);
        } else { // right
            tBoard.boardArray[kingRank][kingFile + 2] = tBoard.boardArray[kingRank][kingFile]; // king
            tBoard.boardArray[rookRank][kingFile + 1] = tBoard.boardArray[rookRank][rookFile]; // rook

            tBoard.boardArray[kingRank][kingFile] = null;
            tBoard.boardArray[rookRank][rookFile] = null;

            tBoard.boardArray[kingRank][kingFile + 2].setMoved(true);
            if (tBoard.boardArray[kingRank][kingFile + 2].getPlayer() == Player.BLACK) {
                blackKingR = kingRank;
                blackKingF = kingFile + 2;
            } else {
                whiteKingR = kingRank;
                whiteKingF = kingFile + 2;
            }
            tBoard.boardArray[rookRank][kingFile + 1].setMoved(true);
        }

        lastMove(tBoard, kingRank, kingFile, rookRank, rookFile, true, false, false, false);
    } // castle

    /**
     * Captures a pawn piece en passant.
     * 
     * @param pawns
     *            String representation of both Pawn pieces.
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
            tBoard.boardArray[enemyRank + 1][enemyFile].setMoved(true);
            tBoard.boardArray[enemyRank + 1][enemyFile].incCaptures();
            enemyRank = enemyRank + 1;
        } else {
            enemyRank = destRank + 1;
            tBoard.boardArray[enemyRank][enemyFile] = null;
            tBoard.boardArray[enemyRank - 1][enemyFile] = tBoard.boardArray[attackerRank][attackerFile];
            tBoard.boardArray[attackerRank][attackerFile] = null;
            tBoard.boardArray[enemyRank - 1][enemyFile].setMoved(true);
            tBoard.boardArray[enemyRank - 1][enemyFile].incCaptures();
            enemyRank = enemyRank - 1;
        }

        lastMove(tBoard, attackerRank, attackerFile, enemyRank, enemyFile, false, true, false, false);
    } // enPassant

    /**
     * Promotes a Pawn to a specified piece through user input.
     * 
     * @param tBoard
     *            Board to promote Pawn on.
     * @param rank
     *            Rank of Pawn.
     * @param file
     *            File of Pawn.
     */
    public void promotePawn(TestBoard tBoard, int rank, int file) {
        Piece pawn = tBoard.boardArray[rank][file];

        List<String> queenMoves = new ArrayList<String>();
        List<String> knightMoves = new ArrayList<String>();
        List<String> rookMoves = new ArrayList<String>();
        List<String> bishopMoves = new ArrayList<String>();

        List<String> possiblePieces = new ArrayList<String>();
        possiblePieces.add("queen");
        possiblePieces.add("knight");
        possiblePieces.add("rook");
        possiblePieces.add("bishop");

        int captures = tBoard.boardArray[rank][file].getCaptures();
        Player player = tBoard.boardArray[rank][file].getPlayer();
        String piece;

        boolean acceptInput = false;

        tBoard.boardArray[rank][file] = null;
        tBoard.boardArray[rank][file] = new Queen(player, rank, file);
        queenMoves = tBoard.boardArray[rank][file].generatePossibleMoves(tBoard.boardArray, rank, file);
        tBoard.boardArray[rank][file] = pawn;

        tBoard.boardArray[rank][file] = null;
        tBoard.boardArray[rank][file] = new Knight(player, rank, file);
        knightMoves = tBoard.boardArray[rank][file].generatePossibleMoves(tBoard.boardArray, rank, file);
        tBoard.boardArray[rank][file] = pawn;

        tBoard.boardArray[rank][file] = null;
        tBoard.boardArray[rank][file] = new Rook(player, rank, file);
        rookMoves = tBoard.boardArray[rank][file].generatePossibleMoves(tBoard.boardArray, rank, file);
        tBoard.boardArray[rank][file] = pawn;

        tBoard.boardArray[rank][file] = null;
        tBoard.boardArray[rank][file] = new Bishop(player, rank, file);
        bishopMoves = tBoard.boardArray[rank][file].generatePossibleMoves(tBoard.boardArray, rank, file);
        tBoard.boardArray[rank][file] = pawn;

        if (queenMoves.isEmpty()) {
            possiblePieces.remove("queen");
        } else if (knightMoves.isEmpty()) {
            possiblePieces.remove("knight");
        } else if (rookMoves.isEmpty()) {
            possiblePieces.remove("rook");
        } else if (bishopMoves.isEmpty()) {
            possiblePieces.remove("bishop");
        }

        if (players == 2 || (players == 1 && whiteTurn)) {
            System.out.println("Which piece would you like to promote to?" + "\nQ or QUEEN" + "\nK or KNIGHT"
                    + "\nR or ROOK" + "\nB or BISHOP");

            while (!acceptInput) {
                piece = input.nextLine();
                if (piece.equalsIgnoreCase("q") || piece.equalsIgnoreCase("queen")) {
                    if (possiblePieces.contains("queen")) {
                        promotedPieces.add(tBoard.boardArray[rank][file]);
                        tBoard.boardArray[rank][file] = null;
                        tBoard.boardArray[rank][file] = new Queen(player, rank, file);
                        tBoard.boardArray[rank][file].setCaptures(captures);
                        acceptInput = true;
                    } else {
                        System.err.println("Error: No legal Queen moves can be made");
                    }
                } else if (piece.equalsIgnoreCase("k") || piece.equalsIgnoreCase("knight")) {
                    if (possiblePieces.contains("knight")) {
                        promotedPieces.add(tBoard.boardArray[rank][file]);
                        tBoard.boardArray[rank][file] = null;
                        tBoard.boardArray[rank][file] = new Knight(player, rank, file);
                        tBoard.boardArray[rank][file].setCaptures(captures);
                        acceptInput = true;
                    } else {
                        System.err.println("Error: No legal Knight moves can be made");
                    }
                } else if (piece.equalsIgnoreCase("r") || piece.equalsIgnoreCase("rook")) {
                    if (possiblePieces.contains("rook")) {
                        promotedPieces.add(tBoard.boardArray[rank][file]);
                        tBoard.boardArray[rank][file] = null;
                        tBoard.boardArray[rank][file] = new Rook(player, rank, file);
                        tBoard.boardArray[rank][file].setCaptures(captures);
                        acceptInput = true;
                    } else {
                        System.err.println("Error: No legal Rook moves can be made");
                    }
                } else if (piece.equalsIgnoreCase("b") || piece.equalsIgnoreCase("bishop")) {
                    if (possiblePieces.contains("bishop")) {
                        promotedPieces.add(tBoard.boardArray[rank][file]);
                        tBoard.boardArray[rank][file] = null;
                        tBoard.boardArray[rank][file] = new Bishop(player, rank, file);
                        tBoard.boardArray[rank][file].setCaptures(captures);
                        acceptInput = true;
                    } else {
                        System.err.println("Error: No legal Bishop moves can be made");
                    }
                } else {
                    System.err.println("Error: Please specify a piece");
                }
            }
        } else {
            if (possiblePieces.contains("queen")) {
                promotedPieces.add(tBoard.boardArray[rank][file]);
                tBoard.boardArray[rank][file] = null;
                tBoard.boardArray[rank][file] = new Queen(player, rank, file);
                tBoard.boardArray[rank][file].setCaptures(captures);
            } else if (possiblePieces.contains("knight")) {
                promotedPieces.add(tBoard.boardArray[rank][file]);
                tBoard.boardArray[rank][file] = null;
                tBoard.boardArray[rank][file] = new Knight(player, rank, file);
                tBoard.boardArray[rank][file].setCaptures(captures);
            } else if (possiblePieces.contains("rook")) {
                promotedPieces.add(tBoard.boardArray[rank][file]);
                tBoard.boardArray[rank][file] = null;
                tBoard.boardArray[rank][file] = new Rook(player, rank, file);
                tBoard.boardArray[rank][file].setCaptures(captures);
            } else if (possiblePieces.contains("bishop")) {
                promotedPieces.add(tBoard.boardArray[rank][file]);
                tBoard.boardArray[rank][file] = null;
                tBoard.boardArray[rank][file] = new Bishop(player, rank, file);
                tBoard.boardArray[rank][file].setCaptures(captures);
            }
        }
    } // promotePawn

    /**
     * Moves a piece on the game board.
     * 
     * @param tBoard
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
    public void makeMove(TestBoard tBoard, int startRank, int startFile, int destRank, int destFile) {
        String player = tBoard.boardArray[startRank][startFile].getPlayer().toString();

        if (tBoard.boardArray[destRank][destFile] != null) {
            if (tBoard.boardArray[startRank][startFile].getPieceType() == PieceType.PAWN
                    && ((player.equalsIgnoreCase(Player.BLACK.toString()) && destRank == 7)
                            || (player.equalsIgnoreCase(Player.WHITE.toString()) && destRank == 0))) {
                capturedPieces.add(tBoard.boardArray[destRank][destFile]);
                tBoard.boardArray[destRank][destFile] = null;
                tBoard.boardArray[destRank][destFile] = tBoard.boardArray[startRank][startFile];
                tBoard.boardArray[startRank][startFile] = null;
                tBoard.boardArray[destRank][destFile].setMoved(true);
                tBoard.boardArray[destRank][destFile].incCaptures();

                promotePawn(tBoard, destRank, destFile);
                lastMove(tBoard, startRank, startFile, destRank, destFile, false, false, true, true);
            } else {
                capturedPieces.add(tBoard.boardArray[destRank][destFile]);
                tBoard.boardArray[destRank][destFile] = null;
                tBoard.boardArray[destRank][destFile] = tBoard.boardArray[startRank][startFile];
                tBoard.boardArray[startRank][startFile] = null;
                tBoard.boardArray[destRank][destFile].setMoved(true);
                tBoard.boardArray[destRank][destFile].incCaptures();

                lastMove(tBoard, startRank, startFile, destRank, destFile, false, false, true, false);
            }
        } else if (tBoard.boardArray[destRank][destFile] == null) {
            if (tBoard.boardArray[startRank][startFile].getPieceType() == PieceType.PAWN
                    && ((player.equalsIgnoreCase(Player.BLACK.toString()) && destRank == 7)
                            || (player.equalsIgnoreCase(Player.WHITE.toString()) && destRank == 0))) {
                capturedPieces.add(tBoard.boardArray[destRank][destFile]);
                tBoard.boardArray[destRank][destFile] = null;
                tBoard.boardArray[destRank][destFile] = tBoard.boardArray[startRank][startFile];
                tBoard.boardArray[startRank][startFile] = null;
                tBoard.boardArray[destRank][destFile].setMoved(true);

                promotePawn(tBoard, destRank, destFile);
                lastMove(tBoard, startRank, startFile, destRank, destFile, false, false, false, true);
            } else {
                tBoard.boardArray[destRank][destFile] = tBoard.boardArray[startRank][startFile];
                tBoard.boardArray[startRank][startFile] = null;
                tBoard.boardArray[destRank][destFile].setMoved(true);

                lastMove(tBoard, startRank, startFile, destRank, destFile, false, false, false, false);
            }
        }
    } // movePiece

    public void undoMove(TestBoard tBoard, List<String> history, List<Piece> captured, List<Piece> promoted) {
        String move = history.get(history.size() - 1);
        int startRank, startFile, destRank, destFile;
        Piece capturedPiece = null;
        Piece promotedPiece = null;

        // was a piece captured?
        if (move.substring(5).contains("capture") && move.substring(5).contains("promote")) {
            capturedPiece = captured.get(captured.size() - 1);
            promotedPiece = promoted.get(promoted.size() - 1);
        } else if (move.substring(5).contains("capture")) {
            capturedPiece = captured.get(captured.size() - 1);
        } else if (move.substring(5).contains("promote")) {
            promotedPiece = promoted.get(promoted.size() - 1);
        }

        // get co-ordinates
        startRank = 8 - Integer.parseInt(move.substring(1, 2));
        startFile = charMap.get(move.charAt(0));
        destRank = 8 - Integer.parseInt(move.substring(3, 4));
        destFile = charMap.get(move.charAt(2));

        // move piece back to start
        tBoard.boardArray[startRank][startFile] = tBoard.boardArray[destRank][destFile];

        // resets the moved flag for the piece if it returns to its home
        // co-ordinates
        String start = String.valueOf(startRank) + String.valueOf(startFile);
        if (tBoard.boardArray[startRank][startFile].getHome().equals(start)) {
            tBoard.boardArray[startRank][startFile].setMoved(false);
            if (tBoard.boardArray[startRank][startFile].getPieceType() == PieceType.PAWN) {
                tBoard.boardArray[startRank][startFile].setMovedTwo(false);
            }
        }

        tBoard.boardArray[destRank][destFile] = null;

        // remove the move from the history
        history.remove(history.size() - 1);

        // restore captured piece and remove the piece from the history
        if (capturedPiece != null && promotedPiece != null) { // if capture and promotion
            tBoard.boardArray[startRank][startFile] = promotedPiece;
            promoted.remove(promoted.size() - 1);
            tBoard.boardArray[startRank][startFile].decCaptures();

            tBoard.boardArray[destRank][destFile] = capturedPiece;
        } else if (capturedPiece != null) { // else if capture
            tBoard.boardArray[destRank][destFile] = capturedPiece;
            captured.remove(captured.size() - 1);
            tBoard.boardArray[startRank][startFile].decCaptures();
        } else if (promotedPiece != null) { // else if promotion
            tBoard.boardArray[startRank][startFile] = promotedPiece;
            promoted.remove(promoted.size() - 1);
        }

        // update variables
        if (!history.isEmpty()) {
            move = history.get(history.size() - 1);

            startRank = 8 - Integer.parseInt(move.substring(1, 2));
            startFile = charMap.get(move.charAt(0));
            destRank = 8 - Integer.parseInt(move.substring(3, 4));
            destFile = charMap.get(move.charAt(2));

            lastPlayer = tBoard.boardArray[destRank][destFile].getPlayer();
            if (lastPlayer == Player.BLACK) {
                currentPlayer = Player.WHITE;
            } else {
                currentPlayer = Player.BLACK;
            }
            lastPieceOldRank = startRank;
            lastPieceOldFile = startFile;
            lastPieceNewRank = destRank;
            lastPieceNewFile = destFile;
            lastPiece = tBoard.boardArray[destRank][destFile].getPieceType();
        } else {
            lastPlayer = null;
            currentPlayer = Player.WHITE;
            lastPieceOldRank = 0;
            lastPieceOldFile = 0;
            lastPieceNewRank = 0;
            lastPieceNewFile = 0;
            lastPiece = null;
        }

        if (players == 2 || (players == 1 && whiteTurn)) {
            System.out.println("Last move undone.");
        }
    } // undoMove

    public List<String> sortMoves(TestBoard tBoard, List<String> moves) {
        int startRank, startFile, destRank, destFile;
        List<String> bestMoves = new ArrayList<String>();
        List<String> sortedMoves = new ArrayList<String>();

        int[] score = new int[moves.size()];

        for (int i = 0; i < moves.size(); i++) {
            startRank = Integer.parseInt(moves.get(i).substring(0, 1));
            startFile = Integer.parseInt(moves.get(i).substring(1, 2));
            destRank = Integer.parseInt(moves.get(i).substring(2, 3));
            destFile = Integer.parseInt(moves.get(i).substring(3, 4));

            makeMove(tBoard, startRank, startFile, destRank, destFile);
            score[i] = -TestRating.rating(tBoard, new ArrayList<String>(), 0);
            undoMove(tBoard, moveHistory, capturedPieces, promotedPieces);
        }

        for (int i = 0; i < Math.min(6, moves.size()); i++) {
            int max = -1000000, maxLocation = 0;
            for (int j = 0; j < moves.size(); j++) {
                if (score[j] > max) {
                    max = score[j];
                    maxLocation = j;
                }
            }

            score[maxLocation] = -1000000;
            bestMoves.add(moves.get(maxLocation));
            moves.remove(maxLocation);
        }
        sortedMoves.addAll(bestMoves);
        sortedMoves.addAll(moves);
        return sortedMoves;
    }

    public void lastMove(TestBoard tBoard, int startRank, int startFile, int destRank, int destFile, boolean castle,
            boolean enPassant, boolean capture, boolean promotion) {
        String player, oldRank, newRank, piece;
        Character oldFile, newFile;

        if (tBoard.boardArray[destRank][destFile].getPlayer() == Player.WHITE) {
            lastPlayer = Player.WHITE;
        } else {
            lastPlayer = Player.BLACK;
        }

        lastPieceOldRank = startRank;
        lastPieceOldFile = startFile;
        lastPieceNewRank = destRank;
        lastPieceNewFile = destFile;

        lastPiece = tBoard.boardArray[lastPieceNewRank][lastPieceNewFile].getPieceType();

        player = lastPlayer.toString();
        oldRank = String.valueOf(8 - lastPieceOldRank);
        oldFile = reverseCharMap.get(lastPieceOldFile);
        newRank = String.valueOf(8 - lastPieceNewRank);
        newFile = reverseCharMap.get(lastPieceNewFile);
        piece = lastPiece.toString();

        if (castle) {
            moveHistory.add(oldFile + oldRank + newFile + newRank + " " + player + " castle");
        } else if (enPassant) {
            String enemy = capturedPieces.get(capturedPieces.size() - 1).getPlayer().toString();
            String enemyPiece = capturedPieces.get(capturedPieces.size() - 1).getPieceType().toString();

            moveHistory.add(oldFile + oldRank + newFile + newRank + " " + player + " " + piece + " capture " + enemy
                    + " " + enemyPiece + " en passant");
        } else if (capture && promotion) {
            String enemy = capturedPieces.get(capturedPieces.size() - 1).getPlayer().toString();
            String enemyPiece = capturedPieces.get(capturedPieces.size() - 1).getPieceType().toString();
            String promotedPiece = promotedPieces.get(promotedPieces.size() - 1).getPieceType().toString();

            moveHistory.add(oldFile + oldRank + newFile + newRank + " " + player + " " + promotedPiece + " capture "
                    + enemy + " " + enemyPiece + ", promote to " + piece);
        } else if (capture) {
            String enemy = capturedPieces.get(capturedPieces.size() - 1).getPlayer().toString();
            String enemyPiece = capturedPieces.get(capturedPieces.size() - 1).getPieceType().toString();

            moveHistory.add(oldFile + oldRank + newFile + newRank + " " + player + " " + piece + " capture " + enemy
                    + " " + enemyPiece);
        } else if (promotion) {
            String promotedPiece = promotedPieces.get(promotedPieces.size() - 1).getPieceType().toString();

            moveHistory.add(oldFile + oldRank + newFile + newRank + " " + player + " " + promotedPiece + " promote to "
                    + piece);
        } else {
            moveHistory.add(oldFile + oldRank + newFile + newRank + " " + player + " " + piece);
        }
    } // lastMove

    /**
     * Checks move is legal.
     * <p>
     * Tries to match the chosen move with all possible moves for the selected piece.
     * 
     * @param tBoard
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
    public boolean isLegalMove(TestBoard tBoard, int startRank, int startFile, int destRank, int destFile) {
        String destination = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(destRank)
                + String.valueOf(destFile);
        // List<String> possibleMoves =
        // board.boardArray[startRank][startFile].generatePossibleMoves(board.boardArray,
        // startRank, startFile);

        for (String move : allPossibleMoves) {
            if (move.substring(0, 4).equals(destination)) {
                return true;
            }
        }

        return false;
    } // isLegalMove
}
