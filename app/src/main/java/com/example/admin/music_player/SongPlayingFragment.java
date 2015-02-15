package com.example.admin.music_player;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import Utilities.SongsManager;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class SongPlayingFragment extends Fragment implements PlayListFragment.SongSelectionListner{


    public final static String TAG = "SongPlayingFragment";
    public static int ONE_TIME_ONLY = 0;
    public static String currentSonghPath = "";
    @InjectView(R.id.tv_end_time)
    TextView tv_end_time;
    @InjectView(R.id.tv_now_playing)
    TextView tv_now_playing;
    @InjectView(R.id.tv_song_name)
    TextView tv_song_name;
    @InjectView(R.id.tv_start_time)
    TextView tv_start_time;
    @InjectView(R.id.ib_forward)
    ImageButton ib_forward;
    @InjectView(R.id.ib_rewind)
    ImageButton ib_rewind;
    @InjectView(R.id.ib_pause)
    ImageButton ib_pause;
    @InjectView(R.id.ib_play)
    ImageButton ib_play;
    @InjectView(R.id.seekBar)
    SeekBar seekBar;
    @InjectView(R.id.iv_song_image)
    ImageView iv_song_image;
    @InjectView(R.id.bt_select_song)
    Button bt_select_song;
    @InjectView(R.id.bt_check)
    Button bt_check;
    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    ;
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tv_start_time.setText(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                            toMinutes((long) startTime)))
            );
            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;

    public SongPlayingFragment() {
        // Required empty public constructor
    }

    public static SongPlayingFragment newInstance(String param1, String param2) {
        SongPlayingFragment fragment = new SongPlayingFragment();
        Bundle args = new Bundle();


        return fragment;
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
        final View rootView = inflater.inflate(R.layout.fragment_song_playing, container, false);

        ButterKnife.inject(this, rootView);


        if (ib_forward == null) {
            Log.i(TAG, "forward button is null");
        } else {
            Log.i(TAG, "forward button is not null");
        }

        tv_song_name.setText("mysong.mp3");
        //Convenience method to create a MediaPlayer for a given resource id.

       // String songPath=getArguments().getString("songPath");
       //  int songIndex = getArguments().getInt("songIndex");

        seekBar.setClickable(false);
        ib_pause.setEnabled(false);

        ib_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSonghPath.isEmpty()) {
                    bt_select_song.callOnClick();
                } else {
                    play(currentSonghPath);
                }

            }
        });
        ib_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
            }
        });

        ib_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forward();
            }
        });
        ib_rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rewind();
            }
        });

        bt_select_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack("SPFRAG");
                fragmentTransaction.add(android.R.id.content, new PlayListFragment(SongPlayingFragment.this));
                fragmentTransaction.commit();
            }
        });

        bt_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SongsManager sm = new SongsManager();
            }
        });

        return rootView;
    }

    public void play(String songPath) {
        //Toast.makeText(getActivity(), "Playing sound", Toast.LENGTH_SHORT).show();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(songPath));
        mediaPlayer.start();
        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();
        if (ONE_TIME_ONLY == 0) {
            seekBar.setMax((int) finalTime);
            ONE_TIME_ONLY = 1;
        }

        tv_end_time.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) finalTime)))
        );
        tv_end_time.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) startTime)))
        );
        seekBar.setProgress((int) startTime);
        myHandler.postDelayed(UpdateSongTime, 100);
        ib_pause.setEnabled(true);
        ib_play.setEnabled(false);
    }

    public void pause() {
        Toast.makeText(getActivity(), "Pausing sound",
                Toast.LENGTH_SHORT).show();

        mediaPlayer.pause();
        ib_pause.setEnabled(false);
        ib_play.setEnabled(true);
    }

    public void forward() {
        int temp = (int) startTime;
        if ((temp + forwardTime) <= finalTime) {
            startTime = startTime + forwardTime;
            mediaPlayer.seekTo((int) startTime);
        } else {
            Toast.makeText(getActivity(),
                    "Cannot jump forward 5 seconds",
                    Toast.LENGTH_SHORT).show();
        }


    }


    public void rewind(){
        int temp = (int)startTime;
        if((temp-backwardTime)>0){
            startTime = startTime - backwardTime;
            mediaPlayer.seekTo((int) startTime);
        }
        else{
            Toast.makeText(getActivity(),
                    "Cannot jump backward 5 seconds",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSongSelected(String songPath) {
        play(songPath);
        currentSonghPath = songPath;

    }
}
