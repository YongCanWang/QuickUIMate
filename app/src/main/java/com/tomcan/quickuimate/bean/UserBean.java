package com.tomcan.quickuimate.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

/**
 * @author Tom灿
 * @description:
 * @date :2024/3/18 17:19
 */
public class UserBean extends BaseObservable {
    private String name;
    private Integer age;
    private String address;

    public UserBean(String name, Integer age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
        notifyPropertyChanged(BR.name);
        notifyPropertyChanged(BR.age);
        notifyPropertyChanged(BR.address);
        notifyChange();
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }
}
