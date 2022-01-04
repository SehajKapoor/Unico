package com.example.unico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Random;

public class StudentSignIn extends AppCompatActivity implements View.OnClickListener {
    EditText e,e2,e7;
    Button b4,b6,b;
    CheckBox checkbox2;
    TextView t7;
  //  Button demo;
    private ProgressDialog LoadingBar;
  static String username,password;
   static String Sname,rollNo,Syear,Sphone;
   String yearNext;//v="This is Year1 OTP",v1="This is Year2 OTP",v3="This is Year3 OTP",v4="This is Year4 OTP";
    DatabaseReference dr;
    String rs;
    static String vv;
  //  int u;
//    String update;
//    long l;
//    int k;
   // static int l=111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_in);
        Bundle bundle=getIntent().getExtras();
        final String year =bundle.getString("year");
        e=findViewById(R.id.editText);
        e2=findViewById(R.id.editText2);
        e7=findViewById(R.id.editText7);
        dr= FirebaseDatabase.getInstance().getReference();

        b4=findViewById(R.id.button4);
      //  b6=findViewById(R.id.button6);
        b4.setOnClickListener(this);
      //  b6.setOnClickListener(this);
        LoadingBar=new ProgressDialog(this);
        yearNext=year;
        checkbox2=findViewById(R.id.checkBox2);
        t7=findViewById(R.id.textView7);
        TextView t7=(TextView) findViewById(R.id.textView7);
        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StudentSignIn.this,StudentForgetPassword.class);
                Bundle bundle =new Bundle();
                bundle.putString("year",year);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    e2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else
                {
                    e2.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });


    }

    @Override
    public void onClick(View v) {



        if(v.getId()==R.id.button4)
        {
            final String a=e.getText().toString().trim();
            final String b=e2.getText().toString().trim();
            // yearNext=year;
            Query q= dr.child(yearNext+"Students").orderByChild("username").equalTo(a);

            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Map m = (Map) ds.getValue();

                        username = (String) m.get("username");
                        password = (String) m.get("password");
                        Sname = (String) m.get("name");
                        rollNo = (String) m.get("roll_no");
                        Syear = (String) m.get("year");
                        Sphone=(String) m.get("contact");
                        if(a.equals(username))
                        {
                            if (b.equals(password))
                            {
                                sendSms();
                         //       update=Integer.toString((int) l);
                           //     updateOTP();
                                Dialog d=new Dialog(StudentSignIn.this);
                                d.setContentView(R.layout.studentotp);
                                d.setCanceledOnTouchOutside(false);
                                final EditText o=d.findViewById(R.id.otp);
                                Button check=d.findViewById(R.id.checkOtp);
                                check.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final String oo=o.getText().toString();
                     //                   long o1=Long.parseLong(oo);
                                        Toast.makeText(StudentSignIn.this, ""+rs, Toast.LENGTH_SHORT).show();

                                        if (oo.equals(rs))
                                        {
//                                            Intent i= new Intent(StudentSignIn.this, StudentHome.class);
//                                                    startActivity(i);
//                                                    finish();

                                            LoadingBar.setTitle("Welcome "+Sname);
                                            LoadingBar.setMessage("Please Wait For Few Seconds");
                                            LoadingBar.show();
                                            LoadingBar.setCanceledOnTouchOutside(false);
                                 //           Toast.makeText(StudentSignIn.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent i= new Intent(StudentSignIn.this, StudentHome.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            },2000);

                                        }
                                        else
                                        {
                                            Toast.makeText(StudentSignIn.this, "Incorrect Code!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                d.show();
                            }
                            else
                            {
                                Toast.makeText(StudentSignIn.this, "Incorrect Username/password", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(StudentSignIn.this, "Incorrect Username/password", Toast.LENGTH_SHORT).show();
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

//        else if (v.getId()==R.id.button6)
//        {
//            Intent i=new Intent(StudentSignIn.this,StudentSignUp.class);
//
//            Bundle bundle =new Bundle();
//            bundle.putString("year",yearNext);
//            i.putExtras(bundle);
//            startActivity(i);
//        }
    }

    public void sendSms()
    {
        Random r=new Random();
        int otp1=r.nextInt(10000);
        rs=Integer.toString(otp1)+Sphone.substring(5,7);

        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permissionCheck==PackageManager.PERMISSION_GRANTED)
        {
            MyMessage(rs);
        }

        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
        }
    }

    private void MyMessage(String locMessage) {


        String phoneNumber=Sphone;
        String message=locMessage+" is the OTP for your Account verification.Thanks for using UNICO!!";

        SmsManager smsManager= SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber,null,message,null,null);
        Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case 0:

                if(grantResults.length>=0  && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                    MyMessage(rs);
                }

                else
                {
                    Toast.makeText(this, "you don't have permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

//    public void updateOTP()
//    {
//        k=Integer.parseInt(update);
//        k+=2;
//        Query q = dr.child(yearNext+"Students").orderByChild("verify").equalTo(vv);
//        q.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds:dataSnapshot.getChildren())
//                {
//
//                    ds.getRef().child("otp").setValue(k);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }


}
class setOTP
{

    public String otp,verify;
    public setOTP(String otp,String v)
    {

        this.otp=otp;
        this.verify=v;
    }
}
