package com.mydarkappfactory.hudsoncafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class HeadCountActivity extends AppCompatActivity {

    EditText headCount;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_count);

        email = getIntent().getStringExtra("Email");

        headCount = findViewById(R.id.headCount);

    }

    public void submitHeadCount(View view) {
        Intent intent = new Intent(HeadCountActivity.this, QRCodeActivity.class);
        intent.putExtra("Email", email);
        intent.putExtra("HeadCount", headCount.getText().toString());
        startActivity(intent);
        finish();
    }
}
