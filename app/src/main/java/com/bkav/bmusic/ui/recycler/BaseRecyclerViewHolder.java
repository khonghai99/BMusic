package com.bkav.bmusic.ui.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bkav.bmusic.service.MediaPlaybackService;

public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    public BaseRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindViewHolder(RecyclerData data);

    public abstract void setupClickableViews(RecyclerActionListener actionListener);

    //HanhNTHe: service
    public abstract void setService(MediaPlaybackService service);

}
