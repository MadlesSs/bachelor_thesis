package com.madless.erasmusapp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;

public class StudentsList extends AppCompatActivity {

    private TabLayout tabLayout;

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StudentsFragment(), "Unchecked");
        adapter.addFragment(new CheckedStudentsFragment(), "Checked");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
