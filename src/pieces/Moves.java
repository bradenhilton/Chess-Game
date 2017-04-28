package pieces;

import java.util.ArrayList;
import java.util.List;

import game.Player;

public class Moves {
    List<String> possibleMoves = new ArrayList<String>();
    Piece[][] boardArray = new Piece[8][8];
    private int startRank, startFile;
    private int[][] offsets = null;
    private PieceType piece;
    Player player, enemyPlayer;
    private boolean attack;

    public List<String> set(PieceType pieceT, Piece[][] bArray, int sRank, int sFile, boolean friendlyCheck,
            boolean enemyCheck) {
        piece = pieceT;
        boardArray = bArray;
        startRank = sRank;
        startFile = sFile;

        player = bArray[startRank][startFile].getPlayer();

        if (player == Player.WHITE) {
            enemyPlayer = Player.BLACK;
        } else {
            enemyPlayer = Player.WHITE;
        }

        if (enemyCheck) {
            attack = true;
        }
        if (friendlyCheck) {
            attack = true;
            player = enemyPlayer;
        }

        switch (piece) {
        case PAWN:
            possibleMoves.addAll(offsets(offsets, possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(enPassant(offsets, possibleMoves, boardArray, startRank, startFile));
            break;
        case ROOK:
            possibleMoves.addAll(up(possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(down(possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(left(possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(right(possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(castle(offsets, possibleMoves, boardArray, startRank, startFile, player));
            break;
        case KNIGHT:
            possibleMoves.addAll(offsets(offsets, possibleMoves, boardArray, startRank, startFile, attack, player));
            break;
        case BISHOP:
            possibleMoves.addAll(upLeft(possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(upRight(possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(downLeft(possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(downRight(possibleMoves, boardArray, startRank, startFile, attack, player));
            break;
        case QUEEN:
            possibleMoves.addAll(up(possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(down(possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(left(possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(right(possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(upLeft(possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(upRight(possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(downLeft(possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(downRight(possibleMoves, boardArray, startRank, startFile, attack, player));
            break;
        case KING:
            possibleMoves.addAll(offsets(offsets, possibleMoves, boardArray, startRank, startFile, attack, player));
            possibleMoves.addAll(castle(offsets, possibleMoves, boardArray, startRank, startFile, player));
            break;
        }

        if (!friendlyCheck && !enemyCheck) {
            isKingInCheck(player, possibleMoves, boardArray, startRank, startFile);
            isKingInCheck(enemyPlayer, possibleMoves, boardArray, startRank, startFile);
        }

        return possibleMoves;
    }

    public List<String> up(List<String> moves, Piece[][] bArray, int sRank, int sFile, boolean attackKing,
            Player player) {
        for (int tempRank = sRank - 1; tempRank >= 0; tempRank--) {
            if (bArray[tempRank][sFile] == null) {
                moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                        + String.valueOf(sFile));
            } else {
                if (bArray[tempRank][sFile].m_player != bArray[sRank][sFile].m_player) {
                    if (bArray[tempRank][sFile].getPieceType() == PieceType.KING) {
                        moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                + String.valueOf(sFile));

                        break;
                    } else {
                        moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                + String.valueOf(sFile) + " capture "
                                + bArray[tempRank][sFile].getPieceType().toString());

                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return moves;
    }

    public List<String> down(List<String> moves, Piece[][] bArray, int sRank, int sFile, boolean attackKing,
            Player player) {
        for (int tempRank = sRank + 1; tempRank <= 7; tempRank++) {
            if (bArray[tempRank][sFile] == null) {
                moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                        + String.valueOf(sFile));
            } else {
                if (bArray[tempRank][sFile].m_player != bArray[sRank][sFile].m_player) {
                    if (bArray[tempRank][sFile].getPieceType() == PieceType.KING) {
                        moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                + String.valueOf(sFile));

                        break;
                    } else {
                        moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                + String.valueOf(sFile) + " capture "
                                + bArray[tempRank][sFile].getPieceType().toString());

                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return moves;
    }

    public List<String> left(List<String> moves, Piece[][] bArray, int sRank, int sFile, boolean attackKing,
            Player player) {
        for (int tempFile = sFile - 1; tempFile >= 0; tempFile--) {
            if (bArray[sRank][tempFile] == null) {
                moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(sRank)
                        + String.valueOf(tempFile));
            } else {
                if (bArray[sRank][tempFile].m_player != bArray[sRank][sFile].m_player) {
                    if (bArray[sRank][tempFile].getPieceType() == PieceType.KING) {
                        moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(sRank)
                                + String.valueOf(tempFile));

                        break;
                    } else {
                        moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(sRank)
                                + String.valueOf(tempFile) + " capture "
                                + bArray[sRank][tempFile].getPieceType().toString());

                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return moves;
    }

    public List<String> right(List<String> moves, Piece[][] bArray, int sRank, int sFile, boolean attackKing,
            Player player) {
        for (int tempFile = sFile + 1; tempFile <= 7; tempFile++) {
            if (bArray[sRank][tempFile] == null) {
                moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(sRank)
                        + String.valueOf(tempFile));
            } else {
                if (bArray[sRank][tempFile].m_player != bArray[sRank][sFile].m_player) {
                    if (bArray[sRank][tempFile].getPieceType() == PieceType.KING) {
                        moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(sRank)
                                + String.valueOf(tempFile));

                        break;
                    } else {
                        moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(sRank)
                                + String.valueOf(tempFile) + " capture "
                                + bArray[sRank][tempFile].getPieceType().toString());

                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return moves;
    }

    public List<String> upLeft(List<String> moves, Piece[][] bArray, int sRank, int sFile, boolean attackKing,
            Player player) {
        for (int tempRank = sRank - 1; tempRank >= 0; tempRank--) {
            for (int tempFile = sFile - 1; tempFile >= 0; tempFile--) {
                if (Math.abs(tempRank - sRank) == Math.abs(tempFile - sFile)) {
                    if (bArray[tempRank][tempFile] == null) {
                        moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                + String.valueOf(tempFile));
                    } else {
                        if (bArray[tempRank][tempFile].m_player != bArray[sRank][sFile].m_player) {
                            if (bArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
                                moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                        + String.valueOf(tempFile) + " check");

                                break;
                            } else {
                                moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                        + String.valueOf(tempFile) + " capture "
                                        + bArray[tempRank][tempFile].getPieceType().toString());

                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        return moves;
    }

    public List<String> upRight(List<String> moves, Piece[][] bArray, int sRank, int sFile, boolean attackKing,
            Player player) {
        for (int tempRank = sRank - 1; tempRank >= 0; tempRank--) {
            for (int tempFile = sFile + 1; tempFile <= 7; tempFile++) {
                if (Math.abs(tempRank - sRank) == Math.abs(tempFile - sFile)) {
                    if (bArray[tempRank][tempFile] == null) {
                        moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                + String.valueOf(tempFile));
                    } else {
                        if (bArray[tempRank][tempFile].m_player != bArray[sRank][sFile].m_player) {
                            if (bArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
                                moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                        + String.valueOf(tempFile) + " check");

                                break;
                            } else {
                                moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                        + String.valueOf(tempFile) + " capture "
                                        + bArray[tempRank][tempFile].getPieceType().toString());

                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        return moves;
    }

    public List<String> downLeft(List<String> moves, Piece[][] bArray, int sRank, int sFile, boolean attackKing,
            Player player) {
        for (int tempRank = sRank + 1; tempRank <= 7; tempRank++) {
            for (int tempFile = sFile - 1; tempFile >= 0; tempFile--) {
                if (Math.abs(tempRank - sRank) == Math.abs(tempFile - sFile)) {
                    if (bArray[tempRank][tempFile] == null) {
                        moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                + String.valueOf(tempFile));
                    } else {
                        if (bArray[tempRank][tempFile].m_player != bArray[sRank][sFile].m_player) {
                            if (bArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
                                moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                        + String.valueOf(tempFile) + " check");

                                break;
                            } else {
                                moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                        + String.valueOf(tempFile) + " capture "
                                        + bArray[tempRank][tempFile].getPieceType().toString());

                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        return moves;
    }

    public List<String> downRight(List<String> moves, Piece[][] bArray, int sRank, int sFile, boolean attackKing,
            Player player) {
        for (int tempRank = sRank + 1; tempRank <= 7; tempRank++) {
            for (int tempFile = sFile + 1; tempFile <= 7; tempFile++) {
                if (Math.abs(tempRank - sRank) == Math.abs(tempFile - sFile)) {
                    if (bArray[tempRank][tempFile] == null) {
                        moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                + String.valueOf(tempFile));
                    } else {
                        if (bArray[tempRank][tempFile].m_player != bArray[sRank][sFile].m_player) {
                            if (bArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
                                moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                        + String.valueOf(tempFile) + " check");

                                break;
                            } else {
                                moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                        + String.valueOf(tempFile) + " capture "
                                        + bArray[tempRank][tempFile].getPieceType().toString());

                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        return moves;
    }

    public List<String> offsets(int[][] moveOffsets, List<String> moves, Piece[][] bArray, int sRank, int sFile,
            boolean attackKing, Player player) {
        if (bArray[sRank][sFile].getPieceType() == PieceType.PAWN) {
            if (bArray[sRank][sFile].m_player == Player.BLACK) {
                if (!bArray[sRank][sFile].hasMoved) {
                    int[][] o = { { 1, 0 }, { 2, 0 }, { 1, -1 }, { 1, 1 }, { 0, -1 }, { 0, 1 } };
                    moveOffsets = o;
                } else {
                    int[][] o = { { 1, 0 }, { 1, -1 }, { 1, 1 }, { 0, -1 }, { 0, 1 } };
                    moveOffsets = o;
                }
            } else if (bArray[sRank][sFile].m_player == Player.WHITE) {
                if (!bArray[sRank][sFile].hasMoved) {
                    int[][] o = { { -1, 0 }, { -2, 0 }, { -1, -1 }, { -1, 1 }, { 0, -1 }, { 0, 1 } };
                    moveOffsets = o;
                } else {
                    int[][] o = { { -1, 0 }, { -1, -1 }, { -1, 1 }, { 0, -1 }, { 0, 1 } };
                    moveOffsets = o;
                }
            }
        } else if (bArray[sRank][sFile].getPieceType() == PieceType.KING) {
            int[][] o = { { -1, 0 }, { -1, 1 }, { -1, -1 }, { 0, -1 }, { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, -1 } };
            moveOffsets = o;
        } else if (bArray[sRank][sFile].getPieceType() == PieceType.KNIGHT) {
            int[][] o = { { -2, 1 }, { -1, 2 }, { 1, 2 }, { 2, 1 }, { 2, -1 }, { 1, -2 }, { -1, -2 }, { -2, -1 } };
            moveOffsets = o;
        }

        for (int[] o : moveOffsets) {
            int tempRank = sRank + o[0];
            int tempFile = sFile + o[1];

            if ((tempRank >= 0 && tempRank <= 7) && (tempFile >= 0 && tempFile <= 7)) {
                if (bArray[tempRank][tempFile] == null) {
                    moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                            + String.valueOf(tempFile));
                } else if (bArray[tempRank][tempFile].m_player != bArray[sRank][sFile].m_player) {
                    if (bArray[tempRank][tempFile].getPieceType() == PieceType.KING) {
                        moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                + String.valueOf(tempFile) + " check");
                    } else {
                        moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank)
                                + String.valueOf(tempFile) + " capture " + bArray[tempRank][tempFile].getPieceType());
                    }
                }
            }
        }

        offsets = moveOffsets;
        return moves;
    }

    public List<String> castle(int[][] moveOffsets, List<String> moves, Piece[][] bArray, int sRank, int sFile,
            Player player) {
        int leftRookF, rightRookF;
        List<String> leftRookPath = new ArrayList<String>();
        List<String> rightRookPath = new ArrayList<String>();

        // checks for castling left
        for (int tempFile = sFile - 1; tempFile >= 0; tempFile--) {
            if (bArray[sRank][tempFile] == null) {
                continue;
            } else {
                if (tempFile == 0) {
                    if (bArray[sRank][tempFile].m_player == bArray[sRank][sFile].m_player
                            && bArray[sRank][tempFile].getPieceType() == PieceType.ROOK
                            && bArray[sRank][tempFile].getMoved() == false) {
                        leftRookF = tempFile;
                        leftRookPath.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(sRank)
                                + String.valueOf(sFile - 1));
                        leftRookPath.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(sRank)
                                + String.valueOf(sFile - 2));
                    }
                } else {
                    leftRookPath.removeAll(leftRookPath);
                    break;
                }
            }
        }

        // checks for castling right
        for (int tempFile = sFile + 1; tempFile <= 7; tempFile++) {
            if (bArray[sRank][tempFile] == null) {
                continue;
            } else {
                if (tempFile == 7) {
                    if (bArray[sRank][tempFile].m_player == bArray[sRank][sFile].m_player
                            && bArray[sRank][tempFile].getPieceType() == PieceType.ROOK
                            && bArray[sRank][tempFile].getMoved() == false) {
                        rightRookF = tempFile;
                        rightRookPath.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(sRank)
                                + String.valueOf(sFile + 1));
                        rightRookPath.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(sRank)
                                + String.valueOf(sFile + 2));
                    }
                } else {
                    rightRookPath.removeAll(rightRookPath);
                    break;
                }
            }
        }

        moves.addAll(leftRookPath);
        moves.addAll(rightRookPath);
        return moves;
    }

    public List<String> enPassant(int[][] moveOffsets, List<String> moves, Piece[][] bArray, int sRank, int sFile) {
        for (int[] o : moveOffsets) {
            int tempRank = sRank + o[0];
            int tempFile = sFile + o[1];

            if ((tempRank >= 0 && tempRank <= 7) && (tempFile >= 0 && tempFile <= 7)) {
                if (tempFile != sFile) {
                    if (tempRank == sRank) {
                        if (bArray[tempRank][tempFile] != null
                                && bArray[tempRank][tempFile].m_player != bArray[sRank][sFile].m_player
                                && bArray[tempRank][tempFile].getPieceType() == PieceType.PAWN
                                && bArray[tempRank][tempFile].hasMovedTwo == true) {
                            if (bArray[sRank][sFile].m_player == Player.BLACK) {
                                moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank + 1)
                                        + String.valueOf(tempFile) + " capture "
                                        + bArray[tempRank][tempFile].getPieceType() + " en passant");
                            } else {
                                moves.add(String.valueOf(sRank) + String.valueOf(sFile) + String.valueOf(tempRank - 1)
                                        + String.valueOf(tempFile) + " capture "
                                        + bArray[tempRank][tempFile].getPieceType() + " en passant");
                            }
                        }
                    }
                }
            }
        }

        return moves;
    }

    public boolean isKingInCheck(Player currentPlayer, List<String> moves, Piece[][] bArray, int sRank, int sFile) {
        int tempStartRank, tempStartFile, tempDestRank, tempDestFile;
        Piece tempPiece = null;
        List<String> check = new ArrayList<String>();
        List<String> friendlyCheck = new ArrayList<String>();
        List<String> enemyCheck = new ArrayList<String>();

        if (currentPlayer == player) {
            for (int i = 0; i < possibleMoves.size(); i++) {
                tempStartRank = Integer.parseInt(possibleMoves.get(i).substring(0, 1));
                tempStartFile = Integer.parseInt(possibleMoves.get(i).substring(0, 1));
                tempDestRank = Integer.parseInt(possibleMoves.get(i).substring(0, 1));
                tempDestFile = Integer.parseInt(possibleMoves.get(i).substring(0, 1));

                if (bArray[tempDestRank][tempDestFile] != null) {
                    tempPiece = bArray[tempDestRank][tempDestFile];
                    bArray[tempDestRank][tempDestFile] = null;
                }

                // move
                bArray[tempDestRank][tempDestFile] = bArray[tempStartRank][tempStartFile];
                bArray[tempStartRank][tempStartFile] = null;

                check.addAll(set(bArray[tempDestRank][tempDestFile].getPieceType(), bArray, tempDestRank, tempDestFile,
                        true, false));

                // move back
                bArray[tempStartRank][tempStartFile] = bArray[tempDestRank][tempDestFile];
                bArray[tempDestRank][tempDestFile] = null;
                if (tempPiece != null) {
                    bArray[tempDestRank][tempDestFile] = tempPiece;
                }

                if (!check.isEmpty()) {
                    friendlyCheck.addAll(check);
                    possibleMoves.remove(i);
                }

                check.clear();
            }

            if (!friendlyCheck.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else {
            for (int i = 0; i < possibleMoves.size(); i++) {
                tempStartRank = Integer.parseInt(possibleMoves.get(i).substring(0, 1));
                tempStartFile = Integer.parseInt(possibleMoves.get(i).substring(0, 1));
                tempDestRank = Integer.parseInt(possibleMoves.get(i).substring(0, 1));
                tempDestFile = Integer.parseInt(possibleMoves.get(i).substring(0, 1));

                if (bArray[tempDestRank][tempDestFile] != null) {
                    tempPiece = bArray[tempDestRank][tempDestFile];
                    bArray[tempDestRank][tempDestFile] = null;
                }

                // move
                bArray[tempDestRank][tempDestFile] = bArray[tempStartRank][tempStartFile];
                bArray[tempStartRank][tempStartFile] = null;

                check.addAll(set(bArray[tempDestRank][tempDestFile].getPieceType(), bArray, tempDestRank, tempDestFile,
                        false, true));

                // move back
                bArray[tempStartRank][tempStartFile] = bArray[tempDestRank][tempDestFile];
                bArray[tempDestRank][tempDestFile] = null;
                if (tempPiece != null) {
                    bArray[tempDestRank][tempDestFile] = tempPiece;
                }

                if (!check.isEmpty()) {
                    enemyCheck.addAll(check);
                    String newMove = possibleMoves.get(i);
                    possibleMoves.set(i, newMove + " check");
                }

                check.clear();
            }

            if (!enemyCheck.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }
    }
}
