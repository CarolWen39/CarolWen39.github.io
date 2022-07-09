package com.example.artistsearch.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.artistsearch.Adaptors.ArtistAdaptor;
import com.example.artistsearch.Adaptors.ArtworkAdaptor;
import com.example.artistsearch.Models.Artist;
import com.example.artistsearch.Models.Artwork;
import com.example.artistsearch.Models.Category;
import com.example.artistsearch.R;
import com.example.artistsearch.RecyclerViewInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ArtworkFragment extends Fragment implements RecyclerViewInterface {
    View view;
    String id;
    RecyclerView artworkListRecyclerView;
    private ArtworkAdaptor artworkAdaptor;
    private ArrayList<Artwork> artworkArrayList;
    ArrayList<String> cateResult;
    private TextView noResult;
    RequestQueue requestQueue;
    RequestQueue cateRequestQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_artwork, container, false);
        this.id = getArguments().getString("artistId");

        noResult = view.findViewById(R.id.noArtworkResult);
        artworkListRecyclerView = view.findViewById(R.id.artworkRecyclerView);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        cateRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        artworkArrayList = new ArrayList<>();
        cateResult = new ArrayList<>();
        artworkAdaptor = new ArtworkAdaptor(getContext(), artworkArrayList, this);
        search_artwork(this.id);
        return view;
    }

    private void search_artwork(String id) {
        String url = String.format("https://webtechnologyhw9-57139.wl.r.appspot.com/artwork?artistId=%1$s", id);
//        String url = String.format("http://10.0.2.2:8080/artwork?artistId=%1$s",id);
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
//                            Log.d("response",response.toString());
                            if(response.length() > 0) {
                                for(int i = 0; i< response.length(); i++) {
                                    JSONObject item = response.getJSONObject(i);
                                    String name = item.getString("title");
                                    String img = item.getString("img");
                                    String id = item.getString("id");
                                    artworkArrayList.add(new Artwork(id, img, name));
//                                    cateResult = search_category(id);
//                                    artworkArrayList.add(new Artwork(id, img, name, cateResult.get(0), cateResult.get(1), cateResult.get(2)));

                                }
//                                Log.d("New Cate", artworkArrayList.toString());
                                // show data
                                artworkListRecyclerView.setAdapter(artworkAdaptor);
                                artworkListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            }
                            else {
                                // hide loading spinner and show search data results
//                                loadingPb.setVisibility(View.GONE);
//                                loadingText.setVisibility(View.GONE);
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
        final Dialog dialog = new Dialog(getContext());
        String artworkId = artworkArrayList.get(position).getArtworkId();

        dialog.setContentView(R.layout.category_dialog);

        ImageView cateImg = dialog.findViewById(R.id.categoryImg);
        TextView cateDesc = dialog.findViewById(R.id.descrptionRes);
        TextView cateTitle = dialog.findViewById(R.id.TitleRes);
        TextView noCate = dialog.findViewById(R.id.noCateResult);
        LinearLayout hasResult = dialog.findViewById(R.id.hasResult);
        ScrollView scrollView = dialog.findViewById(R.id.scrollView);

        scrollView.setVisibility(View.GONE);
         //0 represents no reuslts
//        TextView titleTag = dialog.findViewById(R.id.categoryTitle);
//        TextView descrption = dialog.findViewById(R.id.categoryDesc);
//        titleTag.setVisibility(View.GONE);
//        descrption.setVisibility(View.GONE);
        cateRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = String.format("https://webtechnologyhw9-57139.wl.r.appspot.com/categories?artworkId=%1$s",artworkId);
//        String url = String.format("http://10.0.2.2:8080/categories?artworkId=%1$s",artworkId);
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
//                            Log.d("response",response.toString());
                            if(response.length() > 0) {
                                JSONObject item = response.getJSONObject(0);
                                String title = item.getString("title");
                                String img = item.getString("img");
                                String desc = item.getString("desc");
//                                    artworkArrayList.add(new Artwork(id, img, name));
                                if(!img.equals("/assets/shared/missing_image.png")) {
                                    Glide.with(getContext()).load(img).into(cateImg);
                                }
                                scrollView.setVisibility(View.VISIBLE);
                                cateTitle.setText(title);
                                cateDesc.setText(desc);
//                                dialog.getWindow().setLayout(1000, 1400);
                            }
                            else {
                                noCate.setVisibility(View.VISIBLE);
                                dialog.getWindow().setLayout(800, 220);
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
        cateRequestQueue.add(jsonArrayRequest);

        dialog.show();

    }


}