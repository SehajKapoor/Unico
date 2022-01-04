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

public class YearsFragment extends Fragment {
Button b7,b17,b18,b19;
    private YearsViewModel mViewModel;

    public static YearsFragment newInstance() {
        return new YearsFragment();
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

                StudentFragment s=new StudentFragment();
                Bundle arg=new Bundle();
                arg.putString("year","Year1");
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
                StudentFragment s=new StudentFragment();
                Bundle arg=new Bundle();
                arg.putString("year","Year2");
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
                StudentFragment s=new StudentFragment();
                Bundle arg=new Bundle();
                arg.putString("year","Year3");
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
                StudentFragment s=new StudentFragment();
                Bundle arg=new Bundle();
                arg.putString("year","Year4");
                s.setArguments(arg);
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,s);
                fr.addToBackStack(null);
                fr.commit();

            }
        });
        return v ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(YearsViewModel.class);
        // TODO: Use the ViewModel
    }

}
