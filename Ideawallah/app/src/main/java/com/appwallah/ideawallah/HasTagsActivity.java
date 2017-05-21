package com.appwallah.ideawallah;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appwallah.ideawallah.fragment.HashTagListFragment;
import com.appwallah.ideawallah.fragment.IdeaListFragment;

public class HasTagsActivity extends AppCompatActivity {

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.hashtags_title));
        setContentView(R.layout.activity_tags);


    }
}
