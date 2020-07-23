package com.help.sandesh.User;

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

public class CovidStateSelect extends AppCompatActivity {
    Spinner spin1;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spindata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_state_select);
        spin1=findViewById(R.id.xmlspin1);
        databaseReference= FirebaseDatabase.getInstance().getReference("State");
        spindata=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,spindata);
        spin1.setAdapter(adapter);
        retrievedata();
    }
    public void retrievedata()
    {
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
    public void selectstate(View v) {

        if (spin1.getCount()==0){
            Toast.makeText(getApplicationContext(),"Please select your State /UT",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Intent i = new Intent(this, locateCovid19.class);
            Spinner mySpinner = (Spinner) findViewById(R.id.xmlspin1);
            String text = mySpinner.getSelectedItem().toString();
            Bundle bundle = new Bundle();
            bundle.putString("state",text);
            i.putExtras(bundle);
            startActivity(i);
        }
    }
}
