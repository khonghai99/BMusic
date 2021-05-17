package com.bkav.bmusic.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bkav.bmusic.model.Playlist;
import com.bkav.bmusic.model.Song;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Song> mDetailSong = new MutableLiveData<>();
    private final MutableLiveData<List<Playlist>> mPlaylist = new MutableLiveData<>();
    private final MutableLiveData<Song> mSongFirstClick = new MutableLiveData<>();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        mPlaylist.setValue(new ArrayList<>());
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<Song> getDetailSong() {
        return mDetailSong;
    }

    public MutableLiveData<Song> openDetailSong(){
        return mSongFirstClick;
    }
    public void setSongFirstClick(Song song){
        this.mSongFirstClick.setValue(song);
    }

    public void setDetailSong(Song mSong) {
        this.mDetailSong.setValue(mSong);
    }

    public MutableLiveData<List<Playlist>> getPlaylist() {
        return mPlaylist;
    }

    public void setPlaylist(List<Playlist> mPlaylist) {
        this.mPlaylist.setValue(mPlaylist);
    }
}