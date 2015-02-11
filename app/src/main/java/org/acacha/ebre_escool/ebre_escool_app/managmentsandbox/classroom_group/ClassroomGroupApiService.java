package org.acacha.ebre_escool.ebre_escool_app.apis;

import org.acacha.ebre_escool.ebre_escool_app.pojos.School;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Path;
import retrofit.http.GET;

public interface ClassroomGroupApiService {
    @GET("/classroomgroups")
    void classroomgroupsAsList(Callback<List<School>> cb);

    @GET("/classroomgroups")
    void classroomgroups(Callback<Map<String, School>> cb);

    @GET("/classroomgroup/{id}")
    void classroomgroup(Callback<School> cb, @Path("id") String id);


}
