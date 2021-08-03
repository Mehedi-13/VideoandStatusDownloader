package com.example.videoandstatusdownloader.Fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videoandstatusdownloader.R;
import com.example.videoandstatusdownloader.adapter.WhatsappAdapter;
import com.example.videoandstatusdownloader.databinding.FragmentImageBinding;
import com.example.videoandstatusdownloader.model.WhatsappStatusModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class VideoFragment extends Fragment {
    private FragmentImageBinding binding;
    private ArrayList<WhatsappStatusModel> list;
    private WhatsappAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_image,container,false);

        list= new ArrayList<>();
        getData();

        binding.refresh.setOnRefreshListener(() ->{
            list= new ArrayList<>();
            getData();
            binding.refresh.setRefreshing(false);
        });

        return binding.getRoot();
    }

    private void getData() {
        WhatsappStatusModel model;

        String targetPath= Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/WhatsApp/Media/.Statuses";
        File targetDirectory = new File(targetPath);
        File[] allFiles= targetDirectory.listFiles();

        String targetPathBusiness= Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/WhatsApp Business/Media/.Statuses";
        File targetDirectoryBusiness = new File(targetPathBusiness);
        File[] allFilesBusiness= targetDirectoryBusiness.listFiles();

        Arrays.sort(allFiles,((o1, o2) -> {
            if (o1.lastModified() > o2.lastModified()) return  -1;
            else if (o1.lastModified() <o2.lastModified()) return  +1;
            else return 0;
        }));

        for (int i=0; i<allFiles.length; i++){
            File file= allFiles[i];
            if (Uri.fromFile(file).toString().endsWith(".mp4")){

                model= new WhatsappStatusModel("Whats "+i,
                        Uri.fromFile(file),
                        allFiles[i].getAbsolutePath(),
                        file.getName());

                list.add(model);
            }
        }


        Arrays.sort(allFilesBusiness,((o1,o2) -> {
            if (o1.lastModified() > o2.lastModified()) return  -1;
            else if (o1.lastModified() <o2.lastModified()) return  +1;
            else return 0;
        }));

        for (int i=0; i<allFilesBusiness.length; i++){
            File file= allFilesBusiness[i];
            if (Uri.fromFile(file).toString().endsWith(".mp4")){

                model= new WhatsappStatusModel("WhatsBusiness "+i,
                        Uri.fromFile(file),
                        allFilesBusiness[i].getAbsolutePath(),
                        file.getName());

                list.add(model);
            }
        }

        adapter= new WhatsappAdapter(getActivity(),list);
        binding.whatsappRecycler.setAdapter(adapter);

    }
}