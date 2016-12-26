package com.example.daman.capstone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.daman.capstone.sync.NewsSyncAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            NewsFragment moviesFragment = new NewsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, moviesFragment)
                    .commit();
        }

        NewsSyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.bookmark){
            Intent intent = new Intent(this, FavActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
