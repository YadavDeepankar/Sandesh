package com.help.sandesh.Service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.help.sandesh.R;

import java.util.ArrayList;

public class ServiceChooseState extends AppCompatActivity {

    Spinner spin1,spin2;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayAdapter<String> adapter,adapter2;
    ArrayList<String> spindata,spindata2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_choose_state);

        spin1=findViewById(R.id.xmlspin1);
        databaseReference= FirebaseDatabase.getInstance().getReference("State");
        spindata=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,spindata);
        spin1.setAdapter(adapter);
        retrievedata();

        spin2=findViewById(R.id.xmlspin2);
        databaseReference= FirebaseDatabase.getInstance().getReference("Service");
        spindata2=new ArrayList<String>();
        adapter2=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,spindata2);
        spin2.setAdapter(adapter2);
        retrievedata2();
    }

    public void retrievedata() {
        spindata.clear();
        listener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren())
                {
                    spindata.add(item.getKey().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void retrievedata2() {
        spindata2.clear();
        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    spindata2.add(item.getKey().toString());
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void confirm(View v) {

        if (spin1.getCount()==0){
            Toast.makeText(getApplicationContext(),"Please select your State /UT",
                    Toast.LENGTH_LONG).show();
        }

        else  if (spin2.getCount()==0){
            Toast.makeText(getApplicationContext(),"Please select your SERVICE category",
                    Toast.LENGTH_LONG).show();
        }

        else {
            Intent i = new Intent(this, MapsActivity.class);

            Spinner mySpinner = (Spinner) findViewById(R.id.xmlspin1);
            String text = mySpinner.getSelectedItem().toString();

            Spinner mySpinner2 = (Spinner) findViewById(R.id.xmlspin2);
            String text2 = mySpinner2.getSelectedItem().toString();

            Bundle bundle = new Bundle();
            bundle.putString("state",text);
            bundle.putString("service",text2);
            i.putExtras(bundle);
            startActivity(i);

        }

    }

}
