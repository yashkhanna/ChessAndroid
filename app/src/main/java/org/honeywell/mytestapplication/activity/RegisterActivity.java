package org.honeywell.mytestapplication.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.honeywell.mytestapplication.R;
import org.honeywell.mytestapplication.db.UserContract;
import org.honeywell.mytestapplication.db.UserDBHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yash.khanna on 4/28/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.pass)
    EditText pass;

    @BindView(R.id.pass2)
    EditText pass2;

    @BindView(R.id.btnRegister)
    Button btnRegister;

    UserDBHelper userDBHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(RegisterActivity.this);
    }

    @OnClick(R.id.btnRegister)
    void onClickRegister(View view) {
        if (TextUtils.isEmpty(email.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pass.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pass2.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Confirm Password cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!pass.getText().toString().equals(pass2.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            userDBHelper = new UserDBHelper(RegisterActivity.this);
            SQLiteDatabase db = userDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, email.getText().toString());
            values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, pass.getText().toString());
            try {
                db.insertOrThrow(UserContract.UserEntry.TABLE_NAME, null, values);
            } catch (SQLiteConstraintException e) {
                Toast.makeText(RegisterActivity.this, "This Email Id is already registered", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {

            }
            Toast.makeText(RegisterActivity.this, "Successfully registered! Login to continue", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
