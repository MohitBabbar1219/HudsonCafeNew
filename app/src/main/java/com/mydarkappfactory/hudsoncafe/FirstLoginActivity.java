package com.mydarkappfactory.hudsoncafe;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirstLoginActivity extends AppCompatActivity {

    EditText fnameEdt, lnameEdt, mobileNumEdt;
    String fname, lname, mobileNum, email, password;
    DatabaseReference firebaseDb;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);

        SQLiteOpenHelper dbHelper = new DBHelper(FirstLoginActivity.this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("EMAIL_PASSWORD", new String[]{"EMAIL", "PASSWORD"},
                "_id = 1", null, null, null, null);
        cursor.moveToFirst();

        firebaseDb = FirebaseDatabase.getInstance().getReference();

        email = cursor.getString(0);
        password = cursor.getString(1);

        cursor.close();

        fnameEdt = findViewById(R.id.first_name_edttxt);
        lnameEdt = findViewById(R.id.last_name_edttxt);
        mobileNumEdt = findViewById(R.id.mobile_edttxt);

        firebaseDb = FirebaseDatabase.getInstance().getReference();



    }

    public void submitUserDetails(View view) {

        fname = fnameEdt.getText().toString();
        lname = lnameEdt.getText().toString();
        mobileNum = mobileNumEdt.getText().toString();

        UserDetails details = new UserDetails(fname, lname, mobileNum, email, password, false, -1, new ArrayList<Record>());

        firebaseDb.child(FirebaseModel.Users.USERS).child(email.substring(0, email.indexOf('.'))).setValue(details).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("Bpit", "successfull he bhaii");
                    ContentValues recordValues = new ContentValues();
                    recordValues.put("ANSWER", 1);
                    db.update("IS_LOGGED_IN", recordValues, "_id = 1", null);
                }
            }
        });


        Intent intent = new Intent(FirstLoginActivity.this, MenuCarrousal.class);
        startActivity(intent);
        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Log.d("Bpit", "FirstLogin: finished");

                finish();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
