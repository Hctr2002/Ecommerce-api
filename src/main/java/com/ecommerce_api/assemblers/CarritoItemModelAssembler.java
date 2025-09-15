package com.ecommerce_api.assemblers;

import com.ecommerce_api.controller.CarritoController;
import com.ecommerce_api.model.CarritoItem;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CarritoItemModelAssembler implements RepresentationModelAssembler<CarritoItem, EntityModel<CarritoItem>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<CarritoItem> toModel(CarritoItem item) {
        return EntityModel.of(item,
                linkTo(methodOn(CarritoController.class).obtenerCarritoPorId(item.getCarrito().getId())).withRel("carrito"),
                linkTo(methodOn(CarritoController.class).eliminarItem(item.getCarrito().getId(), item.getId()))
                        .withRel("eliminar")
                        .withType("DELETE")
        );
    }
}
