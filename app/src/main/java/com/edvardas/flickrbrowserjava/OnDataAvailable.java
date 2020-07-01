package com.edvardas.flickrbrowserjava;

import java.util.List;

public interface OnDataAvailable {
    void onDataAvailable(List<Photo> data, DownloadStatus status);
}
