package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.music_player.PlayListFragment;
import com.example.admin.music_player.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utilities.SongsManager;

/**
 * Created by ADMIN on 06-Feb-15.
 */
public class SongsAdapter extends BaseAdapter {

    List<String> songsName;

    LayoutInflater inflater;

    public SongsAdapter(Context context, List<String> songsName) {

        this.songsName =songsName;
        inflater = (LayoutInflater) LayoutInflater.from(context);

    }



    ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();
    HashMap<String,String> songs = new HashMap<String,String>();
    int playlist_item;
    String[] strings;
    int[] ints;


    public SongsAdapter(PlayListFragment playListFragment, ArrayList<HashMap<String, String>> songsListData, int playlist_item, String[] strings, int[] ints) {

        this.songsListData=songsListData;
        this.playlist_item=playlist_item;
        this.strings=strings;
        this.ints=ints;
    }

    SongsManager plm = new SongsManager();
    // get all songs from sdcard


    @Override
    public int getCount() {
        return songsName.size();
    }

    @Override
    public Object getItem(int i) {
        return songsName.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Holder holder;
        if (convertView == null)    //there is nothing in the recycle bin
        {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.playlist_item, null);
            holder.tv_song_title = (TextView) convertView.findViewById(R.id.tv_song_title);
            holder.tv_singer_name = (TextView) convertView.findViewById(R.id.tv_singer_name);
            holder.iv_song_image = (ImageView) convertView.findViewById(R.id.iv_song_image);

            convertView.setTag(holder);
        } else {

            holder = (Holder) convertView.getTag();
        }
        holder.tv_song_title.setText(songsName.get(position));
        holder.tv_singer_name.setText(songsName.get(position));


        return convertView;


    }

    static class Holder {
        TextView tv_song_title;    //these are pointers
        TextView tv_singer_name;
        ImageView iv_song_image;

    }


}
