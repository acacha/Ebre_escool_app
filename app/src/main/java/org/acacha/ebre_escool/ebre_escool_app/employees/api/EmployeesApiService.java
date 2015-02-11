package org.acacha.ebre_escool.ebre_escool_app.employees.api;

import org.acacha.ebre_escool.ebre_escool_app.employees.pojos.employees;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface EmployeesApiService {
    @GET("/employees")
    void employeesAsList(Callback<List<employees>> cb);

    @GET("/employees")
    void employees(Callback<Map<String, employees>> cb);

    @GET("/employee/{id}")
    void employee(Callback<employees> cb, @Path("id") String id);
}
