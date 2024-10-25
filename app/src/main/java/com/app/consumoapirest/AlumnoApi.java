package com.app.consumoapirest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlumnoApi {

    @GET("alumnos/{id}")
    Call<Alumno> getAlumno(@Path("id") Long id);

    @POST("alumnos")
    Call<Alumno> addAlumno(@Body Alumno alumno);

    @PUT("alumnos/{id}")
    Call<Alumno> updateAlumno(@Path("id") Long id, @Body Alumno alumno);

    @DELETE("alumnos/{id}")
    Call<Void> deleteAlumno(@Path("id") Long id);
}
