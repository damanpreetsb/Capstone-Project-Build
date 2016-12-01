package com.example.daman.capstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);

        String source = getIntent().getStringExtra("SOURCE_NAME");

        Bundle bundle = new Bundle();
        bundle.putString("SOURCE",source);

        if (savedInstanceState == null) {
            SourceFragment sourceFragment = new SourceFragment();
            sourceFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.src_container, sourceFragment)
                    .commit();
        }
    }
}
