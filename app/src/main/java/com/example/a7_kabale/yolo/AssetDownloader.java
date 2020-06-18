package com.example.a7_kabale.yolo;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.File;
import java.lang.ref.WeakReference;

public class AssetDownloader {
    private File storage;
    private Context context;

    public AssetDownloader(Context context) {
        this.context = context;
        storage = context.getExternalFilesDir(null);
    }

    public void downloadAssets() {
        File propertyFile = new File(storage.getPath() + "/props.properties");
        if (!propertyFile.exists()) {
            try {
                if (!propertyFile.createNewFile()) {
                    System.err.println("Properties fil kunne ikke laves!?");
                }
                //Skriv eventuelt noget til properties som versionsnummer af weights etc. etc. hvis vi vil

                //Download alt her
                String url1 = "https://waii.dk/upload/uploads/cards.cfg";
                String url2 = "https://waii.dk/upload/uploads/cards.weights";
                new DownloadFilesTask(this, context).execute(url1, url2);

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
            if (!requestedFile.delete()) {
                System.err.println("Asset fil eksisterer men blev ikke slettet?");
            }
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
    //Weakreference fra https://stackoverflow.com/questions/44309241/warning-this-asynctask-class-should-be-static-or-leaks-might-occur
    private static class DownloadFilesTask extends AsyncTask<String, Integer, Long> {
        private WeakReference<Context> contextRef;
        private WeakReference<AssetDownloader> downloaderRef;

        DownloadFilesTask(AssetDownloader assetDownloader , Context context) {
            this.contextRef = new WeakReference<>(context);
            this.downloaderRef = new WeakReference<>(assetDownloader);
        }

        ProgressDialog progressBarDialog;
        private boolean downloading = true;

        @Override
        protected void onPreExecute() {
            progressBarDialog = new ProgressDialog(contextRef.get());
            progressBarDialog.setTitle("Downloading AI Data, please wait!");

            progressBarDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBarDialog.setProgress(0);
            progressBarDialog.setCancelable(false);
            progressBarDialog.show();
        }

        @Override
        protected Long doInBackground(String... urls) {
            DownloadManager dlm = (DownloadManager) contextRef.get().getSystemService(Context.DOWNLOAD_SERVICE);

            for (String url : urls) {
                DownloadManager.Request request = downloaderRef.get().makeRequest(url);
                dlm.enqueue(request);
            }

            DownloadManager.Query query = new DownloadManager.Query();
            Cursor cursor = dlm.query(query);
            cursor.moveToFirst();

            while (downloading) {
                query.setFilterByStatus(DownloadManager.STATUS_RUNNING);
                cursor = dlm.query(query);
                cursor.moveToFirst();
                try {
                    int bytesTotalIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                    long totalBytes = cursor.getInt(bytesTotalIndex);

                    int downloadTotalIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                    long downloadedBytes = cursor.getInt(downloadTotalIndex);

                    publishProgress((int) ((downloadedBytes / (float) totalBytes) * 100));
                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                    }
                } catch (CursorIndexOutOfBoundsException e) {
                    System.err.println("Download cursor went out of bounds? Stopping download dialog.");
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

