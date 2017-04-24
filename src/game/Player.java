package game;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public enum Player {
    WHITE {
        public String toString() {
            return "White";
        }
    },
    BLACK {
        public String toString() {
            return "Black";
        }
    };

    Player toggle() {
        if (this.equals(WHITE)) {
            return BLACK;
        } else {
            return WHITE;
        }
    }
}