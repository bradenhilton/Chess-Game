package pieces;

import java.util.List;

import game.Player;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public abstract class Piece {
    protected boolean hasMoved = false;
    protected boolean hasMovedTwo = false;
    protected Player m_player;
    protected String home;
    protected int captures;

    /**
     * 
     * @param player
     */
    public Piece(Player player, int rank, int file) {
        m_player = player;
        home = String.valueOf(rank) + String.valueOf(file);
        captures = 0;
    }

    public String getHome() {
        return home;
    }

    public abstract String getPieceInfo();

    @Override
    public abstract String toString();

    public Player getPlayer() {
        return m_player;
    }

    public void setMoved(boolean truefalse) {
        this.hasMoved = truefalse;
    }

    public boolean getMoved() {
        return hasMoved;
    }

    public void setMovedTwo(boolean truefalse) {
        this.hasMovedTwo = truefalse;
    }

    public boolean getMovedTwo() {
        return hasMovedTwo;
    }

    public void incCaptures() { // increment captures
        this.captures = captures + 1;
    }

    public void decCaptures() { // decrement captures
        this.captures = captures - 1;
    }

    public void setCaptures(int captures) {
        this.captures = captures;
    }

    public int getCaptures() {
        return captures;
    }

    public abstract PieceType getPieceType();

    public abstract List<String> generatePossibleMoves(Piece[][] boardArray, int startRank, int startFile);
}