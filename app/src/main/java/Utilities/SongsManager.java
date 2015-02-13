package Utilities;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ADMIN on 06-Feb-15.
 */
public class SongsManager {


    int i;

    public SongsManager() {

        getPlayList();
    }

    public static final String MEDIA_PATH = new String("/sdcard/");


    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();



    /**
     * Function to read all mp3 files from sdcard
     * and store the details in ArrayList
     */

    //FileExtensionFilter fef = new FileExtensionFilter();
    public ArrayList<HashMap<String, String>> getPlayList() {

        File home = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Music");

        if (home.listFiles(new FileExtensionFilter()).length > 0) {
            for (File file : home.listFiles(new FileExtensionFilter())) {
                HashMap<String, String> song = new HashMap<String, String>();
                song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
                song.put("songPath", file.getPath());
                // Adding each song to SongList
                songsList.add(song);

                Set keys = song.keySet();
                Iterator itr = keys.iterator();

                String key;
                String value;
                while(itr.hasNext())
                {
                    key = (String)itr.next();
                    value = (String)song.get(key);

                    Log.d("key for hashmap : " , key);
                    Log.d("value of key in hashmap" , value);
                }


            }
        }

        // return songs list array
        return songsList;


    }

    /**
     * Class to filter files which are having .mp3 extension
     */
    class FileExtensionFilter implements FilenameFilter {

        @Override
        public boolean accept(File file, String name) {
            boolean x =(name.endsWith(".mp3") || name.endsWith(".MP3"));
            Log.i("value returned by accept function",String.valueOf(x));
            return x;
        }
    }




}
