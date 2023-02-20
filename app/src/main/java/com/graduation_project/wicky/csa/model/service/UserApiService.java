package com.graduation_project.wicky.csa.model.service;


import com.graduation_project.wicky.csa.model.entity.Entity;
import com.graduation_project.wicky.csa.model.entity.ModelUser;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;


public interface UserApiService {
    @GET("api/logout")
    Observable<BaseResponse<Entity<ModelUser>>> logout();

    @GET("api/user")
    Observable<BaseResponse<Entity<ModelUser>>> getUser(@Query("idList") int id);


    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("api/login")
    Observable<BaseResponse<ModelUser>> login(@Body RequestBody route);


    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("api/root/login")
    Observable<BaseResponse<ModelUser>> loginRoot(@Body RequestBody route);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @PUT("api/user")
    Observable<BaseResponse<ModelUser>> register(@Body RequestBody route);
}
