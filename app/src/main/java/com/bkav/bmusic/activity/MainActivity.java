package com.bkav.bmusic.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bkav.bmusic.R;
import com.bkav.bmusic.model.PlaySong;
import com.bkav.bmusic.model.Song;
import com.bkav.bmusic.service.MediaPlaybackService;
import com.bkav.bmusic.ui.media_playback.MainBottomSheetFragment;
import com.bkav.bmusic.ui.setting.SettingFragment;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageSong;
    private TextView mNameSong;
    private TextView mArtist;
    private ImageView mIsPlaySong;
    private LinearLayout mBottomControl;

    private MainBottomSheetFragment mMainBottomSheetFragment;

    private ActivityViewModel mActivityViewModel;

    public MediaPlaybackService mMediaPlaybackService;

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MediaPlaybackService.MediaPlaybackServiceBinder mediaPlaybackServiceBinder = (MediaPlaybackService.MediaPlaybackServiceBinder) iBinder;
            mMediaPlaybackService = mediaPlaybackServiceBinder.getService();

            //update từ service khi thực hiện các thao tác chuyển bài..
            mMediaPlaybackService.listenChangeStatus(new MediaPlaybackService.IServiceCallback() {
                @Override
                public void onUpdate() {
                    updateUI(mMediaPlaybackService.getPlayingSong());
                    updateBottomSheet();
                }

            });
            if (mMediaPlaybackService.getSharedPreferences().contains("SONG_LIST")) {
                mMediaPlaybackService.loadData();
                updateUI(mMediaPlaybackService.getPlayingSong());
                System.out.println("HanhNTHe: update ");
            } else {
                mBottomControl.setVisibility(View.GONE);
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPermission();   //xin cap quyen doc bo nho

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_library)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

//        mMainBottomSheetFragment = new MainBottomSheetFragment();

        mBottomControl = findViewById(R.id.layout_play_home);
        mBottomControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mMainBottomSheetFragment.show(getSupportFragmentManager(), MainBottomSheetFragment.class.getName());
                mMainBottomSheetFragment = new MainBottomSheetFragment();
                mMainBottomSheetFragment.show(getSupportFragmentManager(), MainBottomSheetFragment.class.getName());
            }
        });

        mImageSong = findViewById(R.id.imgMainSong);
        mNameSong = findViewById(R.id.tvMainNameSong);
        mArtist = findViewById(R.id.tvMainArtist);
        mIsPlaySong = findViewById(R.id.btMainPlay);

        mIsPlaySong.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if(!mMediaPlaybackService.isMusicPlay()){
                    mMediaPlaybackService.preparePlay();
                }else if (mMediaPlaybackService.isPlaying()) {
                    mMediaPlaybackService.pause();
                    mIsPlaySong.setImageResource(R.drawable.ic_play_black_24dp);
                } else {
                    mMediaPlaybackService.play();
                    mIsPlaySong.setImageResource(R.drawable.ic_pause_black_24dp);
                }
            }
        });

        mActivityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        mActivityViewModel.getPlaylist().observe(this, new Observer<PlaySong>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChanged(PlaySong playSong) {
                playSong(playSong.getPlayListSong(), playSong.getPlaySong());
            }
        });
        getThemeNightMode();
    }

    //HanhNTHe: setThemenight
    private void getThemeNightMode(){
        System.out.println("HanhNTHe: getThemeNightMode "+SettingFragment.mNight);
        if(SettingFragment.mNight){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isMyServiceRunning(MediaPlaybackService.class)) {
            connectService();
        } else {
            startService();
            connectService();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void startService() {
        Intent it = new Intent(this, MediaPlaybackService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startService(it);
            System.out.println("HanhNTHe: start service");
        }
    }


    //method
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void connectService() {
        Intent it = new Intent(this, MediaPlaybackService.class);
        bindService(it, mServiceConnection, Context.BIND_AUTO_CREATE);
        System.out.println("HanhNTHe: connect service");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void playSong(ArrayList<Song> songs, Song song) {
//        songs.add(song);
//        songs.add(song);
        if (mMediaPlaybackService != null){
            mMediaPlaybackService.playSong(songs, song);
            updateUI(song);
        }
    }

    public MediaPlaybackService getService() {
        return mMediaPlaybackService;
    }

    //cap nhat giao dien small detail
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        //code thi hanh khi receiver nhan dc intent
        @Override
        public void onReceive(Context context, Intent intent) {
            //kiem tra intent
            if (intent.getAction().equals(MediaPlaybackService.ACTION)) {
                //doc du lieu tu intent
                Boolean change = intent.getBooleanExtra(MediaPlaybackService.MY_KEY, true);
                int isplaying = intent.getIntExtra(MediaPlaybackService.ISPLAYING, 0);
//                if (change&& isplaying==0) {
//                }else if( change && isplaying==1){
//                    mPlay.setImageResource(R.drawable.ic_pause_1);
//                }
            }
        }
    };

    //update ui
    private void updateUI(Song song) {
        if (song == null) {
            return;
        }

        if (mBottomControl.getVisibility() == View.GONE) {
            mBottomControl.setVisibility(View.VISIBLE);
            Animation alpha = new AlphaAnimation(0.00f, 1.00f);
            alpha.setDuration(300);
            mBottomControl.startAnimation(alpha);
        }

        if (song.loadImageFromPath(song.getPathSong()) == null) {
            mImageSong.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_default_song));
            Glide.with(mImageSong)
                    .load(song.getImageUrl())
                    .apply(RequestOptions.
                            placeholderOf(R.drawable.icon_default_song))
                    .into(mImageSong);
        } else {
            mImageSong.setImageBitmap(song.loadImageFromPath(song.getPathSong()));
        }
        mNameSong.setText(song.getNameSong());
        mArtist.setText(song.getSinger());
        if (mMediaPlaybackService.isMusicPlay()) {
            mIsPlaySong.setImageResource(mMediaPlaybackService.isPlaying() ? R.drawable.ic_pause_black_24dp : R.drawable.ic_play_black_24dp);
        } else mIsPlaySong.setImageResource(R.drawable.ic_play_black_24dp);
    }

    public void updateBottomSheet() {
        if (mMainBottomSheetFragment != null && mMainBottomSheetFragment.isVisible()) {
            mMainBottomSheetFragment.updatePlaySongUI();
        }
    }

    // cap quyen doc bo nho
    public void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 || requestCode == 2) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Quyền đọc file: được phép", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Quyền đọc file: không được phép", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //interface
    public interface IServiceConnectListenner1 {
        void onConnect();
    }

    public interface IServiceConnectListenner2 {
        void onConnect();
    }

}