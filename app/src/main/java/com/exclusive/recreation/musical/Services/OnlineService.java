package com.exclusive.recreation.musical.Services;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.exclusive.recreation.musical.Bus.GlobalBus;
import com.exclusive.recreation.musical.Bus.MessageEvent;
import com.exclusive.recreation.musical.R;
import com.exclusive.recreation.musical.View.Adapter.utils.Constant;
import com.exclusive.recreation.musical.View.MainActivity;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Random;




public class OnlineService extends IntentService implements Player.EventListener {

    public static final String ACTION_TOGGLE = "action.ACTION_TOGGLE";
    public static final String ACTION_PLAY = "action.ACTION_PLAY";
    public static final String ACTION_NEXT = "action.ACTION_NEXT";
    public static final String ACTION_PREVIOUS = "action.ACTION_PREVIOUS";
    public static final String ACTION_STOP = "action.ACTION_STOP";
    public static final String ACTION_SEEKTO = "action.ACTION_SEEKTO";

    static ExoPlayer exoPlayer = null;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder notification;
    RemoteViews bigViews, smallViews;

    DataSource.Factory dataSourceFactory;
    ExtractorsFactory extractorsFactory;

    private String NOTIFICATION_CHANNEL_ID = "onlinemp3_ch_1";

    static OnlineService playerService;

    Boolean isNewSong = false;

    Bitmap bitmap;

    static public OnlineService getInstance() {
        if (playerService == null) {
            playerService = new OnlineService();
        }
        return playerService;
    }

