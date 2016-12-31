package com.example.daman.capstone;


import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.daman.capstone.data.FavDBHelper;
import com.example.daman.capstone.data.FavouritesTable;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FavAdapter newsAdapter;
    StaggeredGridLayoutManager llm;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> description = new ArrayList<String>();
    ArrayList<String> newsurl = new ArrayList<String>();
    ArrayList<String> image = new ArrayList<String>();


    public FavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fav, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.favrecyclerview);
        mRecyclerView.setHasFixedSize(true);
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            llm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        else{
            llm = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        }
        mRecyclerView.setLayoutManager(llm);

        newsAdapter = new FavAdapter(getActivity(), id, name, description, newsurl, image);
        mRecyclerView.setAdapter(newsAdapter);

        data();

        return rootView;

    }


    public void data() {

        Cursor cursor = getContext().getContentResolver().query(FavouritesTable.CONTENT_URI, null, null, null, null);
        List<FavDBHelper> testRows = FavouritesTable.getRows(cursor, true);
        for (FavDBHelper element : testRows){

            name.add(element.title);
            description.add(element.description);
            id.add(element.author);
            newsurl.add(element.url);
            image.add(element.image);

        }
    }
}
