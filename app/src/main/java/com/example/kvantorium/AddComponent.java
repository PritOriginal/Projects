package com.example.kvantorium;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kvantorium.server.AddComponentProject;
import com.example.kvantorium.server.ChangeProject;
import com.example.kvantorium.server.GetAllComponents;

import java.util.ArrayList;
import java.util.List;

public class AddComponent extends AppCompatActivity implements View.OnClickListener, OnComponentsListener, android.widget.SearchView.OnCloseListener, android.widget.SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    FloatingActionButton addComponents;
    android.widget.SearchView searchView;
    SQLiteDatabase database;
    RVAdapterAllComponents adapter;
    ProgressBar progressBar;
    DBHelper dbHelper;
    List<Component> allComponent = new ArrayList<Component>();
    ArrayList<Integer> idComponent = new ArrayList<Integer>();
    ArrayList<Integer> countComponent = new ArrayList<Integer>();
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_component);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_components);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        addComponents = (FloatingActionButton) findViewById(R.id.addComponentsInProject);
        addComponents.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.addRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(llm);
        searchView = (android.widget.SearchView)findViewById(R.id.searchComponents);
        searchView.setOnQueryTextListener(this);
        progressBar = (ProgressBar)findViewById(R.id.progressBarAddComponents);
        progressBar.setProgress(0);
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        id = getIntent().getExtras().getInt("id");
        GetAllComponents task = new GetAllComponents(this, progressBar);
        task.execute();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addComponentsInProject:
                int i = 0;
                adapter.setComponents(allComponent);
                ArrayList<Integer> number = adapter.getCountUse();
                ArrayList<Integer> idComp = adapter.getIdComponents();
                while (i < adapter.getItemCount()) {
                    System.out.println("count: " + number);
                    if (number.get(i) != 0) {
                        idComponent.add(idComp.get(i));
                        countComponent.add(number.get(i));
                    }
                    i++;
                }
                AddComponentProject task = new AddComponentProject(id, idComponent, countComponent);
                task.execute();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                this.finish();
                break;
        }
    }

    @Override
    public void onComponentsCompleted(ArrayList<Component> components) {
        allComponent = components;
        adapter = new RVAdapterAllComponents(this, allComponent);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onComponentsError(String error) {

    }

    @Override
    public void onComponentChangeNumberCompleted(int number, int index, Component c) {
        
    }

    @Override
    public void onComponentChangeNumberError(String error) {

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        final List<Component> filteredModelList = filter(allComponent, s);
        adapter.setComponents(filteredModelList);
        return true;
    }

    private static List<Component> filter(List<Component> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Component> filteredModelList = new ArrayList<>();
        for (Component model : models) {
            final String text = model.getNameComponent().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onClose() {
        adapter.setComponents(allComponent);
        return true;
    }
}