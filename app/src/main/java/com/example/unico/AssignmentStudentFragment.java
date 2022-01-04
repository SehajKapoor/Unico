package com.example.unico;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class AssignmentStudentFragment extends Fragment {

    TextView textView,textView18,textView20,assignmentsText;
    Button selectFile,b20;
    EditText pdfName;
    Uri pdfUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String Yearpath;
     String user=StudentSignIn.username;
    String year=StudentSignIn.Syear;

    private AssignmentViewModel mViewModel;

    public static AssignmentFragment newInstance() {
        return new AssignmentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.assignment_student_fragment, container, false);
        b20=v.findViewById(R.id.button20);
        textView=v.findViewById(R.id.textView);
        pdfName=v.findViewById(R.id.txt_pdfName);
        selectFile=v.findViewById(R.id.select_file);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference=FirebaseDatabase.getInstance().getReference();

        b20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new AssignmentsListStudent1());
                fr.addToBackStack(null);
                fr.commit();
            }
        });

        Yearpath=year+"StudentsAssignments";
        databaseReference=FirebaseDatabase.getInstance().getReference(Yearpath+"/"+user+"/");




        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf();
            }
        });



        return v;
    }

    private void selectPdf() {

        Intent i=new Intent();
        i.setType("application/pdf");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select PDF File"),19);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==19 && resultCode== Activity.RESULT_OK  && data!=null)
        {
            pdfUri=data.getData();
            textView.setText("File Selected"+data.getData().getLastPathSegment());
            uploadFile(pdfUri);

        }
        else{
            Toast.makeText(getActivity(), "Please select a file", Toast.LENGTH_SHORT).show();
        }

    }
    private void uploadFile(Uri pdfUri) {
        //     final String fileName=System.currentTimeMillis()+"";

        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();


        String filename=pdfName.getText().toString();
        StorageReference reference=storageReference.child(Yearpath+"/"+user+"/"+filename+System.currentTimeMillis()+".pdf");


        reference.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();

                        while (!uri.isComplete());
                        Uri url=uri.getResult();

                        uploadPDF uploadPDF=new uploadPDF(pdfName.getText().toString(),url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(uploadPDF);

                        //  Toast.makeText(getActivity(), "File Uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        Toast.makeText(getActivity(), "File Uploaded Successfully", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "File Not Uploaded Successfully "+e, Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double currentProgress= ( 100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress((int)currentProgress);

            }


        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==9 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            selectPdf();
        }
        else
        {
            Toast.makeText(getActivity(), "Please Provide Permission", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);
        // TODO: Use the ViewModel
    }

}
