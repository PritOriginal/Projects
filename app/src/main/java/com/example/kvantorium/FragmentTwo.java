package com.example.kvantorium;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.kvantorium.server.GetAllComponents;

import java.util.ArrayList;
import java.util.List;

public class FragmentTwo extends Fragment implements OnComponentsListener, android.widget.SearchView.OnCloseListener, android.widget.SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    RVAdapterComponents adapter;
    List<Component> allComponent = new ArrayList<Component>();
    android.widget.SearchView searchView;
    ProgressBar progressBar;
    Main main;

    public FragmentTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_all_components, container,
                false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_allComponents);
        LinearLayoutManager llm = new LinearLayoutManager(main.getApplicationContext());
        recyclerView.setLayoutManager(llm);
        searchView = (android.widget.SearchView) view.findViewById(R.id.searchComponents);
        searchView.setOnQueryTextListener(this);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBarAllComponents);
        progressBar.setProgress(0);
        System.out.println("Message sent to the server : ");
        GetAllComponents testTask = new GetAllComponents(this, progressBar);
        testTask.execute();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Main) {
            this.main = (Main) context;
        }
    }

    public void updat(ArrayList<Component> components) {
        adapter = new RVAdapterComponents(main, allComponent, false);
        recyclerView.setAdapter(adapter);
    }

    void updateComp(String data) {
        String[] str = data.split("");

        boolean k = false;
        int j = 1;
        String data_ = null;
        for (int i = 0; i < data.length(); i++) {
            if (str[i] == "?") {
                k = true;
            }
            if (k) {
                data_ += str[i];

                if (str[i] == "?") {
                    if (j == 1) {
                        Component c = new Component();
                        c.setNameComponent(data_);
                        j = 2;
                    }
                    if (j == 2) {
                        Component c = new Component();
                        c.setNumber(Integer.parseInt(data_));
                        allComponent.add(c);
                        j = 1;
                    }
                }
            }
        }
    }

    @Override
    public void onComponentsCompleted(ArrayList<Component> components) {
        allComponent = components;
        System.out.println("Components size " + components.size());
        adapter = new RVAdapterComponents(main, allComponent, false);
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
