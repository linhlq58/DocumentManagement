package com.alink.documentmanagement.utils;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static String formatToDate(String inputDate) {
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        String reformattedStr = "";
        String dayOfWeek = "";

        try {
            Date date = fromUser.parse(inputDate);
            reformattedStr = myFormat.format(date);

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int dow = c.get(Calendar.DAY_OF_WEEK);

            switch (dow) {
                case 1:
                    dayOfWeek = "Chủ nhật";
                    break;
                case 2:
                    dayOfWeek = "Thứ hai";
                    break;
                case 3:
                    dayOfWeek = "Thứ ba";
                    break;
                case 4:
                    dayOfWeek = "Thứ tư";
                    break;
                case 5:
                    dayOfWeek = "Thứ năm";
                    break;
                case 6:
                    dayOfWeek = "Thứ sáu";
                    break;
                case 7:
                    dayOfWeek = "Thứ bảy";
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dayOfWeek + ", " + reformattedStr;
    }

    public static String formatToTime(String inputDate) {
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat myFormat = new SimpleDateFormat("HH:mm:ss");
        String reformattedStr = "";

        try {

            reformattedStr = myFormat.format(fromUser.parse(inputDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reformattedStr;
    }

    public static String formatToDateTime(String inputDate) {
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat myFormat = new SimpleDateFormat("HH:mm', ' dd/MM/YYYY");
        String reformattedStr = "";

        try {

            reformattedStr = myFormat.format(fromUser.parse(inputDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reformattedStr;
    }

    public static void downloadFile(String url, File outputFile) {
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch(FileNotFoundException e) {
            return; // swallow a 404
        } catch (IOException e) {
            return; // swallow a 404
        }
    }

    public static String getDocumentMimeType(String fileName) {
        String mimeSubString = fileName.substring(fileName.lastIndexOf(".") + 1).trim();

        if (mimeSubString.equals("pdf")) {
            return "application/pdf";
        } else if (mimeSubString.equals("doc") || mimeSubString.equals("docx")) {
            return "application/msword";
        } else if (mimeSubString.equals("xls") || mimeSubString.equals("xlsx")) {
            return "application/vnd.ms-excel";
        } else {
            return "";
        }
    }

    public static String getNameBeforeExtension(String fileName) {
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        return name;
    }

    public static long startDownload(Uri uri, String fileName, DownloadManager downloadManager, Context context) {
        long downloadReference;

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("Document Download");
        request.setDescription("Document Download");
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, fileName);

        downloadReference = downloadManager.enqueue(request);

        return downloadReference;
    }

    public static void onOpenFile(Uri data, String type, Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);

        /*Uri data = (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) ?
                FileProvider.getUriForFile(DetailActivity.this, BuildConfig.APPLICATION_ID + ".provider", file) :
                Uri.fromFile(file);*/

        intent.setDataAndType(data, type);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
