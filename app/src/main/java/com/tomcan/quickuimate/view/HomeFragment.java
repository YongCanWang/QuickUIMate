package com.tomcan.quickuimate.view;

import android.content.Intent;
import android.view.View;

import androidx.databinding.Observable;
import androidx.lifecycle.Observer;

import com.tomcan.frame.v.QuickFragment;
import com.tomcan.quickuimate.databinding.FragmentHomeBinding;
import com.tomcan.quickuimate.viewmodel.HomeViewModel;

/**
 * @author Tom灿
 * @description: 框架示例-View
 * @date :2024/3/15 17:50
 */
public class HomeFragment extends QuickFragment<FragmentHomeBinding, HomeViewModel> {

    @Override
    public FragmentHomeBinding getLayout() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onStarted() {
        binding.title.setOnClickListener(v -> {
            viewModel.getName();
            viewModel.getAge();
            viewModel.getAddress();
            viewModel.getUser();
        });

        viewModel.getNameObs().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                binding.tvName.setText(viewModel.getNameObs().get());
            }
        });
        viewModel.getAgeLive().observeForever(integer -> {
            binding.tvAge.setText(integer + "岁");
        });
        viewModel.getAddressLive().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvAddress.setText(s);
            }
        });
        viewModel.getUserBeanObs().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
//                binding.setUserBean(sender);
//                binding.setUserBean((UserBean) sender);
                binding.setUserBean(viewModel.getUserBeanObs().get());
            }
        });

        viewModel.getName();
        viewModel.getAge();
        viewModel.getAddress();
        viewModel.getUser();
        binding.butWaterfall.setOnClickListener(view -> startActivity(new Intent(requireContext(), WaterfallActivity.class)));
        binding.butSkin.setOnClickListener(view -> startActivity(new Intent(requireContext(), SkinActivity.class)));
        binding.butGridList.setOnClickListener(view -> startActivity(new Intent(requireContext(), GridListActivity.class)));
        binding.butWebp.setOnClickListener(view -> startActivity(new Intent(requireContext(), WebpActivity.class)));
        binding.butBottomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBottomFragmentDialog myBottomFragmentDialog = new MyBottomFragmentDialog();
                myBottomFragmentDialog.show(requireActivity().getSupportFragmentManager(),
                        myBottomFragmentDialog.getTag());

//                MyBottomDialog myBottomDialog = new MyBottomDialog(requireContext());
//                myBottomDialog.show();
            }
        });
    }

    @Override
    public void onReStart() {

    }

    @Override
    public boolean backPressedEnabled() {
        return super.backPressedEnabled();
    }
}