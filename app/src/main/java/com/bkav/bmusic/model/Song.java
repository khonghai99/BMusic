package com.bkav.bmusic.model;

public class Song {
    private int mID;
    private String mTitle;
    private String mArtist;
    private String mDuration;
    private String mPath;

    public Song(int mID, String mTitle, String mArtist, String mDuration, String mPath) {
        this.mID = mID;
        this.mTitle = mTitle;
        this.mArtist = mArtist;
        this.mDuration = mDuration;
        this.mPath = mPath;
    }


}
