package com.bkav.bmusic.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bkav.bmusic.R;
import com.bkav.bmusic.model.Song;
import com.bkav.bmusic.service.MediaPlaybackService;
import com.bkav.bmusic.ui.recycler.BaseRecyclerViewHolder;
import com.bkav.bmusic.ui.recycler.RecyclerActionListener;
import com.bkav.bmusic.ui.recycler.RecyclerData;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import es.claucookie.miniequalizerlibrary.EqualizerView;


public class DetailSearchItemHolder extends BaseRecyclerViewHolder {

    private ImageView mImageSearch;
    private TextView mNameSearch;
    private TextView mArtistSearch;
    private ImageView mForWard;
    private EqualizerView mEqualizerView;

    private MediaPlaybackService mService;

    public DetailSearchItemHolder(@NonNull View itemView) {
        super(itemView);
        mImageSearch = itemView.findViewById(R.id.id_image_search);
        mNameSearch = itemView.findViewById(R.id.id_name_search);
        mArtistSearch = itemView.findViewById(R.id.artist_search);
        mForWard = itemView.findViewById(R.id.forward);
        mEqualizerView = itemView.findViewById(R.id.equalizer);
    }

    @Override
    public void bindViewHolder(RecyclerData data) {
        if (data instanceof Song) {
            Song song = (Song) data;
            //load anh
            if(mService != null){
                Song playingSong = mService.getPlayingSong();
                updateEqualizerView(playingSong != null && playingSong.getId() == song.getId() && mService.isMusicPlay() && mService.isPlaying(), song);
            }else {
                updateEqualizerView(false, song);
//                mNumber.setText(""+(getLayoutPosition()+1));
            }
//            mImageSearch.setImageResource(R.drawable.ic_baseline_library_music_24);
            mNameSearch.setText(song.getNameSong());
            mArtistSearch.setText(song.getSinger());
        }
    }
    //update s??ng khi ph??t 1 b??i h??t
    public void updateEqualizerView(boolean isPlay, Song song){
        if( isPlay ){
            mEqualizerView.animateBars();
        } else if (!mEqualizerView.isAnimating()){
            mEqualizerView.stopBars();
            //Glide.... de load anh :|
            Glide.with(mImageSearch)
                    .load(song.getImageUrl())
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .apply(RequestOptions.
                            placeholderOf(R.drawable.ic_baseline_library_music_24))
                    .into(mImageSearch);
        }
        mEqualizerView.setVisibility(isPlay ? View.VISIBLE : View.GONE);
        mImageSearch.setVisibility(isPlay ? View.GONE : View.VISIBLE );
    }

    @Override
    public void setupClickableViews(RecyclerActionListener actionListener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onViewClick(getAdapterPosition(), v, DetailSearchItemHolder.this);
            }
        });
    }

    @Override
    public void setService(MediaPlaybackService service) {
        mService = service;
    }

}
