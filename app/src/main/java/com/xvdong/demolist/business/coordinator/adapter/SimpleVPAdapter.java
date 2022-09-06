package com.xvdong.demolist.business.coordinator.adapter;

import com.xvdong.demolist.business.coordinator.fragment.TabFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Created by xvDong on 2022/9/6.
 */

public class SimpleVPAdapter extends FragmentStateAdapter {

    private int mItemCount;

    public SimpleVPAdapter(@NonNull FragmentActivity fragmentActivity,int itemCount) {
        super(fragmentActivity);
        mItemCount = itemCount;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new TabFragment();
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }


}
