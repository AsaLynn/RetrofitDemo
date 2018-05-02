package com.example.think.retrofitdemo;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 创建接口，用于存放网络请求的方法。
 */

public interface NetService {

    @GET("data/sk/101010100.html")
    Call<Weather> getWeather();

    @GET("article/list/{type}?")
    Call<QiushiModel> getArticles(@Path("type") String type, @Query("page") int page);

    @GET("query")
    Call<QueryInfo> getQueryInfo(@QueryMap Map<String, String> map);

    @GET
    Call<Weather> getWeather(@Url String url);

    @GET("/restapi/loading/getStartBanner")
    Call<ResponseBody> getStartBannerResponseBody();

    @FormUrlEncoded
    @POST("jp/avlist/202861101/1/")
    Call<ResponseBody> getJpByPost(@Field("callback") String callback);

    @FormUrlEncoded
    @POST("search-ajaxsearch-searchall")
    Call<ResponseBody> getSearchByPost(@FieldMap Map<String, String> map);

    @Multipart
    @POST("MyUploadServer/servlet/UpLoadFileServlet")
    Call<ResponseBody> postUpLoadFile(@Part() MultipartBody.Part requestBody);

    @POST("MyUploadServer/servlet/MyUploadServlet")
    Call<ResponseBody> uploadFilesByody(@Body MultipartBody multipartBody);

    @Multipart
    @POST("MyUploadServer/servlet/MyUploadServlet")
    Call<ResponseBody> uploadFilesByPart(@Part()  List<MultipartBody.Part> parts);

    @Multipart
    @POST("MyUploadServer/servlet/MyUploadServlet")
    Call<ResponseBody> uploadFilesByPartMap(@PartMap()  Map<String, RequestBody> map);

    @Streaming
    @GET
    Call<ResponseBody> download(@Url String fileUrl);

}
