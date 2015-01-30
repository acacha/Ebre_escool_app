package org.acacha.ebre_escool.ebre_escool_app.apis;

import org.acacha.ebre_escool.ebre_escool_app.pojos.School;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Path;
import retrofit.http.GET;

public interface EbreEscoolApiService {
    @GET("/schools")
    void schoolsAsList(Callback<List<School>> cb);

    @GET("/schools")
    void schools(Callback<Map<String, School>> cb);

    @GET("/school/{id}")
    void school(Callback<School> cb, @Path("id") String id);


}
