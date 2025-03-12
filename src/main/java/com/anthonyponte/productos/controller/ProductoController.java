package com.anthonyponte.productos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

  @RequestMapping("/nuevo")
  public String registrar(Model model) {
    model.addAttribute("producto", new Producto());
    return "producto";
  }

  @GetMapping("/editar/{id}")
  public String editar(@PathVariable("id") Long id, Model model) {
    Producto producto = client.readById(id);
    model.addAttribute("producto", producto);
    return "producto";
  }

  @PostMapping("/guardar")
  public String guardar(Producto producto, BindingResult result, RedirectAttributes attributes) {
    if (result.hasErrors()) {
      return "producto";
    }

System.out.println(producto);

    if (producto.getId() == null) {
      client.create(producto);
    } else {
      client.update(producto.getId(), producto);
    }

    attributes.addFlashAttribute("textAlertSuccess", "Se guardo el producto '" + producto.getId() + "'");

    return "redirect:/productos/";
  }

  @GetMapping("/eliminar/{id}")
  public String eliminar(@PathVariable("id") Long id, RedirectAttributes attr) {
    client.delete(id);
    attr.addFlashAttribute("textAlertSuccess", "Se elimino el producto " + id);
    return "redirect:/productos/";
  }
}
