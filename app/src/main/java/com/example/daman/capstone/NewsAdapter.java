package com.example.daman.capstone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by daman on 14/11/16.
 */

public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<String> id, name, description, newsurl, image;
    private int mMutedColor = 0xFF333333;

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


    public NewsAdapter(Context c, ArrayList<String> id, ArrayList<String> name,
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
    public NewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        MyViewHolder holder;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news, parent, false);
        holder = new MyViewHolder(v);
        holder.mCardView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try{
            Picasso.with(mContext)
                    .load(image.get(position))
                    .into(holder.imageView);
//                    , new Callback() {
//                        @Override
//                        public void onSuccess() {
//                            Bitmap bitmap = ((BitmapDrawable) holder.imageView.getDrawable()).getBitmap();
//                            if (bitmap != null) {
//                                Palette p = Palette.generate(bitmap, 12);
//                                mMutedColor = p.getDarkMutedColor(0xFF333333);
////                                holder.imageView.setImageBitmap(((BitmapDrawable) holder.imageView.getDrawable()).getBitmap());
//                                holder.mCardView.setBackgroundColor(mMutedColor);
//                            }
//
//                        }
//
//                        @Override
//                        public void onError() {
//
//                        }
//                    });

            holder.newstitle.setText(name.get(position));
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,SourceActivity.class);
                    intent.putExtra("SOURCE_NAME", id.get(holder.getAdapterPosition()));
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
