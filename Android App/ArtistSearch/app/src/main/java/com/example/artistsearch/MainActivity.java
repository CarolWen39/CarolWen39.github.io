package com.example.artistsearch;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.splashScreenTheme);
        setContentView(R.layout.activity_main);
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
//                Log.d("CREATION", "We are waiting!\n");
//                setTheme(R.style.progressBarThems);
//                setContentView(R.layout.activity_main);
//                ProgressBar initialProgressBar = findViewById(R.id.progressBar1);
//                initialProgressBar.setVisibility(ProgressBar.VISIBLE);
//                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        ProgressBar initialProgressBar = findViewById(R.id.progressBar1);
//                        initialProgressBar.setVisibility(ProgressBar.INVISIBLE);
//                    }
//                }, 2000);


                // open Home Screen
                Intent intent = new Intent(MainActivity.this, progressBarActivity.class);
                startActivity(intent);
            }
        }, 2000);

    }
}