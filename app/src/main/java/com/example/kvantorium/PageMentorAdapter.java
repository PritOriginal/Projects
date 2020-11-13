package com.example.kvantorium;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageMentorAdapter extends FragmentPagerAdapter {

    ProjectMentorActivity projectActivity;
    private int numOfTabs;

    public PageMentorAdapter(FragmentManager fm, int numOfTabs, ProjectMentorActivity projectActivity) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.projectActivity = projectActivity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ComponentsProjectMentor();
            case 1:
                ObjectivesProjectMentor objectivesProject = new ObjectivesProjectMentor();
                projectActivity.ObjectiveListener = objectivesProject.getCreateListener();
                return objectivesProject;
            case 2:
                return new TeammatesProjectMentor();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}