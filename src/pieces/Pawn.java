package pieces;

import java.util.ArrayList;
import java.util.List;

import game.Player;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class Pawn extends Piece {
    protected PieceType m_type;
    protected String home;

    public Pawn(Player player, int rank, int file) {
        super(player, rank, file);
        m_type = PieceType.PAWN;
        home = String.valueOf(rank) + String.valueOf(file);
    }

    public PieceType getPieceType() {
        return m_type;
    }

    @Override
    public String getPieceInfo() {
        return "Piece type: " + m_type.toString() + "\nPlayer: " + m_player.toString() + "\nHome: " + home.charAt(0)
                + ", " + home.charAt(1) + "\nHas moved: " + hasMoved + "\nHas moved two: " + hasMovedTwo
                + "\nCaptures: " + captures;
    }

    @Override
    public String toString() {
        return m_player.toString().toLowerCase().substring(0, 1) + m_type.toString().substring(0, 2);
    }

    @Override
    public List<String> generatePossibleMoves(Piece[][] boardArray, int startRank, int startFile) {
        // negative (-) indicates up or left
        // positive (+) indicates down or right

        Piece piece1 = boardArray[startRank][startFile];
        Piece piece2;
        String move;

        List<String> possibleMoves = new ArrayList<String>();

        int[][] offsets = null;

        if (piece1.m_player == Player.BLACK) {
            if (!piece1.hasMoved) {
                int[][] o = { { 1, 0 }, { 2, 0 }, { 1, -1 }, { 1, 1 }, { 0, -1 }, { 0, 1 } };
                offsets = o;
            } else {
                int[][] o = { { 1, 0 }, { 1, -1 }, { 1, 1 }, { 0, -1 }, { 0, 1 } };
                offsets = o;
            }
        } else if (piece1.m_player == Player.WHITE) {
            if (!piece1.hasMoved) {
                int[][] o = { { -1, 0 }, { -2, 0 }, { -1, -1 }, { -1, 1 }, { 0, -1 }, { 0, 1 } };
                offsets = o;
            } else {
                int[][] o = { { -1, 0 }, { -1, -1 }, { -1, 1 }, { 0, -1 }, { 0, 1 } };
                offsets = o;
            }
        }

        for (int[] o : offsets) {
            int tempRank = startRank + o[0];
            int tempFile = startFile + o[1];

            if ((tempRank >= 0 && tempRank <= 7) && (tempFile >= 0 && tempFile <= 7)) {
                piece2 = boardArray[tempRank][tempFile];
                if (tempFile != startFile) {
                    if (tempRank != startRank) {
                        if ((piece2 != null) && (piece2.m_player != piece1.m_player)) {
                            if (piece2.getPieceType() == PieceType.KING) {
                                move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                                        + String.valueOf(tempFile) + " check";
                                move = String.format("%-28s", move);
                                possibleMoves.add(move);
                            } else {
                                move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                                        + String.valueOf(tempFile) + " capture "
                                        + boardArray[tempRank][tempFile].getPieceType();
                                move = String.format("%-28s", move);
                                possibleMoves.add(move);
                            }
                        }
                    } else {
                        if (piece2 != null && piece2.m_player != piece1.m_player
                                && piece2.getPieceType() == PieceType.PAWN && piece2.hasMovedTwo == true) {
                            if (boardArray[startRank][startFile].m_player == Player.BLACK) {
                                move = String.valueOf(startRank) + String.valueOf(startFile)
                                        + String.valueOf(tempRank + 1) + String.valueOf(tempFile) + " capture "
                                        + boardArray[tempRank][tempFile].getPieceType() + " en passant";
                                move = String.format("%-28s", move);
                                possibleMoves.add(move);
                            } else {
                                move = String.valueOf(startRank) + String.valueOf(startFile)
                                        + String.valueOf(tempRank - 1) + String.valueOf(tempFile) + " capture "
                                        + boardArray[tempRank][tempFile].getPieceType() + " en passant";
                                move = String.format("%-28s", move);
                                possibleMoves.add(move);
                            }
                        }
                    }
                } else {
                    if (piece2 == null) {
                        move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                                + String.valueOf(tempFile);
                        move = String.format("%-28s", move);
                        possibleMoves.add(move);
                    } else {
                        break;
                    }
                }
            }
        }

        return possibleMoves;
    }
}