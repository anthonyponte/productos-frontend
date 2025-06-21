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
    @GET("/api/v1/productos")
    Call<List<Producto>> listarProductos();

    @GET("/api/v1/productos/{id}")
    Call<Producto> obtenerProductoPorId(@Path("id") Long id);

    @POST("/api/v1/productos")
    Call<Producto> crearProducto(@Body Producto producto);

    @PUT("/api/v1/productos/{id}")
    Call<Producto> actualizarProducto(@Path("id") Long id, @Body Producto producto);

    @DELETE("/api/v1/productos/{id}")
    Call<Void> eliminarProductoPorId(@Path("id") Long id);
}
