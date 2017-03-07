package pieces;

import java.util.ArrayList;
import java.util.List;

import game.Player;

public class Moves {
    List<String> possibleMoves = new ArrayList<String>();
    Piece[][] boardArray = new Piece[8][8];
    public int startRank, startFile;

    public List<String> set(PieceType piece, Piece[][] bArray, int sRank, int sFile) {
        boardArray = bArray;
        startRank = sRank;
        startFile = sFile;

        switch (piece) {
        case PAWN:
            offsets(possibleMoves, boardArray, startRank, startFile);
            enPassant(possibleMoves, boardArray, startRank, startFile);
            isKingInCheck(Player.WHITE, possibleMoves, boardArray, startRank, startFile);
            isKingInCheck(Player.BLACK, possibleMoves, boardArray, startRank, startFile);
        case ROOK:
            up(possibleMoves, boardArray, startRank, startFile);
            down(possibleMoves, boardArray, startRank, startFile);
            left(possibleMoves, boardArray, startRank, startFile);
            right(possibleMoves, boardArray, startRank, startFile);
            castle(possibleMoves, boardArray, startRank, startFile);
            isKingInCheck(Player.WHITE, possibleMoves, boardArray, startRank, startFile);
            isKingInCheck(Player.BLACK, possibleMoves, boardArray, startRank, startFile);
        case KNIGHT:
            offsets(possibleMoves, boardArray, startRank, startFile);
            isKingInCheck(Player.WHITE, possibleMoves, boardArray, startRank, startFile);
            isKingInCheck(Player.BLACK, possibleMoves, boardArray, startRank, startFile);
        case BISHOP:
            upLeft(possibleMoves, boardArray, startRank, startFile);
            upRight(possibleMoves, boardArray, startRank, startFile);
            downLeft(possibleMoves, boardArray, startRank, startFile);
            downRight(possibleMoves, boardArray, startRank, startFile);
            isKingInCheck(Player.WHITE, possibleMoves, boardArray, startRank, startFile);
            isKingInCheck(Player.BLACK, possibleMoves, boardArray, startRank, startFile);
        case QUEEN:
            up(possibleMoves, boardArray, startRank, startFile);
            down(possibleMoves, boardArray, startRank, startFile);
            left(possibleMoves, boardArray, startRank, startFile);
            right(possibleMoves, boardArray, startRank, startFile);
            upLeft(possibleMoves, boardArray, startRank, startFile);
            upRight(possibleMoves, boardArray, startRank, startFile);
            downLeft(possibleMoves, boardArray, startRank, startFile);
            downRight(possibleMoves, boardArray, startRank, startFile);
            isKingInCheck(Player.WHITE, possibleMoves, boardArray, startRank, startFile);
            isKingInCheck(Player.BLACK, possibleMoves, boardArray, startRank, startFile);
        case KING:
            offsets(possibleMoves, boardArray, startRank, startFile);
            castle(possibleMoves, boardArray, startRank, startFile);
            isKingInCheck(Player.WHITE, possibleMoves, boardArray, startRank, startFile);
            isKingInCheck(Player.BLACK, possibleMoves, boardArray, startRank, startFile);
        }

        return possibleMoves;
    }

    public List<String> up(List<String> moves, Piece[][] boardArray, int startRank, int startFile) {
        for (int tempRank = startRank - 1; tempRank >= 0; tempRank--) {
            if (boardArray[tempRank][startFile] == null) {
                possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                        + String.valueOf(startFile));
            } else {
                if (boardArray[tempRank][startFile].m_player != boardArray[startRank][startFile].m_player) {
                    if (boardArray[tempRank][startFile].getPieceType() == PieceType.KING) {
                        possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(tempRank) + String.valueOf(startFile));

                        break;
                    } else {
                        possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(tempRank) + String.valueOf(startFile) + " capture "
                                + boardArray[tempRank][startFile].getPieceType().toString());

                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return possibleMoves;
    }

    public List<String> down(List<String> possibleMoves, Piece[][] boardArray, int startRank, int startFile) {
        for (int tempRank = startRank + 1; tempRank <= 7; tempRank++) {
            if (boardArray[tempRank][startFile] == null) {
                possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(tempRank)
                        + String.valueOf(startFile));
            } else {
                if (boardArray[tempRank][startFile].m_player != boardArray[startRank][startFile].m_player) {
                    if (boardArray[tempRank][startFile].getPieceType() == PieceType.KING) {
                        possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(tempRank) + String.valueOf(startFile));

                        break;
                    } else {
                        possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(tempRank) + String.valueOf(startFile) + " capture "
                                + boardArray[tempRank][startFile].getPieceType().toString());

                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return possibleMoves;
    }

    public List<String> left(List<String> possibleMoves, Piece[][] boardArray, int startRank, int startFile) {
        for (int tempFile = startFile - 1; tempFile >= 0; tempFile--) {
            if (boardArray[startRank][tempFile] == null) {
                possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank)
                        + String.valueOf(tempFile));
            } else {
                if (boardArray[startRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                    if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
                        possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(startRank) + String.valueOf(tempFile));

                        break;
                    } else {
                        possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(startRank) + String.valueOf(tempFile) + " capture "
                                + boardArray[startRank][tempFile].getPieceType().toString());

                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return possibleMoves;
    }

    public List<String> right(List<String> possibleMoves, Piece[][] boardArray, int startRank, int startFile) {
        for (int tempFile = startFile + 1; tempFile <= 7; tempFile++) {
            if (boardArray[startRank][tempFile] == null) {
                possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile) + String.valueOf(startRank)
                        + String.valueOf(tempFile));
            } else {
                if (boardArray[startRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                    if (boardArray[startRank][tempFile].getPieceType() == PieceType.KING) {
                        possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(startRank) + String.valueOf(tempFile));

                        break;
                    } else {
                        possibleMoves.add(String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(startRank) + String.valueOf(tempFile) + " capture "
                                + boardArray[startRank][tempFile].getPieceType().toString());

                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return possibleMoves;
    }

    public List<String> upLeft(List<String> possibleMoves, Piece[][] boardArray, int startRank, int startFile) {

        return possibleMoves;
    }

    public List<String> upRight(List<String> possibleMoves, Piece[][] boardArray, int startRank, int startFile) {

        return possibleMoves;
    }

    public List<String> downLeft(List<String> possibleMoves, Piece[][] boardArray, int startRank, int startFile) {

        return possibleMoves;
    }

    public List<String> downRight(List<String> possibleMoves, Piece[][] boardArray, int startRank, int startFile) {

        return possibleMoves;
    }

    public List<String> offsets(List<String> possibleMoves, Piece[][] boardArray, int startRank, int startFile) {

        return possibleMoves;
    }

    public List<String> castle(List<String> possibleMoves, Piece[][] boardArray, int startRank, int startFile) {

        return possibleMoves;
    }

    public List<String> enPassant(List<String> possibleMoves, Piece[][] boardArray, int startRank, int startFile) {

        return possibleMoves;
    }

    public boolean isKingInCheck(Player player, List<String> possibleMoves, Piece[][] boardArray, int startRank,
            int startFile) {
        for (int i = 0; i < possibleMoves.size(); i++) {
            if (player != boardArray[startRank][startFile].m_player) {

            } else {

            }
        }

        return false;
    }
}
