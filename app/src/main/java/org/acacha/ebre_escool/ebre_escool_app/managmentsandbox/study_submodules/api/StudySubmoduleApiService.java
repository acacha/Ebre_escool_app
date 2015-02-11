package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.study_submodules.api;

import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.study_submodules.pojos.StudySubmodules;

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
    void personsAsList(Callback<List<StudySubmodules>> cb);

    @GET("/study_submodules")
    void persons(Callback<Map<String, StudySubmodules>> cb);

    @GET("/study_submodule/{id}")
    void person(Callback<StudySubmodules> cb, @Path("id") String id);
}
