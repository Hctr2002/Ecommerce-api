package com.ecommerce_api.controller;

import com.ecommerce_api.model.Carrito;
import com.ecommerce_api.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @PostMapping
    public Carrito crearCarrito(@RequestBody Carrito carrito) {
        return carritoService.crearCarrito(carrito);
    }

    @GetMapping
    public List<Carrito> obtenerTodosLosCarritos() {
        return carritoService.obtenerTodosLosCarritos();
    }

    @GetMapping("/{id}")
    public Optional<Carrito> obtenerCarritoPorId(@PathVariable Long id) {
        return carritoService.obtenerCarritoPorId(id);
    }

    @PostMapping("/{carritoId}/items")
    public Carrito agregarProducto(
            @PathVariable Long carritoId,
            @RequestParam Long productoId,
            @RequestParam int cantidad) {
        return carritoService.agregarProducto(carritoId, productoId, cantidad);
    }

    @PutMapping("/{id}")
    public Carrito actualizarCarrito(@PathVariable Long id, @RequestBody Carrito carrito) {
        return carritoService.actualizarCarrito(id, carrito);
    }

    

    @DeleteMapping("/{carritoId}/items/{itemId}")
    public Carrito eliminarItem(@PathVariable Long carritoId, @PathVariable Long itemId) {
        return carritoService.eliminarItem(carritoId, itemId);
    }
}
