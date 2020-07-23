package com.help.sandesh.User;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.help.sandesh.R;

public class detailsqr extends AppCompatActivity {

    TextView tx1,tx2,tx3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsqr);

        tx1=findViewById(R.id.txt1);
        tx2=findViewById(R.id.txt2);
        tx3=findViewById(R.id.txt3);
        Bundle bundle = getIntent().getExtras();
        final String uuid = bundle.getString("UniqueID");

        if (uuid!=null){
            final String uname = bundle.getString("nName");
            final String uphno = bundle.getString("nPhone");
            tx1.setText(uuid.toUpperCase());
            tx1.setTextColor(Color.GREEN);
            tx2.setText("Name : "+uname);
            tx3.setText("Phone No. : "+uphno);
        }
        else {
            tx1.setText("FAILED !!");
            tx1.setTextColor(Color.RED);
            tx2.setText("The USER is not registered");
        }
    }
}
