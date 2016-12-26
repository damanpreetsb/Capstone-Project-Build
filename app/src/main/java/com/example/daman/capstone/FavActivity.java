package com.example.daman.capstone;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.daman.capstone.data.FavDBHelper;
import com.example.daman.capstone.data.FavouritesTable;

import java.util.List;

public class FavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        if (savedInstanceState == null) {
            FavFragment favFragment = new FavFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fav_container, favFragment)
                    .commit();
        }

//        Cursor cursor = getContentResolver().query(FavouritesTable.CONTENT_URI, null, null, null, null);
//        List<FavDBHelper> testRows = FavouritesTable.getRows(cursor, true);
//        for (FavDBHelper element : testRows)
//            Toast.makeText(this, element.url, Toast.LENGTH_SHORT).show();
    }
}
