package com.example.rockit.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

//https://www.youtube.com/watch?v=7Xc_5cduL-Y
public interface APIService {

    @Headers(
            {"Content-Type:application/json",
            "Authorization:key=AAAAvPBnfc8:APA91bHUa7RmPNDR0K_r41R-i0gBEnbSvd5wCvoKMNwbxTRENW0pfJ2rceGE9MRUk4IWbcS3pEfgmo0lAOWgjs_BLOblcZFHx4T81hVcIN8sApav-FER_B0F9zv188YFOW_BK7ekmIno"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
