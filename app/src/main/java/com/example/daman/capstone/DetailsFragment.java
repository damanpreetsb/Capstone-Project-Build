package com.example.daman.capstone;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daman.capstone.data.FavContract;
import com.example.daman.capstone.data.FavDBHelper;
import com.example.daman.capstone.data.FavProvider;
import com.example.daman.capstone.data.FavouritesTable;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    TextView textView, authorText, authorTitle;
    ImageView imageView, btnbrowse, btnshare;
    CardView extraView, cardView;
    MaterialFavoriteButton btnbookmark;
    Animation slideUpAnimation, slideDownAnimation;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(String source, String name, String image, String description, String author, String newsurl) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString("source", source);
        args.putString("name", name);
        args.putString("image", image);
        args.putString("description", description);
        args.putString("author", author);
        args.putString("newsurl", newsurl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View mRootView = inflater.inflate(R.layout.fragment_details, container, false);

        final String source = getArguments().getString("source");
        final String title = getArguments().getString("name");
        final String image = getArguments().getString("image");
        final String description = getArguments().getString("description");
        final String author = getArguments().getString("author");
        final String newsurl = getArguments().getString("newsurl");

        slideUpAnimation = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_up);

        slideDownAnimation = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_down);

        cardView = (CardView) mRootView.findViewById(R.id.card_detail);

        extraView = (CardView) mRootView.findViewById(R.id.extra_view);

        authorTitle = (TextView) mRootView.findViewById(R.id.article_title);
        authorTitle.setText(title);

        authorText = (TextView) mRootView.findViewById(R.id.article_author);
        try {
            String s = "By " + author.substring(0, 1).toUpperCase() + author.substring(1).toLowerCase();
            authorText.setText(s);
        } catch (Exception e) {
            System.err.println(e);
        }

        textView = (TextView) mRootView.findViewById(R.id.article_body);
        textView.setText(description);

        btnbrowse = (ImageView) mRootView.findViewById(R.id.btn_browse);
        btnshare = (ImageView) mRootView.findViewById(R.id.btn_share);

        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), BrowserActivity.class);
                intent.putExtra("URL", newsurl);
                intent.putExtra("SOURCE", source);
                startActivity(intent);

            }
        });

        btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/html");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, newsurl);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));

            }
        });

        btnbookmark = (MaterialFavoriteButton) mRootView.findViewById(R.id.btn_bookmark);

        ArrayList<String> check = queryFavourites();
        System.out.println(check);
        System.out.println(newsurl);
        if (check.contains(newsurl)) {
            btnbookmark.setFavorite(true);
        } else {
            btnbookmark.setFavorite(false);
        }

        btnbookmark.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if (favorite) {
                    FavDBHelper testInstance = new FavDBHelper();
                    testInstance.title = title;
                    testInstance.description = description;
                    testInstance.author = author;
                    testInstance.image = image;
                    testInstance.url = newsurl;
                    testInstance.date = "";

                    try {
                        getActivity().getContentResolver().insert(FavouritesTable.CONTENT_URI, FavouritesTable.getContentValues(testInstance, true));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    getActivity().getContentResolver().delete(FavouritesTable.CONTENT_URI, FavContract.COLUMN_URL + " = ?", new String[]{"" + newsurl});
                }
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (extraView.getVisibility() == View.INVISIBLE) {
                    extraView.startAnimation(slideUpAnimation);
                    extraView.setVisibility(View.VISIBLE);

                } else {
                    extraView.startAnimation(slideDownAnimation);
                    extraView.setVisibility(View.INVISIBLE);
                }
            }
        });

        imageView = (ImageView) mRootView.findViewById(R.id.photo);

        Picasso.with(getContext())
                .load(image)
                .into(imageView);

        return mRootView;
    }

    private ArrayList<String> queryFavourites() {

        Cursor c = getActivity().getContentResolver().query(FavouritesTable.CONTENT_URI, null, null, null, null);
        List<FavDBHelper> list = FavouritesTable.getRows(c, true);
        ArrayList<String> idList = new ArrayList<>();
        for (FavDBHelper element : list) {
            idList.add(element.url);
        }
        return idList;
    }
}
