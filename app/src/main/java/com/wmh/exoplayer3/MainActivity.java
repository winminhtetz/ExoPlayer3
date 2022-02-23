package com.wmh.exoplayer3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class MainActivity extends AppCompatActivity {

    PlayerView playerView;
    ProgressBar progressBar;
    ImageView btFullScreen;
    SimpleExoPlayer simpleExoPlayer;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        playerView   = findViewById(R.id.player_view);
        btFullScreen = findViewById(R.id.bt_fullscreen);
        progressBar  = findViewById(R.id.progreess_bar);

        //make activity fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
        ,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String strVideoUrl = getIntent().getExtras().getString("vdLink");

        Uri videoUrl = Uri.parse("https://rr2---sn-4gvouppg-gbos.googlevideo.com/videoplayback?expire=1645655428&ei=JGEWYqqiDoCB3LUPsL6bkA4&ip=103.42.217.170&id=o-APUf2SoTYfSyG6G9oqUoaEGmoe17UHtLtHb8EOwgud4V&itag=22&source=youtube&requiressl=yes&mh=5-&mm=31%2C29&mn=sn-4gvouppg-gbos%2Csn-npoe7nlz&ms=au%2Crdu&mv=m&mvi=2&pl=24&initcwndbps=167500&vprv=1&mime=video%2Fmp4&ns=2lTN_b0XVfLU9bgWie8zpAkG&cnr=14&ratebypass=yes&dur=964.881&lmt=1636397160237911&mt=1645633502&fvip=3&fexp=24001373%2C24007246&c=WEB&txp=6211224&n=cAW-1qdqW1jGL34QDqD9&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIhALteZsuEivaURDU5ZhBzUaQq0CVKPEuxKR_a5DzEfZFqAiAkktJKEPvXsHtrUA8mUROmcBMaafOgydjaM66ORGU_RQ%3D%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRgIhALDcd0OgC58Xsm_ztY-qmHD7vwA-tBILCgilj2dJqVrIAiEAgUzZvc7wwIVL3JZGNAVIAc6v5F8myAtPzFbUQfDROyU%3D");

        //Uri videoUrl = Uri.parse("https://www.googleapis.com/drive/v3/files/1Fogjt7U3xQ_6TX1EelV0oRJLthN30kKu?alt=media&key=AIzaSyA1v-_D-0AnoNCGO-l1ce4DCRjIUEDTi7E");

        LoadControl loadControl = new DefaultLoadControl();

        //initialize band width meter
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        //track selector
        TrackSelector trackSelector = new DefaultTrackSelector(
                new AdaptiveTrackSelection.Factory(bandwidthMeter)
        );
        //Initialize simple exoplayer
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                MainActivity.this, trackSelector, loadControl
        );
        //Initialize data source factory
        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory(
                "exoplayer_video"
        );
        //Initialize extractors factory
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        //Initialize media source
        MediaSource mediaSource = new ExtractorMediaSource(videoUrl, factory,
                extractorsFactory, null, null);

        //set player
        playerView.setPlayer(simpleExoPlayer);
        //keep screen on
        playerView.setKeepScreenOn(true);
        //Prepare media
        simpleExoPlayer.prepare(mediaSource);
        //Play video when ready
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                //Check condition
                if (playbackState == Player.STATE_BUFFERING){
                    //When buffering
                    //Show progress bar
                    progressBar.setVisibility(View.VISIBLE);
                }else if (playbackState == Player.STATE_READY){
                    //When Ready
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

        btFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check condition
                if (flag){
                    //When flag is true
                    //Set enter full screen image
                    btFullScreen.setImageDrawable(getResources().
                            getDrawable(R.drawable.ic_fullscreen));

                    //set portrait orientation
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    //set flag value to false
                    flag = false;
                }
                else{
                    //When flag is false
                    //Set exit full screen image
                    btFullScreen.setImageDrawable(getResources().
                            getDrawable(R.drawable.ic_fullscreen_exit));
                    //set landscape orientation
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    flag = true;
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Stop video when ready
        simpleExoPlayer.setPlayWhenReady(false);
        //Get playback state
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Play video when ready
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();
    }
}