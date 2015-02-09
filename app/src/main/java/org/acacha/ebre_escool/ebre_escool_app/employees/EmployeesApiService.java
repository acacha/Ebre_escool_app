package org.acacha.ebre_escool.ebre_escool_app.apis;

import org.acacha.ebre_escool.ebre_escool_app.pojos.Employees;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Path;
import retrofit.http.GET;

public interface EmployeesApiService {
    @GET("/employees")
    void employeesAsList(Callback<List<Employee>> cb);

    @GET("/employees")
    void employees(Callback<Map<String, Employee>> cb);

    @GET("/employee/{id}")
    void employee(Callback<Employee> cb, @Path("id") String id);


}
