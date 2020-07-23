package com.help.sandesh.Extra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.help.sandesh.Admin.Adminlogin;
import com.help.sandesh.R;
import com.help.sandesh.Service.Driverlogin;
import com.help.sandesh.User.UserDashBoard;

public class SelectProfileActivity extends AppCompatActivity {

    public String avname,avcode;
    public boolean dontgo=false;
    boolean doubleBackToExitPressedOnce = false;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);
        builder = new AlertDialog.Builder(this);
        if(!internetIsConnected())
        {
            builder.setMessage("YOU ARE NOT CONNECTED TO INTERNET.\n\nPLEASE CONNECT TO WIFI/MOBILE DATA.")
                    .setCancelable(false)
                    .setNegativeButton("close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                            System.exit(0);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("Internet Status");
            alert.show();
        }
        else {
            getversion();
        }
    }

    private void getversion() {
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                checkversion((String) dataSnapshot.child("version").child("vcode").getValue(),(String) dataSnapshot.child("version").child("vname").getValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    private void checkversion(String cvcode,String cvname) {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            avname = packageInfo.versionName;
            avcode = String.valueOf(packageInfo.versionCode);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (TextUtils.equals(avcode,cvcode)&&TextUtils.equals(avname,cvname)){
            Toast.makeText(this, "latest version", Toast.LENGTH_SHORT).show();
            dontgo=true;
        }
        else
        {
            AlertDialog.Builder abuilder=new AlertDialog.Builder(SelectProfileActivity.this);
            abuilder.setMessage("You are running on old version.\nPplease update to LATEST VERSION.")
                    .setCancelable(false)
                    .setNeutralButton("UPDATE NOW", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String url = "https://drive.google.com/open?id=1HwX9Tpjb8G8WHa1XYv32ufFCBWGRQHAb";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    })
                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                            System.exit(0);
                        }
                    });
            AlertDialog alert = abuilder.create();
            alert.setTitle("UPDATE APP ! ! !");
            alert.show();
        }
    }

    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

    public void profile_driver(View v) {
        if (dontgo) {startActivity(new Intent(this, Driverlogin.class));}
    }
    public void profile_user(View v) {
        if (dontgo){startActivity(new Intent(this, UserDashBoard.class));}
    }
    public void profile_admin(View v) {
        if (dontgo){startActivity(new Intent(this, Adminlogin.class));}
    }
    public void about_us(View v) {
        startActivity(new Intent(this, Developers.class));
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
