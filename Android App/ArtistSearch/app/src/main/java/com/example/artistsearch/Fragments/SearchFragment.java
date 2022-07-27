package com.example.artistsearch.Fragments;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.artistsearch.Adaptors.ArtistAdaptor;
import com.example.artistsearch.DetailActivity;
import com.example.artistsearch.HomeActivity;
import com.example.artistsearch.Models.Artist;
import com.example.artistsearch.R;
import com.example.artistsearch.RecyclerViewInterface;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements RecyclerViewInterface {
    String artistName;
    private ProgressBar loadingPb;
    private TextView loadingText;
    private RequestQueue requestQueue;
    private RecyclerView artistListRecyclerView;
    private ArtistAdaptor artistAdaptor;
    private ArrayList<Artist> artistList;
    private TextView noResult;
    TextView nameTv;
    ImageButton backBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((HomeActivity)getActivity()).getSupportActionBar().hide();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        this.artistName = getArguments().getString("artistName");
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        getContext().setTheme(R.style.progressBarTheme);

        nameTv = view.findViewById(R.id.artistSearchName);
        nameTv.setText(this.artistName.toUpperCase());
        backBtn = view.findViewById(R.id.searchBackBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), HomeActivity.class);
//                intent.putExtra("name", artistName);
//                startActivity(intent);
                Fragment homeFragment = new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,
                        homeFragment).commit();
//                ((HomeActivity)getActivity()).onBackPressed();
            }
        });
        // show loading spinner
        this.loadingPb = view.findViewById(R.id.SearchLoadingPB);
        this.loadingText = view.findViewById(R.id.SearchLoadingText);
        this.loadingPb.setVisibility(View.VISIBLE);
        this.loadingText.setVisibility(View.VISIBLE);
        this.noResult = view.findViewById(R.id.noSearchResult);

        // search artists and show
        artistListRecyclerView = view.findViewById(R.id.searchRecyclerView);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        artistList = new ArrayList<>();
        artistAdaptor = new ArtistAdaptor(getContext(), artistList, this);
        search(this.artistName);

        return view;
    }

    private void search(String artistName) {
//        String url = String.format("http://10.0.2.2:8080/search?artistName=%1$s",artistName);
        String url = String.format("https://webtechnologyhw9-57139.wl.r.appspot.com/search?artistName=%1$s",artistName);
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if(response.length() > 0) {
                                for(int i = 0; i< response.length(); i++) {
                                    JSONObject item = response.getJSONObject(i);
                                    String name = item.getString("name");
                                    String img = item.getString("img");
                                    String id = item.getString("id");
                                    artistList.add(new Artist(name, img, id));
                                }

                                // hide loading spinner and show search data results
                                loadingPb.setVisibility(View.GONE);
                                loadingText.setVisibility(View.GONE);

                                // show data
                                artistListRecyclerView.setAdapter(artistAdaptor);
                                artistListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            }
                            else {
                                // hide loading spinner and show search data results
                                loadingPb.setVisibility(View.GONE);
                                loadingText.setVisibility(View.GONE);

                                noResult.setVisibility(View.VISIBLE);
                            }

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
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("id", artistList.get(position).getId());
        intent.putExtra("name", artistList.get(position).getName());
        intent.putExtra("previous", "search");
        startActivity(intent);

    }
}