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


public class SongSearchItemHolder extends BaseRecyclerViewHolder {

    private ImageView mImageSearchButton;
    private TextView mTextView;

    public SongSearchItemHolder(@NonNull View itemView) {
        super(itemView);
        mImageSearchButton = itemView.findViewById(R.id.image_song_item_search);
        mTextView = itemView.findViewById(R.id.song_name_item_search);
    }

    @Override
    public void bindViewHolder(RecyclerData data) {
        if (data instanceof Song) {
            Song song = (Song) data;
            mTextView.setText(song.getNameSong());
            Glide.with(mImageSearchButton)
                    .load(song.getImageUrl())
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .apply(RequestOptions.
                            placeholderOf(R.drawable.placeholder_music))
                    .into(mImageSearchButton);
        }
        //load anh
    }

    @Override
    public void setupClickableViews(RecyclerActionListener actionListener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onViewLongClick(getAdapterPosition(), v, SongSearchItemHolder.this);
            }
        });
    }

    @Override
    public void setService(MediaPlaybackService service) {

    }
}
