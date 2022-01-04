package com.example.unico;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class EventsGallery extends Fragment {
    GridView gv;
    private EventsGalleryViewModel2 mViewModel;

    public static EventsGallery newInstance() {
        return new EventsGallery();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.events_gallery_fragment, container, false);
        gv=v.findViewById(R.id.gridImages);
        gv.setAdapter(new EventsAdapter(getActivity()));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getActivity(),FullImage.class);
                i.putExtra("id",position);
                startActivity(i);
            }
        });
        return v;   }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EventsGalleryViewModel2.class);
        // TODO: Use the ViewModel
    }

}
