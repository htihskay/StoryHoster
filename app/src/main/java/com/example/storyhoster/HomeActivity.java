package com.example.storyhoster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.storyhoster.Adapter.PostAdapter;
import com.example.storyhoster.Model.PostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth auth;
    RecyclerView recyclerView;

    PostAdapter postAdapter;
    List<PostModel> postModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth=FirebaseAuth.getInstance();

        recyclerView=findViewById(R.id.recylerView);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true); //latest post will be appear at the top

        recyclerView.setLayoutManager(layoutManager);

        postModelList = new ArrayList<>();

        //now retrieveing the data from the firebase;

        loadPosts();
    }

    private void loadPosts() {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postModelList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzz "+ds);
                    PostModel postModel=ds.getValue(PostModel.class);
                    postModelList.add(postModel);
                    postAdapter =new PostAdapter(HomeActivity.this,postModelList);
                    recyclerView.setAdapter(postAdapter);

                   // PostModel postModel = ds.getValue(PostModel.class);
                 //   postModelList.add(postModel);
                  //  postAdapter = new PostAdapter(HomeActivity.this , postModelList);
                   // recyclerView.setAdapter(postAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       if(item.getItemId()==R.id.action_logout){
           auth.signOut();
           startActivity(new Intent(HomeActivity.this,MainActivity.class));
       }

        if(item.getItemId()==R.id.action_add_post){
            startActivity(new Intent(HomeActivity.this,AddPostActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
       finishAffinity();
    }
}