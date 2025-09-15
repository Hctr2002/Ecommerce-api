package com.ecommerce_api.controller;

import com.ecommerce_api.assemblers.CarritoItemModelAssembler;
import com.ecommerce_api.assemblers.CarritoModelAssembler;
import com.ecommerce_api.model.Carrito;
import com.ecommerce_api.model.CarritoItem;
import com.ecommerce_api.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private CarritoModelAssembler assembler;

    @Autowired
    private CarritoItemModelAssembler itemAssembler;

    @PostMapping
    public EntityModel<Carrito> crearCarrito(@RequestBody Carrito carrito) {
        Carrito nuevo = carritoService.crearCarrito(carrito);
        return assembler.toModel(nuevo);
    }

    @GetMapping
    public CollectionModel<EntityModel<Carrito>> obtenerTodosLosCarritos() {
        List<EntityModel<Carrito>> carritos = carritoService.obtenerTodosLosCarritos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(carritos,
                linkTo(methodOn(CarritoController.class).obtenerTodosLosCarritos()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Carrito>> obtenerCarritoPorId(@PathVariable Long id) {
        return carritoService.obtenerCarritoPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{carritoId}/items")
    public CollectionModel<EntityModel<CarritoItem>> obtenerItemsDeCarrito(@PathVariable Long carritoId) {
        Carrito carrito = carritoService.obtenerCarritoPorId(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        // Convertimos los items a modelos HATEOAS
        List<EntityModel<CarritoItem>> items = carrito.getItems().stream()
                .map(itemAssembler::toModel)
                .toList();

        return CollectionModel.of(items,
                linkTo(methodOn(CarritoController.class).obtenerItemsDeCarrito(carritoId)).withSelfRel(),
                linkTo(methodOn(CarritoController.class).obtenerCarritoPorId(carritoId)).withRel("carrito")
        );
    }


    @PostMapping("/{carritoId}/items")
    public EntityModel<Carrito> agregarProducto(
            @PathVariable Long carritoId,
            @RequestParam Long productoId,
            @RequestParam int cantidad) {
        Carrito actualizado = carritoService.agregarProducto(carritoId, productoId, cantidad);
        return assembler.toModel(actualizado);
    }

    @PutMapping("/{id}")
    public EntityModel<Carrito> actualizarCarrito(@PathVariable Long id, @RequestBody Carrito carrito) {
        Carrito actualizado = carritoService.actualizarCarrito(id, carrito);
        return assembler.toModel(actualizado);
    }

    @DeleteMapping("/{carritoId}/items/{itemId}")
    public EntityModel<Carrito> eliminarItem(@PathVariable Long carritoId, @PathVariable Long itemId) {
        Carrito actualizado = carritoService.eliminarItem(carritoId, itemId);
        return assembler.toModel(actualizado);
    }
}
