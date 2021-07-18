package com.example.storyhoster.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.storyhoster.Model.PostModel;
import com.example.storyhoster.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyHolder> {

    Context context;

    List<PostModel> postModelList;

    public PostAdapter(Context context, List<PostModel> postModelList) {
        this.context = context;
        this.postModelList = postModelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_post,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String title=postModelList.get(position).getpTitle();
        String description=postModelList.get(position).getpDescription();

        //getting image;
        String image=postModelList.get(position).getpImage();
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWW"+image);

        String timedate=postModelList.get(position).getpTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(Long.parseLong(timedate)));



        holder.postTitle.setText(title);

        holder.postDescription.setText(description);

        holder.postTime.setText(dateString);


        //image upload holder --sunday before lunch
        //It need seperate library to show the image;
        Glide.with(context).load(image).into(holder.postImage);


    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView postTitle,postDescription,postTime;

        //image display
        ImageView postImage;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            postTitle=itemView.findViewById(R.id.postTitle);
            postDescription=itemView.findViewById(R.id.postDescription);
            postTime=itemView.findViewById(R.id.postTime);

            postImage=itemView.findViewById(R.id.postImage);




        }
    }
}
