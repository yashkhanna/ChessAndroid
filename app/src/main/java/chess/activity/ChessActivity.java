package chess.activity;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.honeywell.mytestapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chess.model.Block;
import chess.model.Pair;
import chess.model.Piece;
import chess.model.Player;

/**
 * Created by yash on 14/04/17.
 */

public class ChessActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SIZE = 8;

    Block[][] chess = new Block[SIZE][SIZE];
    TextView[][] textViews = new TextView[SIZE][SIZE];
    Map<View, Pair> viewToPair = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);
        init();
    }

    void init() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                chess[i][j] = new Block();

        initChessBoard();
        initUI();
        refreshUI();
    }

    void initChessBoard() {
        // Initialize Pawns
        for (int j = 0; j < SIZE; j++) {
            chess[1][j].piece = Piece.PAWN;
            chess[6][j].piece = Piece.PAWN;
        }

        // Initialize Rooks
        chess[0][0].piece = Piece.ROOK;
        chess[0][7].piece = Piece.ROOK;
        chess[7][0].piece = Piece.ROOK;
        chess[7][7].piece = Piece.ROOK;

        // Initialize Knights
        chess[0][1].piece = Piece.KNIGHT;
        chess[0][6].piece = Piece.KNIGHT;
        chess[7][1].piece = Piece.KNIGHT;
        chess[7][6].piece = Piece.KNIGHT;

        // Initialize Bishop
        chess[0][2].piece = Piece.BISHOP;
        chess[0][5].piece = Piece.BISHOP;
        chess[7][2].piece = Piece.BISHOP;
        chess[7][5].piece = Piece.BISHOP;

        // Initialize Queen
        chess[0][3].piece = Piece.QUEEN;
        chess[7][3].piece = Piece.QUEEN;

        // Initialize King
        chess[0][4].piece = Piece.KING;
        chess[7][4].piece = Piece.KING;

        // Initialize Chess Pieces
        for (int i = 0; i < SIZE; i++) {
            chess[0][i].player = Player.BLACK;
            chess[1][i].player = Player.BLACK;

            chess[6][i].player = Player.WHITE;
            chess[7][i].player = Player.WHITE;
        }
    }

    void initUI() {
        int linearLayoutsId[] = {R.id.ll1, R.id.ll2, R.id.ll3, R.id.ll4, R.id.ll5, R.id.ll6, R.id.ll7, R.id.ll8};

        for (int i = 0; i < SIZE; i++) {
            LinearLayout linearLayout = (LinearLayout) findViewById(linearLayoutsId[i]);
            for (int j = 0; j < SIZE; j++) {
                textViews[i][j] = (TextView) linearLayout.getChildAt(j);
                textViews[i][j].setOnClickListener(this);
                viewToPair.put(textViews[i][j], new Pair(i, j));
            }
        }
    }

    void refreshUI() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                refreshBlock(i, j);
            }
        }
    }

    void refreshBlock(int row, int col) {
        refreshBlockText(row, col);
        refreshBlockTextColor(row, col);
        refreshBlockBG(row, col);
        refreshBlockAlpha(row, col);
    }

    void refreshBlockText(int row, int col) {
        textViews[row][col].setText(Character.toString(chess[row][col].piece.getValue()));
    }

    void refreshBlockTextColor(int row, int col) {
        if (!chess[row][col].isEmpty()) {
            @ColorRes int colorRes = chess[row][col].player.getValue() ? R.color.white_piece : R.color.black_piece;
            textViews[row][col].setTextColor(ContextCompat.getColor(ChessActivity.this, colorRes));
        }
    }

    void refreshBlockBG(int row, int col) {
        @ColorRes int colorRes = ((row + col) % 2 == 0) ? R.color.black : R.color.white;
        textViews[row][col].setBackgroundColor(ContextCompat.getColor(ChessActivity.this, colorRes));
    }

    void refreshBlockAlpha(int row, int col) {
        textViews[row][col].setAlpha(1f);
    }

    public boolean turn = true;
    public boolean firstMove = true;
    public Pair start;
    public List<Pair> end = new ArrayList<>();

    @Override
    public void onClick(View v) {
        Pair pair = viewToPair.get(v);
        if (pair == null)
            return;
//
//        Block block = chess[pair.x][pair.y];
//        TextView textView = textViews[pair.x][pair.y];
//
//        if (firstMove) {
//            if (block.player == (turn ? Player.WHITE : Player.BLACK)) {
//                start = pair;
//                populateEndList();
//                textView.setBackgroundColor(ContextCompat.getColor(ChessActivity.this, R.color.selected));
//                for (Pair endMoves : end) {
//                    textViews[endMoves.x][endMoves.y].setAlpha(0.35f);
//                }
//                firstMove = false;
//            }
//        } else {
//            if (end.contains(pair)) {
//                chess[pair.x][pair.y] = new Block(chess[start.x][start.y]);
//                setTextView(pair.x, pair.y);
//
//                chess[start.x][start.y] = new Block();
//                setTextView(start.x, start.y);
//
//                for (Pair endMoves : end) {
//                    resetColor(endMoves);
//                }
//
//                resetColor(start);
//
//                firstMove = true;
//                start = null;
//                end.clear();
//                turn = !turn;
//            }
//        }
//
        Toast.makeText(ChessActivity.this, "X: " + pair.x + " Y: " + pair.y, Toast.LENGTH_SHORT).show();
    }

    void populateEndList() {
        end.clear();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Pair pair = new Pair(start.x + i, start.y + j);
                if (checkBounds(pair) && chess[pair.x][pair.y].player != (turn ? Player.WHITE : Player.BLACK)) {
                    end.add(pair);
                }
            }
        }
    }

    boolean checkBounds(Pair pair) {
        return (pair.x >= 0 && pair.x < SIZE && pair.y >= 0 && pair.y < SIZE);
    }

    void resetColor(Pair pair) {
        if ((pair.x + pair.y) % 2 == 0) {
            textViews[pair.x][pair.y].setBackgroundColor(ContextCompat.getColor(ChessActivity.this, R.color.black));
        } else {
            textViews[pair.x][pair.y].setBackgroundColor(ContextCompat.getColor(ChessActivity.this, R.color.white));
        }
        textViews[pair.x][pair.y].setAlpha(1f);
    }

}
