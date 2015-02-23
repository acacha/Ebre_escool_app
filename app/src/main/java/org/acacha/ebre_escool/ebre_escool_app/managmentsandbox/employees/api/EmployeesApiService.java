package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.employees.api;

import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.employees.pojos.Employees;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface EmployeesApiService {
    //ServiceAPI
    @GET("/employees")
    void employeesAsList(Callback<List<Employees>> cb);

    @GET("/employees")
    void employees(Callback<Map<String, Employees>> cb);

    @GET("/employee/{id}")
    void employee(Callback<Employees> cb, @Path("id") String id);
}
