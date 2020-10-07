package com.example.kvantorium;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.kvantorium.server.GetUser;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnUserListener {

    NavigationView navigationView;
    DrawerLayout drawer;
    TextView title_name;
    private SharedPreferences mSettings;

    public int USER_ID = 0;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mSettings = getSharedPreferences("MyPref", MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //GetUser task = new GetUser(this, USER_ID);
        //task.execute();
        login();
        setItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putInt("USER_ID", Integer.parseInt("0"));
            editor.putBoolean("MENTOR", false);
            editor.putString("NAME", "");
            editor.putString("SECOND_NAME", "");
            editor.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        Context context = null;
        int i = 0;
        int id = item.getItemId();

        if (id == R.id.nav_profile_mentor) {
            fragment = new ProfileMentor();
        } else if (id == R.id.nav_people_mentor) {
            fragment = new PeopleMentor();
        }

        // Вставляем фрагмент, заменяя текущий фрагмент
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.con, fragment).commit();
        }
        // Выделяем выбранный пункт меню в шторке
        item.setChecked(true);
        // Выводим выбранный пункт в заголовке
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        //   myProject();
        return true;
    }

    public void setItem() {
        try {
            int useItem = 0;
            useItem = getIntent().getExtras().getInt("item");
            Menu menu;
            MenuItem item = null;
            Fragment fragment = null;
            switch (useItem) {
                case 0:
                    menu = navigationView.getMenu();
                    item = (MenuItem) menu.findItem(R.id.nav_profile_mentor);
                    fragment = new ProfileMentor();
                    break;
                case 1:
                    menu = navigationView.getMenu();
                    item = (MenuItem) menu.findItem(R.id.nav_people_mentor);
                    fragment = new PeopleMentor();
                    break;
            }
            // Вставляем фрагмент, заменяя текущий фрагмент
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.con, fragment).commit();
            }
            // Выделяем выбранный пункт меню в шторке
            item.setChecked(true);
            // Выводим выбранный пункт в заголовке
            setTitle(item.getTitle());
            drawer.closeDrawer(GravityCompat.START);
        } catch (Exception e){
            Menu menu;
            MenuItem item = null;
            Fragment fragment = null;
            menu = navigationView.getMenu();
            item = (MenuItem) menu.findItem(R.id.nav_profile_mentor);
            fragment = new ProfileMentor();
            // Вставляем фрагмент, заменяя текущий фрагмент
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.con, fragment).commit();
            }
            // Выделяем выбранный пункт меню в шторке
            item.setChecked(true);
            // Выводим выбранный пункт в заголовке
            setTitle(item.getTitle());
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void login(){
        try {

            if (mSettings.contains("USER_ID") && mSettings.getInt("USER_ID", 0) != 0) {
                USER_ID = mSettings.getInt("USER_ID", 0);
                if ((mSettings.contains("NAME") && mSettings.getString("NAME", "") == "") && (mSettings.contains("SECOND_NAME") && mSettings.getString("SECOND_NAME", "") == "")) {
                    GetUser task = new GetUser(this, USER_ID);
                    task.execute();
                } else {
                    user.setFirstName(mSettings.getString("NAME", ""));
                    user.setSecondName(mSettings.getString("SECOND_NAME", ""));
                    System.out.println("USER: " + user.getSecondname() + " " + user.getFirstName());
                    View header = navigationView.getHeaderView(0);
                    title_name = (TextView)header.findViewById(R.id.title_name);
                    title_name.setText(user.getSecondname() + " " + user.getFirstName());
                }
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUserCompleted(User us) {
        user = us;
        user.setRaiting(100);
        System.out.println("USER: " + user.getSecondname() + " " + user.getFirstName());
        View header = navigationView.getHeaderView(0);
        title_name = (TextView)header.findViewById(R.id.title_name);
        title_name.setText(user.getSecondname() + " " + user.getFirstName());
    }

    @Override
    public void onUserError(String error) {

    }
}
