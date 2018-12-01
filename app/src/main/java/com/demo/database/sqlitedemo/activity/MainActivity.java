package com.demo.database.sqlitedemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.database.sqlitedemo.R;
import com.demo.database.sqlitedemo.db.SQLiteHelper;
import com.demo.database.sqlitedemo.entity.UserEntity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_name, et_mobile, et_address;
    Button btn_submit, btn_users, btn_upload_profile;
    SQLiteHelper sqLiteHelper;
    String name, mobile, address;
    ImageView iv_profile, iv_user;
    byte[] userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermissions();
        initializeViews();

    }

    private void initializeViews() {
        et_name = findViewById(R.id.et_name);
        et_mobile = findViewById(R.id.et_mobile);
        et_address = findViewById(R.id.et_address);
        iv_profile = findViewById(R.id.iv_profile);
        iv_user = findViewById(R.id.iv_user);
        btn_upload_profile = findViewById(R.id.btn_upload_profile);
        btn_submit = findViewById(R.id.btn_submit);
        btn_users = findViewById(R.id.btn_users);

        btn_submit.setOnClickListener(this);
        btn_users.setOnClickListener(this);
        btn_upload_profile.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath,
                    null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String selectedImagePath = c.getString(columnIndex);
            c.close();
            Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath); // load
            iv_profile.setVisibility(View.VISIBLE);
            iv_profile.setImageBitmap(bitmap);

            //  file = createfile();
            //  savefile(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkValidation() {
        name = et_name.getText().toString().trim();
        mobile = et_mobile.getText().toString().trim();
        address = et_address.getText().toString().trim();

        if (name.equals("")) {
            Toast.makeText(this, R.string.error_enter_name, Toast.LENGTH_SHORT).show();
            et_name.requestFocus();
            return false;
        }
        if (mobile.equals("")) {
            Toast.makeText(this, R.string.error_enter_mobile, Toast.LENGTH_SHORT).show();
            et_mobile.requestFocus();
            return false;
        }
        if (address.equals("")) {
            Toast.makeText(this, R.string.enter_address, Toast.LENGTH_SHORT).show();
            et_address.requestFocus();
            return false;
        }

        if (iv_profile.getDrawable() == null) {
            Toast.makeText(this, R.string.error_upload_profile, Toast.LENGTH_SHORT).show();
            return false;
        }
        Bitmap bitmap = ((BitmapDrawable) iv_profile.getDrawable()).getBitmap();

        userProfile = getByteArray(bitmap);
        return true;
    }

    private byte[] getByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        return outputStream.toByteArray();
    }

    private  boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        if (dbFile == null)
            return false;
        //  return dbFile.exists();
        return false;
    }

    private boolean checkAndRequestPermissions() {
        int writepermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //  int permissionWriteCalendar = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CALENDAR);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (checkValidation())
                    saveUserData();
                break;
            case R.id.btn_upload_profile:
                startGalleryIntent();
                break;
            case R.id.btn_users:
                startUserListActivity();
                break;
            default:
                break;
        }
    }

    private void startUserListActivity() {
        Intent intent = new Intent(this, UserListActivity.class);
        startActivity(intent);
       // finish();
    }

    private void startGalleryIntent() {
        Intent pictureActionIntent = null;
        pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pictureActionIntent, 1);
    }

    private void saveUserData() {
        if (sqLiteHelper == null) {
            sqLiteHelper = new SQLiteHelper(MainActivity.this);
        }
        long id = sqLiteHelper.insertUserEntity(new UserEntity(name, mobile, address, userProfile));
        Toast.makeText(MainActivity.this, R.string.added_successfully, Toast.LENGTH_SHORT).show();
    }
}
