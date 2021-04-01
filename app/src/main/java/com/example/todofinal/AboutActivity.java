package com.example.todofinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    // create and return a intent that launches this activity
    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    // go back to the calling activity
    public void goBack(View view) {
        finish();
    }
}