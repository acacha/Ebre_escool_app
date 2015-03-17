package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.study_submodules.api;

import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.study_submodules.pojos.Resultat;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.study_submodules.pojos.StudySubmodules;

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
 * Created by alex on 09/02/15.
 */
public interface StudySubmoduleApiService {

    /**CallBacks specifies de type of object we are going to get
     *and we use CallBack to make async request.
     * If we don't it will be a sync request and it will block
     * app until it finish
     **/

    //Get all Study submodules method
    @Headers("X-API-KEY:"+ StudySubmoduleApi.API_KEY)
    @GET("/studysubmodules")
    public void getStudySubmodules(Callback<List<StudySubmodules>> callback);

    //Get one study submodule method
    @Headers("X-API-KEY:"+StudySubmoduleApi.API_KEY)
    @GET("/StudySubmodule/id/{id}")
    public void getStudySubmodule(@Path("id") Integer id, Callback<StudySubmodules> callback);

    //Post Study Submodules method(Update one StudySubmodule)
    //@FormUrlEncoded
    @Headers("X-API-KEY:"+StudySubmoduleApi.API_KEY)
    @POST("/studysubmodule")
    public void insertStudySubmodule(@Body StudySubmodules studysubmodule,Callback<Resultat> callback);

    //PUT method
    @Headers("X-API-KEY:"+StudySubmoduleApi.API_KEY)
    @PUT("/studysubmodule")
    public void updateStudySubmodule(@Body StudySubmodules studysubmodule,Callback<Resultat> callback);

    //DELETE Study Submodule
    @Headers("X-API-KEY:"+StudySubmoduleApi.API_KEY)
    @DELETE("/studysubmodule/id/{id}")
    public void deleteStudySubmodule(@Path("id") int id,Callback<Resultat>callback);

    //Mark for deletion
    @Headers("X-API-KEY:"+StudySubmoduleApi.API_KEY)
    @PUT("/markForDeletion")
    public void markForDeletion(@Body StudySubmodules studysubmodule,Callback<Resultat> callback);

}