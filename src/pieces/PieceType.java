package pieces;

public enum PieceType {
    BISHOP {
        public String toString() {
            return "Bishop";
        }
    },
    KING {
        public String toString() {
            return "King";
        }
    },
    KNIGHT {
        public String toString() {
            return "Knight";
        }
    },
    PAWN {
        public String toString() {
            return "Pawn";
        }
    },
    QUEEN {
        public String toString() {
            return "Queen";
        }
    },
    ROOK {
        public String toString() {
            return "Rook";
        }
    };
}
