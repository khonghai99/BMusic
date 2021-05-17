package com.bkav.bmusic.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bkav.bmusic.model.ImageSearchModel;
import com.bkav.bmusic.model.Song;


public class SearchViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private final MutableLiveData<ImageSearchModel> mDetailImageSearch = new MutableLiveData<>();
    private final MutableLiveData<Song> mClickSong = new MutableLiveData<>();
    private final MutableLiveData<ImageSearchModel> mImageSearchFirstClick = new MutableLiveData<>();

    private final MutableLiveData<Song> mItemSearchFirstClick = new MutableLiveData<>();


    public SearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ImageSearchModel> openDetailSearch() {
        return mImageSearchFirstClick;
    }

    public MutableLiveData<ImageSearchModel> getDetailImageSearch() {
        return mDetailImageSearch;
    }

    public MutableLiveData<Song> getClickSong() {
        return mClickSong;
    }

    public void setImageSearchFirstClick(ImageSearchModel image){
        this.mImageSearchFirstClick.setValue(image);
    }
    public void setDetailImageSearch(ImageSearchModel imageSearch){
        this.mDetailImageSearch.setValue(imageSearch);
    }
    public void setClickSong(Song song){
        this.mClickSong.setValue(song);
    }

    public void setItemSearchFirstClick(Song song){
        this.mItemSearchFirstClick.setValue(song);
    }

    public LiveData<Song> openDetailHomeFragment() {
        return mItemSearchFirstClick;
    }


}