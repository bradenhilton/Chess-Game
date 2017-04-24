package pieces;

import java.util.ArrayList;
import java.util.List;

import game.Player;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class Rook extends Piece {
    protected PieceType m_type;
    protected String home;

    public Rook(Player player, int rank, int file) {
        super(player, rank, file);
        m_type = PieceType.ROOK;
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

        up: {
            for (int tempRank = startRank - 1; tempRank >= 0; tempRank--) {
                if (boardArray[tempRank][startFile] == null) {
                    move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                            + String.valueOf(startFile);
                    move = String.format("%-28s", move);
                    possibleMoves.add(move);
                } else {
                    if (boardArray[tempRank][startFile].m_player != boardArray[startRank][startFile].m_player) {
                        if (boardArray[tempRank][startFile].getPieceType() == PieceType.KING) {
                            move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                                    + String.valueOf(startFile) + " check";
                            move = String.format("%-28s", move);
                            possibleMoves.add(move);

                            break up;
                        } else {
                            move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                                    + String.valueOf(startFile) + " capture "
                                    + boardArray[tempRank][startFile].getPieceType().toString();
                            move = String.format("%-28s", move);
                            possibleMoves.add(move);

                            break up;
                        }
                    } else {
                        break up;
                    }
                }
            }
        }
        down: {
            for (int tempRank = startRank + 1; tempRank <= 7; tempRank++) {
                if (boardArray[tempRank][startFile] == null) {
                    move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                            + String.valueOf(startFile);
                    move = String.format("%-28s", move);
                    possibleMoves.add(move);
                } else {
                    if (boardArray[tempRank][startFile].m_player != boardArray[startRank][startFile].m_player) {
                        if (boardArray[tempRank][startFile].getPieceType() == PieceType.KING) {
                            move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                                    + String.valueOf(startFile) + " check";
                            move = String.format("%-28s", move);
                            possibleMoves.add(move);

                            break down;
                        } else {
                            move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                                    + String.valueOf(startFile) + " capture "
                                    + boardArray[tempRank][startFile].getPieceType().toString();
                            move = String.format("%-28s", move);
                            possibleMoves.add(move);

                            break down;
                        }
                    } else {
                        break down;
                    }
                }
            }
        }
        left: {
            for (int tempFile = startFile - 1; tempFile >= 0; tempFile--) {
                if (boardArray[startRank][tempFile] == null) {
                    move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank)
                            + String.valueOf(tempFile);
                    move = String.format("%-28s", move);
                    possibleMoves.add(move);
                } else {
                    if (boardArray[startRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                        if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
                            move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank)
                                    + String.valueOf(tempFile) + " check";
                            move = String.format("%-28s", move);
                            possibleMoves.add(move);

                            break left;
                        } else {
                            move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank)
                                    + String.valueOf(tempFile) + " capture "
                                    + boardArray[startRank][tempFile].getPieceType().toString();
                            move = String.format("%-28s", move);
                            possibleMoves.add(move);

                            break left;
                        }
                    } else {
                        break left;
                    }
                }
            }
        }
        right: {
            for (int tempFile = startFile + 1; tempFile <= 7; tempFile++) {
                if (boardArray[startRank][tempFile] == null) {
                    move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank)
                            + String.valueOf(tempFile);
                    move = String.format("%-28s", move);
                    possibleMoves.add(move);
                } else {
                    if (boardArray[startRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                        if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
                            move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank)
                                    + String.valueOf(tempFile) + " check";
                            move = String.format("%-28s", move);
                            possibleMoves.add(move);

                            break right;
                        } else {
                            move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank)
                                    + String.valueOf(tempFile) + " capture "
                                    + boardArray[startRank][tempFile].getPieceType().toString();
                            move = String.format("%-28s", move);
                            possibleMoves.add(move);

                            break right;
                        }
                    } else {
                        break right;
                    }
                }
            }
        }

        return possibleMoves;
    }
}