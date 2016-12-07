package com.example.daman.capstone;

import android.content.Context;
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
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;
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

        String source = getIntent().getStringExtra("SOURCE_NAME");

        data(this, source);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), source, name, image, description, author);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageMargin((int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        mPager.setPageMarginDrawable(new ColorDrawable(0x22000000));

    }

    public void data(final Context context, String source) {
        try {
            final String BASE_URL = "https://newsapi.org/v1/articles?source="+source+"&apiKey=ed3d44b41e2b475bbdc76c8e4935a5c1";

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    BASE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String syncresponse = object.getString("articles");
                                JSONArray a1obj = new JSONArray(syncresponse);
                                for (int j = 0; j < a1obj.length(); j++) {
                                    JSONObject obj = a1obj.getJSONObject(j);
                                    author.add(obj.getString("author"));
                                    name.add(obj.getString("title"));
                                    description.add(obj.getString("description"));
//                                    newsurl.add(obj.getString("url"));
                                    image.add(obj.getString("urlToImage"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }mPagerAdapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NoConnectionError) {
                        Toast.makeText(context, "No internet connections!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        String source;
        private ArrayList<String> name = new ArrayList<>();
        private ArrayList<String> description = new ArrayList<>();
        private ArrayList<String> image = new ArrayList<>();
        private ArrayList<String> author = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm, String source, ArrayList<String> name, ArrayList<String> image,
                              ArrayList<String> description, ArrayList<String> author) {
            super(fm);
            this.source = source;
            this.name = name;
            this.image = image;
            this.description = description;
            this.author = author;
        }

        @Override
        public Fragment getItem(int position) {
            return DetailsFragment.newInstance(source, name.get(position), image.get(position),
                    description.get(position), author.get(position));
        }

        @Override
        public int getCount() {
            return (name != null) ? name.size() : 0;
        }
    }
}
