package com.ecommerce_api.service;

import com.ecommerce_api.model.Carrito;
import com.ecommerce_api.model.CarritoItem;
import com.ecommerce_api.model.Producto;
import com.ecommerce_api.repository.CarritoItemRepository;
import com.ecommerce_api.repository.CarritoRepository;
import com.ecommerce_api.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Carrito> obtenerTodosLosCarritos() {
        return carritoRepository.findAll();
    }

    public Carrito crearCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public Optional<Carrito> obtenerCarritoPorId(Long id) {
        return carritoRepository.findById(id);
    }

    public Carrito agregarProducto(Long carritoId, Long productoId, int cantidad) {
        Carrito carrito = carritoRepository.findById(carritoId)
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        Producto producto = productoRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        CarritoItem item = new CarritoItem();
        item.setCarrito(carrito);
        item.setProducto(producto);
        item.setCantidad(cantidad);
        item.setSubtotal(producto.getPrecio() * cantidad);

        carrito.getItems().add(item);
        carrito.setTotal(
            carrito.getItems().stream().mapToDouble(CarritoItem::getSubtotal).sum()
        );

        carritoItemRepository.save(item);
        return carritoRepository.save(carrito);
    }

    public Carrito actualizarCarrito(Long id, Carrito carritoActualizado) {
        Carrito carrito = carritoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        // Actualizar campos que te interese permitir cambiar
        carrito.setUsuario(carritoActualizado.getUsuario());

        // Si quieres permitir reemplazar todos los items
        if (carritoActualizado.getItems() != null) {
            carrito.getItems().clear();
            carrito.getItems().addAll(carritoActualizado.getItems());
        }

        // Recalcular total
        carrito.setTotal(
            carrito.getItems().stream()
                .mapToDouble(item -> item.getSubtotal() != null ? item.getSubtotal() : 0.0)
                .sum()
        );

        return carritoRepository.save(carrito);
    }


    public Carrito eliminarItem(Long carritoId, Long itemId) {
        Carrito carrito = carritoRepository.findById(carritoId)
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        CarritoItem item = carritoItemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        carrito.getItems().remove(item);
        carritoItemRepository.delete(item);

        carrito.setTotal(
            carrito.getItems().stream().mapToDouble(CarritoItem::getSubtotal).sum()
        );

        return carritoRepository.save(carrito);
    }
}
