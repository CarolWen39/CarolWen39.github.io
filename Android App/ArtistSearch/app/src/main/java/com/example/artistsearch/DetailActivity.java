package com.example.artistsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.artistsearch.Adaptors.TabAdaptor;
import com.example.artistsearch.Fragments.ArtworkFragment;
import com.example.artistsearch.Fragments.DetailFragment;
import com.example.artistsearch.Fragments.HomeFragment;
import com.example.artistsearch.Models.FavoriteArtist;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements MyListener{
    String id, name, nation, birthday;
    TextView nameTv;
    ImageButton backBtn;
    ImageButton favoriteBtn;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    TabAdaptor tabAdaptor;
    String previous;
    public static final String SHARED_PREFS = "sharedPrefs";
    SharedPreferences sharedPreferences;
    ArrayList<FavoriteArtist> favoriteArtistArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.progressBarTheme);
        setContentView(R.layout.activity_detail);
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        previous = getIntent().getStringExtra("previous");
        nameTv = findViewById(R.id.artistDetailName);
        nameTv.setText(name);


        // send data to detail fragment
        Bundle detailBundle = new Bundle();
        detailBundle.putString("artistId", id);
        detailBundle.putString("artistName", name);

        // send data to artwork fragment
        Bundle artworkBundle = new Bundle();
        artworkBundle.putString("artistId", id);
        // set Fragmentclass Arguments
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(detailBundle);
        ArtworkFragment artworkFragment = new ArtworkFragment();
        artworkFragment.setArguments(artworkBundle);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.tabPage);
        tabAdaptor = new TabAdaptor(this, id, name);
        viewPager2.setAdapter(tabAdaptor);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0) {
                    tab.setText("DETAILS");
                    tab.setIcon(R.drawable.ic_detail);
                }
                else {
                    tab.setText("ARTWORK");
                    tab.setIcon(R.drawable.ic_artwork);
                }
            }
        });
        tabLayoutMediator.attach();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        backBtn = findViewById(R.id.backBtn);
        favoriteBtn = findViewById(R.id.favoriteBtn);

        // check whether artist in favorite already
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if(sharedPreferences.contains(id)) {
            favoriteBtn.setBackgroundResource(R.drawable.ic_menu_favorited);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(previous.equals("home")) {
//                    Log.d("previous", previous);
                    Intent intent = new Intent(DetailActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else {
//                    Log.d("previous11111", previous);
                    onBackPressed();
                }
            }
        });
        favoriteArtistArrayList = new ArrayList<>();
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // already favorite
                if(sharedPreferences.contains(id)) {
                    favoriteBtn.setBackgroundResource(R.drawable.ic_menu_favorite);
                    removeFavoriteData(id);
                    Toast.makeText(DetailActivity.this, name + " is removed from favorites", Toast.LENGTH_SHORT).show();

                }
                else {
                    favoriteArtistArrayList.add(new FavoriteArtist(name, id, nation, birthday));
                    favoriteBtn.setBackgroundResource(R.drawable.ic_menu_favorited);
                    addFavoriteData();
                    Toast.makeText(DetailActivity.this, name + " is added to favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void sendDataBack(String nation, String birthday) {
        this.nation = nation;
        this.birthday = birthday;
    }

    public void addFavoriteData() {
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favoriteArtistArrayList);
        editor.putString(id, json);
        editor.apply();
    }

    public void removeFavoriteData(String id) {
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(id);
        editor.apply();
    }


}