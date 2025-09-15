package com.ecommerce_api.assemblers;

import com.ecommerce_api.controller.CarritoController;
import com.ecommerce_api.model.Carrito;
import com.ecommerce_api.model.CarritoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CarritoModelAssembler implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>> {

    @Autowired
    private CarritoItemModelAssembler itemAssembler;

    @SuppressWarnings("null")
    @Override
    public EntityModel<Carrito> toModel(Carrito carrito) {
        // Convertir items a EntityModel<CarritoItem>
        List<EntityModel<CarritoItem>> itemsConLinks = carrito.getItems().stream()
                .map(itemAssembler::toModel)
                .collect(Collectors.toList());

        // Empaquetar carrito con links
        EntityModel<Carrito> carritoModel = EntityModel.of(carrito,
                linkTo(methodOn(CarritoController.class).obtenerCarritoPorId(carrito.getId())).withSelfRel(),
                linkTo(methodOn(CarritoController.class).obtenerTodosLosCarritos()).withRel("carritos"),
                linkTo(methodOn(CarritoController.class).actualizarCarrito(carrito.getId(), carrito)).withRel("actualizar").withType("PUT"),
                linkTo(methodOn(CarritoController.class).agregarProducto(carrito.getId(), null, 0)).withRel("agregarProducto").withType("POST")
        );

        // Agregar los items como parte del modelo
        carritoModel.add(itemsConLinks.stream()
                .map(EntityModel::getLinks)
                .flatMap(l -> l.stream())
                .collect(Collectors.toList()));

        return carritoModel;
    }
}
