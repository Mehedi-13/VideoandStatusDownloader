package com.example.videoandstatusdownloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.videoandstatusdownloader.Fragment.ImageFragment;
import com.example.videoandstatusdownloader.Fragment.VideoFragment;
import com.example.videoandstatusdownloader.databinding.ActivityMainBinding;
import com.example.videoandstatusdownloader.databinding.ActivityWhatsappBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

public class WhatsappActivity extends AppCompatActivity {

    private ActivityWhatsappBinding binding;
    private WhatsappActivity activity;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_whatsapp);

        activity=this;

        initview();

    }

    private void initview() {
        adapter= new ViewPagerAdapter(activity.getSupportFragmentManager(),
        activity.getLifecycle());

        adapter.addfragment(new ImageFragment(),"Images");
        adapter.addfragment(new VideoFragment(),"Videos");

        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(1);

        new TabLayoutMediator(binding.tabLayout,binding.viewPager,(tab, position) -> {

            tab.setText(adapter.fragmentTitleList.get(position));

        }).attach();

        for (int i=0; i<binding.tabLayout.getTabCount(); i++){

            TextView tv= (TextView) LayoutInflater.from(activity)
                    .inflate(R.layout.custom_tab,null);

            binding.tabLayout.getTabAt(i).setCustomView(tv);
        }






    }





    class  ViewPagerAdapter extends FragmentStateAdapter{

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        public void  addfragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }


        @NonNull
        @org.jetbrains.annotations.NotNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size() ;
        }
    }




//    private Void showPermissionDialog() {
//
//        if (SDK_INT >= Build.VERSION_CODES.R){
//            try {
//                Intent intent= new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                intent.addCategory("android.intent.category.DEFAULT");
//                intent.setData(Uri.parse(String.format("package:%s", new Object[]{getApplicationContext().getPackageManager()})));
//                startActivityForResult(intent,2000);
//            } catch (Exception e) {
//                Intent intent= new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                startActivityForResult(intent,2000);
//
//            }
//
//        }else {
//            ActivityCompat.requestPermissions(WhatsappActivity.this,
//                    new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE},64);
//        }
//
//    }
//
//
//
//    private boolean checkPermission() {
//       if (SDK_INT >= Build.VERSION_CODES.R){
//           return Environment.isExternalStorageManager();
//       }else {
//           int write = ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);
//           int read = ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);
//
//           return write == PackageManager.PERMISSION_GRANTED &&
//                   read== PackageManager.PERMISSION_GRANTED;
//
//       }
//    }
}