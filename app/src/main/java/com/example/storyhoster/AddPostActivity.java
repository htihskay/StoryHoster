package com.example.storyhoster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
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

public class AddPostActivity extends AppCompatActivity {

    EditText title_blog,description_blog;
    Button upload;
    ImageButton blog_image;
    Uri blog_image_uri;
    ProgressDialog pd;
    FirebaseAuth auth;

    //image upload
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        //permission();
        title_blog=findViewById(R.id.title_blog);
        description_blog=findViewById(R.id.description_blog);
        upload=findViewById(R.id.upload);
        blog_image=findViewById(R.id.post_image_blog);
        pd=new ProgressDialog(this);

        auth=FirebaseAuth.getInstance();

        //image upload
        storageReference= FirebaseStorage.getInstance().getReference();


        //uploading image
        blog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(AddPostActivity.this)
                        .crop(4f, 3f)	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        //when user click on upload button upload the data to firebase
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
                    uploadData(title ,blog_image_uri, description);
                }
            }
        });

    }

    private void uploadData(String title,Uri image ,String description) {


        pd.setMessage("Publishing Post");
        pd.show();
        final String timeStamp= String.valueOf(System.currentTimeMillis());
        String filepath="Posts/"+"post_"+timeStamp;



        //Uploading the image using one seperate storage reference ..

        StorageReference imgupload=storageReference.child(filepath);

        //now im using the  uri to upload;
        imgupload.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();

                    while (!uriTask.isSuccessful());
                String downloadUri = uriTask.getResult().toString();
                    if(uriTask.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();

                        HashMap<String , Object> hashMap = new HashMap<>();

                        hashMap.put("uid" , user.getUid());
                        hashMap.put("uEmail" , user.getEmail());
                        hashMap.put("pId" , timeStamp);
                        hashMap.put("pTitle" , title);
                        hashMap.put("pImage",downloadUri);
                        hashMap.put("pDescription" , description);
                        hashMap.put("pTime" ,  timeStamp);

                        //now we will pust the data to firebase database
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                        ref.child(timeStamp).setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        pd.dismiss();;
                                        Toast.makeText(AddPostActivity.this, "Post Published", Toast.LENGTH_SHORT).show();
                                        title_blog.setText("");
                                        description_blog.setText("");
                                        blog_image.setImageURI(null);


                                        //when post is publised user must go to home activity means main dashboad
                                        startActivity(new Intent(AddPostActivity.this , HomeActivity.class));


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(AddPostActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddPostActivity.this,"Image upload failed",Toast.LENGTH_SHORT).show();
            }
        });



        //uri is recieved post is publised to database

        //now we will upload the data to firebase database for
            //after pase here


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri= data.getData();
        blog_image_uri = uri;
        blog_image.setImageURI(uri);
        System.out.println("ZCXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+uri);
    }
}