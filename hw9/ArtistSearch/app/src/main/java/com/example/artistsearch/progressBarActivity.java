package com.example.artistsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.ProgressBar;

public class progressBarActivity extends AppCompatActivity {
    MenuItem mDynamicMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.progressBarTheme);

        setContentView(R.layout.activity_progressbar);

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ProgressBar initialProgressBar = findViewById(R.id.progressBar1);
                initialProgressBar.setVisibility(ProgressBar.INVISIBLE);

//                RecyclerView homeview = findViewById(R.id.appHome);
//                homeview.setVisibility(RecyclerView.VISIBLE);
                Intent intent = new Intent(progressBarActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }, 2000);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Log.d("prepare", "Yes");
        mDynamicMenuItem.setVisible(false);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        mDynamicMenuItem = menu.findItem(R.id.action_search);
        Log.d("creat", "Yes");
        return false;
    }
}