package com.edvardas.flickrbrowserjava;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickListen";

    private final OnRecyclerClickListener listener;
    private final GestureDetectorCompat gestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerClickListener listener) {
        this.listener = listener;
        gestureDetector = null;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");
        return super.onInterceptTouchEvent(rv, e);
    }
}
