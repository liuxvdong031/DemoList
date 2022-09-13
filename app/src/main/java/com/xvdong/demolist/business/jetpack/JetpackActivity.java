package com.xvdong.demolist.business.jetpack;

import android.os.Bundle;

import com.xvdong.demolist.R;
import com.xvdong.demolist.business.jetpack.viewmodel.JetpackViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

public class JetpackActivity extends AppCompatActivity {

    private JetpackViewModel mViewModel;
    private com.xvdong.demolist.databinding.ActivityJetpackBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Activity跟ViewModel产生关系的方式
        mViewModel = new ViewModelProvider(this).get(JetpackViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_jetpack);
        mBinding.setViewModel(mViewModel);

        //Activity跟LifeCycle产生关系的方式
        getLifecycle().addObserver(mBinding.lctv);
    }

}