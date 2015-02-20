package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.incident.api;

/**
 * Created by liviu on 11/02/15.
 */

import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.incident.pojos.studysubmodules;

import java.util.List;
import java.util.Map;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;


public interface IncidentApiService {

    @GET("/incidents")
    void incidentsAsList(Callback<List<studysubmodules>> cb);
    @GET("/incidents")
    void incidents(Callback<Map<String, studysubmodules>> cb);
    @GET("/incidents/{id}")
    void incidents(Callback<studysubmodules> cb, @Path("id") String id);

}
