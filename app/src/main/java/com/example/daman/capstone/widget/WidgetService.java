package com.example.daman.capstone.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.request.target.AppWidgetTarget;
import com.example.daman.capstone.R;
import com.example.daman.capstone.data.FavDBHelper;
import com.example.daman.capstone.data.FavouritesTable;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daman on 27/12/16.
 */

public class WidgetService extends RemoteViewsService {

    /*
* So pretty simple just defining the Adapter of the listview
* here Adapter is ListProvider
* */
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private AppWidgetTarget appWidgetTarget;
    private ArrayList<String> mWidgetItems = new ArrayList<String>();
    private ArrayList<String> mImages = new ArrayList<String>();
    private ArrayList<String> mUrls = new ArrayList<String>();
    private Context mContext;
    private int mAppWidgetId;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        // In onCreate() you setup any connections / cursors to your data
        // source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred
        // to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result
        // in an ANR.
        Cursor cursor = mContext.getContentResolver().query(FavouritesTable.CONTENT_URI, null, null, null, null);
        List<FavDBHelper> testRows = FavouritesTable.getRows(cursor, true);
        for (FavDBHelper element : testRows) {
            mWidgetItems.add(element.title);
            mImages.add(element.image);
            mUrls.add(element.url);
        }

        // We sleep for 3 seconds here to show how the empty view appears in the
        // interim.
        // The empty view is set in the StackWidgetProvider and should be a
        // sibling of the
        // collection view.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        // In onDestroy() you should tear down anything that was setup for your
        // data source,
        // eg. cursors, connections, etc.
        mWidgetItems.clear();
        mImages.clear();
    }

    public int getCount() {
        return mWidgetItems.size();
    }

    public RemoteViews getViewAt(int position) {
        // position will always range from 0 to getCount() - 1.

        // We construct a remote views item based on our widget item xml file,
        // and set the
        // text based on the position.
        RemoteViews rv = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_stack);

        try {
            Bitmap b = Picasso.with(mContext).load(mImages.get(position)).resize(400, 400 ).centerCrop().get();
            rv.setImageViewBitmap(R.id.widget_item, b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rv.setTextViewText(R.id.widget_text, mWidgetItems.get(position));

        // Next, we set a fill-intent which will be used to fill-in the pending
        // intent template
        // which is set on the collection view in StackWidgetProvider.
        Bundle extras = new Bundle();
        extras.putString(NewsWidget.EXTRA_ITEM, mWidgetItems.get(position));
        extras.putString(NewsWidget.IMAGE_ITEM, mImages.get(position));
        extras.putString(NewsWidget.URL_ITEM, mUrls.get(position));
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);

        // You can do heaving lifting in here, synchronously. For example, if
        // you need to
        // process an image, fetch something from the network, etc., it is ok to
        // do it here,
        // synchronously. A loading view will show up in lieu of the actual
        // contents in the
        // interim.
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Return the remote views object.
        return rv;
    }

    public RemoteViews getLoadingView() {
        // You can create a custom loading view (for instance when getViewAt()
        // is slow.) If you
        // return null here, you will get the default loading view.
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
        // This is triggered when you call AppWidgetManager
        // notifyAppWidgetViewDataChanged
        // on the collection view corresponding to this factory. You can do
        // heaving lifting in
        // here, synchronously. For example, if you need to process an image,
        // fetch something
        // from the network, etc., it is ok to do it here, synchronously. The
        // widget will remain
        // in its current state while work is being done here, so you don't need
        // to worry about
        // locking up the widget.
    }
}