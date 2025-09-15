package com.ecommerce_api.assemblers;

import com.ecommerce_api.controller.CarritoController;
import com.ecommerce_api.model.Carrito;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CarritoModelAssembler implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Carrito> toModel(Carrito carrito) {
        return EntityModel.of(carrito,
                linkTo(methodOn(CarritoController.class).obtenerCarritoPorId(carrito.getId())).withSelfRel(),
                linkTo(methodOn(CarritoController.class).obtenerTodosLosCarritos()).withRel("carritos"),
                linkTo(methodOn(CarritoController.class).actualizarCarrito(carrito.getId(), carrito)).withRel("actualizar").withType("PUT"),
                linkTo(methodOn(CarritoController.class).agregarProducto(carrito.getId(), null, 0)).withRel("agregarProducto").withType("POST")
        );
    }
}
