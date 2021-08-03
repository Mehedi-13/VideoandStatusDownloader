package com.example.videoandstatusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.videoandstatusdownloader.databinding.ActivityFacebookBinding;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FacebookActivity extends AppCompatActivity {

    private ActivityFacebookBinding binding;
    private  FacebookActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_facebook);
        activity=this;

        binding.downloadBtn.setOnClickListener(v -> {
            getFaceBookData();
        });

    }

    private void getFaceBookData() {
        URL url= null;
        try {
            url = new URL(binding.urlFacebook.getText().toString());

            String host= url.getHost();
            if (host.contains("facebook.com")){
                new callGetData().execute(binding.urlFacebook.getText().toString());

            }else Toast.makeText(activity, "Url is invalid", Toast.LENGTH_SHORT).show();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    class callGetData extends AsyncTask<String,Void, Document>{

        Document fbDoc;

        @Override
        protected Document doInBackground(String... strings) {
            try {
                fbDoc= Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fbDoc;
        }

        @Override
        protected void onPostExecute(@NotNull Document document) {
            String videoUrl = document.select("meta[property=\"og:video\"]")
                    .last().attr("content");
            if (!videoUrl.equals(""))
                Util.download(videoUrl,Util.RootDirectoryFacebook,activity,"facebook "+System.currentTimeMillis()+".mp4");
        }
    }
}