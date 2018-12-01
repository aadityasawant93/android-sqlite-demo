package com.demo.database.sqlitedemo.entity;

public class UserEntity {
    int id;
    String name;
    String mobile;
    String address;
    byte[] profilePic;

    public static final String TABLE_NAME = "tbl_user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MOBILE = "mobile";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PROFILE_PIC = "profile";

    public static final String CREATE_TABLE = "create table  " + TABLE_NAME +
            "( " + COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " varchar, " +
            COLUMN_MOBILE + " varchar, " +
            COLUMN_ADDRESS + " varchar ," +
            COLUMN_PROFILE_PIC + " blob )";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public UserEntity(String name, String mobile, String address) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
    }

    public UserEntity(int id, String name, String mobile, String address) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
    }

    public UserEntity( String name, String mobile, String address, byte[] profilePic) {

        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.profilePic = profilePic;
    }

    public UserEntity(int id, String name, String mobile, String address, byte[] profilePic) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.profilePic = profilePic;
    }

    public UserEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }
}
