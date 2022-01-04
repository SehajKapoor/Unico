package com.example.unico;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TableFirst extends Fragment {
Button view11,edit;
    private TableFirstViewModel mViewModel;

    public static TableFirst newInstance() {
        return new TableFirst();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.table_first_fragment, container, false);
        view11=v.findViewById(R.id.view);
        view11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new ShowTimeTable());
               // fr.addToBackStack(null);
                fr.commit();

            }
        });
        edit=v.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new TimeTable1());
             //   fr.addToBackStack(null);
                fr.commit();
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TableFirstViewModel.class);
        // TODO: Use the ViewModel
    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//        Intent i=new Intent(TeacherSignIn.this,MainActivity.class);
//        startActivity(i);
//
//    }

}
