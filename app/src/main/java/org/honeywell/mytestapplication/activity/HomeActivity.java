package org.honeywell.mytestapplication.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.honeywell.mytestapplication.R;
import org.honeywell.mytestapplication.fragments.ChessFragment;
import org.honeywell.mytestapplication.fragments.HomeFragment;
import org.honeywell.mytestapplication.fragments.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yash.khanna on 4/27/2017.
 */

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.content_frame)
    FrameLayout frameLayout;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String email;

    String CURRENT_FRAGMENT;
    String FRAGMENT_ONE = "FRAGMENT_ONE";
    String FRAGMENT_TWO = "FRAGMENT_TWO";
    String FRAGMENT_THREE = "FRAGMENT_THREE";
    String FRAGMENT_FOUR = "FRAGMENT_FOUR";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
        init();
        loadHomeFragment();
    }

    void init() {
        initToolBar();
        initNavigationView();
        initDrawerToggle();
        setEmailInHeader();
    }

    void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_lock_idle_alarm);
    }

    void initNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    void initDrawerToggle() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerToggle.syncState();
    }

    void setEmailInHeader() {
        LinearLayout navHeaderLayout = (LinearLayout) navigationView.getHeaderView(0);
        TextView textView = (TextView) navHeaderLayout.getChildAt(0);
        if (!TextUtils.isEmpty(getIntent().getExtras().getString("EMAIL"))) {
            email = getIntent().getExtras().getString("EMAIL");
        } else {
            email = "Guest";
        }
        textView.setText(email);
    }

    void loadHomeFragment() {
        replaceFragment(getFragment(0));
        setTitle(R.string.nav_one);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_fav:
                Toast.makeText(this, "FAV", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_settings:
                Toast.makeText(this, "SETTINGS", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_one:
                fragment = getFragment(0);
                break;

            case R.id.nav_two:
                fragment = getFragment(1);
                break;

            case R.id.nav_three:
                fragment = getFragment(2);
                break;
        }

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_FRAGMENT) == null) {
            replaceFragment(fragment);
            item.setChecked(true);
            setTitle(item.getTitle());
        }

        drawerLayout.closeDrawers();
        return true;
    }

    Fragment getFragment(int index) {
        Fragment fragment;
        Bundle bundle = new Bundle();
        bundle.putString("EMAIL", email);
        switch (index) {
            case 0:
                fragment = ChessFragment.getChessFragment(null);
                CURRENT_FRAGMENT = FRAGMENT_ONE;
                break;

            case 1:
                bundle.putBoolean("SHOW_TIMER", true);
                fragment = ChessFragment.getChessFragment(bundle);
                CURRENT_FRAGMENT = FRAGMENT_TWO;
                break;

            case 2:
                fragment = new SettingsFragment();
                CURRENT_FRAGMENT = FRAGMENT_THREE;
                break;

            default:
                fragment = new HomeFragment();
                CURRENT_FRAGMENT = FRAGMENT_ONE;
                break;
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, CURRENT_FRAGMENT).commit();
    }
}
