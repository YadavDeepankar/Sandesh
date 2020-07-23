package com.help.sandesh.Service;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.help.sandesh.R;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GenQR extends AppCompatActivity {
    Button btngeen;
    ImageView imgcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_qr);
        btngeen=findViewById(R.id.btngen);
        imgcode=findViewById(R.id.imgviewgen);
        Bundle bundle = getIntent().getExtras();
        final String uuid = bundle.getString("UniqueID");
        btngeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uuid.length()!=0)
                {
                    try {
                        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
                        BitMatrix bitMatrix=multiFormatWriter.encode(uuid, BarcodeFormat.QR_CODE,500,500);
                        BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
                        Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
                        imgcode.setImageBitmap(bitmap);
                    }
                    catch (Exception e){
                        Toast.makeText(GenQR.this, ""+e.toString(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}