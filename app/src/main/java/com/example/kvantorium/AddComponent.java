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
        //allComponent = dbHelper.getAllComponents(database);
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
                //intent.putExtra("id", id);
                //startActivity(intent);
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
                    //boolean haveComp = false;
                    //int id_e = Integer.parseInt(String.valueOf("1" + i));
                    //int id_t = Integer.parseInt(String.valueOf("2" + i));
                    //EditText e = (EditText)recyclerView.findViewById(id_e);
                    //TextView t = (TextView)recyclerView.findViewById(id_t);
                    //String nameComp = t.getText().toString();
                    //String num = e.getText().toString();
                    //int number = Integer.parseInt(num);
                    //int id_e = Integer.parseInt("1" + i);
                    //recyclerView.getAdapter().notifyDataSetChanged();
                    //EditText e = recyclerView.getLayoutManager().findViewByPosition(i).findViewById(R.id.addComponent_number);
                    //EditText e = (EditText)view.findViewById(R.id.addComponent_number);
                    System.out.println("count: " + number);
                    if (number.get(i) != 0) {
                        idComponent.add(idComp.get(i));
                        countComponent.add(number.get(i));
                    }
                    i++;
                }
                AddComponentProject task = new AddComponentProject(id, idComponent, countComponent);
                task.execute();
                Intent intent = new Intent(this, ProjectActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                /*
                int i = 0;
                while (i < allComponent.size()) {
                    boolean haveComp = false;
                    int id_e = Integer.parseInt(String.valueOf("1" + i));
                    int id_t = Integer.parseInt(String.valueOf("2" + i));
                    EditText e = (EditText) findViewById(id_e);
                    TextView t = (TextView) findViewById(id_t);
                    String nameComp = t.getText().toString();
                    String num = e.getText().toString();
                    int number = Integer.parseInt(num);
                    if (number != 0) {
                        Cursor cursor = database.query(DBHelper.TABLE_NAME3, null, null, null, null, null, null);
                        if (cursor.moveToFirst()) {
                            do {
                                int idColIndexComponent = cursor.getColumnIndex(DBHelper.ID);
                                int idComp = cursor.getInt(idColIndexComponent);
                                if (i + 1 == idComp) {
                                    Cursor cursor1 = database.query(DBHelper.TABLE_NAME2, null, null, null, null, null, null);
                                    if (cursor1.moveToFirst()) {
                                        do {
                                            int idProjectColIndexComp = cursor1.getColumnIndex(DBHelper.ID_PROJECT);
                                            int idProject = cursor1.getInt(idProjectColIndexComp);
                                            if (idProject == id) {
                                                int idColIndexComp = cursor1.getColumnIndex(DBHelper.ID);
                                                int idComponent = cursor1.getInt(idColIndexComp);
                                                int numberColIndexComp = cursor1.getColumnIndex(DBHelper.NUMBER);
                                                int numberComp = cursor1.getInt(numberColIndexComp);
                                                int idCompColIndexComp = cursor1.getColumnIndex(DBHelper.ID_COMPONENT);
                                                int idComp_ = cursor1.getInt(idCompColIndexComp);
                                                if (idComp == idComp_) {
                                                    ContentValues contentValues = new ContentValues();
                                                    contentValues.put(DBHelper.NUMBER, numberComp + number);
                                                    database.update(DBHelper.TABLE_NAME2, contentValues, DBHelper.ID + " = " + idComponent, null);
                                                    haveComp = true;
                                                }
                                            }
                                        } while (cursor1.moveToNext());
                                        if (haveComp == false) {
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put(DBHelper.ID_PROJECT, id);
                                            contentValues.put(DBHelper.ID_COMPONENT, i + 1);
                                            contentValues.put(DBHelper.NUMBER, number);
                                            database.insert(DBHelper.TABLE_NAME2, null, contentValues);
                                        }
                                    } else {
                                        cursor1.close();
                                    }
                                }
                            } while (cursor.moveToNext());
                        } else {
                            cursor.close();
                        }
                    }
                    dbHelper.updateComponent(database, i + 1, number);
                    i++;
                }
                Intent intent = new Intent(AddComponent.this, EditProject.class);
                //           id++;
                intent.putExtra("id", id);
                startActivity(intent);
                 */
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