    public static Boolean getIsPlayling() {
        return exoPlayer != null && exoPlayer.getPlayWhenReady();
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param    to name the worker thread, important only for debugging.
     */
    public OnlineService() {
        super(null);
    }


    @Override
    public void onCreate() {
        try {
            registerReceiver(onCallIncome, new IntentFilter("android.intent.action.PHONE_STATE"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), Util.getUserAgent(getApplicationContext(), "onlinemp3"), bandwidthMeter);
        extractorsFactory = new DefaultExtractorsFactory();

        exoPlayer = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);
        exoPlayer.addListener(this);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        super.onCreate();
    }


    @Override
    public int onStartCommand(@Nullable  Intent intent, int flags, int startId) {
        try {
            String action = intent.getAction();
            switch (action) {

                case ACTION_PLAY:
                    startNewSong();
                    break;
                case ACTION_TOGGLE:
                    togglePlay();
                    break;
                case ACTION_SEEKTO:
                    seekTo(intent.getExtras().getLong("seekto"));
                    break;
                case ACTION_STOP:
                    stop(intent);
                    break;
                case ACTION_PREVIOUS:
                    if (true) {
                        previous();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case ACTION_NEXT:
                    if (true) {
                        next();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    private void next() {
        Log.e( "next: ","next" );
        setBuffer(true);
        if (Constant.isSuffle) {
            Random rand = new Random();
            Constant.playPos = rand.nextInt((Constant.arrayList_play.size() - 1) + 1);
        } else {
            if (Constant.playPos < (Constant.arrayList_play.size() - 1)) {
                Constant.playPos = Constant.playPos + 1;
            } else {
                Constant.playPos = 0;
            }
        }
        startNewSong();

    }

    private void stop(Intent intent) {
        Constant.isPlaying = false;
        Constant.isPlayed = false;

        exoPlayer.setPlayWhenReady(false);
        changePlayPause(false);
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
        try {
            unregisterReceiver(onCallIncome);
        } catch (Exception e) {
            e.printStackTrace();
        }
        stopService(intent);
        stopForeground(true);
    }

    private void togglePlay() {

        if (exoPlayer.getPlayWhenReady()) {
            exoPlayer.setPlayWhenReady(false);
        } else {
            exoPlayer.setPlayWhenReady(true);
        }
        changePlayPause(exoPlayer.getPlayWhenReady());
        updateNotiPlay(exoPlayer.getPlayWhenReady());
    }

    private void seekTo(long seek) {
        exoPlayer.seekTo((int) seek);
    }


    @SuppressLint("RestrictedApi")
    private void updateNotiPlay(boolean isPlay) {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notification.mActions.remove(1);
                Intent playIntent = new Intent(this, OnlineService.class);
                playIntent.setAction(ACTION_TOGGLE);
                PendingIntent ppreviousIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                if (isPlay) {
                    notification.mActions.add(1, new NotificationCompat.Action(
                            R.mipmap.ic_noti_pause, "Pause",
                            ppreviousIntent));

                } else {
                    notification.mActions.add(1, new NotificationCompat.Action(
                            R.mipmap.ic_noti_play, "Play",
                            ppreviousIntent));
                }
            } else {
                if (isPlay) {
                    bigViews.setImageViewResource(R.id.imageView_noti_play, android.R.drawable.ic_media_pause);
                } else {
                    bigViews.setImageViewResource(R.id.imageView_noti_play, android.R.drawable.ic_media_play);
                }
            }
            mNotificationManager.notify(101, notification.build());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateNoti() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setContentTitle(Constant.arrayList_play.get(Constant.playPos).getSongName());
            notification.setContentText(Constant.arrayList_play.get(Constant.playPos).getArtistName());
        } else {
            bigViews.setTextViewText(R.id.textView_noti_name, Constant.arrayList_play.get(Constant.playPos).getSongName());
            bigViews.setTextViewText(R.id.textView_noti_artist, Constant.arrayList_play.get(Constant.playPos).getArtistName());
            smallViews.setTextViewText(R.id.status_bar_artist_name, Constant.arrayList_play.get(Constant.playPos).getArtistName());
            smallViews.setTextViewText(R.id.status_bar_track_name, Constant.arrayList_play.get(Constant.playPos).getSongName());
        }
        updateNotiImage();
        updateNotiPlay(exoPlayer.getPlayWhenReady());
       // changeImageAnimation();
    }


    private void changePlayPause(boolean playWhenReady) {
    }




    private void previous() {
        setBuffer(true);
        if (Constant.isSuffle) {
            Random rand = new Random();
            Constant.playPos = rand.nextInt((Constant.arrayList_play.size() - 1) + 1);
        } else {
            if (Constant.playPos > 0) {
                Constant.playPos = Constant.playPos - 1;
            } else {
                Constant.playPos = Constant.arrayList_play.size() - 1;
            }
        }
        startNewSong();

    }


    private void createNoti() {
        bigViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        smallViews = new RemoteViews(getPackageName(), R.layout.layout_noti_small);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.putExtra("isnoti", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent previousIntent = new Intent(this, OnlineService.class);
        previousIntent.setAction(ACTION_PREVIOUS);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent playIntent = new Intent(this, OnlineService.class);
        playIntent.setAction(ACTION_TOGGLE);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, OnlineService.class);
        nextIntent.setAction(ACTION_NEXT);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, OnlineService.class);
        closeIntent.setAction(ACTION_STOP);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        notification = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon))
                .setContentTitle(getString(R.string.app_name))
                .setPriority(Notification.PRIORITY_LOW)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_notification)
                .setTicker(Constant.arrayList_play.get(Constant.playPos).getSongName())
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setOnlyAlertOnce(true);

        NotificationChannel mChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Online Song";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_LOW;
            mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            mNotificationManager.createNotificationChannel(mChannel);

            MediaSessionCompat mMediaSession;
            mMediaSession = new MediaSessionCompat(getApplicationContext(), "ONLINEMP3");
            mMediaSession.setFlags(
                    MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                            MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

            notification.setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mMediaSession.getSessionToken())
                    .setShowCancelButton(true)
                    .setShowActionsInCompactView(0, 1, 2)
                    .setCancelButtonIntent(
                            MediaButtonReceiver.buildMediaButtonPendingIntent(getApplicationContext(), PlaybackStateCompat.ACTION_STOP)))
                    .addAction(new NotificationCompat.Action(
                            R.mipmap.ic_noti_previous, "Previous",
                            ppreviousIntent))
                    .addAction(new NotificationCompat.Action(
                            R.mipmap.ic_noti_pause, "Pause",
                            pplayIntent))
                    .addAction(new NotificationCompat.Action(
                            R.mipmap.ic_noti_next, "Next",
                            pnextIntent))
                    .addAction(new NotificationCompat.Action(
                            R.mipmap.ic_noti_close, "Close",
                            pcloseIntent));

