package com.xvdong.demolist.business.coordinator.fragment;

import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gyf.immersionbar.ImmersionBar;
import com.xvdong.demolist.R;
import com.xvdong.demolist.business.coordinator.adapter.SimpleVPAdapter;
import com.xvdong.demolist.core.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

/**
 * Created by xvDong on 2022/9/6.
 */

public class CoordinatorLayoutDemoFragment2 extends BaseFragment {

   private TabLayout mTabLayout;
   private ViewPager2 mViewPager;
   private AppBarLayout mAppBarLayout;
   private CollapsingToolbarLayout mCollapsingToolbarLayout;
   private final String[] titles = {"头条", "新闻", "娱乐"};

   @Override
   public int getLayoutId() {
      return R.layout.fragment_coorbinator2;
   }

   @Override
   public void initView(View view) {
      super.initView(view);
      Toolbar toolbar = view.findViewById(R.id.tool_bar);
      mTabLayout = view.findViewById(R.id.tab_layout);
      mViewPager = view.findViewById(R.id.view_pager);
      mAppBarLayout = view.findViewById(R.id.appbar);
      mCollapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar_layout);
      mActivity.setSupportActionBar(toolbar);

   }

   @Override
   public void initListener(View view) {
      super.initListener(view);

      //根据AppBar的状态改变title的显示与隐藏
//      mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
//         if (verticalOffset == 0) {
//            Log.d(TAG, "展开了");
//            mCollapsingToolbarLayout.setTitle("");
//         } else if (Math.abs(verticalOffset) > 0 && Math.abs(verticalOffset) < appBarLayout.getTotalScrollRange()) {
//            Log.d(TAG, "滑动中");
//            mCollapsingToolbarLayout.setTitle("");
//         } else {
//            Log.d(TAG, "折叠了");
//            mCollapsingToolbarLayout.setTitle("ToolBar Title");
//         }
//      });
   }

   @Override
   public void initData() {
      super.initData();
      ImmersionBar.with(this).statusBarColor(R.color.translate).statusBarDarkFont(true).init();
      mViewPager.setAdapter(new SimpleVPAdapter(mActivity,titles.length));
      new TabLayoutMediator(mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
         @Override
         public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            tab.setText(titles[position]);
         }
      }).attach();
   }
}
