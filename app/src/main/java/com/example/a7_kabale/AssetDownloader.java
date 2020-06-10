package com.example.a7_kabale;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import java.io.File;

public class AssetDownloader {
    File storage;
    Context context;

    public AssetDownloader(Context context) {
        this.context = context;
        storage = context.getExternalFilesDir(null);
    }

    public void downloadAssets() {
        File propertyFile = new File(storage.getPath() + "/props.properties");
        if (!propertyFile.exists()) {
            try {
                propertyFile.createNewFile();
                //Skriv eventuelt noget til properties som versionsnummer af weights etc. etc. hvis vi vil

                //Download alt her
                DownloadManager dlm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                DownloadManager.Request weightsRequest = makeRequest("https://waii.dk/upload/uploads/cards.weights", "cards.weights");
                DownloadManager.Request cfgRequest = makeRequest("https://waii.dk/upload/uploads/cards.cfg", "cards.cfg");

                dlm.enqueue(weightsRequest);
                dlm.enqueue(cfgRequest);

            } catch (Exception e) {
                System.err.println("Something went wrong with asset downloads!");
            }

        } else {
            System.out.println("Files exists!");
        }
    }

    private DownloadManager.Request makeRequest(String url, String outName) {
        File requestedFile = new File(storage.getPath() + "/data/" + outName);
        if (requestedFile.exists()) {
            requestedFile.delete();
        }

        Uri Download_Uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setTitle("Downloading");
        request.setDescription("Downloading configuration files for detection");
        request.setDestinationInExternalFilesDir(context, "data", outName);
        request.setVisibleInDownloadsUi(false);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

        return request;
    }
}
