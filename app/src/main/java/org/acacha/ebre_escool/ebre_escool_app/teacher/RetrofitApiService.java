package org.acacha.ebre_escool.ebre_escool_app.teacher;

import org.acacha.ebre_escool.ebre_escool_app.teacher.teacher_pojos.Teacher;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by criminal on 2/02/15.
 */
public interface RetrofitApiService {


    /**CallBacks specifies de type of object we are going to get
     *and we use CallBack to make async request.
     * If we don't it will be a sync request and it will block
     * app until it finish
     **/
    //Get all teachers method
    @GET("/teachers")
    public void getTeachers(Callback<List<Teacher>> callback);

    //Get one teacher method

    @GET("/teacher/id/{id}")
    public void getTeacher(@Path("id") Integer id, Callback<Teacher> callback);



}
