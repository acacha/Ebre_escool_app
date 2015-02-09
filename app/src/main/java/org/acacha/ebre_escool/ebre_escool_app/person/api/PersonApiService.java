package org.acacha.ebre_escool.ebre_escool_app.person.api;

import org.acacha.ebre_escool.ebre_escool_app.person.pojos.person;

import java.util.List;
import java.util.Map;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface PersonApiService {
    @GET("/persons")
    void personsAsList(Callback<List<person>> cb);

    @GET("/persons")
    void persons(Callback<Map<String, person>> cb);

    @GET("/person/{id}")
    void person(Callback<person> cb, @Path("id") String id);


}
