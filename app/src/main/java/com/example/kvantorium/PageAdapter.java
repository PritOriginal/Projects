package com.example.kvantorium;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    ProjectActivity projectActivity;
    ComponentsProject componentsProject;
    private int numOfTabs;

    public PageAdapter(FragmentManager fm, int numOfTabs, ProjectActivity projectActivity) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.projectActivity = projectActivity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                componentsProject = new ComponentsProject();
                return componentsProject;
            case 1:
                ObjectivesProject objectivesProject = new ObjectivesProject();
                projectActivity.ObjectiveListener = objectivesProject.getCreateListener();
                return objectivesProject;
            case 2:
                return new TeammatesProject();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    public void updateComponents() {
        componentsProject.update();
    }
}
