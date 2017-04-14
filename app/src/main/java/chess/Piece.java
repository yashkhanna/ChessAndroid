package chess;

/**
 * Created by yash on 14/04/17.
 */

public class Piece {
    char piece;
    boolean turn;
    boolean isSelected;

    public Piece() {
        this.piece = ' ';
        this.turn = false;
        this.isSelected = false;
    }
}
