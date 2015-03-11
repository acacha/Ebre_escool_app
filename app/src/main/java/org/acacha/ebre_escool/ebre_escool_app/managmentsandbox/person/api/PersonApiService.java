package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.person.api;

import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.person.pojos.Person;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface PersonApiService {
    @GET("/persons")
    void personsAsList(Callback<List<Person>> cb);

    @GET("/persons")
    void persons(Callback<Map<String, Person>> cb);

    @GET("/persons/person/id/{id}")
    void person(Callback<Person> cb, @Path("id") String id);

    @PUT("/person")
    public void addPerson(@Body Person person,Callback<Person> cb);

    @PUT("/markedForDeletion")
    void markedForDeletion(@Body Person person,Callback<Person> cb);
}
