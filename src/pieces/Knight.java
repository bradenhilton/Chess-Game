package pieces;

import java.util.ArrayList;
import java.util.List;

import game.Player;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class Knight extends Piece {
    protected PieceType m_type;
    protected String home;

    public Knight(Player player, int rank, int file) {
        super(player, rank, file);
        m_type = PieceType.KNIGHT;
        home = String.valueOf(rank) + String.valueOf(file);
    }

    public PieceType getPieceType() {
        return m_type;
    }

    @Override
    public String getPieceInfo() {
        return "Piece type: " + m_type.toString() + "\nPlayer: " + m_player.toString() + "\nHome: " + home.charAt(0)
                + ", " + home.charAt(1) + "\nHas moved: " + hasMoved + "\nCaptures: " + captures;
    }

    @Override
    public String toString() {
        return m_player.toString().toLowerCase().substring(0, 1) + m_type.toString().substring(0, 2);
    }

    @Override
    public List<String> generatePossibleMoves(Piece[][] boardArray, int startRank, int startFile) {
        // negative (-) indicates up or left
        // positive (+) indicates down or right

        String move;

        List<String> possibleMoves = new ArrayList<String>();

        int[][] offsets = { { -2, 1 }, { -1, 2 }, { 1, 2 }, { 2, 1 }, { 2, -1 }, { 1, -2 }, { -1, -2 }, { -2, -1 } };

        for (int[] o : offsets) {
            int tempRank = startRank + o[0];
            int tempFile = startFile + o[1];

            if ((tempRank >= 0 && tempRank <= 7) && (tempFile >= 0 && tempFile <= 7)) {
                if (boardArray[tempRank][tempFile] == null) {
                    move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                            + String.valueOf(tempFile);
                    move = String.format("%-28s", move);
                    possibleMoves.add(move);
                } else if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                    if (boardArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
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
            }
        }

        // Moves moves = new Moves();
        // possibleMoves = moves.set(m_type, boardArray, startRank, startFile);

        return possibleMoves;
    }
}