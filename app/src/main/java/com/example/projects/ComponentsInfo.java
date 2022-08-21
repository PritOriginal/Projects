package com.example.projects;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.projects.server.GetComponent;
import com.example.projects.server.GetComponentByComponentProject;

import java.util.ArrayList;

public class ComponentsInfo extends AppCompatActivity implements OnComponentsListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapterComponent pageAdapter;
    OnComponentsListener descriptionListener;
    OnComponentsListener characteristicsListener;
    OnComponentsListener documentationListener;
    public Component component = null;
    public int id;
    boolean fromProject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_components_info);
        id = getIntent().getExtras().getInt("id");
        fromProject = getIntent().getExtras().getBoolean("project");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_component);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tabLayout = (TabLayout)findViewById(R.id.tabsComponent);
        TabItem tabComponents = (TabItem)findViewById(R.id.tabDescription);
        TabItem tabObjectives = (TabItem)findViewById(R.id.tabCharacteristics);
        TabItem tabTeams = (TabItem)findViewById(R.id.tabDocumentation);
        viewPager = (ViewPager)findViewById(R.id.viewPagerComponent);

        pageAdapter = new PagerAdapterComponent(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (fromProject) {
            GetComponentByComponentProject getComponentByComponentProject = new GetComponentByComponentProject(this,this, id);
            getComponentByComponentProject.execute();
        } else {
            GetComponent getComponent = new GetComponent(this,this, id);
            getComponent.execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.component_toolbar_menu, menu);
        return true;
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
    public void onComponentsCompleted(ArrayList<Component> components) {
        component = components.get(0);
        descriptionListener.onComponentsCompleted(components);
        characteristicsListener.onComponentsCompleted(components);
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
}
