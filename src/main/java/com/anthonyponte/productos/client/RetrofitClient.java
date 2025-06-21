package com.anthonyponte.productos.client;

import com.anthonyponte.productos.service.ProductoService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://localhost:30095";
    private static Retrofit retrofit = null;

    public static ProductoService getProductoService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ProductoService.class);
    }
}
