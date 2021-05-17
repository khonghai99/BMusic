package com.bkav.bmusic.ui.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;

import com.bkav.bmusic.R;
import com.bkav.bmusic.activity.AddSongToPlaylist;
import com.bkav.bmusic.model.Song;
import com.bkav.bmusic.service.MediaPlaybackService;
import com.bkav.bmusic.ui.recycler.BaseRecyclerViewHolder;
import com.bkav.bmusic.ui.recycler.RecyclerActionListener;
import com.bkav.bmusic.ui.recycler.RecyclerData;

public class AddSongInPlayListItemHolder extends BaseRecyclerViewHolder {

    private CheckBox mSelectSong;

    public AddSongInPlayListItemHolder(@NonNull View itemView) {
        super(itemView);
        mSelectSong = itemView.findViewById(R.id.item_song);
        attachListener();
    }

    @Override
    public void bindViewHolder(RecyclerData data) {
        if (data instanceof Song) {
            Song song = (Song) data;
            System.out.println("hanhnthe song name " + song.getNameSong());
            mSelectSong.setText(song.getNameSong());
            mSelectSong.setOnCheckedChangeListener(null);
        } else if (data instanceof AddSongToPlaylist.CheckboxSong) {
            AddSongToPlaylist.CheckboxSong cbSong = (AddSongToPlaylist.CheckboxSong) data;
            System.out.println("hanhnthe song name " + cbSong.mSong.getNameSong());
            mSelectSong.setText(cbSong.mSong.getNameSong());
            mSelectSong.setChecked(cbSong.mChecked);
            mSelectSong.setOnCheckedChangeListener((buttonView, isChecked) -> cbSong.mChecked = isChecked);
        }

    }

    @Override
    public void setupClickableViews(RecyclerActionListener actionListener) {
        itemView.setOnClickListener(v -> actionListener.onViewClick(getAdapterPosition(), v, AddSongInPlayListItemHolder.this));
    }

    @Override
    public void setService(MediaPlaybackService service) {
    }

    //Listener nhận sự kiện khi các Checkbox thay đổi trạng thái
    CompoundButton.OnCheckedChangeListener m_listener
            = (compoundButton, b) -> System.out.println("HanhNTHe: compoundButton " + compoundButton + "  text " + compoundButton.getText());

    //Gán Listener vào CheckBox
    void attachListener() {
        mSelectSong.setOnCheckedChangeListener(m_listener);
    }
}
