package com.bkav.bmusic.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bkav.bmusic.R;
import com.bkav.bmusic.model.ImageSearchModel;
import com.bkav.bmusic.model.Song;
import com.bkav.bmusic.ui.home.DetailSongFragment;

public class SearchFragment extends Fragment {

    private SearchViewModel mSearchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mSearchViewModel =
                new ViewModelProvider(requireActivity()).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        mSearchViewModel.openDetailSearch().observe(getViewLifecycleOwner(), new Observer<ImageSearchModel>() {
            @Override
            public void onChanged(ImageSearchModel image) {
                System.out.println("HanhNTHe: search click image " +image);
                if (image != null) {
                    SearchFragment.this.openDetailFragment();
                    mSearchViewModel.setDetailImageSearch(image);
                    mSearchViewModel.setImageSearchFirstClick(null);
                }
                System.out.println("HanhNTHe: search click image ");
            }
        });

        mSearchViewModel.openDetailHomeFragment().observe(getViewLifecycleOwner(), new Observer<Song>() {
            @Override
            public void onChanged(Song song) {
                System.out.println("Hanh NTHe song "+song);
                if(song != null) {
                    openDetailHomeFragment();
                    mSearchViewModel.setClickSong(song);
                    mSearchViewModel.setItemSearchFirstClick(null);
                }
            }
        });

        System.out.println("HanhNTHe; search onCreateView ");
        openOverviewFragment();
        return root;
    }


    private void openOverviewFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_search, new SearchOverViewFragment(),
                        SearchOverViewFragment.class.getName())
                .addToBackStack(null)
                .commit();
    }

    private void openDetailFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_search, new DetailSearchFragment(), DetailSearchFragment.class.getName())
                .addToBackStack(null)
                .commit();
    }

    private void openDetailHomeFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_search, new DetailSongFragment(), DetailSongFragment.class.getName())
                .addToBackStack(null)
                .commit();
    }

}