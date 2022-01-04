package com.example.unico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ManagementStudentsList extends AppCompatActivity {
    ListView lv;
    ArrayList<String> nameList, rollNoList, rollArr,fees;
    DatabaseReference dr;
    String year, rolll;
   // String fee;
    int img = R.drawable.studenticon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_students_list);
        dr = FirebaseDatabase.getInstance().getReference();
        Bundle bundle = getIntent().getExtras();
        year = bundle.getString("year");

        lv = findViewById(R.id.students_list);

        nameList = new ArrayList<>();
        rollNoList = new ArrayList<>();
        rollArr = new ArrayList<>();
        fees=new ArrayList<>();
      //  retrieve_fee();

        retrieve_data();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ManagementStudentsList.this, ManagementStudentInfo.class);
                Bundle bundle = new Bundle();
                bundle.putString("year", year);
                String a = rollArr.get(position);
                bundle.putString("rollNo", a);
                i.putExtras(bundle);
                startActivity(i);

            }
        });


    }
//
//    private void retrieve_fee() {
//
//        Query q = dr.child(year + "Students");
//
//        q.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    Map m = (Map) ds.getValue();
//
//                    fee = (String) m.get("fee_status");
//
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }


    public void retrieve_data()
    {
        Query q=dr.child(year+"Students");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Map m= (Map) ds.getValue();

                    String name=(String) m.get("name");
                    String roll_no=(String) m.get("roll_no");
                    String f=(String) m.get("fee_status");
                    rolll=roll_no;
                    nameList.add(name);
                    rollNoList.add(roll_no);
                    fees.add(f);
                    String[] n = new String[nameList.size()];
                    String[] r = new String[rollNoList.size()];
                    String[] fee = new String[fees.size()];
                    for (int i = 0; i < n.length; i++) {
                        n[i] = nameList.get(i);
                    }
                    for (int i = 0; i < r.length; i++) {
                        r[i] = rollNoList.get(i);
                    }
                    for (int i = 0; i < fee.length; i++) {
                        fee[i] = fees.get(i);
                    }

                    rollArr.add(rolll);
                    MyAdapter5 adapter = new MyAdapter5(getApplication(),n,r,img,fee);
                    lv.setAdapter(adapter);

                    //  lv.setAdapter(ad);
                    //lvRoll.setAdapter(ad1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
class MyAdapter5 extends ArrayAdapter<String>
{
    Context context;
    String nameT[];
    int rImage;
    String rollT[];
    String f[];
    MyAdapter5(Context c,String namee[],String roll[],int imgs,String f[]){
        super(c,R.layout.students_list,R.id.mainTitleS,namee);
        this.context=c;
        this.nameT=namee;
        this.rImage=imgs;
        this.rollT=roll;
        this.f=f;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=layoutInflater.inflate(R.layout.management_list,parent,false);


        ImageView image=row.findViewById(R.id.Sicon);
        TextView title=row.findViewById(R.id.mainTitleS);
        TextView description=row.findViewById(R.id.subTitleS);
        TextView pay=row.findViewById(R.id.pay);
        image.setImageResource(rImage);
        title.setText(nameT[position]);
        description.setText("Roll No- "+rollT[position]);
        pay.setText(f[position]);
            pay.setTextColor(Color.RED);
        return row;
    }
}