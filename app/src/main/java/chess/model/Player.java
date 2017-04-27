package chess.model;

/**
 * Created by yash.khanna on 4/26/2017.
 */
public enum Player {
    BLACK(false), WHITE(true), NULL(null);

    Boolean bool;

    Player(Boolean bool) {
        this.bool = bool;
    }

    public Boolean getValue() {
        return bool;
    }

}
