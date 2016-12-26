package com.example.daman.capstone.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by daman on 26/12/16.
 */

public class NewsSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static NewsSyncAdapter sSunshineSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("MovieSyncService", "onCreate - MovieSyncService");
        synchronized (sSyncAdapterLock) {
            if (sSunshineSyncAdapter == null) {
                sSunshineSyncAdapter = new NewsSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSunshineSyncAdapter.getSyncAdapterBinder();
    }
}
