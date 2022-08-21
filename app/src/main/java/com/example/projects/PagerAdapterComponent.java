package com.example.projects;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapterComponent extends FragmentPagerAdapter {
    ComponentsInfo componentsInfo;
    private int numOfTabs;

    public PagerAdapterComponent(FragmentManager fm, int numOfTabs, ComponentsInfo componentsInfo) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.componentsInfo = componentsInfo;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DescriptionComponent descriptionComponent = new DescriptionComponent();
                componentsInfo.descriptionListener = descriptionComponent.getListener();
                return descriptionComponent;
            case 1:
                CharacteristicsComponent characteristicsComponent = new CharacteristicsComponent();
                componentsInfo.characteristicsListener = characteristicsComponent.getListener();
                return characteristicsComponent;
            case 2:
                DocumentationComponent documentationComponent = new DocumentationComponent();
                componentsInfo.documentationListener = documentationComponent.getListener();
                return documentationComponent;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
