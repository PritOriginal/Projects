package com.example.kvantorium;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllComponents extends AppCompatActivity {
    Server server;

    //String HOST = "192.168.1.199";
    //String HOST = "91.219.102.45";

    int PORT = 8080;

    RecyclerView recyclerView;
    SQLiteDatabase database;
    RVAdapterAllComponents adapter;
    DBHelper dbHelper;
    List<Component> allComponent = new ArrayList<Component>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_components);
/*
        if (Internet.isOnline(this)) {

            Server server = new Server(HOST, PORT);

            try {

                server.openConnection();
                server.sendData("getAllComponents");
                int components = Integer.valueOf(server.getData());
                int i = 0;
                while (i < components) {
                    Component c = new Component();
                    String n = server.getData();
                    c.setNameComponent(server.getData());
                    int all = Integer.valueOf(server.getData());
                    int use = Integer.valueOf(server.getData());
                    c.setNumber(all - use);
                    allComponent.add(c);
                }

            } catch (Exception e) {
                e.printStackTrace();
                server = null;
            }
        } else {

            dbHelper = new DBHelper(this);
            database = dbHelper.getWritableDatabase();
            allComponent = dbHelper.getAllComponents(database);

        }
*/
/*
        Server server = new Server(HOST, PORT);

        try {

            server.openConnection();
            server.sendData("getAllComponents");
            int components = Integer.valueOf(server.getData());
            int i = 0;
            while (i < components) {
                Component c = new Component();
                String n = server.getData();
                c.setNameComponent(server.getData());
                int all = Integer.valueOf(server.getData());
                int use = Integer.valueOf(server.getData());
                c.setNumber(all - use);
                allComponent.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
            server = null;
        }
*/
      //  recyclerView = (RecyclerView) findViewById(R.id.rv_allComponents);
       // LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
       // recyclerView.setLayoutManager(llm);

        //adapter = new RVAdapterAllComponents(this, allComponent);
        //recyclerView.setAdapter(adapter);
    }
}
