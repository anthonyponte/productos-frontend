package com.anthonyponte.productos.service;

import java.util.List;

import com.anthonyponte.productos.model.Producto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductoService {
    @POST("/api/v1/productos")
    Call<Producto> create(@Body Producto producto);

    @GET("/api/v1/productos")
    Call<List<Producto>> readAll();

    @GET("/api/v1/productos/{id}")
    Call<Producto> read(@Path("id") Long id);

    @PUT("/api/v1/productos/{id}")
    Call<Producto> update(@Path("id") Long id, @Body Producto producto);

    @DELETE("/api/v1/productos/{id}")
    Call<Void> delete(@Path("id") Long id);
}
