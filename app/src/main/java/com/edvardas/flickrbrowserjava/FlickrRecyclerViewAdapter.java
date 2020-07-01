package com.edvardas.flickrbrowserjava;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageHolder> {
    private static final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> photoList;
    private Context context;

    public FlickrRecyclerViewAdapter(Context context, List<Photo> photoList) {
        this.photoList = photoList;
        this.context = context;
    }

    @NonNull
    @Override
    public FlickrImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);
        return new FlickrImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrImageHolder holder, int position) {
        Photo photo = photoList.get(position);
        Log.d(TAG, "onBindViewHolder: " + photoList.get(position) + " --> " + position);
        Picasso.with(context).load(photo.getImage())
            .error(R.drawable.placeholder_img)
            .placeholder(R.drawable.placeholder_img)
            .into(holder.thumbnail);
        holder.title.setText(photo.getTitle());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
        return ((photoList != null) && photoList.size() != 0) ? photoList.size() : 0;
    }

    void loadNewData(List<Photo> newPhotos) {
        photoList = newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhotoIntoPosition(int position) {
        return ((photoList != null) && photoList.size() != 0) ? photoList.get(position) : null;
    }

    static class FlickrImageHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "FlickrImageHolder";
        ImageView thumbnail;
        TextView title;

        FlickrImageHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageHolder: starts");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
