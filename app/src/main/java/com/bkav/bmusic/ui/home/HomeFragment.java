package com.bkav.bmusic.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bkav.bmusic.R;
import com.bkav.bmusic.model.Constants;
import com.bkav.bmusic.model.Playlist;
import com.bkav.bmusic.model.Song;
import com.bkav.bmusic.ui.recycler.BaseRecyclerViewHolder;
import com.bkav.bmusic.ui.recycler.RecyclerActionListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    RecyclerActionListener mRecyclerActionListener = new RecyclerActionListener() {
        @Override
        public void onViewClick(int position, View view, BaseRecyclerViewHolder viewHolder) {
        }

        @Override
        public void onViewLongClick(int position, View view, BaseRecyclerViewHolder viewHolder) {
        }

        @Override
        public void clickSong(Song song) {
            super.clickSong(song);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.openDetailSong().observe(getViewLifecycleOwner(), new Observer<Song>() {
            @Override
            public void onChanged(Song song) {
                Log.i("HaiKH", "onChanged: "+song);
                if(song != null) {
                    openDetailFragment();
                    homeViewModel.setDetailSong(song);
                    homeViewModel.setSongFirstClick(null);
                }
            }
        });

        openOverviewFragment();

        getData();

        return root;
    }

    private void openOverviewFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.home_fragment_container, new HomeOverviewFragment(), HomeOverviewFragment.class.getName())
                .commit();
    }

    private void openDetailFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.home_fragment_container, new DetailSongFragment(), DetailSongFragment.class.getName())
                .addToBackStack(null)
                .commit();
    }

    //Create data
    private void getData() {
        ArrayList<Playlist> mData = new ArrayList<>();

        new Firebase(Constants.FIREBASE_REALTIME_DATABASE_URL).child(Constants.FIREBASE_REALTIME_SONG_PATH)
                .limitToLast(5).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Gson gson = new Gson();

                Object object = dataSnapshot.getValue(Object.class);
                String json = gson.toJson(object);

//                Type listType = new TypeToken<ArrayList<Song>>() {}.getType();
//                ArrayList<Song> data = gson.fromJson(json, listType);
                try {
                    Type listType = new TypeToken<HashMap<String, Song>>() {
                    }.getType();
                    HashMap<String, Song> data = gson.fromJson(json, listType);
                    if (data != null) {
                        Playlist playlist = new Playlist(1,"Mới phát hành", new ArrayList<>(data.values()));
                        mData.add(playlist);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                Playlist playlist = map.get(map.keySet().toArray()[0]);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        new Firebase(Constants.FIREBASE_REALTIME_DATABASE_URL).child(Constants.FIREBASE_REALTIME_HOME_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Gson gson = new Gson();

                Object object = dataSnapshot.getValue(Object.class);
                String json = gson.toJson(object);

                Type listType = new TypeToken<ArrayList<Playlist>>() {}.getType();
                ArrayList<Playlist> data = gson.fromJson(json, listType);

//                for (String key : map.keySet()) {
//                    data.add(map.get(key));
//                }

//                if (homeViewModel.getPlaylist().getValue().size() == 0)
//                homeViewModel.setPlaylist(data);
                for (int i =0; i < data.size() ; i++)
                    mData.add(data.get(i));
                homeViewModel.setPlaylist(mData);

//                Playlist playlist = map.get(map.keySet().toArray()[0]);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}