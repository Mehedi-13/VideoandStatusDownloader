package com.example.videoandstatusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.videoandstatusdownloader.databinding.ActivityShareChatBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.CacheRequest;
import java.net.MalformedURLException;
import java.net.URL;

public class ShareChatActivity extends AppCompatActivity {
private ActivityShareChatBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_share_chat);

        binding.downloadBtn.setOnClickListener(v -> {
            getShareChatsData();
        });
    }

    private void getShareChatsData() {
        URL url= null;
        try {
            url = new URL(binding.urlShareChat.getText().toString());


            String host= url.getHost();
            if (host.contains("sharechat")){
                new callGetShareChatsData().execute(binding.urlShareChat.getText().toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    class callGetShareChatsData extends AsyncTask<String,Void, Document>{

        Document shareChatDocument;
        @Override
        protected Document doInBackground(String... strings) {
            try {
                shareChatDocument = Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return shareChatDocument;
        }

        @Override
        protected void onPostExecute(Document document) {
            String videoUrl= document.select("meta[property=\"og:video:secure_url\"]")
                    .last().attr("content");
            if (!videoUrl.equals("")){
                Util.download(videoUrl,Util.RootDirectoryShareChat,ShareChatActivity.this,"shareChat "+System.currentTimeMillis()+".mp4");
            }

        }
    }
}