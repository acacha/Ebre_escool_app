package org.acacha.ebre_escool.ebre_escool_app.study_submodules.api;

import org.acacha.ebre_escool.ebre_escool_app.study_submodules.pojos.study_submodules;
import java.util.List;
import java.util.Map;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by alex on 09/02/15.
 */
public interface StudySubmoduleApiService {
    @GET("/study_submodules")
    void personsAsList(Callback<List<study_submodules>> cb);

    @GET("/study_submodules")
    void persons(Callback<Map<String, study_submodules>> cb);

    @GET("/study_submodule/{id}")
    void person(Callback<study_submodules> cb, @Path("id") String id);
}
