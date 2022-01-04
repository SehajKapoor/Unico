package com.example.unico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

public class Forgetpassword extends AppCompatActivity {

    EditText contactNo,e15,e16;
    Button b,b13;
    CheckBox showpassword;
    String Contact_No,rs,newP,confirmP;
    DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        contactNo = findViewById(R.id.no);
        dr=FirebaseDatabase.getInstance().getReference();
        b = findViewById(R.id.bb);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact_No = contactNo.getText().toString();

                if (Contact_No.length()==10)
                {

                    Random r=new Random();
                    int otp1=r.nextInt(10000);
                    rs=Integer.toString(otp1)+Contact_No.substring(3,5);

                    int permissionCheck = ContextCompat.checkSelfPermission(Forgetpassword.this, Manifest.permission.SEND_SMS);

                    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                        MyMessage(rs);
                    }
                    else {
                        ActivityCompat.requestPermissions(Forgetpassword.this, new String[]{Manifest.permission.SEND_SMS}, 0);
                    }
                    final Dialog d1=new Dialog(Forgetpassword.this);
                    d1.setContentView(R.layout.teacher_forget_password);
                    d1.setCanceledOnTouchOutside(false);
                    final EditText otp=d1.findViewById(R.id.otp11);
                    Button check=d1.findViewById(R.id.checkOtp11);
                    check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (otp.getText().toString().equals(rs))
                            {
                                d1.dismiss();
                                final Dialog d = new Dialog(Forgetpassword.this);
                                d.setContentView(R.layout.dialoganswer);
                                d.setCanceledOnTouchOutside(false);
                                e15 = d.findViewById(R.id.editText15);
                                e16 = d.findViewById(R.id.editText16);
                                showpassword = d.findViewById(R.id.showpassword);
                                showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            e15.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                            e16.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                        } else {
                                            e15.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                            e16.setTransformationMethod(PasswordTransformationMethod.getInstance());

                                        }

                                    }
                                });

                                b13=d.findViewById(R.id.button13);
                                b13.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        newP=e15.getText().toString().trim();
                                        confirmP=e16.getText().toString().trim();
                                        if (newP.equals(confirmP))
                                        {
                                            Query q = dr.child("Teacher").orderByChild("contact").equalTo(Contact_No);
                                            q.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    for(DataSnapshot ds:dataSnapshot.getChildren())
                                                    {

                                                        ds.getRef().child("password").setValue(newP);
                                                        Toast.makeText(Forgetpassword.this, "Password Changed Successfully!!", Toast.LENGTH_SHORT).show();

                                                        Intent i=new Intent(Forgetpassword.this,TeacherSignIn.class);
                                                        startActivity(i);
                                                        finish();
                                                    }}

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
//
                                        }

                                    }
                                });
                                d.show();
                            }
                            else
                            {
                                Toast.makeText(Forgetpassword.this, "incorrect Code", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                    d1.show();
                }
                else
                {
                    Toast.makeText(Forgetpassword.this, "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                }
            }

            private void MyMessage(String locMessage) {
                String phoneNumber=Contact_No;
                String message=locMessage+" is the OTP for changing your account password.Thanks for using UNICO!!";

                SmsManager smsManager= SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber,null,message,null,null);
                Toast.makeText(Forgetpassword.this, "Message Sent", Toast.LENGTH_SHORT).show();

            }


        });

    }
}

