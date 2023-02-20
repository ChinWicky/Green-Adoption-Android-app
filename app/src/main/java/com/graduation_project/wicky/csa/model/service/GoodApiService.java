package com.graduation_project.wicky.csa.model.service;


import android.databinding.ObservableField;

import com.graduation_project.wicky.csa.model.entity.Entity;
import com.graduation_project.wicky.csa.model.entity.ModelGood;
import com.graduation_project.wicky.csa.model.entity.ModelGoodNoId;
import com.graduation_project.wicky.csa.model.entity.ModelUser;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface GoodApiService {

    @GET("api/product")
    Observable<BaseResponse<Entity<ModelGood>>> goodGet();

    @GET("api/product")
    Observable<BaseResponse<Entity<ModelGood>>> goodGet(@Query("pageSize") int pageSize,@Query("pageNum") int pageNumber);

    @GET("api/product")
    Observable<BaseResponse<Entity<ModelGood>>> goodGet(@Query("productIdList") int id);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @PUT("api/product")
    Observable<BaseResponse> addGood(@Body RequestBody route);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/product/{productId}")
    Observable<BaseResponse> updateGood(@Path("productId") int productId, @Body RequestBody route);


    @Multipart
    @POST("api/picture")
    Observable<BaseResponse<String>> uploadPhoto (@Part MultipartBody.Part part);


//    @Multipart
//    @POST("api/product")
//    Observable<BaseResponse<String>> uploadPhoto ( @Part("file") RequestBody image);


}
