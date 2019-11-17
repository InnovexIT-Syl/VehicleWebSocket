package com.example.ragib.javawebsocketclientapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TestOCR extends AppCompatActivity {

    EditText ocr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ocr);

        ocr=findViewById(R.id.ocr_test);


    }

    public void onClickSubmit(View view) {
        Intent intent = new Intent(getApplicationContext(), _1SignInActivity.class);
        intent.putExtra("ocr",ocr.getText().toString().trim().toUpperCase());
        startActivity(intent);
    }
}
