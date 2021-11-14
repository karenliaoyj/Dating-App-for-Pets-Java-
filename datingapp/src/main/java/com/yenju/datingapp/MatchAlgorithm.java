package com.yenju.datingapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MatchAlgorithm {
    //input: UserID, output :USerID
    //
    public static int matchUser(int userID){
        ArrayList<UserProfile> users = new ArrayList<>();
        UserProfile myUserProfile = DBConnection.getUserAttribute(userID);
        if(myUserProfile == null ){
            return -1;
        }
        ArrayList<Integer> mutualLikeID = DBConnection.getMutualLikeUsers(userID);
        if(mutualLikeID == null || mutualLikeID.isEmpty()){
            return -1;
        }
        for(int i =0; i<mutualLikeID.size();i++){
            int likedId = mutualLikeID.get(i);
            UserProfile userProfile = DBConnection.getUserAttribute(likedId);
            users.add(userProfile);
        }
        Collections.sort(users, new Comparator<UserProfile>() {
            @Override
            public int compare(UserProfile o1, UserProfile o2) {
                int same1 = o1.sameAttribute(myUserProfile);
                int same2 = o2.sameAttribute(myUserProfile);
                if(same1>same2){
                    return 1;
                }else if (same1 == same2){
                    return 0;
                }else{
                    return -1;
                }
            }
        });
        return users.get(users.size()-1).userID;



    }
}
