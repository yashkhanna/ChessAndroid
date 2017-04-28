package org.honeywell.mytestapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLoggedIn = sharedPreferences.getBoolean("LOGIN", false);
        String email = sharedPreferences.getString("EMAIL", null);
        if (!isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("EMAIL", email);
            startActivity(intent);
        }
    }

}