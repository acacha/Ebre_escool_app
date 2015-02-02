package org.acacha.ebre_escool.ebre_escool_app;

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
}
