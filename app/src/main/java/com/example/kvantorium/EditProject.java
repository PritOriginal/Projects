package com.example.kvantorium;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.kvantorium.server.ChangeProject;
import com.example.kvantorium.server.GetProject;

public class EditProject extends AppCompatActivity implements OnProjectListener {
    public int USER_ID;
    EditText nameProject;
    EditText descriptionProject;
    DBHelper dbHelper;
    SQLiteDatabase database;
    Project project = new Project();
    int id;
    String id_;
    final String componentsName[] = new String[]
            {"Ардуино",
                    "Компьютер",
                    "Светодиоды"};
    final int allNumber[] = new int[]
            {100,
                    50,
                    5000};
    final int useNumber[] = new int[]
            {10,
                    10,
                    100};
    //Bundle ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit_project);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nameProject = (EditText)findViewById(R.id.editNameProject);
        descriptionProject = (EditText)findViewById(R.id.editDescriptionProject);
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        USER_ID = getIntent().getExtras().getInt("idUser");
        id = getIntent().getExtras().getInt("id");
        id_ = String.valueOf(id);
        GetProject task = new GetProject(this, id);
        task.execute();
    }
    public void setProject() {
        nameProject.setText(project.getName());
        descriptionProject.setText(project.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_delete_project:
                database.delete(dbHelper.TABLE_NAME, "id =" + id, null );
                dbHelper.close();
                Intent intent1 = new Intent(EditProject.this, Main.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void editProject(View view) {
        String name = nameProject.getText().toString();
        String description = descriptionProject.getText().toString();
        ChangeProject task = new ChangeProject(id, name, description);
        task.execute();
        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("description", description);
        setResult(RESULT_OK, intent);
        this.finish();
    }

    @Override
    public void onProjectCompleted(Project proj) {
        project = proj;
        setProject();
    }


    @Override
    public void onProjectError(String error) {

    }
}

