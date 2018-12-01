package com.demo.database.sqlitedemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.demo.database.sqlitedemo.R;
import com.demo.database.sqlitedemo.adapter.UserListAdapter;
import com.demo.database.sqlitedemo.db.SQLiteHelper;
import com.demo.database.sqlitedemo.entity.UserEntity;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
    RecyclerView rv;
    private SQLiteHelper sqLiteHelper;
    TextView tv_no_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        initailizeViews();
        getAllUsers();
    }


    private void initailizeViews() {
        rv = findViewById(R.id.rv);
        tv_no_user = findViewById(R.id.tv_no_user);
    }

    // retreive all users from database
    private void getAllUsers() {
        if (sqLiteHelper == null) {
            sqLiteHelper = new SQLiteHelper(UserListActivity.this);
        }
        ArrayList<UserEntity> userEntityArrayList = sqLiteHelper.getAllUsers();
        if (userEntityArrayList != null && userEntityArrayList.size() > 0) {
            UserListAdapter userListAdapter = new UserListAdapter(this, userEntityArrayList);
            rv.setAdapter(userListAdapter);
        } else {
            tv_no_user.setVisibility(View.VISIBLE);
        }

    }


}
