package org.honeywell.mytestapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.honeywell.mytestapplication.R;

/**
 * Created by yash on 13/04/17.
 */

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText pass;
    Button btn;
    String str1;
    String str2;

    String EMAIL = "email";
    String PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        btn = (Button) findViewById(R.id.btn);
        email.setText(str1);
        pass.setText(str2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText() != null && pass.getText() != null) {
                    if (email.getText().toString().equals(EMAIL) && pass.getText().toString().equals(PASS)) {
                        Toast.makeText(LoginActivity.this, "Authenticated", Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("LOGIN", true);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, ChessActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        str1 = savedInstanceState.getString("EMAIL", "");
        str2 = savedInstanceState.getString("PASS", "");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("EMAIL", str1);
        outState.putString("PASS", str2);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
