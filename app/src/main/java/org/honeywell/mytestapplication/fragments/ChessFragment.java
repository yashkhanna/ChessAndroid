package org.honeywell.mytestapplication.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.honeywell.mytestapplication.R;
import org.honeywell.mytestapplication.chess.Block;
import org.honeywell.mytestapplication.chess.Pair;
import org.honeywell.mytestapplication.chess.Piece;
import org.honeywell.mytestapplication.chess.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yash.khanna on 4/28/2017.
 */

public class ChessFragment extends Fragment implements View.OnClickListener {

    private final int SIZE = 8;

    Block[][] chess = new Block[SIZE][SIZE];
    TextView[][] textViews = new TextView[SIZE][SIZE];
    Map<View, Pair> viewToPair = new HashMap<>();
    TextView turnTextView;
    CountDownTimer countDownTimer;

    // Init params
    boolean showTimer = false;
    boolean showHints;

    public static ChessFragment getChessFragment(Bundle bundle) {
        ChessFragment chessFragment = new ChessFragment();
        chessFragment.setArguments(bundle);
        return chessFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            showTimer = getArguments().getBoolean("SHOW_TIMER", false);
        }
    }

    public void setShowHints(boolean bool) {
        showHints = bool;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chess, null, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (showTimer) {
            initTimer();
        }
    }

    void initTimer() {
        countDownTimer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                turnTextView.setText("Turn: " + turn.name() + " Seconds remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                switchTurn();
                start();
            }
        };
    }

    @Override
    public void onPause() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (countDownTimer != null) {
            countDownTimer.start();
        }
        super.onResume();
    }

    void init(View view) {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                chess[i][j] = new Block();

        initUI(view);
        initChessBoard();
        initChessUI(view);
        refreshUI();
    }

    void initUI(View view) {
        turnTextView = (TextView) view.findViewById(R.id.turnTextView);
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

    void initChessUI(View view) {
        int linearLayoutsId[] = {R.id.ll1, R.id.ll2, R.id.ll3, R.id.ll4, R.id.ll5, R.id.ll6, R.id.ll7, R.id.ll8};

        for (int i = 0; i < SIZE; i++) {
            LinearLayout linearLayout = (LinearLayout) view.findViewById(linearLayoutsId[i]);
            for (int j = 0; j < SIZE; j++) {
                textViews[i][j] = (TextView) linearLayout.getChildAt(j * 2);
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
            textViews[row][col].setTextColor(ContextCompat.getColor(getActivity(), colorRes));
        }
    }

    void refreshBlockBG(int row, int col) {
        @ColorRes int colorRes = ((row + col) % 2 == 0) ? R.color.black : R.color.white;
        refreshBlockBG(row, col, colorRes);
    }

    void refreshBlockBG(int row, int col, @ColorRes int colorRes) {
        textViews[row][col].setBackgroundColor(ContextCompat.getColor(getActivity(), colorRes));
    }

    void refreshBlockAlpha(int row, int col) {
        refreshBlockAlpha(row, col, 1f);
    }

    void refreshBlockAlpha(int row, int col, float alpha) {
        textViews[row][col].setAlpha(alpha);
    }

    public Player turn = Player.WHITE;
    public boolean startSelected = false;

    public Pair transitionStart;
    public List<Pair> transitionSpace = new ArrayList<>();
    public Pair transitionEnd;

    void initTransition() {
        if (transitionStart != null)
            refreshBlock(transitionStart.x, transitionStart.y);

        if (transitionEnd != null)
            refreshBlock(transitionEnd.x, transitionEnd.y);

        if (!transitionSpace.isEmpty())
            unmarkTransitionBlocks();

        transitionStart = null;
        transitionEnd = null;
        transitionSpace.clear();
    }

    void markStartBlock() {
        refreshBlockBG(transitionStart.x, transitionStart.y, R.color.selected);
    }

    void unmarkStartBlock() {
        chess[transitionStart.x][transitionStart.y].player = Player.NULL;
        chess[transitionStart.x][transitionStart.y].piece = Piece.EMPTY;
        refreshBlock(transitionStart.x, transitionStart.y);
    }

    void markEndBlock() {
        chess[transitionEnd.x][transitionEnd.y].player = chess[transitionStart.x][transitionStart.y].player;
        chess[transitionEnd.x][transitionEnd.y].piece = chess[transitionStart.x][transitionStart.y].piece;
        refreshBlock(transitionEnd.x, transitionEnd.y);
    }

    void markTransitionBlocks() {
        for (Pair pair : transitionSpace) {
            if (chess[pair.x][pair.y].player == inverseTurn()) {
                refreshBlockBG(pair.x, pair.y, R.color.enemy);
            } else {
                refreshBlockBG(pair.x, pair.y, R.color.path);
            }
        }
    }

    void unmarkTransitionBlocks() {
        for (Pair pair : transitionSpace) {
            refreshBlock(pair.x, pair.y);
        }
    }

    @Override
    public void onClick(View v) {
        Pair pair = viewToPair.get(v);
        if (pair == null)
            return;

        Block block = chess[pair.x][pair.y];

        if (!startSelected) {
            initTransition();
            if (block.player == turn) {
                transitionStart = pair;
                populateEndList();

                markStartBlock();
                markTransitionBlocks();

                if (!transitionSpace.isEmpty()) {
                    startSelected = true;
                }
            } else {
                Toast.makeText(getActivity(), "Selection not allowed", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (transitionSpace.contains(pair)) {
                transitionEnd = pair;
                markEndBlock();
                unmarkStartBlock();
                unmarkTransitionBlocks();
                countDownTimer.start();
                switchTurn();
            }
            startSelected = false;
        }

//        Toast.makeText(ChessActivity.this, "X: " + pair.x + " Y: " + pair.y, Toast.LENGTH_SHORT).show();
    }

    void populateEndList() {
        switch (chess[transitionStart.x][transitionStart.y].piece) {
            case PAWN:
                populatePawnList();
                break;
            case KING:
                populateKingList();
                break;
            case KNIGHT:
                populateKnightList();
                break;
            case BISHOP:
                populateBishopList();
                break;
            case ROOK:
                populateRookList();
                break;
            case QUEEN:
                populateQueenList();
                break;
            default:
                break;
        }
    }

    boolean addElementToSpace(int x, int y) {
        if (checkBounds(x, y)) {
            if (chess[x][y].player == turn) {
                return false;
            } else if (chess[x][y].player == inverseTurn()) {
                transitionSpace.add(new Pair(x, y));
                return false;
            } else {
                transitionSpace.add(new Pair(x, y));
                return true;
            }
        }
        return false;
    }

    void populateKnightList() {
        int x = transitionStart.x;
        int y = transitionStart.y;
        addElementToSpace(x + 2, y + 1);
        addElementToSpace(x + 2, y - 1);
        addElementToSpace(x + 1, y + 2);
        addElementToSpace(x + 1, y - 2);
        addElementToSpace(x - 2, y + 1);
        addElementToSpace(x - 2, y - 1);
        addElementToSpace(x - 1, y + 2);
        addElementToSpace(x - 1, y - 2);
    }

    void populateBishopList() {
        int ctr;
        int x = transitionStart.x;
        int y = transitionStart.y;

        ctr = 1;
        while (addElementToSpace(x + ctr, y + ctr)) {
            ctr++;
        }
        ctr = 1;
        while (addElementToSpace(x + ctr, y - ctr)) {
            ctr++;
        }
        ctr = 1;
        while (addElementToSpace(x - ctr, y + ctr)) {
            ctr++;
        }
        ctr = 1;
        while (addElementToSpace(x - ctr, y - ctr)) {
            ctr++;
        }
    }

    void populateRookList() {
        int ctr;
        int x = transitionStart.x;
        int y = transitionStart.y;

        ctr = 1;
        while (addElementToSpace(x, y + ctr)) {
            ctr++;
        }
        ctr = 1;
        while (addElementToSpace(x, y - ctr)) {
            ctr++;
        }
        ctr = 1;
        while (addElementToSpace(x + ctr, y)) {
            ctr++;
        }
        ctr = 1;
        while (addElementToSpace(x - ctr, y)) {
            ctr++;
        }
    }

    void populateQueenList() {
        populateRookList();
        populateBishopList();
    }

    void populateKingList() {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = transitionStart.x + i;
                int y = transitionStart.y + j;
                addElementToSpace(x, y);
            }
        }
    }

    void populatePawnList() {
        int x = transitionStart.x;
        int y = transitionStart.y;
        if (turn == Player.WHITE) {
            if (checkBounds(x - 1, y) && chess[x - 1][y].player == Player.NULL) {
                if (addElementToSpace(x - 1, y) && x == 6) {
                    addElementToSpace(x - 2, y);
                }
            }
            if (checkBounds(x - 1, y - 1) && chess[x - 1][y - 1].player == inverseTurn()) {
                addElementToSpace(x - 1, y - 1);
            }
            if (checkBounds(x - 1, y + 1) && chess[x - 1][y + 1].player == inverseTurn()) {
                addElementToSpace(x - 1, y + 1);
            }
        } else {
            if (checkBounds(x + 1, y) && chess[x + 1][y].player == Player.NULL) {
                if (addElementToSpace(x + 1, y) && x == 1) {
                    addElementToSpace(x + 2, y);
                }
            }
            if (checkBounds(x + 1, y - 1) && chess[x + 1][y - 1].player == inverseTurn()) {
                addElementToSpace(x + 1, y - 1);
            }
            if (checkBounds(x + 1, y + 1) && chess[x + 1][y + 1].player == inverseTurn()) {
                addElementToSpace(x + 1, y + 1);
            }
        }
    }

    boolean checkBounds(int row, int col) {
        return (row >= 0 && row < SIZE && col >= 0 && col < SIZE);
    }

    void switchTurn() {
        turn = (turn == Player.WHITE) ? Player.BLACK : Player.WHITE;
    }

    Player inverseTurn() {
        return (turn == Player.WHITE) ? Player.BLACK : Player.WHITE;
    }
}
