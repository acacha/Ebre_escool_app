package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher.api;


import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher.pojos.Result;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher.pojos.Teacher;
import org.apache.http.NameValuePair;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.RestMethod;

/**
 * Created by criminal on 2/02/15.
 */
public interface TeacherApiService {


    /**CallBacks specifies de type of object we are going to get
     *and we use CallBack to make async request.
     * If we don't it will be a sync request and it will block
     * app until it finish
     **/
    //Get all teachers method
    @Headers("X-API-KEY:"+TeacherApi.API_KEY)
    @GET("/teachers")
    public void getTeachers(Callback<List<Teacher>> callback);

    //Get one teacher method
    @Headers("X-API-KEY:"+TeacherApi.API_KEY)
    @GET("/teacher/id/{id}")
    public void getTeacher(@Path("id") Integer id, Callback<Teacher> callback);

    //Post Teacher method(Update one teacher)
    //@FormUrlEncoded
    @Headers("X-API-KEY:"+TeacherApi.API_KEY)
    @POST("/teacher")
    public void insertTeacher(@Body Teacher teacher,Callback<Result> callback);

    //PUT method
    @Headers("X-API-KEY:"+TeacherApi.API_KEY)
    @PUT("/teacher")
    public void updateTeacher(@Body Teacher teacher,Callback<Result> callback);

    //DELETE teacher
    @Headers("X-API-KEY:"+TeacherApi.API_KEY)
    @DELETE("/teacher/id/{id}")
    public void deleteTeacher(@Path("id") int id,Callback<Result>callback);

    //Mark for deletion
    @Headers("X-API-KEY:"+TeacherApi.API_KEY)
    @PUT("/markForDeletion")
    public void markForDeletion(@Body Teacher teacher,Callback<Result> callback);






}
