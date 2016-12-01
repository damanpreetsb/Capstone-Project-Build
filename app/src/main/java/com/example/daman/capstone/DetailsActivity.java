package com.example.daman.capstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null) {
            DetailsFragment moviesFragment = new DetailsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_container, moviesFragment)
                    .commit();
        }
    }
}
