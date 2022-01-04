package com.example.unico;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TeacherFragment extends Fragment {
    private TeacherViewModel mViewModel;
Button b9,b11,b12,b14,b15,b16;
    public static TeacherFragment newInstance() {
        return new TeacherFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.teacher_fragment, container, false);
        b9=v.findViewById(R.id.button9);
        b11=v.findViewById(R.id.button11);
        b12=v.findViewById(R.id.button12);
        b14=v.findViewById(R.id.button14);
        b15=v.findViewById(R.id.button15);
        b16=v.findViewById(R.id.button16);
        b16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new TableFirst());
                fr.addToBackStack(null);
                fr.commit();

            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new YearsFragment());
                fr.addToBackStack(null);
                fr.commit();

            }
        });
b12.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        FragmentTransaction fr=getFragmentManager().beginTransaction();
        fr.replace(R.id.nav_host_fragment,new TeacherNotes1());
        fr.addToBackStack(null);
        fr.commit();

    }});
b15.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        FragmentTransaction fr=getFragmentManager().beginTransaction();
        fr.replace(R.id.nav_host_fragment,new AssignmentFragment());
        fr.addToBackStack(null);
        fr.commit();
    }
});

        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new YearsForAttendance());
                fr.addToBackStack(null);
                fr.commit();
            }
        });

        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new Evaluation1());
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TeacherViewModel.class);
        // TODO: Use the ViewModel
    }

}