            notification.setContentTitle(Constant.arrayList_play.get(Constant.playPos).getSongName());
            notification.setContentText(Constant.arrayList_play.get(Constant.playPos).getArtistName());
        } else {
            bigViews.setOnClickPendingIntent(R.id.imageView_noti_play, pplayIntent);

            bigViews.setOnClickPendingIntent(R.id.imageView_noti_next, pnextIntent);

            bigViews.setOnClickPendingIntent(R.id.imageView_noti_prev, ppreviousIntent);

            bigViews.setOnClickPendingIntent(R.id.imageView_noti_close, pcloseIntent);
            smallViews.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);

            bigViews.setImageViewResource(R.id.imageView_noti_play, android.R.drawable.ic_media_pause);

            bigViews.setTextViewText(R.id.textView_noti_name, Constant.arrayList_play.get(Constant.playPos).getSongName());
            smallViews.setTextViewText(R.id.status_bar_track_name, Constant.arrayList_play.get(Constant.playPos).getArtistName());

            bigViews.setTextViewText(R.id.textView_noti_artist, Constant.arrayList_play.get(Constant.playPos).getSongName());
            smallViews.setTextViewText(R.id.status_bar_artist_name, Constant.arrayList_play.get(Constant.playPos).getArtistName());

            notification.setCustomContentView(smallViews).setCustomBigContentView(bigViews);
        }

        startForeground(101, notification.build());
        updateNotiImage();
    }

    private void updateNotiImage() {
    }


    private void startNewSong() {

        isNewSong = true;
        setBuffer(true);

        String url = "null";
        try {
            url = Constant.arrayList_play.get(Constant.playPos).getMusicUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(url),
                dataSourceFactory, extractorsFactory, null, null);
        exoPlayer.prepare(videoSource);
        exoPlayer.setPlayWhenReady(true);
        createNoti();
        // db operations to store stuffs

    }

    private void setBuffer(boolean isBuffer) {

        if (!isBuffer) {
            changeEquilizer();
        }
        GlobalBus.getBus().postSticky(new MessageEvent(isBuffer, "buffer"));

    }

    private void changeEquilizer() {
     //   GlobalBus.getBus().postSticky(new ItemAlbums("", "", "", ""));
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }















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
        if (playbackState == Player.STATE_ENDED) {
            onCompletion();
        }
        if (playbackState == Player.STATE_READY && playWhenReady) {
            if (isNewSong) {
                isNewSong = false;
                Constant.isPlayed = true;
                setBuffer(false);
                GlobalBus.getBus().postSticky(Constant.arrayList_play.get(Constant.playPos));
                if (notification == null) {
                    createNoti();
                   // changeImageAnimation();
                } else {
                    updateNoti();
                }
            } else {
                updateNotiPlay(exoPlayer.getPlayWhenReady());
            }
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
        exoPlayer.setPlayWhenReady(false);
        setBuffer(false);
        changePlayPause(false);
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

    private void onCompletion() {
        if (Constant.isRepeat) {
            exoPlayer.seekTo(0);
        } else {
            if (Constant.isSuffle) {
                Random rand = new Random();
                Constant.playPos = rand.nextInt((Constant.arrayList_play.size() - 1) + 1);
            } else {
                next();
            }
        }

        startNewSong();
    }


    BroadcastReceiver onCallIncome = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String a = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if (Constant.isPlaying) {
                if (a.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) || a.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    exoPlayer.setPlayWhenReady(false);
                }
//                else if (a.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
//                    exoPlayer.setPlayWhenReady(true);
//                }
            }
        }
    };
}
