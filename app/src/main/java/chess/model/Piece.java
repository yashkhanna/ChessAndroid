package chess.model;

/**
 * Created by yash on 14/04/17.
 */

public enum  Piece {

    PAWN('P'),
    ROOK('R'),
    KNIGHT('K'),
    BISHOP('B'),
    QUEEN('Q'),
    KING('*'),
    EMPTY(' ');

    char c;

    Piece(char c) {
        this.c = c;
    }

    public char getValue() {
        return c;
    }
}
