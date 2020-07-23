package com.help.sandesh.Service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.help.sandesh.R;
import com.help.sandesh.Extra.SOS;
import com.help.sandesh.Extra.SelectProfileActivity;

public class DriverDashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dash_board);
    }

    public void broadcast(View v) {
        startActivity(new Intent(this, ServiceChooseState.class));
    }
    public void sos(View v) {
        startActivity(new Intent(this, SOS.class));
    }
    public void LOGOUT(View v) {

        FirebaseDatabase.getInstance().getReference("DriverAvail").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Location").removeValue();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SelectProfileActivity.class));

    }


    public void QR(View v) {
        Intent i = new Intent(this, GenQR.class);
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        final String uuid=user.getUid();
        Bundle bundle = new Bundle();
        bundle.putString("UniqueID",uuid);
        i.putExtras(bundle);
        startActivity(i);

    }

    @Override
    public void onBackPressed()
    {

    }
}
