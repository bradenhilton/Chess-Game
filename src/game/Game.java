package game;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class Game {
    private Board chessBoard = new Board();
    private boolean whiteTurn = true;
    private Map<Character, Integer> charMap = new HashMap<Character, Integer>();
    private Map<Integer, Character> reverseCharMap = new HashMap<Integer, Character>();

    /**
     * Starts the game.
     * <p>
     * Adds characters to the character map, then starts the appropriate game loop depending on number of players.
     * 
     * @param numPlayers
     *            Number of players.
     */
    public void start(int numPlayers) {
        charMap.put('a', 0);
        charMap.put('b', 1);
        charMap.put('c', 2);
        charMap.put('d', 3);
        charMap.put('e', 4);
        charMap.put('f', 5);
        charMap.put('g', 6);
        charMap.put('h', 7);

        reverseCharMap.put(0, 'a');
        reverseCharMap.put(1, 'b');
        reverseCharMap.put(2, 'c');
        reverseCharMap.put(3, 'd');
        reverseCharMap.put(4, 'e');
        reverseCharMap.put(5, 'f');
        reverseCharMap.put(6, 'g');
        reverseCharMap.put(7, 'h');

        if (numPlayers == 0) {
            new ZeroplayerGame(chessBoard, charMap, reverseCharMap, whiteTurn);
        }

        if (numPlayers == 1) {
            new SingleplayerGame(chessBoard, charMap, reverseCharMap, whiteTurn);
        }

        if (numPlayers == 2) {
            new MultiplayerGame(chessBoard, charMap, reverseCharMap, whiteTurn);
        }
    }
} // Game