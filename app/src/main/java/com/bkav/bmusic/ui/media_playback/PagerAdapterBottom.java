package com.bkav.bmusic.ui.media_playback;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapterBottom extends FragmentStatePagerAdapter {
    public PagerAdapterBottom(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new ImageFragment();
                break;
            case 1:
                frag = new LyricsFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
