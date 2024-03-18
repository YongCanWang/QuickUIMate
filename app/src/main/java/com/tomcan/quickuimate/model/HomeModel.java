package com.tomcan.quickuimate.model;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import com.tomcan.quickui.m.QuickModel;
import com.tomcan.quickuimate.BR;
import com.tomcan.quickuimate.bean.UserBean;

/**
 * @author Tom灿
 * @description: 框架示例-Model
 * @date :2024/3/18 13:15
 */
public class HomeModel extends QuickModel {
    int index = 0;
    public void getName(ObservableField<String> nameObs) {
        nameObs.set("Li Hua" + ++index);
//        nameObs.notifyPropertyChanged(BR._all);
        nameObs.notifyPropertyChanged(BR.homeViewModel);
    }

    public void getAge(MutableLiveData<Integer> ageLive) {
        ageLive.setValue(24 + ++index);
    }

    public void getAddress(MutableLiveData<String> addressLive) {
        addressLive.setValue("北京亦庄荣京东街" + ++index + "号");
    }

    public void getUser(ObservableField<UserBean> userBeanObs) {
        userBeanObs.set(new UserBean("Tom" + ++index, 18 + ++index, "北京亦庄荣京东街朝林广场" + ++index));
    }

}
