package com.tomcan.quickuimate.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MutableLiveData;

import com.tomcan.quickui.vm.QuickViewModel;
import com.tomcan.quickuimate.bean.UserBean;
import com.tomcan.quickuimate.model.HomeModel;


/**
 * @author Tom灿
 * @description: 框架示例-ViewModel
 * @date :2024/3/15 17:54
 */
public class HomeViewModel extends QuickViewModel<HomeModel> {
    private ObservableField<String> nameObs = new ObservableField<>();
    private MutableLiveData<Integer> ageLive = new MutableLiveData<>();
    private MutableLiveData<String> addressLive = new MutableLiveData<>();
    private ObservableField<UserBean> userBeanObs = new ObservableField<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        nameObs.set("Tom");
        nameObs.notifyPropertyChanged(BR.homeViewModel);
        ageLive.setValue(18);
    }

    public void getName() {
        m.getName(nameObs);
    }

    public void getAge() {
        m.getAge(ageLive);
    }

    public void getAddress() {
        m.getAddress(addressLive);
    }

    public void getUser() {
        m.getUser(userBeanObs);
    }


    public ObservableField<String> getNameObs() {
        return nameObs;
    }

    public MutableLiveData<Integer> getAgeLive() {
        return ageLive;
    }

    public MutableLiveData<String> getAddressLive() {
        return addressLive;
    }

    public ObservableField<UserBean> getUserBeanObs() {
        return userBeanObs;
    }

}
