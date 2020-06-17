package com.example.a7_kabale;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
                String url1 = "https://waii.dk/upload/uploads/cards.cfg";
                String url2 = "https://waii.dk/upload/uploads/cards.weights";
                new DownloadFilesTask().execute(url1, url2);

            } catch (Exception e) {
                System.err.println("Something went wrong with asset downloads!");
            }

        } else {
            System.out.println("Files exists!");
        }
    }

    private DownloadManager.Request makeRequest(String url) {
        File file = new File(url);
        String name = file.getName();

        File requestedFile = new File(storage.getPath() + "/data/" + name);
        if (requestedFile.exists()) {
            requestedFile.delete();
        }

        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("Downloading");
        request.setDescription("Downloading configuration files for detection");
        request.setDestinationInExternalFilesDir(context, "data", name);
        request.setVisibleInDownloadsUi(false);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

        return request;
    }

    //Stjålet fra https://developer.android.com/reference/android/os/AsyncTask
    //Og https://stackoverflow.com/questions/20092460/show-progress-bar-while-downloading-using-download-manager
    private class DownloadFilesTask extends AsyncTask<String, Integer, Long> {
        final ProgressDialog progressBarDialog= new ProgressDialog(context);
        private boolean downloading = true;

        @Override
        protected void onPreExecute() {
            progressBarDialog.setTitle("Download AI Data, please wait!");

            progressBarDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBarDialog.setProgress(0);
            progressBarDialog.setCancelable(false);
            progressBarDialog.show();
        }

        @Override
        protected Long doInBackground(String... urls) {
            DownloadManager dlm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            ArrayList<DownloadManager.Request> requests = new ArrayList<DownloadManager.Request>();
            for (String url : urls) {
                DownloadManager.Request request = makeRequest(url);
                dlm.enqueue(request);
            }

            DownloadManager.Query query = new DownloadManager.Query();
            Cursor cursor = dlm.query(query);
            cursor.moveToFirst();

            while (downloading) {
                query.setFilterByStatus(DownloadManager.STATUS_RUNNING);
                cursor = dlm.query(query);
                cursor.moveToFirst();
                int bytesTotalIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                long totalBytes = cursor.getInt(bytesTotalIndex);

                int downloadTotalIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                long downloadedBytes = cursor.getInt(downloadTotalIndex);

                publishProgress((int) ((downloadedBytes / (float) totalBytes) * 100));
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false;
                }
            }

            return (long) 1;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            progressBarDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            progressBarDialog.dismiss();
        }
    }


}

