package org.honeywell.mytestapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.honeywell.mytestapplication.R;
import org.honeywell.mytestapplication.db.UserContract;
import org.honeywell.mytestapplication.db.UserDBHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yash on 13/04/17.
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.pass)
    EditText pass;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.btnRegister)
    Button btnRegister;

    String str1;
    String str2;

    boolean byPass = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);

        email.setText(str1);
        pass.setText(str2);
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClick() {
        if (byPass) {
            saveSharedPreferences();
            startChessActivity();
            return;
        }

        if (TextUtils.isEmpty(email.getText().toString())) {
            Toast.makeText(LoginActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pass.getText().toString())) {
            Toast.makeText(LoginActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            if (readFromDB()) {
                saveSharedPreferences();
                startChessActivity();
            } else {
                Toast.makeText(LoginActivity.this, "Either email or password is incorrect", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void saveSharedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("LOGIN", true);
        editor.apply();
    }

    void startChessActivity() {
        Intent intent = new Intent(LoginActivity.this, ChessActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    boolean readFromDB() {
        UserDBHelper userDBHelper = new UserDBHelper(LoginActivity.this);
        SQLiteDatabase db = userDBHelper.getReadableDatabase();
        String[] projection = {
                UserContract.UserEntry.COLUMN_NAME_PASSWORD
        };
        String selection = UserContract.UserEntry.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = {email.getText().toString()};

        Cursor cursor = db.query(UserContract.UserEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        boolean passwordMatches = false;

        while (cursor.moveToNext()) {
            String password = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PASSWORD));
//            Log.d("YASH", " " + password);
            if (password.equals(pass.getText().toString())) {
                passwordMatches = true;
            }
        }
        cursor.close();
        return passwordMatches;
    }

    @OnClick(R.id.btnRegister)
    public void onRegisterClick() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}