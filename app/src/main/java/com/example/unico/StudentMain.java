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


public class StudentMain extends Fragment {
    Button b9,b11,b12,b14,b15,b16;



    private StudentMainViewModel mViewModel;

    public static StudentMain newInstance() {
        return new StudentMain();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.student_main_fragment, container, false);



        b9=v.findViewById(R.id.button9);
        b11=v.findViewById(R.id.button11);
        b12=v.findViewById(R.id.button12);
        b14=v.findViewById(R.id.button14);
        b15=v.findViewById(R.id.button15);
        b16=v.findViewById(R.id.button16);
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new Notes1());
                fr.addToBackStack(null);
                fr.commit();

            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new ShowTimeTable());
                fr.addToBackStack(null);
                fr.commit();

            }
        });

        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new StudentAttendanceGraph());
                fr.addToBackStack(null);
                fr.commit();

            }
        });
        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new AssignmentStudentFragment());
                fr.addToBackStack(null);
                fr.commit();

            }
        });
        b16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new EventsGallery());
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new PerformanceGraph());
                fr.addToBackStack(null);
                fr.commit();
            }
        });

        return v;     }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StudentMainViewModel.class);
        // TODO: Use the ViewModel
    }

}
