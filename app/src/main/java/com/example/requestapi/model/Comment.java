package com.example.requestapi.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private int id;
    private int postId;
    private String name;
    private String email;
    private String body;

    public Comment (int id, int postId, String name, String email, String body) {
        this.id = id;
        this.postId = postId;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }

    protected Comment(Parcel in) {
        this.id = in.readInt();
        this.postId = in.readInt();
        this.name = in.readString();
        this.email = in.readString();
        this.body = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.postId);
        parcel.writeString(this.name);
        parcel.writeString(this.email);
        parcel.writeString(this.body);
    }
}
