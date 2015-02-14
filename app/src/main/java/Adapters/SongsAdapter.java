package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.music_player.R;

import java.util.ArrayList;
import java.util.HashMap;

import Utilities.SongsManager;

/**
 * Created by ADMIN on 06-Feb-15.
 */
public class SongsAdapter extends BaseAdapter {



    LayoutInflater inflater;

    ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    HashMap<String,String> songs = new HashMap<String,String>();

    SongsManager plm = new SongsManager();

    public SongsAdapter(Context context, ArrayList<HashMap<String, String>> songsList) {
        this.songsList = songsList;
        inflater = (LayoutInflater) LayoutInflater.from(context);


    }


    // get all songs from sdcard


    @Override
    public int getCount() {
        return songsList.size();
    }

    @Override
    public Object getItem(int i) {
        return songsList.get(i);
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

            holder.iv_song_image = (ImageView) convertView.findViewById(R.id.iv_song_image);

            convertView.setTag(holder);
        } else {

            holder = (Holder) convertView.getTag();
        }
        holder.tv_song_title.setText((CharSequence) songsList.get(position).get("songTitle"));



        return convertView;


    }

    static class Holder {
        TextView tv_song_title;    //these are pointers

        ImageView iv_song_image;

    }


}
