package com.xvdong.demolist.business.jetpack;

import android.os.Bundle;

import com.xvdong.demolist.R;
import com.xvdong.demolist.business.jetpack.viewmodel.JetpackViewModel;
import com.xvdong.demolist.databinding.ActivityJetpackBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

public class JetpackActivity extends AppCompatActivity {

    private JetpackViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(JetpackViewModel.class);
        ActivityJetpackBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_jetpack);
        binding.setViewModel(mViewModel);
    }
}