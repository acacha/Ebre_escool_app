package org.acacha.ebre_escool.ebre_escool_app.person.api;

import org.acacha.ebre_escool.ebre_escool_app.pojos.School;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface PersonApiService {
    @GET("/schools")
    void schoolsAsList(Callback<List<School>> cb);

    @GET("/schools")
    void schools(Callback<Map<String, School>> cb);

    @GET("/school/{id}")
    void school(Callback<School> cb, @Path("id") String id);


}
