package com.example.unico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ManagementStudentInfo extends AppCompatActivity {
TextView Name,Roll,User,Year;
Spinner Fee;
String f[]={"Not Paid","Paid"};
DatabaseReference dr;
String y,r;
Button Update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_student_info);
        Name=findViewById(R.id.name);
        Roll=findViewById(R.id.roll);
        User=findViewById(R.id.user);
        Year=findViewById(R.id.year);
        Fee=findViewById(R.id.fee);
        Bundle bundle=getIntent().getExtras();
        y =bundle.getString("year");
        r =bundle.getString("rollNo");
        dr= FirebaseDatabase.getInstance().getReference();


        ArrayAdapter fees=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,f);
        Fee.setAdapter(fees);

        retrieve_data();
        Update=findViewById(R.id.update);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_data();
            }
        });


    }

    private void update_data() {

        final String ff = Fee.getSelectedItem().toString();
        Query q = dr.child(y + "Students").orderByChild("roll_no").equalTo(r);


        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    ds.getRef().child("fee_status").setValue(ff);

                    }
                Toast.makeText(ManagementStudentInfo.this, "Status Updated Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }

    private void retrieve_data() {

        Query q= dr.child(y+"Students").orderByChild("roll_no").equalTo(r);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    Map m = (Map) ds.getValue();

                    String name = (String) m.get("name");
                    String roll = (String) m.get("roll_no");
                    String year = (String) m.get("year");
                    String user = (String) m.get("username");
                    String fee= (String) m.get("fee_status");


                    Name.setText(name);
                    Roll.setText(roll);
                    Year.setText(year);
                    User.setText(user);
                    Fee.setPrompt(fee);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
