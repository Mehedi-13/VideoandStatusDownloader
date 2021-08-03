package com.example.videoandstatusdownloader;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

public class Util {

    public  static  String RootDirectoryFacebook = "/My Story Saver/facebook/";
    public  static  String RootDirectoryShareChat = "/My Story Saver/shareChat/";

    public  static File RootDirectoryWhatsapp=
            new File(Environment.getExternalStorageDirectory()
            +"/Download/MyStorySaver/Whatsapp");

    public static void createFileFolder(){

        if (!RootDirectoryWhatsapp.exists()){
            RootDirectoryWhatsapp.mkdirs();
        }
    }

    public static void download (String downloadPath, String destinationPath, Context context, String fileName){
        Toast.makeText(context, "Download Starting", Toast.LENGTH_SHORT).show();



        Uri uri= Uri.parse(downloadPath);
        DownloadManager.Request request= new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
//popup notification
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(fileName);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,destinationPath+fileName);
        ((DownloadManager)context.getSystemService(context.DOWNLOAD_SERVICE)).enqueue(request);  //downloadManager
    }



}
