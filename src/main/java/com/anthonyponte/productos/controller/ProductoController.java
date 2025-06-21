package com.anthonyponte.productos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.anthonyponte.productos.client.RetrofitClient;
import com.anthonyponte.productos.model.Producto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Controller
@RequestMapping("/productos")
public class ProductoController {

  @GetMapping
  public DeferredResult<String> listarProductos(Model model) {
    DeferredResult<String> view = new DeferredResult<>();

    Call<List<Producto>> call = RetrofitClient.getProductoService().listarProductos();
    call.enqueue(new Callback<List<Producto>>() {
      @Override
      public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
        if (response.isSuccessful()) {
          List<Producto> listProducto = response.body();
          model.addAttribute("listProducto", listProducto);
          view.setResult("productos");
        } else {
          view.setErrorResult("error/500");
        }
      }

      @Override
      public void onFailure(Call<List<Producto>> call, Throwable t) {
        view.setErrorResult("error/timeout");
      }
    });

    return view;
  }

  @RequestMapping("/nuevo")
  public String registrar(Model model) {
    model.addAttribute("producto", new Producto());
    return "producto";
  }

  @GetMapping("/editar/{id}")
  public DeferredResult<String> obtenerProductoPorId(@PathVariable("id") Long id, Model model) {
    DeferredResult<String> view = new DeferredResult<>();

    Call<Producto> call = RetrofitClient.getProductoService().obtenerProductoPorId(id);
    call.enqueue(new Callback<Producto>() {
      @Override
      public void onResponse(Call<Producto> call, Response<Producto> response) {
        if (response.isSuccessful()) {
          Producto producto = response.body();
          model.addAttribute("producto", producto);
          view.setResult("producto");
        } else {
          view.setErrorResult("error/500");
        }
      }

      @Override
      public void onFailure(Call<Producto> call, Throwable t) {
        view.setErrorResult("error/timeout");
      }
    });

    return view;
  }

  @PostMapping("/guardar")
  public DeferredResult<String> guardarProducto(Producto producto, BindingResult result, RedirectAttributes attr) {
    DeferredResult<String> view = new DeferredResult<>();

    if (result.hasErrors()) {
      view.setResult("producto");
      return view;
    }

    Callback<Producto> callback = new Callback<Producto>() {
      @Override
      public void onResponse(Call<Producto> call, Response<Producto> response) {
        if (response.isSuccessful()) {
          Producto p = response.body();
          String mensaje = producto.getId() == null
              ? "Se guardó el producto '" + p.getNombre() + "'"
              : "Se actualizó el producto '" + p.getNombre() + "'";
          attr.addFlashAttribute("textAlertSuccess", mensaje);
          view.setResult("redirect:/productos");
        } else {
          view.setErrorResult("error/500");
        }
      }

      @Override
      public void onFailure(Call<Producto> call, Throwable t) {
        view.setErrorResult("error/timeout");
      }
    };

    if (producto.getId() == null) {
      RetrofitClient.getProductoService().crearProducto(producto).enqueue(callback);
    } else {
      RetrofitClient.getProductoService().actualizarProducto(producto.getId(), producto).enqueue(callback);
    }

    return view;
  }

  @GetMapping("/eliminar/{id}")
  public DeferredResult<String> eliminarProductoPorId(@PathVariable("id") Long id, RedirectAttributes attr) {
    DeferredResult<String> view = new DeferredResult<>();

    Call<Void> call = RetrofitClient.getProductoService().eliminarProductoPorId(id);
    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
          attr.addFlashAttribute("textAlertSuccess", "Se elimino el producto " + id);
          view.setResult("redirect:/productos");
        } else {
          view.setErrorResult("error/500");
        }
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {
        view.setErrorResult("error/timeout");
      }
    });

    return view;
  }
}
