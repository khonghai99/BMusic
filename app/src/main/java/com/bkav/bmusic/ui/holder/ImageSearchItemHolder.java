package com.bkav.bmusic.ui.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bkav.bmusic.R;
import com.bkav.bmusic.model.ImageSearchModel;
import com.bkav.bmusic.service.MediaPlaybackService;
import com.bkav.bmusic.ui.recycler.BaseRecyclerViewHolder;
import com.bkav.bmusic.ui.recycler.RecyclerActionListener;
import com.bkav.bmusic.ui.recycler.RecyclerData;

public class ImageSearchItemHolder extends BaseRecyclerViewHolder {

    private ImageView mImageSearchButton;

    public ImageSearchItemHolder(@NonNull View itemView) {
        super(itemView);
        mImageSearchButton = itemView.findViewById(R.id.image_search);
//        mImageSearchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("");
//            }
//        });
    }

    @Override
    public void bindViewHolder(RecyclerData data) {
        if (data instanceof ImageSearchModel){
            ImageSearchModel image = (ImageSearchModel) data;
//            Glide.with(mImageSearchButton)
//                    .load(image.getImageSearchUrl())
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(mImageSearchButton);
            mImageSearchButton.setImageResource(image.getImageSearchUrl());
        }
//            mImageSearchButton.setImageResource(((ImageSearchModel) data).getImageSearchUrl());
        //load anh
    }

    @Override
    public void setupClickableViews(RecyclerActionListener actionListener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onViewClick(getAdapterPosition(), v, ImageSearchItemHolder.this);
                System.out.println("HanhNTHe: search "+getAdapterPosition());
            }
        });
    }

    @Override
    public void setService(MediaPlaybackService service) {

    }

}
