package com.example.daman.capstone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by daman on 1/12/16.
 */

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<String> id, name, description, newsurl, image;

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView imageView;
        TextView newstitle;

        MyViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            newstitle = (TextView) v.findViewById(R.id.news_title);
            imageView = (ImageView) v.findViewById(R.id.grid_image);
        }
    }


    public SourceAdapter(Context c, ArrayList<String> id, ArrayList<String> name,
                       ArrayList<String> description, ArrayList<String> newsurl,
                       ArrayList<String> image) {
        mContext = c;
        this.id = id;
        this.name = name;
        this.description = description;
        this.newsurl = newsurl;
        this.image = image;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        MyViewHolder holder;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news, parent, false);
        holder = new MyViewHolder(v);
        holder.mCardView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,int position) {
        try{
            Picasso.with(mContext)
                    .load(image.get(position))
                    .into(holder.imageView);

            holder.newstitle.setText(name.get(position));
            final Bundle bundle = new Bundle();
            bundle.putString("TITLE",name.get(position));
            bundle.putString("DESCRIPTION",description.get(position));
            bundle.putString("IMAGE", image.get(position));
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,DetailsActivity.class);
                    intent.putExtra("BUNDLE", bundle);
                    mContext.startActivity(intent);
                }
            });

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return name.size();
    }
}
