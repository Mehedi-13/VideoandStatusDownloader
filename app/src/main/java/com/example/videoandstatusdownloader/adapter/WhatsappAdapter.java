package com.example.videoandstatusdownloader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videoandstatusdownloader.R;
import com.example.videoandstatusdownloader.Util;
import com.example.videoandstatusdownloader.databinding.WhatsappItemLayoutBinding;
import com.example.videoandstatusdownloader.model.WhatsappStatusModel;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WhatsappAdapter extends RecyclerView.Adapter<WhatsappAdapter.whatsappViewHolder> {


    private Context context;
    private ArrayList<WhatsappStatusModel> list;
    private LayoutInflater inflater;
    private  String saveFilePath = Util.RootDirectoryWhatsapp+"/";

    public WhatsappAdapter(Context context, ArrayList<WhatsappStatusModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public whatsappViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        if (inflater==null){
            inflater=LayoutInflater.from(parent.getContext());
        }

        return new whatsappViewHolder(DataBindingUtil.inflate(inflater, R.layout.whatsapp_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WhatsappAdapter.whatsappViewHolder holder, int position) {

        WhatsappStatusModel item= list.get(position);
        if (item.getUri().toString().endsWith(".mp4"))
            holder.binding.playBtn.setVisibility(View.VISIBLE);
        else holder.binding.playBtn.setVisibility(View.GONE);

        Glide.with(context).load(item.getPath()).into(holder.binding.statusImg);
        holder.binding.download.setOnClickListener(v -> {
            Util.createFileFolder();
            final String path=item.getPath();
            final File file= new File(path);
            File destFile= new File(saveFilePath);

            try {
                FileUtils.copyFileToDirectory(file,destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "Save to: "+saveFilePath, Toast.LENGTH_SHORT).show();


        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class whatsappViewHolder extends RecyclerView.ViewHolder {

        WhatsappItemLayoutBinding binding;

        public whatsappViewHolder( WhatsappItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }
    }
}
