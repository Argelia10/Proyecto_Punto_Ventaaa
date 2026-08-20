package com.punto.venta.service;

import com.punto.venta.dto.PedidoDetalleDTO;
import com.punto.venta.entity.Pedido;
import com.punto.venta.entity.PedidoDetalle;
import com.punto.venta.entity.Producto;
import com.punto.venta.repository.PedidoDetalleRepository;
import com.punto.venta.repository.PedidoRepository;
import com.punto.venta.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoDetalleService {

    private final PedidoDetalleRepository pedidoDetalleRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public PedidoDetalleService(PedidoDetalleRepository pedidoDetalleRepository,
                                PedidoRepository pedidoRepository,
                                ProductoRepository productoRepository) {
        this.pedidoDetalleRepository = pedidoDetalleRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    public List<PedidoDetalle> findAll() {
        return pedidoDetalleRepository.findAll();
    }

    public PedidoDetalle save(PedidoDetalleDTO dto) {

        PedidoDetalle detalle = new PedidoDetalle();

        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setSubtotal(dto.getSubtotal());

        Pedido pedido = pedidoRepository.findById(dto.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        Producto producto = productoRepository.findById(dto.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        detalle.setIdPedido(pedido);
        detalle.setIdProducto(producto);

        return pedidoDetalleRepository.save(detalle);
    }
}
