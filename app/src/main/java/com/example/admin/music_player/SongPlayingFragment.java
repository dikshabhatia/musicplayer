package com.example.admin.music_player;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SongPlayingFragment extends Fragment {




    public static SongPlayingFragment newInstance(String param1, String param2) {
        SongPlayingFragment fragment = new SongPlayingFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    public SongPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_song_playing, container, false);

        return rootView;
    }


}
