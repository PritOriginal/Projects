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
                //projectsDB.execSQL("CREATE TABLE");
                //ContentValues P = new ContentValues();
                //P.put("name", name);
                //P.put("description", description);
                AddProject task = new AddProject(USER_ID, name, description);
                task.execute();
                /* база
                contentValues.put(DBHelper.ID_USER ,USER_ID);
                contentValues.put(DBHelper.NAME, name);
                contentValues.put(DBHelper.DESCRIPTION, description);
                database.insert(DBHelper.TABLE_NAME, null, contentValues);
                 */
                //m.addProject(p);
                //  intent.putExtra("name",name);
                //intent.putExtra("description",description);
                Intent intent = new Intent(CreateProject.this, Main.class);
                startActivity(intent);
                break;
        }
        dbHelper.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id_ = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                //Intent intent = new Intent(this, Test.class);
                //startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
