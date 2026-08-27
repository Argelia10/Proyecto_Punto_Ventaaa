package com.punto.venta.service;

import com.punto.venta.dto.PedidoDetalleDTO;
import com.punto.venta.entity.Pedido;
import com.punto.venta.entity.PedidoDetalle;
import com.punto.venta.entity.Producto;
import com.punto.venta.repository.PedidoDetalleRepository;
import com.punto.venta.repository.PedidoRepository;
import com.punto.venta.repository.ProductoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PedidoDetalleService {

    private final PedidoDetalleRepository pedidoDetalleRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public PedidoDetalleService(
            PedidoDetalleRepository pedidoDetalleRepository,
            PedidoRepository pedidoRepository,
            ProductoRepository productoRepository) {

        this.pedidoDetalleRepository = pedidoDetalleRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    // GET
    public List<PedidoDetalle> findAll() {
        return pedidoDetalleRepository.findAll();
    }

    // POST
    public PedidoDetalle save(PedidoDetalleDTO dto) {

        PedidoDetalle detalle = new PedidoDetalle();

        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setSubtotal(dto.getSubtotal());
        detalle.setEstado(true);

        Pedido pedido = pedidoRepository
                .findById(dto.getIdPedido())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pedido no encontrado"
                ));

        Producto producto = productoRepository
                .findById(dto.getIdProducto())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto no encontrado"
                ));

        detalle.setIdPedido(pedido);
        detalle.setIdProducto(producto);

        return pedidoDetalleRepository.save(detalle);
    }

    // PUT - ACTUALIZAR
    public PedidoDetalle actualizar(
            Integer idPedidoDetalle,
            PedidoDetalleDTO dto) {

        PedidoDetalle detalleExistente = pedidoDetalleRepository
                .findById(idPedidoDetalle)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Detalle de pedido no encontrado"
                ));

        if (dto.getCantidad() != null) {
            detalleExistente.setCantidad(dto.getCantidad());
        }

        if (dto.getPrecioUnitario() != null) {
            detalleExistente.setPrecioUnitario(dto.getPrecioUnitario());
        }

        if (dto.getSubtotal() != null) {
            detalleExistente.setSubtotal(dto.getSubtotal());
        }

        if (dto.getEstado() != null) {
            detalleExistente.setEstado(dto.getEstado());
        }

        if (dto.getIdPedido() != null) {

            Pedido pedido = pedidoRepository
                    .findById(dto.getIdPedido())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Pedido no encontrado"
                    ));

            detalleExistente.setIdPedido(pedido);
        }

        if (dto.getIdProducto() != null) {

            Producto producto = productoRepository
                    .findById(dto.getIdProducto())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Producto no encontrado"
                    ));

            detalleExistente.setIdProducto(producto);
        }

        return pedidoDetalleRepository.save(detalleExistente);
    }

    // DELETE
    public void eliminar(Integer idPedidoDetalle) {

        if (!pedidoDetalleRepository.existsById(idPedidoDetalle)) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Detalle de pedido no encontrado"
            );
        }

        pedidoDetalleRepository.deleteById(idPedidoDetalle);
    }

    // ANULAR
    public PedidoDetalle anular(Integer idPedidoDetalle) {

        PedidoDetalle detalleExistente = pedidoDetalleRepository
                .findById(idPedidoDetalle)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Detalle de pedido no encontrado"
                ));

        detalleExistente.setEstado(false);

        return pedidoDetalleRepository.save(detalleExistente);
    }
}