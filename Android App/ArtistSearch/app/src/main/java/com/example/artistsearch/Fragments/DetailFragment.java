package com.example.artistsearch.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.artistsearch.MyListener;
import com.example.artistsearch.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailFragment extends Fragment {
    View view;
    ProgressBar loadingPb;
    TextView loadingText;
    LinearLayout nameTag, nationTag, birthTag, deathTag, bioTag;
    TextView nameRes, nationRes, birthdayRes, deathdayRes, bioRes;
    RequestQueue requestQueue;
    String id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail, container, false);
        this.id = getArguments().getString("artistId");
        this.loadingPb = view.findViewById(R.id.DetailLoadingPB);
        this.loadingText = view.findViewById(R.id.DetailLoadingText);
        this.loadingPb.setVisibility(View.VISIBLE);
        this.loadingText.setVisibility(View.VISIBLE);

        this.nameTag = view.findViewById(R.id.detailName);
        this.nationTag = view.findViewById(R.id.detailNation);
        this.birthTag = view.findViewById(R.id.detailBirth);
        this.deathTag = view.findViewById(R.id.detailDeath);
        this.bioTag = view.findViewById(R.id.detailBio);
        this.nameTag.setVisibility(View.INVISIBLE);
        this.nationTag.setVisibility(View.INVISIBLE);
        this.birthTag.setVisibility(View.INVISIBLE);
        this.deathTag.setVisibility(View.INVISIBLE);
        this.bioTag.setVisibility(View.INVISIBLE);

        this.nameRes = view.findViewById(R.id.detailNameRes);
        this.nationRes = view.findViewById(R.id.detailNationRes);
        this.birthdayRes = view.findViewById(R.id.detailBirthRes);
        this.deathdayRes = view.findViewById(R.id.detailDeathRes);
        this.bioRes = view.findViewById(R.id.detailBioRes);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
       search_detail(this.id);

        return view;
    }

    private void search_detail(String id) {
//        String url = String.format("http://10.0.2.2:8080/detail?artistId=%1$s",id);
        String url = String.format("https://webtechnologyhw9-57139.wl.r.appspot.com/detail?artistId=%1$s",id);
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String name = response.getString("name");
                            String nation = response.getString("nation");
                            String birthday = response.getString("birthday");
                            String deathday = response.getString("deathday");
                            String bio = response.getString("bio");
                             // hide loading spinner and show search data results
                            loadingPb.setVisibility(View.GONE);
                            loadingText.setVisibility(View.GONE);

                            // show data
                            if(checkNull(name)) {
                                nameTag.setVisibility(View.VISIBLE);
                                nameRes.setText(name);
                            }
                            if(checkNull(nation)) {
                                nationTag.setVisibility(View.VISIBLE);
                                nationRes.setText(nation);
                            }
                            else {
                                nation = "";
                            }
                            if(checkNull(birthday)) {
                                birthTag.setVisibility(View.VISIBLE);
                                birthdayRes.setText(birthday);
                            }
                            else {
                                birthday = "";
                            }
                            if(checkNull(deathday)) {
                                deathTag.setVisibility(View.VISIBLE);
                                deathdayRes.setText(deathday);
                            }
                            if(checkNull(bio)) {
                                bioTag.setVisibility(View.VISIBLE);
                                bioRes.setText(bio);
                            }
                            MyListener myListener = (MyListener) getActivity();
                            myListener.sendDataBack(nation, birthday);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("That didn't work!", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private boolean checkNull(String s) {
        return !(s.equals("") || s == null || s.equals("unknown") || s.equals("null"));
    }
}