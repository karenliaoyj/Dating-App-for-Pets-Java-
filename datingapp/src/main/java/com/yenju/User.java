public class User{
    String name;
    int id;
    int password; //to do : hash password
    String profileUUID; // multithread: 同時有數個人上傳圖片
    String text;
    //added:
    int age;
    String color;
    String size;

}

public class LikeAmount{
    int ID;
    boolean likeOrDislike;
    int userId1;
    int userId2;    
}

//to do : matching method

public class notification{ // one particular notification
    String text;
    int ID;
    int senterUserId;
    int receiverUserId;

}

public class message{
    int ID;
    int senterUserId;
    int receiverUserId;
    Sting message;

}

public class match{ // to do: match algorithm
    int ID;
    int userId1;
    int userId2;

}


