package pieces;

import java.util.ArrayList;
import java.util.List;

import game.Player;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class King extends Piece {
    protected PieceType m_type;
    protected String home;

    public King(Player player, int rank, int file) {
        super(player, rank, file);
        m_type = PieceType.KING;
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

        List<String> attackerMoves = new ArrayList<String>();
        List<String> possibleMoves = new ArrayList<String>();
        List<String> leftRookPath = new ArrayList<String>();
        List<String> rightRookPath = new ArrayList<String>();

        int kingR = startRank;
        int kingF = startFile;
        int leftRookR = startRank;
        int leftRookF = 0;
        int rightRookR = startRank;
        int rightRookF = 0;

        int[][] offsets = { { -1, 0 }, { -1, 1 }, { -1, -1 }, { 0, -1 }, { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, -1 } };

        int[][] knightOffsets = { { -2, 1 }, { -1, 2 }, { 1, 2 }, { 2, 1 }, { 2, -1 }, { 1, -2 }, { -1, -2 },
                { -2, -1 } };

        // generates temporary moves
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

        // checks for castling left
        for (int tempFile = startFile - 1; tempFile >= 0; tempFile--) {
            if (boardArray[startRank][tempFile] == null) {
                continue;
            } else {
                if (tempFile == 0) {
                    if (boardArray[startRank][tempFile].m_player == boardArray[startRank][startFile].m_player
                            && boardArray[startRank][tempFile].getPieceType() == PieceType.ROOK
                            && boardArray[startRank][tempFile].getMoved() == false) {
                        leftRookF = tempFile;
                        leftRookPath.add(String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(startRank) + String.valueOf(startFile - 1));
                        leftRookPath.add(String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(startRank) + String.valueOf(startFile - 2));
                    }
                } else {
                    leftRookPath.removeAll(leftRookPath);
                    break;
                }
            }
        }

        // checks for castling right
        for (int tempFile = startFile + 1; tempFile <= 7; tempFile++) {
            if (boardArray[startRank][tempFile] == null) {
                continue;
            } else {
                if (tempFile == 7) {
                    if (boardArray[startRank][tempFile].m_player == boardArray[startRank][startFile].m_player
                            && boardArray[startRank][tempFile].getPieceType() == PieceType.ROOK
                            && boardArray[startRank][tempFile].getMoved() == false) {
                        rightRookF = tempFile;
                        rightRookPath.add(String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(startRank) + String.valueOf(startFile + 1));
                        rightRookPath.add(String.valueOf(startRank) + String.valueOf(startFile)
                                + String.valueOf(startRank) + String.valueOf(startFile + 2));
                    }
                } else {
                    rightRookPath.removeAll(rightRookPath);
                    break;
                }
            }
        }

        // generates attacker moves
        for (int i = 0; i < possibleMoves.size(); i++) {
            int sRank = Integer.parseInt(possibleMoves.get(i).substring(2, 3));
            int sFile = Integer.parseInt(possibleMoves.get(i).substring(3, 4));

            up: {
                for (int tempRank = sRank - 1; tempRank >= 0; tempRank--) {
                    if (boardArray[tempRank][sFile] == null) {
                        continue;
                    } else {
                        if (boardArray[tempRank][sFile].m_player != boardArray[startRank][startFile].m_player) {
                            if (boardArray[tempRank][sFile].getPieceType() == PieceType.ROOK
                                    || boardArray[tempRank][sFile].getPieceType() == PieceType.QUEEN) {
                                attackerMoves.add(String.valueOf(tempRank) + String.valueOf(sFile)
                                        + String.valueOf(sRank) + String.valueOf(sFile));
                                break up;
                            }
                        }
                    }
                }
            }
            down: {
                for (int tempRank = sRank + 1; tempRank <= 7; tempRank++) {
                    if (boardArray[tempRank][sFile] == null) {
                        continue;
                    } else {
                        if (boardArray[tempRank][sFile].m_player != boardArray[startRank][startFile].m_player) {
                            if (boardArray[tempRank][sFile].getPieceType() == PieceType.ROOK
                                    || boardArray[tempRank][sFile].getPieceType() == PieceType.QUEEN) {
                                attackerMoves.add(String.valueOf(tempRank) + String.valueOf(sFile)
                                        + String.valueOf(sRank) + String.valueOf(sFile));
                                break down;
                            }
                        }
                    }
                }
            }
            left: {
                for (int tempFile = sFile - 1; tempFile >= 0; tempFile--) {
                    if (boardArray[sRank][tempFile] == null) {
                        continue;
                    } else {
                        if (boardArray[sRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                            if (boardArray[sRank][tempFile].getPieceType() == PieceType.ROOK
                                    || boardArray[sRank][tempFile].getPieceType() == PieceType.QUEEN) {
                                attackerMoves.add(String.valueOf(sRank) + String.valueOf(tempFile)
                                        + String.valueOf(sRank) + String.valueOf(sFile));
                                break left;
                            }
                        }
                    }
                }
            }
            right: {
                for (int tempFile = sFile + 1; tempFile <= 7; tempFile++) {
                    if (boardArray[sRank][tempFile] == null) {
                        continue;
                    } else {
                        if (boardArray[sRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                            if (boardArray[sRank][tempFile].getPieceType() == PieceType.ROOK
                                    || boardArray[sRank][tempFile].getPieceType() == PieceType.QUEEN) {
                                attackerMoves.add(String.valueOf(sRank) + String.valueOf(tempFile)
                                        + String.valueOf(sRank) + String.valueOf(sFile));
                                break right;
                            }
                        }
                    }
                }
            }
            upLeft: {
                for (int tempRank = sRank - 1; tempRank >= 0; tempRank--) {
                    for (int tempFile = sFile - 1; tempFile >= 0; tempFile--) {
                        if (Math.abs(tempRank - sRank) == Math.abs(tempFile - sFile)) {
                            if (boardArray[tempRank][tempFile] == null) {
                                continue;
                            } else if (tempRank == sRank - 1 && tempFile == sFile - 1) {
                                if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                                    if (boardArray[tempRank][tempFile].getPieceType() == PieceType.PAWN) {
                                        attackerMoves.add(String.valueOf(tempRank) + String.valueOf(tempFile)
                                                + String.valueOf(sRank) + String.valueOf(sFile));
                                        break upLeft;
                                    }
                                }
                            } else {
                                if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                                    if (boardArray[tempRank][tempFile].getPieceType() == PieceType.QUEEN
                                            || boardArray[tempRank][tempFile].getPieceType() == PieceType.BISHOP) {
                                        attackerMoves.add(String.valueOf(tempRank) + String.valueOf(tempFile)
                                                + String.valueOf(sRank) + String.valueOf(sFile));
                                        break upLeft;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            upRight: {
                for (int tempRank = sRank - 1; tempRank >= 0; tempRank--) {
                    for (int tempFile = sFile + 1; tempFile <= 7; tempFile++) {
                        if (Math.abs(tempRank - sRank) == Math.abs(tempFile - sFile)) {
                            if (boardArray[tempRank][tempFile] == null) {
                                continue;
                            } else if (tempRank == sRank - 1 && tempFile == sFile - 1) {
                                if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                                    if (boardArray[tempRank][tempFile].getPieceType() == PieceType.PAWN) {
                                        attackerMoves.add(String.valueOf(tempRank) + String.valueOf(tempFile)
                                                + String.valueOf(sRank) + String.valueOf(sFile));
                                        break upRight;
                                    }
                                }
                            } else {
                                if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                                    if (boardArray[tempRank][tempFile].getPieceType() == PieceType.QUEEN
                                            || boardArray[tempRank][tempFile].getPieceType() == PieceType.BISHOP) {
                                        attackerMoves.add(String.valueOf(tempRank) + String.valueOf(tempFile)
                                                + String.valueOf(sRank) + String.valueOf(sFile));
                                        break upRight;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            downLeft: {
                for (int tempRank = sRank + 1; tempRank <= 7; tempRank++) {
                    for (int tempFile = sFile - 1; tempFile >= 0; tempFile--) {
                        if (Math.abs(tempRank - sRank) == Math.abs(tempFile - sFile)) {
                            if (boardArray[tempRank][tempFile] == null) {
                                continue;
                            } else if (tempRank == sRank - 1 && tempFile == sFile - 1) {
                                if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                                    if (boardArray[tempRank][tempFile].getPieceType() == PieceType.PAWN) {
                                        attackerMoves.add(String.valueOf(tempRank) + String.valueOf(tempFile)
                                                + String.valueOf(sRank) + String.valueOf(sFile));
                                        break downLeft;
                                    }
                                }
                            } else {
                                if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                                    if (boardArray[tempRank][tempFile].getPieceType() == PieceType.QUEEN
                                            || boardArray[tempRank][tempFile].getPieceType() == PieceType.BISHOP) {
                                        attackerMoves.add(String.valueOf(tempRank) + String.valueOf(tempFile)
                                                + String.valueOf(sRank) + String.valueOf(sFile));
                                        break downLeft;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            downRight: {
                for (int tempRank = sRank + 1; tempRank <= 7; tempRank++) {
                    for (int tempFile = sFile + 1; tempFile <= 7; tempFile++) {
                        if (Math.abs(tempRank - sRank) == Math.abs(tempFile - sFile)) {
                            if (boardArray[tempRank][tempFile] == null) {
                                continue;
                            } else if (tempRank == sRank - 1 && tempFile == sFile - 1) {
                                if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                                    if (boardArray[tempRank][tempFile].getPieceType() == PieceType.PAWN) {
                                        attackerMoves.add(String.valueOf(tempRank) + String.valueOf(tempFile)
                                                + String.valueOf(sRank) + String.valueOf(sFile));
                                        break downRight;
                                    }
                                }
                            } else {
                                if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                                    if (boardArray[tempRank][tempFile].getPieceType() == PieceType.QUEEN
                                            || boardArray[tempRank][tempFile].getPieceType() == PieceType.BISHOP) {
                                        attackerMoves.add(String.valueOf(tempRank) + String.valueOf(tempFile)
                                                + String.valueOf(sRank) + String.valueOf(sFile));
                                        break downRight;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            knight: {
                for (int[] o : knightOffsets) {
                    int tempRank = sRank + o[0];
                    int tempFile = sFile + o[1];

                    if ((tempRank >= 0 && tempRank <= 7) && (tempFile >= 0 && tempFile <= 7)) {
                        if (boardArray[tempRank][tempFile] == null) {
                            continue;
                        } else if (boardArray[tempRank][tempFile].m_player != boardArray[startRank][startFile].m_player) {
                            if (boardArray[tempRank][tempFile].getPieceType() == PieceType.KNIGHT) {
                                attackerMoves.add(String.valueOf(tempRank) + String.valueOf(tempFile)
                                        + String.valueOf(sRank) + String.valueOf(sFile));

                                break knight;
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
        }

        // cross checks attacker moves with possible moves
        for (int i = 0; i < attackerMoves.size(); i++) {
            for (int j = 0; j < possibleMoves.size(); j++) {
                if (attackerMoves.get(i).substring(2, 4).equals(possibleMoves.get(j).substring(2, 4))) {
                    possibleMoves.remove(j);
                }
            }

            if (!leftRookPath.isEmpty()) {
                for (int k = 0; k < leftRookPath.size(); k++) {
                    if (attackerMoves.get(i).substring(2, 4).equals(leftRookPath.get(k).substring(2, 4))) {
                        leftRookPath.removeAll(leftRookPath);
                        break;
                    }
                }
            }

            if (!rightRookPath.isEmpty()) {
                for (int l = 0; l < rightRookPath.size(); l++) {
                    if (attackerMoves.get(i).substring(2, 4).equals(rightRookPath.get(l).substring(2, 4))) {
                        rightRookPath.removeAll(rightRookPath);
                        break;
                    }
                }
            }
        }

        if (!leftRookPath.isEmpty()) {
            move = String.valueOf(kingR) + String.valueOf(kingF) + String.valueOf(leftRookR) + String.valueOf(leftRookF)
                    + " castle";
            move = String.format("%-28s", move);
            possibleMoves.add(move);
        }

        if (!rightRookPath.isEmpty()) {
            move = String.valueOf(kingR) + String.valueOf(kingF) + String.valueOf(rightRookR)
                    + String.valueOf(rightRookF) + " castle";
            move = String.format("%-28s", move);
            possibleMoves.add(move);
        }

        return possibleMoves;
    }

    public List<String> generateAttackerMoves(Piece[][] boardArray, int startRank, int startFile) {
        List<String> attackerMoves = new ArrayList<String>();

        return attackerMoves;
    }
}