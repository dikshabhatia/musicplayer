package com.example.admin.music_player;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import Adapters.SongsAdapter;
import Utilities.SongsManager;


public class PlayListFragment extends Fragment {

    ListView listView;
    public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_play_list, container, false);

        ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();
        SongsManager plm = new SongsManager();
        // get all songs from sdcard
        this.songsList = plm.getPlayList();

        // looping through playlist
        for (int i = 0; i < songsList.size(); i++) {
            // creating new HashMap
            HashMap<String, String> song = songsList.get(i);

            // adding HashList to ArrayList
            songsListData.add(song);
        }

       listView =(ListView)rootView.findViewById(R.id.lv_songs);
       SongsAdapter adapterObject= new SongsAdapter(getActivity(),songsList);
       listView.setAdapter(adapterObject);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                //bundle.putString("songPath",songsList.get(i).get("songPath"));

                bundle.putInt("songIndex" , i);

                SongPlayingFragment fragment = new SongPlayingFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(android.R.id.content,fragment).addToBackStack("").commit();


            }
        });



        return rootView;
    }



}
