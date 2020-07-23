package com.help.sandesh.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.help.sandesh.R;
import com.help.sandesh.Extra.SOS;

public class UserDashBoard extends AppCompatActivity {

    String PhoneNo,sName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);
    }

    public void u_locate(View v) {
        startActivity(new Intent(this, UserChooseState.class));
    }

    public void u_sos(View v) {
        startActivity(new Intent(this, SOS.class));
    }

    public void u_India_Live(View view) {
        String url = "https://covid19india.org";

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void u_World_Live(View view) {
        String url = "https://www.worldometers.info/coronavirus/";

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void locCovid(View view) {
        startActivity(new Intent(UserDashBoard.this,CovidStateSelect.class));
    }

    public void scanqqr(View view) {
        IntentIntegrator intentIntegrator=new IntentIntegrator(UserDashBoard.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setPrompt("Scanning...");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        final IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
//        Toast.makeText(this, ""+intentResult.getContents(), Toast.LENGTH_SHORT).show();
        if (intentResult!=null && intentResult.getContents()!=null)
        {
            Toast.makeText(UserDashBoard.this, "PLEASE WAIT WHILE WE VERIFY", Toast.LENGTH_LONG).show();
            FirebaseDatabase.getInstance().getReference("Driver").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Intent i=new Intent(UserDashBoard.this,detailsqr.class);
//                    if (dataSnapshot.child(intentResult.getContents().trim()).exists())
//                    {
//                        final String pphno=(String) dataSnapshot.child(intentResult.getContents().trim()).child("Phno").getValue();
//                        final String psname=(String) dataSnapshot.child(intentResult.getContents().trim()).child("FullName").getValue();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("UniqueID","VERIFIED USER");
//                        bundle.putString("nName",psname);
//                        bundle.putString("nPhone",pphno);
//                        i.putExtras(bundle);
//                        startActivity(i);
//                    }
//                    else
//                    {
////                        Toast.makeText(UserDashBoard.this, "this is a fake user", Toast.LENGTH_LONG).show();
//                        Bundle bundle1 = new Bundle();
//                        bundle1.putString("UniqueID",null);
//                        i.putExtras(bundle1);
//                        startActivity(i);
//                    }
                    if (dataSnapshot.child(intentResult.getContents().trim()).exists())
                    {
                        final String pphno=(String) dataSnapshot.child(intentResult.getContents().trim()).child("Phno").getValue();
                        final String psname=(String) dataSnapshot.child(intentResult.getContents().trim()).child("FullName").getValue();

                        new AlertDialog.Builder(UserDashBoard.this)
                                .setTitle("AUTHORISED USER")
                                .setMessage("The user has been verified as\n\nName : "+psname+"\nPhone : "+pphno)
                                .create().show();
                    }
                    else{
                        new AlertDialog.Builder(UserDashBoard.this)
                                .setTitle("UNAUTHORISED USER")
                                .setMessage("The user does not exists in our database !!")
                                .create().show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
            Toast.makeText(this, "Please scan again", Toast.LENGTH_SHORT).show();

        super.onActivityResult(requestCode, resultCode, data);
    }


}
