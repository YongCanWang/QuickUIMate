package com.tomcan.quickuimate.mate;


import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tomcan.quickuimate.R;
import com.tomcan.quickuimate.obs.OnStackObservable;
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
    private FrameLayout containerLayout;
    private static final ObservableArrayList<NaviBaseFragment> stacks = new ObservableArrayList<>();

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

    public void setContainer(FrameLayout containerLayout) {
        this.containerLayout = containerLayout;
        this.container = containerLayout.getId();
    }

    public void setAttachActivity(AppCompatActivity activity) {
        this.activity = activity;
        fragmentManager = activity.getSupportFragmentManager();
    }

    public void add(NaviBaseFragment frameLayout) {
        fragmentManager.beginTransaction()
                .add(container, frameLayout)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .show(frameLayout).commit();
        stacks.add(frameLayout);
    }

    public void add(NaviBaseFragment fragment, int flContainer) {
        fragmentManager.beginTransaction()
                .add(flContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .show(fragment).commit();
        stacks.add(fragment);
    }

    public void add(NaviBaseFragment fragment, FrameLayout flContainer) {
        fragmentManager.beginTransaction()
                .add(flContainer.getId(), fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .show(fragment).commit();
        stacks.add(fragment);
    }

    public void add(Fragment fragment) {
        fragmentManager.beginTransaction()
                .add(container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .show(fragment).commit();
    }


    public void show(NaviBaseFragment fragment, int containerViewId) {
        if (fragment.isAdded()) {
            fragmentManager.beginTransaction().addToBackStack(null).show(fragment).commit();
        } else {
            fragmentManager.beginTransaction().addToBackStack(null).add(containerViewId, fragment).show(fragment).commit();
        }
        stacks.add(fragment);
    }


    public void replaceStack(NaviBaseFragment fragment) {
        fragmentManager.beginTransaction()
                .replace(container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
        stacks.add(fragment);
    }

    public void replaceStack(NaviBaseFragment fragment, int container) {
        fragmentManager.beginTransaction()
                .replace(container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
        stacks.add(fragment);
    }

    public void replaceStack(NaviBaseFragment fragment, ViewGroup container) {
        fragmentManager.beginTransaction()
                .replace(container.getId(), fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
        stacks.add(fragment);
    }

    public void replaceStack(NaviBaseFragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .replace(container, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
        stacks.add(fragment);
    }

    public void replaceAnimatorStack(NaviBaseFragment fragment) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.fragment_slide_right_enter,
                        R.animator.fragment_slide_left_exit,
                        R.animator.fragment_slide_left_enter,
                        R.animator.fragment_slide_right_exit)
                .replace(container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
        stacks.add(fragment);
    }

    public void pop() {
        activity.getSupportFragmentManager().popBackStack();
        stacks.remove(stacks.get(stacks.size() - 1));
    }

    public void popAll() {
        for (int i = 0; i < stacks.size(); i++) {
            activity.getSupportFragmentManager().popBackStack();
        }
        stacks.clear();
    }

    public void popStacks() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        stacks.clear();
    }

    public void remove() {
        fragmentManager.beginTransaction().remove(stacks.get(stacks.size() - 1)).commit();
        stacks.remove(stacks.size() - 1);
    }

    public void removeAll() {
        for (int i = stacks.size() - 1; i >= 0; i--)
            fragmentManager.beginTransaction().remove(stacks.get(i)).commit();
        stacks.clear();
    }

    public void popRemove() {
        activity.getSupportFragmentManager().popBackStack();
        fragmentManager.beginTransaction().remove(stacks.get(stacks.size() - 1)).commit();
        stacks.remove(stacks.size() - 1);
    }

    public void popRemoveAll() {
        for (int i = stacks.size() - 1; i >= 0; i--) {
            fragmentManager.popBackStack();
            fragmentManager.beginTransaction().remove(stacks.get(i)).commit();
        }
        stacks.clear();
    }


    public void popBottomStacks() {
        for (int i = stacks.size() - 2; i >= 0; i--)
            fragmentManager.popBackStack();

        for (int i = stacks.size() - 2; i >= 0; i--)
            stacks.remove(i);
    }

    public void removeBottomStacks() {
        for (int i = stacks.size() - 2; i >= 0; i--)
            fragmentManager.beginTransaction().remove(stacks.get(i)).commit();

        for (int i = stacks.size() - 2; i >= 0; i--)
            stacks.remove(i);
    }

    public boolean isStacksEmpty() {
        return stacks.size() == 0;
    }

    public void setStacksObservable(OnStackObservable stacksObservable) {
        stacks.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                if (stacksObservable != null) stacksObservable.onChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                if (stacksObservable != null)
                    stacksObservable.onStacksInserted(stacks.get(stacks.size() - 1));
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                if (stacksObservable != null) stacksObservable.onStacksRemoved();
            }
        });
    }


    public FrameLayout getContainerLayout() {
        return containerLayout;
    }


}
