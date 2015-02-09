package org.acacha.ebre_escool.ebre_escool_app.study_submodules;

import org.acacha.ebre_escool.ebre_escool_app.study_submodules.pojos.study_submodules;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by alex on 09/02/15.
 */
public class RetrofitApiService {
    /**CallBacks specifies de type of object we are going to get
     *and we use CallBack to make async request.
     * If we don't it will be a sync request and it will block
     * app until it finish
     **/

    //Get all study_submodules method
    @GET("/studysubmodules")
    public void getStudy_submodules(Callback<List<study_submodules>> callback){

    }

    //Get one study_submodules method
    @GET("/studysubmodule/id/{id}")
    public void getStudy_submodule(@Path("id") Integer id, Callback<study_submodules> callback) {

    }
}