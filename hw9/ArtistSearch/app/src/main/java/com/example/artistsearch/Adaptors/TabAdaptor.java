package com.example.artistsearch.Adaptors;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.artistsearch.Fragments.ArtworkFragment;
import com.example.artistsearch.Fragments.DetailFragment;


public class TabAdaptor extends FragmentStateAdapter {
    String id;
    String name;
    public TabAdaptor(@NonNull FragmentActivity fragmentActivity, String id, String name) {
        super(fragmentActivity);
        this.id = id;
        this.name = name;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("artistId", id);
        if (position == 1) {
            ArtworkFragment artworkFragment = new ArtworkFragment();
            artworkFragment.setArguments(bundle);
            return artworkFragment;
        }
        DetailFragment detailFragment = new DetailFragment();
        bundle.putString("artistName", name);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
