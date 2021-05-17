package com.bkav.bmusic.ui.holder;

import android.annotation.SuppressLint;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bkav.bmusic.R;
import com.bkav.bmusic.model.Song;
import com.bkav.bmusic.service.MediaPlaybackService;
import com.bkav.bmusic.ui.recycler.BaseRecyclerViewHolder;
import com.bkav.bmusic.ui.recycler.RecyclerActionListener;
import com.bkav.bmusic.ui.recycler.RecyclerData;

import es.claucookie.miniequalizerlibrary.EqualizerView;

public class FavoriteSongItemLibraryHolder extends BaseRecyclerViewHolder {
    private ImageView mImageFavorite;
    private TextView mNameSongFavorite;
    private TextView mArtist;
    private ImageView mOptionFavorite;
    private EqualizerView mEqualizerView;

    private MediaPlaybackService mService;
    private RecyclerActionListener mAction;

    public FavoriteSongItemLibraryHolder(@NonNull View itemView) {
        super(itemView);
        mImageFavorite = itemView.findViewById(R.id.id_image_favorite);
        mNameSongFavorite = itemView.findViewById(R.id.id_name_song_favorite);
        mArtist = itemView.findViewById(R.id.artist_song_favorite);
        mOptionFavorite = itemView.findViewById(R.id.id_option_favorite);
        mEqualizerView = itemView.findViewById(R.id.equalizer);
    }

    @Override
    public void bindViewHolder(RecyclerData data) {
        if (data instanceof Song) {
            Song song = (Song) data;
//            if (song.loadImageFromPath(song.getPathSong()) == null) {
//                mImageFavorite.setImageResource(R.drawable.ic_queue_music_black_24dp);
//            } else {
//                mImageFavorite.setImageBitmap(song.loadImageFromPath(song.getPathSong()));
//            }
            if(mService != null){
                Song playingSong = mService.getPlayingSong();
                updateEqualizerView(playingSong != null && playingSong.getId() == song.getId() && mService.isMusicPlay() && mService.isPlaying(), song);
            }else {
                updateEqualizerView(false, song);
//                mNumber.setText(""+(getLayoutPosition()+1));
            }
            mNameSongFavorite.setText(song.getNameSong());
            mArtist.setText(song.getSinger());

            mOptionFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(itemView.getContext(), mOptionFavorite);
                    popupMenu.inflate(R.menu.menu_favorite_song);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @SuppressLint("NonConstantResourceId")
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.dis_like_song:
                                    clickButtonMenu(song, RecyclerActionListener.CONTROL_UPDATE.DELETE_FAVORITE_SONG);
                                    return true;
                                case R.id.add_playlist_song:
                                    clickButtonMenu(song, RecyclerActionListener.CONTROL_UPDATE.ADD_PLAYLIST);
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popupMenu.show();
                }
            });
        }

    }

    //update sóng khi phát 1 bài hát
    public void updateEqualizerView(boolean isPlay, Song song){
        if( isPlay ){
            mEqualizerView.animateBars();
        } else if (!mEqualizerView.isAnimating()){
            mEqualizerView.stopBars();
            if (song.loadImageFromPath(song.getPathSong()) == null) {
                mImageFavorite.setImageResource(R.drawable.tim);
            } else {
                mImageFavorite.setImageBitmap(song.loadImageFromPath(song.getPathSong()));
            }
        }
        mEqualizerView.setVisibility(isPlay ? View.VISIBLE : View.INVISIBLE);
        mImageFavorite.setVisibility(isPlay ? View.INVISIBLE : View.VISIBLE );
    }

    @Override
    public void setupClickableViews(RecyclerActionListener actionListener) {
        mAction = actionListener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onViewClick(getAdapterPosition(), v, FavoriteSongItemLibraryHolder.this);
            }
        });
    }

    @Override
    public void setService(MediaPlaybackService service) {
        mService = service;
    }

    private void clickButtonMenu(Song song, RecyclerActionListener.CONTROL_UPDATE state){
        System.out.println("HanhNTHe: click button song "+song+ " state "+state);
        mAction.updateSongFromMenuButton(song, state);
    }
}
