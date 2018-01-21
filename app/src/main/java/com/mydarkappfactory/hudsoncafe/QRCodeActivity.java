package com.mydarkappfactory.hudsoncafe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeActivity extends AppCompatActivity {

    String email, headCount;
    EditText otpText;
    ImageView qrCode;
    DatabaseReference firebaseDb;
    boolean isOtpSubmitted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        headCount = getIntent().getStringExtra("HeadCount");
        email = getIntent().getStringExtra("Email");

        otpText = findViewById(R.id.otpEdtTxt);
        qrCode = findViewById(R.id.qrCode);

        isOtpSubmitted = true;

        firebaseDb = FirebaseDatabase.getInstance().getReference();

        Gson gson = new Gson();

        String qrCodeData = gson.toJson(new QRCodeData(email, headCount));

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(qrCodeData, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        firebaseDb.child("USERS").child(email).child("assignedTable").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int tableNumber = dataSnapshot.getValue(Integer.class);
                if (isOtpSubmitted) {
                    if (tableNumber != -1) {
                        firebaseDb.child("tables").child(tableNumber + "").child("Customer_id").setValue(email);
                        Intent intent = new Intent(QRCodeActivity.this, MenuCarrousal.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void submitOtp(View view) {
        String otp = otpText.getText().toString();
        if (otp.length() == 6) {

            final int tableNumber = Integer.parseInt(otp.substring(0, 2));
            final int otpTable = Integer.parseInt(otp.substring(2, 6));
            firebaseDb.child("tables").child(tableNumber + "").child("otp").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int tableOtp = dataSnapshot.getValue(Integer.class);
                    if (tableOtp == otpTable) {
                        isOtpSubmitted = false;
                        firebaseDb.child("USERS").child(email).child("assignedTable").setValue(tableNumber);
                        firebaseDb.child("tables").child(tableNumber + "").child("Customer_id").setValue(email);
                        Intent intent = new Intent(QRCodeActivity.this, MenuCarrousal.class);
                        startActivity(intent);
                        finish();
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }
}
