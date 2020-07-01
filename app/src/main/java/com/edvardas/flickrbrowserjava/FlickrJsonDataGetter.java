package com.edvardas.flickrbrowserjava;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FlickrJsonDataGetter extends AsyncTask<String, Void, List<Photo>> implements OnDownloadComplete {
    private static final String TAG = "FlickrJsonDataGetter";

    private List<Photo> photoList = null;
    private String baseURL;
    private String language;
    private boolean matchAll;
    private final OnDataAvailable callback;
    private boolean runningOnSameThread = false;

    public FlickrJsonDataGetter(OnDataAvailable callback, String baseURL, String language, boolean matchAll) {
        this.baseURL = baseURL;
        this.callback = callback;
        this.language = language;
        this.matchAll = matchAll;
    }

    void executeOnSameThread(String searchCriteria) {
        runningOnSameThread = true;
        String destinationUri = createUri(searchCriteria, language, matchAll);
        RawDataDownloader downloader = new RawDataDownloader(this);
        downloader.execute(destinationUri);
    }

    @Override
    protected List<Photo> doInBackground(String... strings) {
        String destinationUri = createUri(strings[0], language, matchAll);
        RawDataDownloader downloader = new RawDataDownloader(this);
        downloader.runInSameThread(destinationUri);
        return photoList;
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        if (callback != null) {
            callback.onDataAvailable(photoList, DownloadStatus.OK);
        }
    }

    private String createUri(String searchCriteria, String lang, boolean matchAll) {
        return Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", lang)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            photoList = new ArrayList<>();
        }

        try {
            JSONObject jsonData = new JSONObject(data);
            JSONArray itemsArray = jsonData.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                String title = jsonPhoto.getString("title");
                String author = jsonPhoto.getString("author");
                String authorId = jsonPhoto.getString("author_id");
                String tags = jsonPhoto.getString("tags");
                JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                String photoUrl = jsonMedia.getString("m");
                String link = photoUrl.replaceFirst("_m.", "_b.");
                Photo photo = new Photo(title, author, authorId, link, tags, photoUrl);
                photoList.add(photo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "onDownloadComplete: Error while parsing JSON data" + e.getMessage());
            status = DownloadStatus.FAILED_OR_EMPTY;
        }
        if (runningOnSameThread && callback != null) {
            callback.onDataAvailable(photoList, status);
        }
    }
}
