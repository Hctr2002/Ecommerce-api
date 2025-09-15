package com.ecommerce_api.assemblers;

import com.ecommerce_api.controller.ProductoController;
import com.ecommerce_api.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).obtenerProductoPorId(producto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).obtenerTodosLosProductos()).withRel("productos"),
                linkTo(methodOn(ProductoController.class).eliminarProducto(producto.getId())).withRel("eliminar").withType("DELETE")
        );
    }
}
