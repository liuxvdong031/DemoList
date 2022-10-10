package com.xvdong.demolist.business.jetpack.viewmodel;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.LogUtils;
import com.xvdong.demolist.core.http.ApiService;
import com.xvdong.demolist.core.http.RetrofitClient;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xvDong on 2022/9/13.
 */

public class JetpackViewModel extends ViewModel {

    public ObservableField<String> haha = new ObservableField<>("haha");

    @SuppressLint("CheckResult")
    public void getRepoList(){
        RetrofitClient.getInstance()
                .getApiService(ApiService.class)
                .getRepoList("stars","android",10,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repoBean -> {
                    LogUtils.json(repoBean);
                }, throwable -> {
                    LogUtils.json(throwable);
                });
    }
}
