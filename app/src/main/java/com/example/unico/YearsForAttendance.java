package com.example.unico;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class YearsForAttendance extends Fragment {

    Button b7,b17,b18,b19;
    static String y;
    private YearsForAttendanceViewModel mViewModel;

    public static YearsForAttendance newInstance() {
        return new YearsForAttendance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.years_fragment, container, false);

        b7=v.findViewById(R.id.button7);
        b17=v.findViewById(R.id.button17);
        b18=v.findViewById(R.id.button18);
        b19=v.findViewById(R.id.button19);

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AttendanceTable s=new AttendanceTable();
                Bundle arg=new Bundle();
                y="Year1";
                arg.putString("year",y);
                s.setArguments(arg);
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,s);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        b17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttendanceTable s=new AttendanceTable();
                Bundle arg=new Bundle();
                y="Year2";
                arg.putString("year",y);
                s.setArguments(arg);
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,s);
                fr.addToBackStack(null);
                fr.commit();

            }
        });
        b18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttendanceTable s=new AttendanceTable();
                Bundle arg=new Bundle();
                y="Year3";
                arg.putString("year",y);
                s.setArguments(arg);
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,s);
                fr.addToBackStack(null);
                fr.commit();

            }
        });
        b19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttendanceTable s=new AttendanceTable();
                Bundle arg=new Bundle();
                y="Year4";
                arg.putString("year",y);
                s.setArguments(arg);
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,s);
                fr.addToBackStack(null);
                fr.commit();

            }
        });




        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(YearsForAttendanceViewModel.class);
        // TODO: Use the ViewModel
    }

}
