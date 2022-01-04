package com.example.unico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageButton;
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

public class TeacherSignIn extends AppCompatActivity {
    EditText e,e2,e7;
    Button b4,b6;
    CheckBox checkbox;
    static String username, password,name,phone;
    TextView t7;
    String v="This is teacher's OTP";
    String rs;
    DatabaseReference dr;
    private SharedPreferences mprefrences;
    private SharedPreferences.Editor mEditor;
    private ProgressDialog LoadingBar;
//    Long update,up;
//     Long l;
//    int u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign_in);
        checkbox=findViewById(R.id.checkBox2);
       // check=findViewById(R.id.checkBox3);

        dr= FirebaseDatabase.getInstance().getReference();

        e=findViewById(R.id.editText);
        e2=findViewById(R.id.editText2);
        e7=findViewById(R.id.editText7);
        t7=findViewById(R.id.textView7);

        TextView t7=findViewById(R.id.textView7);
        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(TeacherSignIn.this,Forgetpassword.class);
                startActivity(i);
            }
        });
        b4=findViewById(R.id.button4);
        LoadingBar=new ProgressDialog(this);

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String a=e.getText().toString().trim();
                final String b=e2.getText().toString().trim();

                Query q= dr.child("Teacher").orderByChild("username").equalTo(a);

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Map m = (Map) ds.getValue();

                            username = (String) m.get("username");
                            password = (String) m.get("password");
                            name=(String) m.get("name");
                            phone=(String) m.get("contact");
                            if(a.equals(username) && b.equals(password))
                            {
//                                check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                                    @Override
//                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                                        if (isChecked)
//                                        {
//                                            SharedPreferences sharedPreferences=getSharedPreferences("MyData",MODE_PRIVATE);
//                                            SharedPreferences.Editor editor=sharedPreferences.edit();
//                                            editor.putString("username",e.getText().toString());
//                                            editor.putString("password",e2.getText().toString());
//                                            editor.commit();
//
//                                            e.setText(username);
//                                            e2.setText(password);
//
//                                            LoadingBar.setTitle("Welcome "+name);
//                                            LoadingBar.setMessage("Please Wait For Few Seconds");
//                                            LoadingBar.show();
//                                            LoadingBar.setCanceledOnTouchOutside(false);
//                                            new Handler().postDelayed(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    Intent i= new Intent(TeacherSignIn.this,TeacherHome.class);
//                                                    startActivity(i);
//                                                    finish();
//                                                }
//                                            },2000);
//
//                                        }
//                                        else
//                                        {
                                            sendSms();
                                       //     update=l;
                                      //      updateOTP();
                                            Dialog d=new Dialog(TeacherSignIn.this);
                                            d.setContentView(R.layout.teacherotp);
                                            d.setCanceledOnTouchOutside(false);
                                            final EditText o=d.findViewById(R.id.otp1);
                                            Button check=d.findViewById(R.id.checkOtp1);
                                            check.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String oo=o.getText().toString();
                                               //     Long o1=Long.parseLong(oo);
                                             //       Toast.makeText(TeacherSignIn.this, ""+oo, Toast.LENGTH_SHORT).show();

                                                    if (oo.equals(rs)) {



                                                        LoadingBar.setTitle("Welcome "+name);
                                                        LoadingBar.setMessage("Please Wait For Few Seconds");
                                                        LoadingBar.show();
                                                        LoadingBar.setCanceledOnTouchOutside(false);
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Intent i= new Intent(TeacherSignIn.this,TeacherHome.class);
                                                                startActivity(i);
                                                                finish();
                                                            }
                                                        },2000);

                                                    }
                                                }
                                            });
                                            d.show();
//                                        }
//
//                                    }
//                                });

                                //otp=++l;

                            }
                            else
                            {
                                Toast.makeText(TeacherSignIn.this, "Incorrect Username/password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            public void sendSms()
            {
                Random r=new Random();
                int otp1=r.nextInt(10000);
                rs=Integer.toString(otp1)+phone.substring(2,4);
            //    rs=phone.substring(2,7);
                int permissionCheck= ContextCompat.checkSelfPermission(TeacherSignIn.this, Manifest.permission.SEND_SMS);

                if(permissionCheck==PackageManager.PERMISSION_GRANTED)
                {
                    MyMessage(rs);
                }

                else
                {
                    ActivityCompat.requestPermissions(TeacherSignIn.this,new String[]{Manifest.permission.SEND_SMS},0);   }
            }

            private void MyMessage(String locMessage) {


                String phoneNumber=phone;
                String message=locMessage+" is the OTP for your Account verification.Thanks for using UNICO!!";

                SmsManager smsManager= SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber,null,message,null,null);
                Toast.makeText(TeacherSignIn.this, "Message Sent", Toast.LENGTH_SHORT).show();
            }

     //       @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                TeacherSignIn.super.onRequestPermissionsResult(requestCode, permissions, grantResults);

                switch (requestCode)
                {
                    case 0:

                        if(grantResults.length>=0  && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        {

                            MyMessage(rs);
                        }

                        else
                        {
                            Toast.makeText(TeacherSignIn.this, "you don't have permission", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }

//            public void updateOTP()
//            {
//                //up=update;
//                //u+=9;
//                update+=11;
//                Query q = dr.child("Teacher").orderByChild("verify").equalTo(v);
//                q.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for(DataSnapshot ds:dataSnapshot.getChildren())
//                        {
//
//                            ds.getRef().child("otp").setValue(update);
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }


        });


//        b6=findViewById(R.id.button6);
//        b6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(TeacherSignIn.this,TeacherSignUp.class);
//                startActivity(i);
//            }
//        });
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    e2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    e2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i=new Intent(TeacherSignIn.this,MainActivity.class);
        startActivity(i);

    }

}
//class setOTP
//{
//
//
//    public String otp,verify;
//    public setOTP(String otp,String v)
//    {
//
//     this.otp=otp;
//     this.verify=v;
//    }
//}



