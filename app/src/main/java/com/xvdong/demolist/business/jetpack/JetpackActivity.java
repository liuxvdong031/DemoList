package com.xvdong.demolist.business.jetpack;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.xvdong.demolist.R;
import com.xvdong.demolist.business.coordinator.adapter.SimpleRVAdapter;
import com.xvdong.demolist.business.jetpack.viewmodel.JetpackViewModel;
import com.xvdong.demolist.core.data.bean.DataBean;
import com.xvdong.demolist.core.util.GlideEngine;
import com.xvdong.demolist.databinding.ActivityJetpackBinding;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class JetpackActivity extends AppCompatActivity {

    private JetpackViewModel mViewModel;
    private ActivityJetpackBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ViewModel Activity跟ViewModel产生关系的方式
        mViewModel = new ViewModelProvider(this).get(JetpackViewModel.class);

        //DataBinding xml文件的数据绑定
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_jetpack);
        mBinding.setViewModel(mViewModel);

        //Lifecycle让view 用于Activity的生命周期感应
        getLifecycle().addObserver(mBinding.lctv);

        mViewModel.getRepoList();

        for (int i = 0; i < 20; i++) {
            UserBean userBean = new UserBean(String.valueOf(i), i);
            test(userBean);
        }
        mBinding.kv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.haha.set("wo shi ni da ye");
            }
        });

        mBinding.tvEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.tvEmoji.setText(mBinding.et.getText().toString());
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, false);
        mBinding.recyclerView.setLayoutManager(gridLayoutManager);
        ArrayList<DataBean> dataBeans = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            DataBean dataBean = new DataBean("我是" + i, GlideEngine.DEFAULT_IMAGE_URL);
            dataBeans.add(dataBean);
        }
//        PagingScrollHelper pagingScrollHelper = new PagingScrollHelper();
//        pagingScrollHelper.setUpRecycleView(mBinding.recyclerView);
//        pagingScrollHelper.setScrollWidth(SizeUtils.dp2px(300));
        mBinding.recyclerView.setAdapter(new SimpleRVAdapter(this,dataBeans));

        UserBean userBean = new UserBean("111", 1);
//        mBinding.setUserBean(userBean);
        mBinding.included.setUserBean(userBean);
    }

    public void test (UserBean bean){
//        LogUtils.json(bean);
        if (true){
            Observable.just(bean)
                    .subscribeOn(Schedulers.io())
                    .map(new Function<UserBean, UserBean>() {
                        @Override
                        public UserBean apply(UserBean userBean) throws Exception {
                            Random random = new Random();
                            Thread.sleep(random.nextInt(100000));
                            userBean.setNum(userBean.getAge() * 10);
                            if (userBean.age % 2== 0){
                                throw new  RuntimeException("111");
                            }
//                            LogUtils.json("TAG",bean);
                            return userBean;
                        }
                    }).subscribe(new Consumer<UserBean>() {
                        @Override
                        public void accept(UserBean userBean) throws Exception {
                            LogUtils.json(userBean.hashCode());
                            LogUtils.json(bean.hashCode());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            LogUtils.json(throwable);
                        }
                    });
        }

    }

    public class UserBean {
        private String name;
        private int age;
        private int num;

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public UserBean(String name, int age) {
            this.name = name;
            this.age = age;

        }
    }
}