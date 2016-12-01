package com.example.daman.capstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle bundle = getIntent().getBundleExtra("BUNDLE");

        if (savedInstanceState == null) {
            DetailsFragment moviesFragment = new DetailsFragment();
            moviesFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_container, moviesFragment)
                    .commit();
        }
    }
}
