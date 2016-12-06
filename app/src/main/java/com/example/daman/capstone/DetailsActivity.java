package com.example.daman.capstone;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.WindowManager;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;
    Bundle bundle;
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private ArrayList<String> image = new ArrayList<>();
    private ArrayList<String> author = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        bundle = getIntent().getBundleExtra("BUNDLE");
        name = bundle.getStringArrayList("TITLE");
        image = bundle.getStringArrayList("IMAGE");
        description = bundle.getStringArrayList("DESCRIPTION");
        author = bundle.getStringArrayList("AUTHOR");

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), name, image, description, author);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageMargin((int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        mPager.setPageMarginDrawable(new ColorDrawable(0x22000000));

    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<String> name = new ArrayList<>();
        private ArrayList<String> description = new ArrayList<>();
        private ArrayList<String> image = new ArrayList<>();
        private ArrayList<String> author = new ArrayList<>();
        public MyPagerAdapter(FragmentManager fm, ArrayList<String> name, ArrayList<String> image,
                              ArrayList<String> description, ArrayList<String> author) {
            super(fm);
            this.name = name;
            this.image = image;
            this.description = description;
            this.author = author;
        }

        @Override
        public Fragment getItem(int position) {
            return DetailsFragment.newInstance(name.get(position), image.get(position),
                    description.get(position), author.get(position));
        }

        @Override
        public int getCount() {
            return (name != null) ? name.size() : 0;
        }
    }
}
