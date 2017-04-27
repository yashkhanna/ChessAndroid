package org.honeywell.mytestapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ListView;


import org.honeywell.mytestapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yash.khanna on 4/27/2017.
 */

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.content_frame)
    FrameLayout frameLayout;

    @BindView(R.id.left_drawer)
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
    }
}
