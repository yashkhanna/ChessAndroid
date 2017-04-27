package org.honeywell.mytestapplication.chess;

/**
 * Created by yash on 14/04/17.
 */

public enum  Piece {

    PAWN((char) 0x2659),
    ROOK((char) 0x2656),
    KNIGHT((char) 0x2658),
    BISHOP((char) 0x265D),
    QUEEN((char) 0x2655),
    KING((char) 0x2654),
    EMPTY(' ');

    char c;

    Piece(char c) {
        this.c = c;
    }

    public char getValue() {
        return c;
    }
}
