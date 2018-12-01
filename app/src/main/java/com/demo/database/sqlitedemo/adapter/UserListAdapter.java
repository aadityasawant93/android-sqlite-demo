package com.demo.database.sqlitedemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.database.sqlitedemo.R;
import com.demo.database.sqlitedemo.entity.UserEntity;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    Context context;
    ArrayList<UserEntity> userEntityArrayList;

    public UserListAdapter(Context context, ArrayList<UserEntity> userEntityArrayList) {
        this.context = context;
        this.userEntityArrayList = userEntityArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.user_list_adapter_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        try {
            UserEntity userEntity = userEntityArrayList.get(i);
            if (userEntity != null) {
                myViewHolder.tv_name.setText(userEntity.getName());
                myViewHolder.tv_mobile.setText(userEntity.getMobile());
                myViewHolder.tv_address.setText(userEntity.getAddress());
                Bitmap bitmap = getImage(userEntity.getProfilePic());
                myViewHolder.iv_profile.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    public int getItemCount() {
        return userEntityArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_mobile, tv_address;
        ImageView iv_profile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_mobile = itemView.findViewById(R.id.tv_mobile);
            tv_address = itemView.findViewById(R.id.tv_address);
            iv_profile = itemView.findViewById(R.id.iv_profile);
        }
    }
}
