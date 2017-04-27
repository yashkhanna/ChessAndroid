package chess.model;

/**
 * Created by yash.khanna on 4/26/2017.
 */
public class Block {
    public Player player;
    public Piece piece;

    public Block(Player player, Piece piece) {
        this.player = player;
        this.piece = piece;
    }

    public Block() {
        this.player = Player.NULL;
        this.piece = Piece.EMPTY;
    }

    public Block(Block block) {
        this.player = block.player;
        this.piece = block.piece;
    }

    public boolean isEmpty() {
        return piece == Piece.EMPTY;
    }
}
