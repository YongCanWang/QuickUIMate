package com.tomcan.quickuimate.mate;


import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tomcan.quickuimate.ui.NaviBaseFragment;


/**
 * @author TomCan
 * @description:
 * @date :2019/4/28 15:36
 */
public class FragmentMate {

    private static FragmentMate fragmentMate;
    private AppCompatActivity activity;
    private FragmentManager fragmentManager;
    private int container;

    private FragmentMate() {
    }


    public static FragmentMate getInstance() {
        if (fragmentMate == null)
            fragmentMate = new FragmentMate();
        return fragmentMate;
    }


    public void setContainer(int container) {
        this.container = container;
    }

    public void setContainer(FrameLayout container) {
        this.container = container.getId();
    }


    public void setAttachActivity(AppCompatActivity activity) {
        this.activity = activity;
        fragmentManager = activity.getSupportFragmentManager();
    }


    public void add(NaviBaseFragment frameLayout) {
        fragmentManager.beginTransaction()
                .add(container, frameLayout)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .show(frameLayout).commit();
    }


    public void show(NaviBaseFragment fragment, int containerViewId) {
        if (fragment.isAdded()) {
            fragmentManager.beginTransaction().show(fragment).commit();
        } else {
            fragmentManager.beginTransaction().add(containerViewId, fragment).show(fragment).commit();
        }
    }

    public void remove(NaviBaseFragment baseFragment) {
        fragmentManager.beginTransaction().remove(baseFragment).commit();
    }


    public void replaceStack(NaviBaseFragment fragment) {
        fragmentManager.beginTransaction()
                .replace(container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    public void replaceStack(NaviBaseFragment fragment, int container) {
        fragmentManager.beginTransaction()
                .replace(container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    public void replaceStack(NaviBaseFragment fragment, ViewGroup container) {
        fragmentManager.beginTransaction()
                .replace(container.getId(), fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    public void replaceStack(NaviBaseFragment frameLayout, String tag) {
        fragmentManager.beginTransaction()
                .replace(container, frameLayout, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }


}
