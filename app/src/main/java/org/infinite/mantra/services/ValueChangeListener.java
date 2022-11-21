package org.infinite.mantra.services;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import org.infinite.mantra.ui.charts.ChartsFragment;
import org.infinite.mantra.ui.reviews.ReviewsFragment;

public class ValueChangeListener extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new ReviewsFragment();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
