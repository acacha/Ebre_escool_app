package org.acacha.ebre_escool.ebre_escool_app.incident.ApiIncident;

/**
 * Created by liviu on 11/02/15.
 */

import org.acacha.ebre_escool.ebre_escool_app.incident.Pojos.incident;
import java.util.List;
import java.util.Map;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;


public interface IncidentApiService {

    @GET("/incidents")
    void incidentsAsList(Callback<List<incident>> cb);
    @GET("/incidents")
    void incidents(Callback<Map<String, incident>> cb);
    @GET("/incidents/{id}")
    void incidents(Callback<incident> cb, @Path("id") String id);




}
