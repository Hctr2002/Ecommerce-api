package com.ecommerce_api.assemblers;

import com.ecommerce_api.controller.UsuarioController;
import com.ecommerce_api.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).obtenerTodosLosUsuarios()).withRel("usuarios"),
                linkTo(methodOn(UsuarioController.class).eliminarUsuario(usuario.getId())).withRel("eliminar").withType("DELETE"),
                linkTo(methodOn(UsuarioController.class).actualizarUsuario(usuario.getId(), usuario)).withRel("actualizar").withType("PUT")
        );
    }
}
