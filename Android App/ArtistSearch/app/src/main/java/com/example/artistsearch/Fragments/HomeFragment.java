package com.example.artistsearch.Fragments;

import static com.example.artistsearch.DetailActivity.SHARED_PREFS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.artistsearch.Adaptors.FavoriteArtistAdaptor;
import com.example.artistsearch.DetailActivity;
import com.example.artistsearch.HomeActivity;
import com.example.artistsearch.Models.FavoriteArtist;
import com.example.artistsearch.R;
import com.example.artistsearch.RecyclerViewInterface;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class HomeFragment extends Fragment implements RecyclerViewInterface {
    View view;
    private TextView linkText;
    private TextView dateText;
    RecyclerView favoriteRecyclerView;
    FavoriteArtistAdaptor favoriteArtistAdaptor;
    ArrayList<FavoriteArtist> favoriteArtistArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((HomeActivity)getActivity()).getSupportActionBar().show();
        view = inflater.inflate(R.layout.fragment_home, container, false);
        loadFavoriteData();
        linkText = view.findViewById(R.id.appLink);
        dateText = view.findViewById(R.id.appDate);
        setDate();
        openArtsy();
        favoriteRecyclerView = view.findViewById(R.id.favoriteRecyclerView);
        favoriteArtistAdaptor = new FavoriteArtistAdaptor(getContext(), favoriteArtistArrayList, this);
        favoriteRecyclerView .setAdapter(favoriteArtistAdaptor);
        favoriteRecyclerView .setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
    private void setDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = sdf.format(c.getTime());
        dateText.setText(strDate);
    }

    static private class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String url) {
            super(url);
        }
        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

    private void stripUnderlines(TextView textView) {
        Spannable s = new SpannableString(textView.getText());
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span: spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            s.setSpan(span, start, end, 0);
        }
        textView.setText(s);
    }

    private void openArtsy() {
        stripUnderlines(linkText);
        linkText.setBackgroundColor(Color.parseColor("#ffffff"));
        linkText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void loadFavoriteData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<FavoriteArtist>>() {}.getType();
        Map<String, ?> allFavorites = sharedPreferences.getAll();
        favoriteArtistArrayList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allFavorites.entrySet()) {
            String json = sharedPreferences.getString(entry.getKey(), null);
//            Log.d("JSON", entry.getKey());
            ArrayList<FavoriteArtist> tempList = gson.fromJson(json, type);

            String tempName = tempList.get(0).getName();
//            Log.d("Name", tempName);
            String tempId = tempList.get(0).getId();
            String tempNation = tempList.get(0).getNation();
            String tempBirth = tempList.get(0).getBirthday();
            favoriteArtistArrayList.add(new FavoriteArtist(tempName, tempId, tempNation, tempBirth));
        }
        if(favoriteArtistArrayList == null) {
            Log.d("MESSAGE", "List is Empty");
            favoriteArtistArrayList = new ArrayList<>();
        }
    }
    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("id", favoriteArtistArrayList.get(position).getId());
        intent.putExtra("name", favoriteArtistArrayList.get(position).getName());
        intent.putExtra("previous", "home");
        startActivity(intent);
    }
}