package com.smartcerist.mobile.util;

import com.smartcerist.mobile.model.Home;
import com.smartcerist.mobile.model.Response;
import com.smartcerist.mobile.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("signin")
    Observable<Response> signIn(@Body User user);

    @POST("signup")
    Observable<Response> signUp(@Body User user);

    @GET("users/{email}")
    Observable getProfile(@Path("email") String email);

    @GET("user/homes")
    Observable<List<Home>> getHomes();

}
