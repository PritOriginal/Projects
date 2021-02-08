package com.example.kvantorium;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.kvantorium.server.AddProject;

public class CreateProject extends AppCompatActivity implements View.OnClickListener  {
    int USER_ID;
    EditText nameProject;
    EditText descriptionProject;
    FloatingActionButton create;
    DBHelper dbHelper;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_project);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        USER_ID = getIntent().getExtras().getInt("idUser");
        nameProject = (EditText)findViewById(R.id.nameProject);
        descriptionProject = (EditText)findViewById(R.id.descriptionProject);
        create = (FloatingActionButton) findViewById(R.id.createProject);
        create.setOnClickListener(this);
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
    }


    @Override
    public void onClick(View v) {
        ContentValues contentValues = new ContentValues();
        switch (v.getId()) {
            case R.id.createProject:
                String name = nameProject.getText().toString();
                String description = descriptionProject.getText().toString();
                AddProject task = new AddProject(USER_ID, name, description);
                task.execute();
                Intent intent = new Intent(CreateProject.this, Main.class);
                startActivity(intent);
                break;
        }
        dbHelper.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
