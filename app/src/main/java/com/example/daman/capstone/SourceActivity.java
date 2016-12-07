package com.example.daman.capstone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SourceActivity extends AppCompatActivity {

    String source = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);

        source = getIntent().getStringExtra("SOURCE_NAME");


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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.source, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.switch_layout){
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("SOURCE_NAME", source);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
