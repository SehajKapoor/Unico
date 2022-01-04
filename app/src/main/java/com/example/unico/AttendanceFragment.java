package com.example.unico;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AttendanceFragment extends Fragment {


    ImageView img;
    RadioButton present,absent;

    String formattedDate;

    LocationManager lm ;
    DatabaseReference dr;

    String aa,attendance1,ss;

    String Sname,SrollNo,Syear,Susername;


    private AttendanceViewModel mViewModel;

    public static AttendanceFragment newInstance() {
        return new AttendanceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.attendance_fragment, container, false);

        present = (RadioButton) v.findViewById(R.id.radioButton3);
        absent = (RadioButton) v.findViewById(R.id.radioButton4);


        img=(ImageView) v.findViewById(R.id.imageView2);

        lm=(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        dr= FirebaseDatabase.getInstance().getReference();

       // obj=new getset();
        Sname=StudentSignIn.Sname;
        SrollNo=StudentSignIn.rollNo;
        Syear=StudentSignIn.Syear;
        Susername=StudentSignIn.username;


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //TODO: Consider calling

                    return;
                }
                Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(location!=null) {
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1000, new MyLocationListener());
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, new MyLocationListener());
                }

            }
        });



        return v;
    }


    private class MyLocationListener implements LocationListener {
        String s = "";
        @Override
        public void onLocationChanged(Location location) {

            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

            try {
                List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addressList != null && addressList.size() > 0) {
                    Address address = addressList.get(0);

                    String rs = address.getLocality();
            Toast.makeText(getActivity(),"MY LOCATION IS "+ rs, Toast.LENGTH_SHORT).show();


                    if (rs.equals("Patiala") ) {
                        s = "present";
                        present.setChecked(true);
                        attendance1 = "present";

                    } else {

                        s = "absent";
                        absent.setChecked(true);
                        attendance1 = "absent";
                    }
                }

                getDate();

                addAttendance obj = new addAttendance(formattedDate, attendance1);
                dr.child(Sname+SrollNo).push().setValue(obj);


                update_attendence(Susername);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }
@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AttendanceViewModel.class);
        // TODO: Use the ViewModel
    }




    public void getDate()
    {

        formattedDate = new SimpleDateFormat("yyyy-MMM-dd").format(new Date());
        // Use formattedDate as your String filled with the date.
    }


    public void update_attendence(String n)
    {

        Query q=dr.child(Syear+"Students").orderByChild("username").equalTo(n);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ds.getRef().child("attendance").setValue(attendance1);
                    ds.getRef().child("date").setValue(formattedDate);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

class addAttendance
{

    public String date,attendence;
    public addAttendance(String date,String attendence)
    {


        this.date=date;
        this.attendence=attendence;


    }
}