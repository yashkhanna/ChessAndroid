package chess;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.honeywell.mytestapplication.R;

/**
 * Created by yash on 14/04/17.
 */

public class ChessActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    LinearLayout ll;
    Piece[][] chess = new Piece[8][8];
    LinearLayout[] linearLayouts = new LinearLayout[8];
    TextView[][] textViews = new TextView[8][8];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);
        init();
        initUI();
    }

    void init() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                chess[i][j] = new Piece();


        linearLayouts[0] = (LinearLayout) findViewById(R.id.ll1);
        linearLayouts[1] = (LinearLayout) findViewById(R.id.ll2);
        linearLayouts[2] = (LinearLayout) findViewById(R.id.ll3);
        linearLayouts[3] = (LinearLayout) findViewById(R.id.ll4);
        linearLayouts[4] = (LinearLayout) findViewById(R.id.ll5);
        linearLayouts[5] = (LinearLayout) findViewById(R.id.ll6);
        linearLayouts[6] = (LinearLayout) findViewById(R.id.ll7);
        linearLayouts[7] = (LinearLayout) findViewById(R.id.ll8);

        for (int i = 0; i < 8; i++) {
            LinearLayout linearLayout = linearLayouts[i];
            for (int j = 0; j < 8; j++) {
                textViews[i][j] = (TextView) linearLayout.getChildAt(j);
                textViews[i][j].setOnClickListener(this);
            }
        }
    }

    void initUI() {
        for (int j = 0; j < 8; j++) {
            chess[1][j].piece = 'P';
            setTextView(1, j);
        }

        for (int j = 0; j < 8; j++) {
            chess[6][j].piece = 'P';
            setTextView(6, j);
        }

        chess[0][0].piece = 'R';
        setTextView(0, 0);
        chess[0][7].piece = 'R';
        setTextView(0, 7);
        chess[7][0].piece = 'R';
        setTextView(7, 0);
        chess[7][7].piece = 'R';
        setTextView(7, 7);

        chess[0][1].piece = 'K';
        setTextView(0, 1);
        chess[0][6].piece = 'K';
        setTextView(0, 6);
        chess[7][1].piece = 'K';
        setTextView(7, 1);
        chess[7][6].piece = 'K';
        setTextView(7, 6);

        chess[0][2].piece = 'B';
        setTextView(0, 2);
        chess[0][5].piece = 'B';
        setTextView(0, 5);
        chess[7][2].piece = 'B';
        setTextView(7, 2);
        chess[7][5].piece = 'B';
        setTextView(7, 5);

        chess[0][3].piece = 'Q';
        setTextView(0, 3);
        chess[7][3].piece = 'Q';
        setTextView(7, 3);

        chess[0][4].piece = '*';
        setTextView(0, 4);
        chess[7][4].piece = '*';
        setTextView(7, 4);
    }

    void setTextView(int i, int j) {
        textViews[i][j].setText(chess[i][j].piece + "");
    }

    void updateUIOnMove(Pair start, Pair end) {
        chess[end.x][end.y] = chess[start.x][start.y];
        chess[start.x][start.y].piece = ' ';

    }

    Pair convertViewToXY(View view) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (textViews[i][j] == view) {
                    return new Pair(i, j);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    boolean isSelected = false;
    boolean turn = false;

    @Override
    public void onClick(View v) {
        Pair pair = convertViewToXY(v);
        if (pair == null)
            return;

        textViews[pair.x][pair.y].setSelected(true);
        textViews[pair.x][pair.y].setBackgroundColor(ContextCompat.getColor(ChessActivity.this, R.color.selected));


        Toast.makeText(ChessActivity.this, "X: " + pair.x + " Y: " + pair.y, Toast.LENGTH_SHORT).show();
    }
}
