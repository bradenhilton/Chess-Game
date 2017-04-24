package pieces;

import java.util.ArrayList;
import java.util.List;

import game.Player;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class Bishop extends Piece {
    protected PieceType m_type;
    protected String home;

    public Bishop(Player player, int rank, int file) {
        super(player, rank, file);
        m_type = PieceType.BISHOP;
        home = String.valueOf(rank) + String.valueOf(file);
    }

    public PieceType getPieceType() {
        return m_type;
    }

    public String getHome() {
        return home;
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

        upLeft: {
            for (int tempRank = startRank - 1; tempRank >= 0; tempRank--) {
                for (int tempFile = startFile - 1; tempFile >= 0; tempFile--) {
                    if (Math.abs(tempRank - startRank) == Math.abs(tempFile - startFile)) {
                        if (boardArray[tempRank][tempFile] == null) {
                            move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                                    + String.valueOf(tempFile);
                            move = String.format("%-28s", move);
                            possibleMoves.add(move);
                        } else {
                            if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                                if (boardArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
                                    move = String.valueOf(String.valueOf(startRank) + String.valueOf(startFile)
                                            + String.valueOf(tempRank) + String.valueOf(tempFile) + " check");
                                    move = String.format("%-28s", move);
                                    possibleMoves.add(move);

                                    break upLeft;
                                } else {
                                    move = String.valueOf(String.valueOf(startRank) + String.valueOf(startFile)
                                            + String.valueOf(tempRank) + String.valueOf(tempFile) + " capture "
                                            + boardArray[tempRank][tempFile].getPieceType().toString());
                                    move = String.format("%-28s", move);
                                    possibleMoves.add(move);

                                    break upLeft;
                                }
                            } else {
                                break upLeft;
                            }
                        }
                    }
                }
            }
        }
        upRight: {
            for (int tempRank = startRank - 1; tempRank >= 0; tempRank--) {
                for (int tempFile = startFile + 1; tempFile <= 7; tempFile++) {
                    if (Math.abs(tempRank - startRank) == Math.abs(tempFile - startFile)) {
                        if (boardArray[tempRank][tempFile] == null) {
                            move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                                    + String.valueOf(tempFile);
                            move = String.format("%-28s", move);
                            possibleMoves.add(move);
                        } else {
                            if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                                if (boardArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
                                    move = String.valueOf(startRank) + String.valueOf(startFile)
                                            + String.valueOf(tempRank) + String.valueOf(tempFile) + " check";
                                    move = String.format("%-28s", move);
                                    possibleMoves.add(move);

                                    break upRight;
                                } else {
                                    move = String.valueOf(startRank) + String.valueOf(startFile)
                                            + String.valueOf(tempRank) + String.valueOf(tempFile) + " capture "
                                            + boardArray[tempRank][tempFile].getPieceType().toString();
                                    move = String.format("%-28s", move);
                                    possibleMoves.add(move);

                                    break upRight;
                                }
                            } else {
                                break upRight;
                            }
                        }
                    }
                }
            }
        }
        downLeft: {
            for (int tempRank = startRank + 1; tempRank <= 7; tempRank++) {
                for (int tempFile = startFile - 1; tempFile >= 0; tempFile--) {
                    if (Math.abs(tempRank - startRank) == Math.abs(tempFile - startFile)) {
                        if (boardArray[tempRank][tempFile] == null) {
                            move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                                    + String.valueOf(tempFile);
                            move = String.format("%-28s", move);
                            possibleMoves.add(move);
                        } else {
                            if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                                if (boardArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
                                    move = String.valueOf(startRank) + String.valueOf(startFile)
                                            + String.valueOf(tempRank) + String.valueOf(tempFile) + " check";
                                    move = String.format("%-28s", move);
                                    possibleMoves.add(move);

                                    break downLeft;
                                } else {
                                    move = String.valueOf(startRank) + String.valueOf(startFile)
                                            + String.valueOf(tempRank) + String.valueOf(tempFile) + " capture "
                                            + boardArray[tempRank][tempFile].getPieceType().toString();
                                    move = String.format("%-28s", move);
                                    possibleMoves.add(move);

                                    break downLeft;
                                }
                            } else {
                                break downLeft;
                            }
                        }
                    }
                }
            }
        }
        downRight: {
            for (int tempRank = startRank + 1; tempRank <= 7; tempRank++) {
                for (int tempFile = startFile + 1; tempFile <= 7; tempFile++) {
                    if (Math.abs(tempRank - startRank) == Math.abs(tempFile - startFile)) {
                        if (boardArray[tempRank][tempFile] == null) {
                            move = String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                                    + String.valueOf(tempFile);
                            move = String.format("%-28s", move);
                            possibleMoves.add(move);
                        } else {
                            if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                                if (boardArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
                                    move = String.valueOf(startRank) + String.valueOf(startFile)
                                            + String.valueOf(tempRank) + String.valueOf(tempFile) + " check";
                                    move = String.format("%-28s", move);
                                    possibleMoves.add(move);

                                    break downRight;
                                } else {
                                    move = String.valueOf(startRank) + String.valueOf(startFile)
                                            + String.valueOf(tempRank) + String.valueOf(tempFile) + " capture "
                                            + boardArray[tempRank][tempFile].getPieceType().toString();
                                    move = String.format("%-28s", move);
                                    possibleMoves.add(move);

                                    break downRight;
                                }
                            } else {
                                break downRight;
                            }
                        }
                    }
                }
            }
        }

        return possibleMoves;
    } // generatePossibleMoves
}