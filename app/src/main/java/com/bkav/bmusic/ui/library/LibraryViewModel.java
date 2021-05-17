package com.bkav.bmusic.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bkav.bmusic.model.PlaySong;
import com.bkav.bmusic.model.Playlist;
import com.bkav.bmusic.model.Song;


public class LibraryViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private final MutableLiveData<Playlist> mDetailPlaylist = new MutableLiveData<>();
    private final MutableLiveData<PlaySong> mPlaySong = new MutableLiveData<>();
    // nhan su kien khi click vao mot bai hat
    private final MutableLiveData<Song> mClickSong = new MutableLiveData<>();

    private String mCurrentRef;

    private final MutableLiveData<Playlist> mPlaylistFirstClick = new MutableLiveData<>();

    public LibraryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<Playlist> getDetailPlayList() {
        return mDetailPlaylist;
    }

    public void setDetailPlaylist(Playlist mPLayList) {
        this.mDetailPlaylist.setValue(mPLayList);
    }
//
    public MutableLiveData<PlaySong> getPlaylist() {
        return mPlaySong;
    }

    public void setPlaylist(PlaySong mPlaylist) {
        this.mPlaySong.setValue(mPlaylist);
    }

    public MutableLiveData<Playlist> openDetailPlaylist(){
        return mPlaylistFirstClick;
    }
    public void setPlaylistFirstClick( Playlist playlist){
        this.mPlaylistFirstClick.setValue(playlist);
    }

    public String getCurrentRef() {
        return mCurrentRef;
    }

    public void setCurrentRef(String mCurrentRef) {
        this.mCurrentRef = mCurrentRef;
    }
}