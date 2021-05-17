package com.bkav.bmusic.ui.media_playback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bkav.bmusic.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class ImageFragment extends Fragment {

    private ImageView mImageSongMedia;

    public ImageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_song_media, container, false);
        mImageSongMedia = view.findViewById(R.id.image_song_media_play);
        MediaPlaybackModel mediaPlaybackModel =
                new ViewModelProvider(requireActivity()).get(MediaPlaybackModel.class);
        mediaPlaybackModel.getPathImage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String pathImage) {
               updateImageSong(pathImage);
            }
        });
        return view;
    }

    public void updateImageSong(String imagePath){
        Bitmap bitmap = loadImageFromPath(imagePath);
        if (bitmap == null) {
//            mImageSongMedia.setImageResource(R.drawable.icon_default_song);
            Glide.with(mImageSongMedia)
                    .load(imagePath)
                    .apply(RequestOptions.
                            placeholderOf(R.drawable.icon_default_song))
                    .into(mImageSongMedia);
        } else {
            mImageSongMedia.setImageBitmap(bitmap);
        }
    }

    //lay anh theo bitmap neu co path
    public Bitmap loadImageFromPath(String path) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] data = mediaMetadataRetriever.getEmbeddedPicture();
        return data == null ? null : BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
