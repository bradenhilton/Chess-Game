package pieces;

import java.util.ArrayList;
import java.util.List;

import game.Player;

public class Rook extends Piece {
    protected PieceType m_type;

    public Rook(Player player) {
        super(player);
        m_type = PieceType.ROOK;
    }

    public PieceType getPieceType() {
        return m_type;
    }

    @Override
    public String toString() {
        return m_player.toString().toLowerCase().substring(0, 1) + m_type.toString().substring(0, 2);
    }

    @Override
    public List<String> generatePossibleMoves(Piece[][] boardArray, int startRank, int startFile) {
        // negative (-) indicates up or left
        // positive (+) indicates down or right

        List<String> possibleMoves = new ArrayList<String>();

        up: {
            for (int tempRank = startRank - 1; tempRank >= 0; tempRank--) {
                if (boardArray[tempRank][startFile] == null) {
                    possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                            + String.valueOf(startFile));
                } else {
                    if (boardArray[tempRank][startFile].m_player != boardArray[startRank][startFile].m_player) {
                        if (boardArray[tempRank][startFile].getPieceType() == PieceType.KING) {
                            possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                    + String.valueOf(tempRank) + String.valueOf(startFile) + " check");

                            break up;
                        } else {
                            possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                    + String.valueOf(tempRank) + String.valueOf(startFile) + " capture "
                                    + boardArray[tempRank][startFile].getPieceType().toString());

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
                    possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                            + String.valueOf(startFile));
                } else {
                    if (boardArray[tempRank][startFile].m_player != boardArray[startRank][startFile].m_player) {
                        if (boardArray[tempRank][startFile].getPieceType() == PieceType.KING) {
                            possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                    + String.valueOf(tempRank) + String.valueOf(startFile) + " check");

                            break down;
                        } else {
                            possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                    + String.valueOf(tempRank) + String.valueOf(startFile) + " capture "
                                    + boardArray[tempRank][startFile].getPieceType().toString());

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
                    possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank)
                            + String.valueOf(tempFile));
                } else {
                    if (boardArray[startRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                        if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
                            possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                    + String.valueOf(startRank) + String.valueOf(tempFile) + " check");

                            break left;
                        } else {
                            possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                    + String.valueOf(startRank) + String.valueOf(tempFile) + " capture "
                                    + boardArray[startRank][tempFile].getPieceType().toString());

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
                    possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank)
                            + String.valueOf(tempFile));
                } else {
                    if (boardArray[startRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                        if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
                            possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                    + String.valueOf(startRank) + String.valueOf(tempFile) + " check");

                            break right;
                        } else {
                            possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                    + String.valueOf(startRank) + String.valueOf(tempFile) + " capture "
                                    + boardArray[startRank][tempFile].getPieceType().toString());

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