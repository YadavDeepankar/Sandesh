package com.help.sandesh.Extra;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.help.sandesh.BuildConfig;
import com.help.sandesh.R;

public class Developers extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developers);


    }

    public void website(View view) {
        String url = "https://trackerexclusive.wixsite.com/sandesh";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void update(View view) {
        String url = "https://drive.google.com/open?id=1HwX9Tpjb8G8WHa1XYv32ufFCBWGRQHAb";
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
//        Toast.makeText(Developers.this, "Version"+versionCode +"\nName"+versionName, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}