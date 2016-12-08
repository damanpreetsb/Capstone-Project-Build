package com.example.daman.capstone;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    FloatingActionButton fab;
    net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout collapsingToolbarLayout;
    TextView textView, authorText;
    ImageView imageView;
    private boolean bookmark = true;


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
        String image = getArguments().getString("image");
        String description = getArguments().getString("description");
        String author = getArguments().getString("author");
        final String newsurl = getArguments().getString("newsurl");

        fab = (FloatingActionButton) mRootView.findViewById(R.id.share_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/html");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, newsurl);
                startActivity(Intent.createChooser(sharingIntent,"Share using"));
            }
        });

        collapsingToolbarLayout = ((net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout) mRootView.findViewById(R.id.collapsing_toolbar_layout));
        collapsingToolbarLayout.setTitle(title);

        authorText = (TextView) mRootView.findViewById(R.id.article_author);
        String s = "By "+author.substring(0,1).toUpperCase() + author.substring(1).toLowerCase();
        authorText.setText(s);

        textView = (TextView) mRootView.findViewById(R.id.article_body);
        textView.setText(description);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BrowserActivity.class);
                intent.putExtra("URL", newsurl);
                intent.putExtra("SOURCE", source);
                startActivity(intent);
            }
        });

        imageView = (ImageView) mRootView.findViewById(R.id.photo);

        Picasso.with(getContext())
                .load(image)
                .into(imageView);

        AppBarLayout appBarLayout = (AppBarLayout) mRootView.findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                switch (verticalOffset){
                    case 1:
                        fab.setVisibility(View.GONE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getActivity().getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                        }
                        break;
                    case 0:
                        fab.setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getActivity().getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
                        }
                        break;
                }
            }
        });

        Toolbar toolbar = (Toolbar) mRootView.findViewById(R.id.app_bar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
        toolbar.inflateMenu(R.menu.details);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.switch_layout) {
                    Intent intent = new Intent(getActivity(),SourceActivity.class);
                    intent.putExtra("SOURCE_NAME", source);
                    getContext().startActivity(intent);
                }
                if(id == R.id.bookmark) {
                    if(bookmark) {
                        item.setIcon(R.drawable.ic_bookmark_black_24dp);
                        bookmark = false;
                    }
                    else {
                        item.setIcon(R.drawable.ic_bookmark_border_black_24dp);
                        bookmark = true;
                    }
                }
                return false;
            }
        });

        return mRootView;
    }
}
