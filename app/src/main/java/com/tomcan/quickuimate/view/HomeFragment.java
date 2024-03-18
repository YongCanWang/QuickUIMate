package com.tomcan.quickuimate.view;

import android.view.View;

import androidx.databinding.Observable;
import androidx.lifecycle.Observer;

import com.tomcan.quickui.v.QuickFragment;
import com.tomcan.quickuimate.R;
import com.tomcan.quickuimate.databinding.FragmentHomeBinding;
import com.tomcan.quickuimate.viewmodel.HomeViewModel;

/**
 * @author Tom灿
 * @description: 框架示例-View
 * @date :2024/3/15 17:50
 */
public class HomeFragment extends QuickFragment<FragmentHomeBinding, HomeViewModel> {

    @Override
    public int layout() {
        return R.layout.fragment_home;
    }

    @Override
    public void setView() {
        binding.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.getName();
                vm.getAge();
                vm.getAddress();
                vm.getUser();
            }
        });
    }

    @Override
    public void function() {
        vm.getNameObs().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                binding.tvName.setText(vm.getNameObs().get());
            }
        });
        vm.getAgeLive().observeForever(integer -> {
            binding.tvAge.setText(integer + "岁");
        });
        vm.getAddressLive().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvAddress.setText(s);
            }
        });
        vm.getUserBeanObs().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
//                binding.setUserBean(sender);
//                binding.setUserBean((UserBean) sender);
                binding.setUserBean(vm.getUserBeanObs().get());
            }
        });

        vm.getName();
        vm.getAge();
        vm.getAddress();
        vm.getUser();
    }

    @Override
    public void resume() {

    }

    @Override
    public void loseTrack() {

    }

    @Override
    public void loseView() {

    }

    @Override
    public void finish() {

    }

    @Override
    public boolean back() {
        return false;
    }
}
