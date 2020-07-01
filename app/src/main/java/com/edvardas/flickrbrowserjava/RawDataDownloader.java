package com.edvardas.flickrbrowserjava;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RawDataDownloader extends AsyncTask<String, Void, String> {
    private static final String TAG = "RawDataDownloader";

    private DownloadStatus downloadStatus;
    private final OnDownloadComplete callback;

    public RawDataDownloader(OnDownloadComplete callback) {
        this.downloadStatus = DownloadStatus.IDLE;
        this.callback = callback;
    }

    void runInSameThread(String s) {
        if (callback != null) {
            callback.onDownloadComplete(doInBackground(s), downloadStatus);
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if (strings == null) {
            downloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }

        try {
            downloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: response code was " + response);
            StringBuilder result = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.append(line).append("\n");
            }
            downloadStatus = DownloadStatus.OK;
            return result.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid URL" + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: Error while reading data: " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "doInBackground: Illegal access! " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try{
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: Error closing reader" + e.getMessage());
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (callback != null) {
            callback.onDownloadComplete(s, downloadStatus);
        }
    }
}
