package com.example.storyhoster;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class TestingActivity extends AppCompatActivity {


    EditText title_blog,description_blog;
    Button upload;
    ImageView blog_image;
    ProgressDialog pd;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);


        title_blog=findViewById(R.id.title_blog);
        description_blog=findViewById(R.id.description_blog);
        upload=findViewById(R.id.upload);
        blog_image=findViewById(R.id.post_image_blog);
        pd=new ProgressDialog(this);

        auth= FirebaseAuth.getInstance();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = title_blog.getText().toString();
                String description = description_blog.getText().toString();

                if (TextUtils.isEmpty(title)){
                    title_blog.setError("Title is required");
                }
                else if (TextUtils.isEmpty(description)){
                    description_blog.setError("Description is required");
                }
                else {
                    uploadData(title , description);
                }
            }
        });



    }

    private void uploadData(String title, String description) {
        pd.setMessage("Publishing Post");
        pd.show();
        final String timeStamp= String.valueOf(System.currentTimeMillis());
        String filepath="Posts/"+"post_"+timeStamp;

            //uri is recieved post is publised to database

            //now we will upload the data to firebase database for
            FirebaseUser user = auth.getCurrentUser();

            HashMap<String , Object> hashMap = new HashMap<>();

            hashMap.put("uid" , user.getUid());
            hashMap.put("uEmail" , user.getEmail());
            hashMap.put("pId" , timeStamp);
            hashMap.put("pTitle" , title);
            //  hashMap.put("pImage" , downloadUri);
            hashMap.put("pDescription" , description);
            hashMap.put("pTime" ,  timeStamp);

            //now we will pust the data to firebase database
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
            ref.child(timeStamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();;
                            Toast.makeText(TestingActivity.this, "Post Published", Toast.LENGTH_SHORT).show();
                            title_blog.setText("");
                            description_blog.setText("");
                            blog_image.setImageURI(null);


                            //when post is publised user must go to home activity means main dashboad
                            startActivity(new Intent(TestingActivity.this , HomeActivity.class));


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(TestingActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



    }
}