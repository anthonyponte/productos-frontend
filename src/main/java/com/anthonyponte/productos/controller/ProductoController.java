package com.anthonyponte.productos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anthonyponte.productos.client.ProductoClient;
import com.anthonyponte.productos.model.Producto;

@Controller
@RequestMapping("/productos")
public class ProductoController {
  private final ProductoClient client;

  public ProductoController(ProductoClient client) {
    this.client = client;
  }

  @GetMapping("/")
  public String consultar(Model model) {
    List<Producto> listProducto = client.readAll();
    model.addAttribute("listProducto", listProducto);
    return "productos";
  }

  @GetMapping("/editar/{id}")
  public String editar(@PathVariable("id") Long id, Model model) {
    Producto producto = client.readById(id);
    model.addAttribute("producto", producto);
    return "producto";
  }
}
