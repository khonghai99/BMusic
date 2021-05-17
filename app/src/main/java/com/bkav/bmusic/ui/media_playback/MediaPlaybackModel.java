package com.bkav.bmusic.ui.media_playback;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bkav.bmusic.model.PlaySong;
import com.bkav.bmusic.model.Playlist;
import com.bkav.bmusic.model.Song;

public class MediaPlaybackModel extends ViewModel {

    private MutableLiveData<String> mPathImage;
    private final MutableLiveData<Playlist> mDetailPlaylist = new MutableLiveData<>();
    private final MutableLiveData<PlaySong> mPlaySong = new MutableLiveData<>();
    // nhan su kien khi click vao mot bai hat
    private final MutableLiveData<Song> mClickSong = new MutableLiveData<>();

    private final MutableLiveData<Playlist> mPlaylistFirstClick = new MutableLiveData<>();

    public MediaPlaybackModel() {
        mPathImage = new MutableLiveData<>();
        mPathImage.setValue("This is notifications fragment");
    }

    public LiveData<String> getPathImage() {
        return mPathImage;
    }

    public void setPathImage(String mPathImage) {
        this.mPathImage.setValue(mPathImage);
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
}