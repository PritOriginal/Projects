package com.example.kvantorium;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    FloatingActionButton create;
    FloatingActionButton clear;
    DBHelper dbHelper;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    RVAdapter adapter;
    List<Project> projects = new ArrayList<Project>();
    public int USER_ID = 0;
    private int countID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(llm);
        dbHelper = new DBHelper(this);
        create = (FloatingActionButton) findViewById(R.id.create);
        create.setOnClickListener(this);
        database = dbHelper.getWritableDatabase();

        update();
    }
    public void update() {
        projects = dbHelper.getAllProjectUser(database, USER_ID);
        adapter = new RVAdapter(this, projects, false);
        recyclerView.setAdapter(adapter);
        dbHelper.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create:
                Intent intent = new Intent(MainActivity.this, CreateProject.class);
                intent.putExtra("idUser", USER_ID);
                startActivity(intent);
                break;
        }
    }
}
