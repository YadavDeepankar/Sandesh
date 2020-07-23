package com.help.sandesh.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.help.sandesh.R;
import com.help.sandesh.Extra.SelectProfileActivity;
import com.help.sandesh.User.UserChooseState;
import com.help.sandesh.Extra.signup;

public class AdminDashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);
    }


    public void a_managedriver(View v) {
        startActivity(new Intent(this, signup.class));
    }
    public void u_locate(View v) {
        startActivity(new Intent(this, UserChooseState.class));
    }
    public void a_LOGOUT(View v) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SelectProfileActivity.class));
    }
    public void addHosp(View view) {
        startActivity(new Intent(AdminDashBoard.this,addhospital.class));
    }

    @Override
    public void onBackPressed()
    {

    }
}
