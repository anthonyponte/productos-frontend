package com.anthonyponte.productos.client;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.anthonyponte.productos.model.Producto;

@Service
public class ProductoClient {
    private final WebClient client;

    public ProductoClient(WebClient.Builder webClientBuilder) {
        this.client = webClientBuilder.baseUrl("http://msproductos.anthonyponte.com/api/productos").build();
    }

    public Producto create(Producto producto) {
        return client.post()
                .uri("")
                .bodyValue(producto)
                .retrieve()
                .bodyToMono(Producto.class)
                .block();
    }

    public List<Producto> readAll() {
        return client.get()
                .uri("/")
                .retrieve()
                .bodyToFlux(Producto.class)
                .collectList()
                .block();
    }

    public Producto readById(Long id) {
        return client.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Producto.class)
                .block();
    }

    public Producto update(Long id, Producto producto) {
        return client.put()
                .uri("/{id}", id)
                .bodyValue(producto)
                .retrieve()
                .bodyToMono(Producto.class)
                .block();
    }

    public void delete(Long id) {
        client.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
