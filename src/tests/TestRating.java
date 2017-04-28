package tests;

import java.util.ArrayList;
import java.util.List;

import game.Player;
import pieces.King;
import pieces.Piece;
import pieces.PieceType;

// attribute to https://chessprogramming.wikispaces.com/Simplified+evaluation+function
// and Logic Crazy Chess https://www.youtube.com/channel/UCmMjMHTeUEBJJZhxix-N-yg
public class TestRating {
    static TestBoard board;
    static List<String> moves = new ArrayList<String>();
    static Player player;
    static int depth, whiteKingR, whiteKingF, blackKingR, blackKingF;

    static int whitePawnBoard[][] = { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 50, 50, 50, 50, 50, 50, 50, 50 },
            { 10, 10, 20, 30, 30, 20, 10, 10 }, { 5, 5, 10, 25, 25, 10, 5, 5 }, { 0, 0, 0, 20, 20, 0, 0, 0 },
            { 5, -5, -10, 0, 0, -10, -5, 5 }, { 5, 10, 10, -20, -20, 10, 10, 5 }, { 0, 0, 0, 0, 0, 0, 0, 0 } };
    static int whiteRookBoard[][] = { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 5, 10, 10, 10, 10, 10, 10, 5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 }, { -5, 0, 0, 0, 0, 0, 0, -5 }, { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 }, { -5, 0, 0, 0, 0, 0, 0, -5 }, { 0, 0, 0, 5, 5, 0, 0, 0 } };
    static int whiteKnightBoard[][] = { { -50, -40, -30, -30, -30, -30, -40, -50 }, { -40, -20, 0, 0, 0, 0, -20, -40 },
            { -30, 0, 10, 15, 15, 10, 0, -30 }, { -30, 5, 15, 20, 20, 15, 5, -30 }, { -30, 0, 15, 20, 20, 15, 0, -30 },
            { -30, 5, 10, 15, 15, 10, 5, -30 }, { -40, -20, 0, 5, 5, 0, -20, -40 },
            { -50, -40, -30, -30, -30, -30, -40, -50 } };
    static int whiteBishopBoard[][] = { { -20, -10, -10, -10, -10, -10, -10, -20 }, { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -10, 0, 5, 10, 10, 5, 0, -10 }, { -10, 5, 5, 10, 10, 5, 5, -10 }, { -10, 0, 10, 10, 10, 10, 0, -10 },
            { -10, 10, 10, 10, 10, 10, 10, -10 }, { -10, 5, 0, 0, 0, 0, 5, -10 },
            { -20, -10, -10, -10, -10, -10, -10, -20 } };
    static int whiteQueenBoard[][] = { { -20, -10, -10, -5, -5, -10, -10, -20 }, { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -10, 0, 5, 5, 5, 5, 0, -10 }, { -5, 0, 5, 5, 5, 5, 0, -5 }, { 0, 0, 5, 5, 5, 5, 0, -5 },
            { -10, 5, 5, 5, 5, 5, 0, -10 }, { -10, 0, 5, 0, 0, 0, 0, -10 }, { -20, -10, -10, -5, -5, -10, -10, -20 } };
    static int whiteKingMidBoard[][] = { { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 }, { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 }, { -20, -30, -30, -40, -40, -30, -30, -20 },
            { -10, -20, -20, -20, -20, -20, -20, -10 }, { 20, 20, 0, 0, 0, 0, 20, 20 },
            { 20, 30, 10, 0, 0, 10, 30, 20 } };
    static int whiteKingEndBoard[][] = { { -50, -40, -30, -20, -20, -30, -40, -50 },
            { -30, -20, -10, 0, 0, -10, -20, -30 }, { -30, -10, 20, 30, 30, 20, -10, -30 },
            { -30, -10, 30, 40, 40, 30, -10, -30 }, { -30, -10, 30, 40, 40, 30, -10, -30 },
            { -30, -10, 20, 30, 30, 20, -10, -30 }, { -30, -30, 0, 0, 0, 0, -30, -30 },
            { -50, -30, -30, -30, -30, -30, -30, -50 } };

    static int blackPawnBoard[][] = { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 5, 10, 10, -20, -20, 10, 10, 5 },
            { 5, -5, -10, 0, 0, -10, -5, 5 }, { 0, 0, 0, 20, 20, 0, 0, 0 }, { 5, 5, 10, 25, 25, 10, 5, 5 },
            { 10, 10, 20, 30, 30, 20, 10, 10 }, { 50, 50, 50, 50, 50, 50, 50, 50 }, { 0, 0, 0, 0, 0, 0, 0, 0 } };
    static int blackRookBoard[][] = { { 0, 0, 0, 5, 5, 0, 0, 0 }, { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 }, { -5, 0, 0, 0, 0, 0, 0, -5 }, { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 }, { 5, 10, 10, 10, 10, 10, 10, 5 }, { 0, 0, 0, 0, 0, 0, 0, 0 } };
    static int blackKnightBoard[][] = { { -50, -40, -30, -30, -30, -30, -40, -50 }, { -40, -20, 0, 5, 5, 0, -20, -40 },
            { -30, 5, 10, 15, 15, 10, 5, -30 }, { -30, 0, 15, 20, 20, 15, 0, -30 }, { -30, 5, 15, 20, 20, 15, 5, -30 },
            { -30, 0, 10, 15, 15, 10, 0, -30 }, { -40, -20, 0, 0, 0, 0, -20, -40 },
            { -50, -40, -30, -30, -30, -30, -40, -50 } };
    static int blackBishopBoard[][] = { { -20, -10, -10, -10, -10, -10, -10, -20 }, { -10, 5, 0, 0, 0, 0, 5, -10 },
            { -10, 10, 10, 10, 10, 10, 10, -10 }, { -10, 0, 10, 10, 10, 10, 0, -10 }, { -10, 5, 5, 10, 10, 5, 5, -10 },
            { -10, 0, 5, 10, 10, 5, 0, -10 }, { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -20, -10, -10, -10, -10, -10, -10, -20 } };
    static int blackQueenBoard[][] = { { -20, -10, -10, -5, -5, -10, -10, -20 }, { -10, 0, 5, 0, 0, 0, 0, -10 },
            { -10, 5, 5, 5, 5, 5, 0, -10 }, { 0, 0, 5, 5, 5, 5, 0, -5 }, { -5, 0, 5, 5, 5, 5, 0, -5 },
            { -10, 0, 5, 5, 5, 5, 0, -10 }, { -10, 0, 0, 0, 0, 0, 0, -10 }, { -20, -10, -10, -5, -5, -10, -10, -20 } };
    static int blackKingMidBoard[][] = { { 20, 30, 10, 0, 0, 10, 30, 20 }, { 20, 20, 0, 0, 0, 0, 20, 20 },
            { -10, -20, -20, -20, -20, -20, -20, -10 }, { -20, -30, -30, -40, -40, -30, -30, -20 },
            { -30, -40, -40, -50, -50, -40, -40, -30 }, { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 }, { -30, -40, -40, -50, -50, -40, -40, -30 } };
    static int blackKingEndBoard[][] = { { -50, -30, -30, -30, -30, -30, -30, -50 }, { -30, -30, 0, 0, 0, 0, -30, -30 },
            { -30, -10, 20, 30, 30, 20, -10, -30 }, { -30, -10, 30, 40, 40, 30, -10, -30 },
            { -30, -10, 30, 40, 40, 30, -10, -30 }, { -30, -10, 20, 30, 30, 20, -10, -30 },
            { -30, -20, -10, 0, 0, -10, -20, -30 }, { -50, -40, -30, -20, -20, -30, -40, -50 } };

    public static int rating(TestBoard tBoard, List<String> allMoves, int gameDepth) {
        board = tBoard;
        moves = allMoves;
        depth = gameDepth;

        int white = 0;
        int black = 0;
        int total = 0;

        player = Player.WHITE;
        white += attackRating(player);
        white += materialRating(player);
        white += moveabilityRating(player);
        white += positionalRating(player, materialRating(player));

        player = Player.BLACK;
        black += attackRating(player);
        black += materialRating(player);
        black += moveabilityRating(player);
        black += positionalRating(player, materialRating(player));

        total = white - black;
        return -(total + (depth * 50));
    }

    public static int attackRating(Player player) {
        int total = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.boardArray[i][j] != null && board.boardArray[i][j].getPlayer() == player) {
                    Piece piece = board.boardArray[i][j]; // assign piece

                    if (piece.getPieceType() == PieceType.PAWN) {
                        board.boardArray[i][j] = new King(player, i, j);

                        if (isCheck()) {
                            total -= 64;
                        }

                        board.boardArray[i][j] = piece;
                    } else if (piece.getPieceType() == PieceType.BISHOP) {
                        board.boardArray[i][j] = new King(player, i, j);

                        if (isCheck()) {
                            total -= 300;
                        }

                        board.boardArray[i][j] = piece;
                    } else if (piece.getPieceType() == PieceType.KNIGHT) {
                        board.boardArray[i][j] = new King(player, i, j);

                        if (isCheck()) {
                            total -= 300;
                        }

                        board.boardArray[i][j] = piece;
                    } else if (piece.getPieceType() == PieceType.ROOK) {
                        board.boardArray[i][j] = new King(player, i, j);

                        if (isCheck()) {
                            total -= 500;
                        }

                        board.boardArray[i][j] = piece;
                    } else if (piece.getPieceType() == PieceType.QUEEN) {
                        board.boardArray[i][j] = new King(player, i, j);

                        if (isCheck()) {
                            total -= 900;
                        }

                        board.boardArray[i][j] = piece;
                    }
                }
            }
        }

        if (isCheck()) {
            total -= 200;
        }

        return total / 2;
    }

    public static int materialRating(Player player) {
        int total = 0;
        int bishops = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.boardArray[i][j] != null && board.boardArray[i][j].getPlayer() == player) {
                    Piece piece = board.boardArray[i][j]; // assign piece

                    if (piece.getPieceType() == PieceType.PAWN) {
                        total += 100;
                    } else if (piece.getPieceType() == PieceType.BISHOP) {
                        bishops += 1;
                    } else if (piece.getPieceType() == PieceType.KNIGHT) {
                        total += 300;
                    } else if (piece.getPieceType() == PieceType.ROOK) {
                        total += 500;
                    } else if (piece.getPieceType() == PieceType.QUEEN) {
                        total += 900;
                    }

                    if (bishops >= 2) { // greater than or equal to 2 to handle pawn promotions
                        total += (300 * bishops);
                    } else if (bishops == 1) { // penalty for only one bishop
                        total += 250;
                    }
                }
            }
        }

        return total;
    }

    public static int moveabilityRating(Player player) {
        int total = 0;

        total += moves.size();

        if (moves.size() == 0) {
            if (isCheckmate()) {
                total -= 200000 * depth;
            } else {
                total -= 150000 * depth;
            }
        }

        return total;
    }

    public static int positionalRating(Player player, int material) {
        int total = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.boardArray[i][j] != null) {
                    Piece piece = board.boardArray[i][j];
                    if (piece.getPlayer() == player) {
                        if (piece.getPieceType() == PieceType.PAWN) {
                            if (player == Player.WHITE) {
                                total += whitePawnBoard[i][j];
                            } else {
                                total += blackPawnBoard[i][j];
                            }
                        } else if (piece.getPieceType() == PieceType.ROOK) {
                            if (player == Player.WHITE) {
                                total += whiteRookBoard[i][j];
                            } else {
                                total += blackRookBoard[i][j];
                            }
                        } else if (piece.getPieceType() == PieceType.KNIGHT) {
                            if (player == Player.WHITE) {
                                total += whiteKnightBoard[i][j];
                            } else {
                                total += blackKnightBoard[i][j];
                            }
                        } else if (piece.getPieceType() == PieceType.BISHOP) {
                            if (player == Player.WHITE) {
                                total += whiteBishopBoard[i][j];
                            } else {
                                total += blackBishopBoard[i][j];
                            }
                        } else if (piece.getPieceType() == PieceType.QUEEN) {
                            if (player == Player.WHITE) {
                                total += whiteQueenBoard[i][j];
                            } else {
                                total += blackQueenBoard[i][j];
                            }
                        } else if (piece.getPieceType() == PieceType.KING) {
                            if (material >= 1750) {
                                if (player == Player.WHITE) {
                                    total += whiteKingMidBoard[i][j];
                                } else {
                                    total += blackKingMidBoard[i][j];
                                }
                                total += piece.generatePossibleMoves(board.boardArray, i, j).size() * 10;
                            } else {
                                if (player == Player.WHITE) {
                                    total += whiteKingMidBoard[i][j];
                                } else {
                                    total += blackKingMidBoard[i][j];
                                }
                                total += piece.generatePossibleMoves(board.boardArray, i, j).size() * 30;
                            }
                        }
                    }
                }
            }
        }

        return total;
    }

    public static boolean isCheck() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.boardArray[i][j] != null && board.boardArray[i][j].getPieceType() == PieceType.KING) {
                    if (board.boardArray[i][j].getPlayer() == Player.WHITE) {
                        whiteKingR = i;
                        whiteKingF = j;
                    } else {
                        blackKingR = i;
                        blackKingF = j;
                    }
                }
            }
        }

        String whiteKing = String.valueOf(whiteKingR) + String.valueOf(whiteKingF);
        String blackKing = String.valueOf(blackKingR) + String.valueOf(blackKingF);

        for (String move : moves) {
            if (move.substring(2, 4).matches(whiteKing)) {
                return true;
            } else if (move.substring(2, 4).matches(blackKing)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isCheckmate() {
        if (isCheck()) {
            if (moves.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
