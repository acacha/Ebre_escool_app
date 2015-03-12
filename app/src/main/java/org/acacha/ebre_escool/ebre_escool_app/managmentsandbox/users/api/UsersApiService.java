package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.api;

import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.study_submodules.pojos.StudySubmodules;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.pojos.Resultat;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.pojos.Users;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by dorian on 11/02/15.
 */
public interface UsersApiService {
//    @GET("/users")
//    void personsAsList(Callback<List<users>> cb);
//
//    @GET("/users")
//    void persons(Callback<Map<String, users>> cb);
//
//    @GET("/users/{id}")
//    void person(Callback<users> cb, @Path("id") String id);

    @Headers("X-API-KEY:"+ UsersApi.API_KEY)
    @GET("/Users")
    public void getUsers(Callback<List<Users>> callback);

    @Headers("X-API-KEY:"+UsersApi.API_KEY)
    @GET("/users/id/{id}")
    public void getUser(@Path("id") Integer id, Callback<Users> callback);

    @Headers("X-API-KEY:"+UsersApi.API_KEY)
    @POST("/user")
    public void insertUser(@Body Users user,Callback<Resultat> callback);

    //PUT method
    @Headers("X-API-KEY:"+UsersApi.API_KEY)
    @PUT("/user")
    public void updateUser(@Body Users user,Callback<Resultat> callback);

    @Headers("X-API-KEY:"+UsersApi.API_KEY)
    @DELETE("/user/id/{id}")
    public void deleteUser(@Path("id") int id,Callback<Resultat>callback);

    @Headers("X-API-KEY:"+UsersApi.API_KEY)
    @PUT("/markForDeletion")
    public void markForDeletion(@Body Users user,Callback<Resultat> callback);

}
