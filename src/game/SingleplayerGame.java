package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import pieces.Bishop;
import pieces.Knight;
import pieces.Piece;
import pieces.PieceType;
import pieces.Queen;
import pieces.Rook;

public class SingleplayerGame {
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

    private Player lastPlayer;
    private Player currentPlayer;
    private PieceType lastPiece;
    private int lastPieceOldRank = 0;
    private int lastPieceOldFile = 0;
    private int lastPieceNewRank = 0;
    private int lastPieceNewFile = 0;

    private int blackKingR;
    private int blackKingF;
    private int whiteKingR;
    private int whiteKingF;

    private Board board;
    private Board resetBoard;
    private int players = 1;
    private int globalDepth = 4;

    public SingleplayerGame(Board chessBoard, Map<Character, Integer> cMap, Map<Integer, Character> reverseCMap,
            boolean turn) {
        resetBoard = chessBoard;
        board = chessBoard;
        charMap = cMap;
        reverseCharMap = reverseCMap;
        whiteTurn = turn;

        singleplayerLoop(board, whiteTurn);
    }

    public void singleplayerLoop(Board chessBoard, boolean turn) {
        while (!(gameOver || stalemate)) {
            if (!moveHistory.isEmpty()) {
                if (chessBoard.boardArray[lastPieceNewRank][lastPieceNewFile].getPieceType() == PieceType.PAWN
                        && (Math.abs(lastPieceNewRank - lastPieceOldRank) == 2
                                && lastPieceNewFile == lastPieceOldFile)) {
                    chessBoard.boardArray[lastPieceNewRank][lastPieceNewFile].setMovedTwo(true);
                } else {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (chessBoard.boardArray[i][j] != null) {
                                chessBoard.boardArray[i][j].setMovedTwo(false);
                            }
                        }
                    }
                }
            }

