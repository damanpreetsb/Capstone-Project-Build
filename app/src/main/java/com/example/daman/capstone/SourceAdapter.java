package com.example.daman.capstone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
            mCardView = (CardView) v.findViewById(R.id.source_card_view);
            newstitle = (TextView) v.findViewById(R.id.source_news_title);
            imageView = (ImageView) v.findViewById(R.id.source_grid_image);
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
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.source_news, parent, false);
        holder = new MyViewHolder(v);
        holder.mCardView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,int position) {
        try{
            Glide.clear(holder.imageView);
            Glide.with(holder.imageView.getContext())
                    .load(image.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model,
                                                       Target<GlideDrawable> target,
                                                       boolean isFromMemoryCache, boolean isFirstResource) {
                            Bitmap bitmap = ((GlideBitmapDrawable) resource.getCurrent()).getBitmap();
                            Palette palette = Palette.generate(bitmap);
                            int defaultColor = 0xFF333333;
                            int color = palette.getDarkMutedColor(defaultColor);
                            int textcolor = palette.getLightMutedColor(defaultColor);
                            holder.mCardView.setCardBackgroundColor(color);
//                            holder.newstitle.setTextColor(textcolor);
                            return false;
                        }
                    })
                    .into(holder.imageView);

            holder.newstitle.setText(name.get(position));
            final Bundle bundle = new Bundle();
            bundle.putStringArrayList("TITLE",name);
            bundle.putStringArrayList("DESCRIPTION",description);
            bundle.putStringArrayList("IMAGE", image);
            bundle.putStringArrayList("AUTHOR", id);
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
