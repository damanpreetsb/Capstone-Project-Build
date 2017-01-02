package com.example.daman.capstone;


import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daman.capstone.data.FavContract;
import com.example.daman.capstone.data.FavDBHelper;
import com.example.daman.capstone.data.FavouritesTable;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView mRecyclerView;
    private FavAdapter favAdapter;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fav, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.favrecyclerview);
        mRecyclerView.setHasFixedSize(true);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            llm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        } else {
            llm = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        }
        mRecyclerView.setLayoutManager(llm);

        favAdapter = new FavAdapter(getActivity(), id, name, description, newsurl, image, null);
        mRecyclerView.setAdapter(favAdapter);

        data();

        return rootView;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), FavouritesTable.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favAdapter.swapCursor(null);
    }

    public void data() {

        Cursor cursor = getContext().getContentResolver().query(FavouritesTable.CONTENT_URI, null, null, null, null);
        List<FavDBHelper> testRows = FavouritesTable.getRows(cursor, true);
        for (FavDBHelper element : testRows) {

            name.add(element.title);
            description.add(element.description);
            id.add(element.author);
            newsurl.add(element.url);
            image.add(element.image);

        }
    }
}
