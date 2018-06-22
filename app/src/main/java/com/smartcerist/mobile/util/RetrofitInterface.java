package com.smartcerist.mobile.util;

import com.smartcerist.mobile.model.Home;
import com.smartcerist.mobile.model.Notification;
import com.smartcerist.mobile.model.Response;
import com.smartcerist.mobile.model.Room;
import com.smartcerist.mobile.model.Rule;
import com.smartcerist.mobile.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("signin")
    Observable<Response> signIn(@Body User user);

    @POST("signup")
    Observable<Response> signUp(@Body User user);

    @GET("user/homes")
    Observable<List<Home>> getHomes();

    @GET("/users/user")
    Observable<User> getUserProfile();

    @GET("/user/notifications/all")
    Observable<List<Notification>> getNotifications();

    @GET("/user/notifications")
    Observable<List<Notification>> getNotificationsNotSeen();

    @PUT("/api{path}") // path example: /lights/led3
    Observable<String> toggleObjectState(@Path("path") String path, @Body String value);

    @GET("/api{path}")
    Observable<String> getObjectMeasure(@Path("path") String path);

    @PUT("/homes/{homeId}/rules/{ruleId}")
    Observable<List<Rule>> updateRule(@Path("homeId") String homeId, @Path("ruleId") String ruleId, @Body Rule rule);
}
