package com.yenju.datingapp;

public class UserProfile {
    public int userID;
    public String userName;
    public String intro;
    public String photoName;
    public String toy;
    public String color;
    public String activity;


    public int sameAttribute(UserProfile userProfile){
        int count = 0;
        if(userProfile.toy !=null && this.toy != null && userProfile.toy.equals(this.toy)){
            count++;
        }
        if(userProfile.activity !=null && this.activity != null &&userProfile.activity.equals(this.activity)){
            count++;
        }
        if(userProfile.color !=null && this.color != null &&userProfile.color.equals(this.color)){
            count++;
        }
        return count;
    }
}
