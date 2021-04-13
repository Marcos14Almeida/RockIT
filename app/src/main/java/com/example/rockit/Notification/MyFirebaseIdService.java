package com.example.rockit.Notification;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

//https://www.youtube.com/watch?v=7Xc_5cduL-Y 5:00
public class MyFirebaseIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh(){
        super.onTokenRefresh();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        if(firebaseUser!=null){
        updateToken(refreshToken);}
    }

    private void updateToken(String refreshToken){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Token token = new Token(refreshToken);
        assert firebaseUser != null;
        FirebaseDatabase.getInstance().getReference("Tokens").child(firebaseUser.getUid()).setValue(token);
    }

}