            if (legalMove) {
                chessBoard.printBoard(chessBoard.boardArray);
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
                    if (chessBoard.boardArray[i][j] != null) {
                        if (chessBoard.boardArray[i][j].getPlayer() == currentPlayer) {
                            allPossibleFriendlyMoves.addAll(
                                    chessBoard.boardArray[i][j].generatePossibleMoves(chessBoard.boardArray, i, j));
                        } else {
                            allPossibleEnemyMoves.addAll(
                                    chessBoard.boardArray[i][j].generatePossibleMoves(chessBoard.boardArray, i, j));
                        }
                    }
                }
            }

            allPossibleMoves.addAll(allPossibleFriendlyMoves);
            allPossibleMoves.addAll(allPossibleEnemyMoves);

            if (isCheckmate(chessBoard, allPossibleEnemyMoves)) {
                gameOver = true;
                break;
            }

            if (currentPlayer == Player.WHITE) {
                if (!moveHistory.isEmpty()) {
                    System.out.println(moveHistory.get(moveHistory.size() - 1));
                } else {
                    System.out.println("Move -> d2d4 etc." + "\nList possible moves -> list"
                            + "\nGet piece info -> info" + "\nUndo last move -> undo" + "\nCastle -> castle");
                }

                System.out.println(currentPlayer.toString() + " to move");
                move = input.nextLine().toLowerCase();

                String piece;
                switch (move) {
                case "list":
                    System.out.println("Location of piece:");
                    piece = input.nextLine();

                    if (!piece.matches("[a-hA-H][1-8]")) {
                        System.out.println("Please enter the location of a piece");
                        break;
                    } else if (piece.equalsIgnoreCase("q") || piece.equalsIgnoreCase("quit")) {
                        break;
                    } else {
                        int r, f; // rank and file

                        r = 8 - Integer.parseInt(piece.substring(1, 2));
                        f = charMap.get(piece.charAt(0));

                        if (chessBoard.boardArray[r][f].getPlayer() == currentPlayer) {
                            listPossibleMoves(chessBoard, piece);
                        } else {
                            System.out.println("Please enter the location of one of your own pieces");
                        }
                    }
                    break;
                case "info":
                    System.out.println("Location of piece: ");
                    piece = input.nextLine();

                    if (!piece.matches("[a-hA-H][1-8]")) {
                        System.out.println("Please enter the location of a piece");
                        break;
                    } else if (piece.equalsIgnoreCase("q") || piece.equalsIgnoreCase("quit")) {
                        break;
                    } else {
                        int r, f; // rank and file

                        r = 8 - Integer.parseInt(piece.substring(1, 2));
                        f = charMap.get(piece.charAt(0));

                        if (chessBoard.boardArray[r][f].getPlayer() == currentPlayer) {
                            getPieceInfo(chessBoard, piece);
                        } else {
                            System.out.println("Please enter the location of one of your own pieces");
                        }
                    }
                    break;
                case "undo":
                    if (!moveHistory.isEmpty()) {
                        undoMove(chessBoard, moveHistory, capturedPieces, promotedPieces);
                        undoMove(chessBoard, moveHistory, capturedPieces, promotedPieces);
                        whiteTurn = !whiteTurn;
                    } else {
                        System.err.println("Error: No move to undo.");
                    }
                    break;
                case "castle":
                    System.out.println("Location of King and Rook: ");
                    String kingAndRook = input.nextLine();

                    if (!kingAndRook.matches("[a-hA-H][1-8][a-hA-H][1-8]")) {
                        System.out.println("Please enter the location of the King and the Rook to castle");
                        break;
                    } else if (kingAndRook.equalsIgnoreCase("q") || kingAndRook.equalsIgnoreCase("quit")) {
                        break;
                    } else {
                        singleplayerParseMove(chessBoard, kingAndRook, whiteTurn, true);
                    }
                    break;
                default:
                    singleplayerParseMove(chessBoard, move, whiteTurn, false);
                    break;
                }
            } else {
                if (!moveHistory.isEmpty()) {
                    System.out.println(moveHistory.get(moveHistory.size() - 1));
                }

                System.out.println(currentPlayer.toString() + " to move");

                Board tempBoard = chessBoard;
                long startTime = System.currentTimeMillis();
                System.out.println(currentPlayer.toString() + " is thinking . . .");
                move = alphaBeta(tempBoard, globalDepth, Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0);
                long endTime = System.currentTimeMillis();
                System.out.println("alpha-beta finishing with score of: " + move.substring(28));
                System.out.println("That took " + (endTime - startTime) + " milliseconds");
                move = move.substring(0, 4);

                Character startFile = reverseCharMap.get(Integer.parseInt(move.substring(1, 2)));
                int startRank = 8 - Integer.parseInt(move.substring(0, 1));
                Character destFile = reverseCharMap.get(Integer.parseInt(move.substring(3, 4)));
                int destRank = 8 - Integer.parseInt(move.substring(2, 3));

                if (move.substring(4).contains("castle")) {
                    move = String.valueOf(startFile)
                            + String.valueOf(startRank + String.valueOf(destFile) + String.valueOf(destRank));
                    singleplayerParseMove(chessBoard, move, whiteTurn, true);
                } else {
                    move = String.valueOf(startFile)
                            + String.valueOf(startRank + String.valueOf(destFile) + String.valueOf(destRank));
                    singleplayerParseMove(chessBoard, move, whiteTurn, false);
                }
            }
        }

        if (gameOver) {
            System.out.println(lastPlayer.toString().toUpperCase() + " WINS, GAME OVER!");
            System.out.println("Play again?");
            String restart = input.nextLine();

            if (restart.equalsIgnoreCase("y") || restart.equalsIgnoreCase("yes")) {
                legalMove = false;
                whiteTurn = true;
                gameOver = false;
                stalemate = false;
                inCheck = false;

                singleplayerLoop(resetBoard, true);
            } else {
                System.out.println("Goodbye!");
                input.close();
                System.exit(0);
            }
        }
    }

    public void singleplayerParseMove(Board chessBoard, String move, boolean turn, boolean castle) {
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
                                castle(chessBoard, startRank, startFile, newRank, newFile);
                                legalMove = true;
                                whiteTurn = !whiteTurn;
                                singleplayerLoop(chessBoard, whiteTurn);
                            } else if (allPossibleMoves.get(i).substring(4).contains("en passant")) {
                                enPassant(chessBoard, startRank, startFile, newRank, newFile);
                                legalMove = true;
                                whiteTurn = !whiteTurn;
                                singleplayerLoop(chessBoard, whiteTurn);
                            } else if (allPossibleMoves.get(i).substring(4).contains("check")) {
                                makeMove(chessBoard, startRank, startFile, newRank, newFile);
                                legalMove = true;
                                whiteTurn = !whiteTurn;
                                singleplayerLoop(chessBoard, whiteTurn);
                            } else if (allPossibleMoves.get(i).substring(4).contains("capture")) {
                                makeMove(chessBoard, startRank, startFile, newRank, newFile);
                                legalMove = true;
                                whiteTurn = !whiteTurn;
                                singleplayerLoop(chessBoard, whiteTurn);
                            } else {
                                makeMove(chessBoard, startRank, startFile, newRank, newFile);
                                legalMove = true;
                                whiteTurn = !whiteTurn;
                                singleplayerLoop(chessBoard, whiteTurn);
                            }
                        }
                    }
                }
            }
        }
    }

    public List<String> listPossibleMoves(Board chessBoard, String piece) {
        int startRank, startFile, destRank;
        char destFile;
        List<String> allMoves = new ArrayList<String>();

        if (piece.length() != 2 && !piece.matches("[a-hA-H][1-8]")) {
            System.err.println("Error: Invalid piece");
        } else {
            startFile = charMap.get(piece.toLowerCase().charAt(0));
            startRank = 8 - Integer.parseInt(piece.substring(1, 2));

            if (chessBoard.boardArray[startRank][startFile] == null) {
                System.err.println("Error: No piece found at " + piece.substring(0, 2));
            } else {
                allMoves = chessBoard.boardArray[startRank][startFile].generatePossibleMoves(chessBoard.boardArray,
                        startRank, startFile);

                System.out.println(piece + " selected\nPossible moves:");

                if (allMoves.isEmpty()) {
                    System.out.println("None!");
                } else {
                    for (String move : allMoves) {
                        destRank = 8 - Integer.parseInt(move.substring(2, 3));
                        destFile = reverseCharMap.get(Integer.parseInt(move.substring(3, 4)));
                        System.out.println(String.valueOf(destFile) + String.valueOf(destRank) + move.substring(4));
                    }
                }
            }
        }
        return allMoves;
    } // listPossibleMoves

    /**
     * Gets info of a specified piece (player owner, piece type etc.)
     * 
     * @param chessBoard
     *            Current game board.
     * @param piece
     *            Piece to get info about.
     */
    public void getPieceInfo(Board chessBoard, String piece) {
        int startRank, startFile;

        if (piece.length() != 2 && !piece.matches("[a-hA-H][1-8]")) {
            System.err.println("Error: Invalid piece");
        } else {
            startFile = charMap.get(piece.toLowerCase().charAt(0));
            startRank = 8 - Integer.parseInt(piece.substring(1, 2));

            if (chessBoard.boardArray[startRank][startFile] == null) {
                System.err.println("Error: No piece found at " + piece.substring(0, 2));
            } else {
                System.out.println(chessBoard.boardArray[startRank][startFile].getPieceInfo());
            }
        }
    } // getPieceInfo

    public boolean isCheck(Board chessBoard, List<String> possibleMoves) {
        if ((chessBoard.boardArray[blackKingR][blackKingF] != null
                && chessBoard.boardArray[blackKingR][blackKingF].getPieceType() != PieceType.KING
                && chessBoard.boardArray[blackKingR][blackKingF].getPlayer() != Player.BLACK)
                && (chessBoard.boardArray[whiteKingR][whiteKingF] != null
                        && chessBoard.boardArray[whiteKingR][whiteKingF].getPieceType() != PieceType.KING
                        && chessBoard.boardArray[whiteKingR][whiteKingF].getPlayer() != Player.WHITE)) { // if no kings
            return false;
        } else if (chessBoard.boardArray[blackKingR][blackKingF] != null
                && chessBoard.boardArray[blackKingR][blackKingF].getPieceType() != PieceType.KING
                && chessBoard.boardArray[blackKingR][blackKingF].getPlayer() != Player.BLACK) { // else if no black king
            return false;
        } else if (chessBoard.boardArray[whiteKingR][whiteKingF] != null
                && chessBoard.boardArray[whiteKingR][whiteKingF].getPieceType() != PieceType.KING
                && chessBoard.boardArray[whiteKingR][whiteKingF].getPlayer() != Player.WHITE) { // else if no white king
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

    public boolean isCheckmate(Board chessBoard, List<String> possibleMoves) {
        List<String> newMoves = new ArrayList<String>();
        for (String m : possibleMoves) {
            if (m.contains("check")) {
                int rank = Integer.parseInt(m.substring(2, 3));
                int file = Integer.parseInt(m.substring(3, 4));

                newMoves = chessBoard.boardArray[rank][file].generatePossibleMoves(chessBoard.boardArray, rank, file);

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

    /**
     * Provides "AI" player with competent moves.
     * 
     * @param depth
     *            Depth of the search tree.
     * @param player
     *            Starting player colour.
     * @param alpha
     *            Alpha score.
     * @param beta
     *            Beta score.
     * @return Move value.
     */
    public String alphaBeta(Board chessBoard, int depth, int beta, int alpha, String move, int playerNum) {
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
                if (chessBoard.boardArray[i][j] != null && chessBoard.boardArray[i][j].getPlayer() == player) {
                    allMoves.addAll(chessBoard.boardArray[i][j].generatePossibleMoves(chessBoard.boardArray, i, j));
                }
            }
        }

        allMoves = sortMoves(chessBoard, allMoves);

        if (depth == 0 || allMoves.isEmpty()) {
            return move + String.valueOf(Rating.rating(chessBoard, allMoves, depth) * (playerNum * 2 - 1));
        }

        playerNum = 1 - playerNum;
        for (String m : allMoves) {
            startRank = Integer.parseInt(m.substring(0, 1));
            startFile = Integer.parseInt(m.substring(1, 2));
            destRank = Integer.parseInt(m.substring(2, 3));
            destFile = Integer.parseInt(m.substring(3, 4));

            makeMove(chessBoard, startRank, startFile, destRank, destFile);

            String returnString = alphaBeta(chessBoard, depth - 1, beta, alpha, m, playerNum);
            value = Integer.parseInt(returnString.substring(28));

            undoMove(chessBoard, moveHistory, capturedPieces, promotedPieces);

            if (playerNum == 0) {
                if (value <= beta) {
                    beta = value;

                    if (depth == globalDepth) {
                        move = returnString.substring(0, 28);
                    }
                }
            } else {
                if (value > alpha) {
                    alpha = value;

                    if (depth == globalDepth) {
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
    public void castle(Board chessBoard, int piece1Rank, int piece1File, int piece2Rank, int piece2File) {
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

        lastMove(chessBoard, kingRank, kingFile, rookRank, rookFile, true, false, false, false);
    } // castle

    /**
     * Captures a pawn piece en passant.
     * 
     * @param pawns
     *            String representation of both Pawn pieces.
     */
    public void enPassant(Board chessBoard, int startRank, int startFile, int destRank, int destFile) {
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
            chessBoard.boardArray[enemyRank + 1][enemyFile].incCaptures();
            enemyRank = enemyRank + 1;
        } else {
            enemyRank = destRank + 1;
            chessBoard.boardArray[enemyRank][enemyFile] = null;
            chessBoard.boardArray[enemyRank - 1][enemyFile] = chessBoard.boardArray[attackerRank][attackerFile];
            chessBoard.boardArray[attackerRank][attackerFile] = null;
            chessBoard.boardArray[enemyRank - 1][enemyFile].setMoved(true);
            chessBoard.boardArray[enemyRank - 1][enemyFile].incCaptures();
            enemyRank = enemyRank - 1;
        }

        lastMove(chessBoard, attackerRank, attackerFile, enemyRank, enemyFile, false, true, false, false);
    } // enPassant

    /**
     * Promotes a Pawn to a specified piece through user input.
     * 
     * @param chessBoard
     *            Board to promote Pawn on.
     * @param rank
     *            Rank of Pawn.
     * @param file
     *            File of Pawn.
     */
    public void promotePawn(Board chessBoard, int rank, int file) {
        Piece pawn = chessBoard.boardArray[rank][file];

        List<String> queenMoves = new ArrayList<String>();
        List<String> knightMoves = new ArrayList<String>();
        List<String> rookMoves = new ArrayList<String>();
        List<String> bishopMoves = new ArrayList<String>();

        List<String> possiblePieces = new ArrayList<String>();
        possiblePieces.add("queen");
        possiblePieces.add("knight");
        possiblePieces.add("rook");
        possiblePieces.add("bishop");

        int captures = chessBoard.boardArray[rank][file].getCaptures();
        Player player = chessBoard.boardArray[rank][file].getPlayer();
        String piece;

        boolean acceptInput = false;

        chessBoard.boardArray[rank][file] = null;
        chessBoard.boardArray[rank][file] = new Queen(player, rank, file);
        queenMoves = chessBoard.boardArray[rank][file].generatePossibleMoves(chessBoard.boardArray, rank, file);
        chessBoard.boardArray[rank][file] = pawn;

        chessBoard.boardArray[rank][file] = null;
        chessBoard.boardArray[rank][file] = new Knight(player, rank, file);
        knightMoves = chessBoard.boardArray[rank][file].generatePossibleMoves(chessBoard.boardArray, rank, file);
        chessBoard.boardArray[rank][file] = pawn;

        chessBoard.boardArray[rank][file] = null;
        chessBoard.boardArray[rank][file] = new Rook(player, rank, file);
        rookMoves = chessBoard.boardArray[rank][file].generatePossibleMoves(chessBoard.boardArray, rank, file);
        chessBoard.boardArray[rank][file] = pawn;

        chessBoard.boardArray[rank][file] = null;
        chessBoard.boardArray[rank][file] = new Bishop(player, rank, file);
        bishopMoves = chessBoard.boardArray[rank][file].generatePossibleMoves(chessBoard.boardArray, rank, file);
        chessBoard.boardArray[rank][file] = pawn;

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
                        promotedPieces.add(chessBoard.boardArray[rank][file]);
                        chessBoard.boardArray[rank][file] = null;
                        chessBoard.boardArray[rank][file] = new Queen(player, rank, file);
                        chessBoard.boardArray[rank][file].setCaptures(captures);
                        acceptInput = true;
                    } else {
                        System.err.println("Error: No legal Queen moves can be made");
                    }
                } else if (piece.equalsIgnoreCase("k") || piece.equalsIgnoreCase("knight")) {
                    if (possiblePieces.contains("knight")) {
                        promotedPieces.add(chessBoard.boardArray[rank][file]);
                        chessBoard.boardArray[rank][file] = null;
                        chessBoard.boardArray[rank][file] = new Knight(player, rank, file);
                        chessBoard.boardArray[rank][file].setCaptures(captures);
                        acceptInput = true;
                    } else {
                        System.err.println("Error: No legal Knight moves can be made");
                    }
                } else if (piece.equalsIgnoreCase("r") || piece.equalsIgnoreCase("rook")) {
                    if (possiblePieces.contains("rook")) {
                        promotedPieces.add(chessBoard.boardArray[rank][file]);
                        chessBoard.boardArray[rank][file] = null;
                        chessBoard.boardArray[rank][file] = new Rook(player, rank, file);
                        chessBoard.boardArray[rank][file].setCaptures(captures);
                        acceptInput = true;
                    } else {
                        System.err.println("Error: No legal Rook moves can be made");
                    }
                } else if (piece.equalsIgnoreCase("b") || piece.equalsIgnoreCase("bishop")) {
                    if (possiblePieces.contains("bishop")) {
                        promotedPieces.add(chessBoard.boardArray[rank][file]);
                        chessBoard.boardArray[rank][file] = null;
                        chessBoard.boardArray[rank][file] = new Bishop(player, rank, file);
                        chessBoard.boardArray[rank][file].setCaptures(captures);
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
                promotedPieces.add(chessBoard.boardArray[rank][file]);
                chessBoard.boardArray[rank][file] = null;
                chessBoard.boardArray[rank][file] = new Queen(player, rank, file);
                chessBoard.boardArray[rank][file].setCaptures(captures);
            } else if (possiblePieces.contains("knight")) {
                promotedPieces.add(chessBoard.boardArray[rank][file]);
                chessBoard.boardArray[rank][file] = null;
                chessBoard.boardArray[rank][file] = new Knight(player, rank, file);
                chessBoard.boardArray[rank][file].setCaptures(captures);
            } else if (possiblePieces.contains("rook")) {
                promotedPieces.add(chessBoard.boardArray[rank][file]);
                chessBoard.boardArray[rank][file] = null;
                chessBoard.boardArray[rank][file] = new Rook(player, rank, file);
                chessBoard.boardArray[rank][file].setCaptures(captures);
            } else if (possiblePieces.contains("bishop")) {
                promotedPieces.add(chessBoard.boardArray[rank][file]);
                chessBoard.boardArray[rank][file] = null;
                chessBoard.boardArray[rank][file] = new Bishop(player, rank, file);
                chessBoard.boardArray[rank][file].setCaptures(captures);
            }
        }
    } // promotePawn

    /**
     * Moves a piece on the game board.
     * 
     * @param chessBoard
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
    public void makeMove(Board chessBoard, int startRank, int startFile, int destRank, int destFile) {
        String player = chessBoard.boardArray[startRank][startFile].getPlayer().toString();

        if (chessBoard.boardArray[destRank][destFile] != null) {
            if (chessBoard.boardArray[startRank][startFile].getPieceType() == PieceType.PAWN
                    && ((player.equalsIgnoreCase(Player.BLACK.toString()) && destRank == 7)
                            || (player.equalsIgnoreCase(Player.WHITE.toString()) && destRank == 0))) {
                capturedPieces.add(chessBoard.boardArray[destRank][destFile]);
                chessBoard.boardArray[destRank][destFile] = null;
                chessBoard.boardArray[destRank][destFile] = chessBoard.boardArray[startRank][startFile];
                chessBoard.boardArray[startRank][startFile] = null;
                chessBoard.boardArray[destRank][destFile].setMoved(true);
                chessBoard.boardArray[destRank][destFile].incCaptures();

                promotePawn(chessBoard, destRank, destFile);
                lastMove(chessBoard, startRank, startFile, destRank, destFile, false, false, true, true);
            } else {
                capturedPieces.add(chessBoard.boardArray[destRank][destFile]);
                chessBoard.boardArray[destRank][destFile] = null;
                chessBoard.boardArray[destRank][destFile] = chessBoard.boardArray[startRank][startFile];
                chessBoard.boardArray[startRank][startFile] = null;
                chessBoard.boardArray[destRank][destFile].setMoved(true);
                chessBoard.boardArray[destRank][destFile].incCaptures();

                lastMove(chessBoard, startRank, startFile, destRank, destFile, false, false, true, false);
            }
        } else if (chessBoard.boardArray[destRank][destFile] == null) {
            if (chessBoard.boardArray[startRank][startFile].getPieceType() == PieceType.PAWN
                    && ((player.equalsIgnoreCase(Player.BLACK.toString()) && destRank == 7)
                            || (player.equalsIgnoreCase(Player.WHITE.toString()) && destRank == 0))) {
                capturedPieces.add(chessBoard.boardArray[destRank][destFile]);
                chessBoard.boardArray[destRank][destFile] = null;
                chessBoard.boardArray[destRank][destFile] = chessBoard.boardArray[startRank][startFile];
                chessBoard.boardArray[startRank][startFile] = null;
                chessBoard.boardArray[destRank][destFile].setMoved(true);

                promotePawn(chessBoard, destRank, destFile);
                lastMove(chessBoard, startRank, startFile, destRank, destFile, false, false, false, true);
            } else {
                chessBoard.boardArray[destRank][destFile] = chessBoard.boardArray[startRank][startFile];
                chessBoard.boardArray[startRank][startFile] = null;
                chessBoard.boardArray[destRank][destFile].setMoved(true);

                lastMove(chessBoard, startRank, startFile, destRank, destFile, false, false, false, false);
            }
        }

        // if (chessBoard.boardArray[destRank][destFile].getPieceType() ==
        // PieceType.PAWN
        // && (chessBoard.boardArray[destRank][destFile].getPlayer() == Player.BLACK
        // && destRank == 7)
        // || (chessBoard.boardArray[destRank][destFile].getPlayer() == Player.WHITE
        // && destRank == 0)) {
        //
        // }
    } // movePiece

    /**
     * Undo the previous move.
     * 
     * @param chessBoard
     *            Board to undo the move on.
     * @param history
     *            History of moves.
     * @param captured
     *            History of captured pieces.
     */
    public void undoMove(Board chessBoard, List<String> history, List<Piece> captured, List<Piece> promoted) {
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
        chessBoard.boardArray[startRank][startFile] = chessBoard.boardArray[destRank][destFile];

        // resets the moved flag for the piece if it returns to its home
        // co-ordinates
        String start = String.valueOf(startRank) + String.valueOf(startFile);
        if (chessBoard.boardArray[startRank][startFile].getHome().equals(start)) {
            chessBoard.boardArray[startRank][startFile].setMoved(false);
            if (chessBoard.boardArray[startRank][startFile].getPieceType() == PieceType.PAWN) {
                chessBoard.boardArray[startRank][startFile].setMovedTwo(false);
            }
        }

        chessBoard.boardArray[destRank][destFile] = null;

        // remove the move from the history
        history.remove(history.size() - 1);

        // restore captured piece and remove the piece from the history
        if (capturedPiece != null && promotedPiece != null) { // if capture and promotion
            chessBoard.boardArray[startRank][startFile] = promotedPiece;
            promoted.remove(promoted.size() - 1);
            chessBoard.boardArray[startRank][startFile].decCaptures();

            chessBoard.boardArray[destRank][destFile] = capturedPiece;
        } else if (capturedPiece != null) { // else if capture
            chessBoard.boardArray[destRank][destFile] = capturedPiece;
            captured.remove(captured.size() - 1);
            chessBoard.boardArray[startRank][startFile].decCaptures();
        } else if (promotedPiece != null) { // else if promotion
            chessBoard.boardArray[startRank][startFile] = promotedPiece;
            promoted.remove(promoted.size() - 1);
        }

        // update variables
        if (!history.isEmpty()) {
            move = history.get(history.size() - 1);

            startRank = 8 - Integer.parseInt(move.substring(1, 2));
            startFile = charMap.get(move.charAt(0));
            destRank = 8 - Integer.parseInt(move.substring(3, 4));
            destFile = charMap.get(move.charAt(2));

            lastPlayer = chessBoard.boardArray[destRank][destFile].getPlayer();
            lastPieceOldRank = startRank;
            lastPieceOldFile = startFile;
            lastPieceNewRank = destRank;
            lastPieceNewFile = destFile;
            lastPiece = chessBoard.boardArray[destRank][destFile].getPieceType();
        } else {
            lastPlayer = null;
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

    public List<String> sortMoves(Board chessBoard, List<String> moves) {
        int startRank, startFile, destRank, destFile;
        List<String> bestMoves = new ArrayList<String>();
        List<String> sortedMoves = new ArrayList<String>();

        int[] score = new int[moves.size()];

        for (int i = 0; i < moves.size(); i++) {
            startRank = Integer.parseInt(moves.get(i).substring(0, 1));
            startFile = Integer.parseInt(moves.get(i).substring(1, 2));
            destRank = Integer.parseInt(moves.get(i).substring(2, 3));
            destFile = Integer.parseInt(moves.get(i).substring(3, 4));

            makeMove(chessBoard, startRank, startFile, destRank, destFile);
            score[i] = -Rating.rating(chessBoard, new ArrayList<String>(), 0);
            undoMove(chessBoard, moveHistory, capturedPieces, promotedPieces);
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

    /**
     * Update variables with last move completed.
     * 
     * @param chessBoard
     *            Board the move took place on.
     * @param lastMove
     *            String representation of previous move.
     */
    public void lastMove(Board chessBoard, int startRank, int startFile, int destRank, int destFile, boolean castle,
            boolean enPassant, boolean capture, boolean promotion) {
        String player, oldRank, newRank, piece;
        Character oldFile, newFile;

        if (chessBoard.boardArray[destRank][destFile].getPlayer() == Player.WHITE) {
            lastPlayer = Player.WHITE;
        } else {
            lastPlayer = Player.BLACK;
        }

        lastPieceOldRank = startRank;
        lastPieceOldFile = startFile;
        lastPieceNewRank = destRank;
        lastPieceNewFile = destFile;

        lastPiece = chessBoard.boardArray[lastPieceNewRank][lastPieceNewFile].getPieceType();

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
     * @param chessBoard
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
    public boolean isLegalMove(Board chessBoard, int startRank, int startFile, int destRank, int destFile) {
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