package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.punto.venta.dto.PedidoDetalleDTO;
import com.punto.venta.entity.Pedido;
import com.punto.venta.entity.PedidoDetalle;
import com.punto.venta.entity.Producto;
import com.punto.venta.repository.PedidoDetalleRepository;

@Service
public class PedidoDetalleService {

    private final PedidoDetalleRepository pedidoDetalleRepository;

    public PedidoDetalleService(PedidoDetalleRepository pedidoDetalleRepository) {
        this.pedidoDetalleRepository = pedidoDetalleRepository;
    }

    public List<PedidoDetalleDTO> listarTodos() {
        return pedidoDetalleRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // --- MÉTODOS DE BÚSQUEDA ACTIVOS ---

    public List<PedidoDetalleDTO> mostrarActivos() {
        return pedidoDetalleRepository.findByEstadoTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDetalleDTO> mostrarActivosFiltro(String nombreProducto) {
        return pedidoDetalleRepository.findByEstadoTrueAndIdProductoNombreContainingIgnoreCase(nombreProducto)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDetalleDTO> mostrarActivosFiltroTop(String nombreProducto) {
        return pedidoDetalleRepository.findTop2ByEstadoTrueAndIdProductoNombreContainingIgnoreCase(nombreProducto)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // -----------------------------------

    public PedidoDetalleDTO crear(PedidoDetalleDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(dto.getIdPedido());
        
        Producto producto = new Producto();
        producto.setIdProducto(dto.getIdProducto());

        boolean duplicado = pedidoDetalleRepository.existsByIdPedidoAndIdProducto(pedido, producto);
        if (duplicado) {
            throw new RuntimeException("El detalle de pedido ya existe");
        }
        
        PedidoDetalle entity = convertToEntity(dto);
        entity.setEstado(true);

        return convertToDTO(pedidoDetalleRepository.save(entity));
    }

    public PedidoDetalleDTO actualizar(Integer idPedidoDetalle, PedidoDetalleDTO dto) {
        PedidoDetalle detalleExistente = pedidoDetalleRepository.findById(idPedidoDetalle)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Detalle de pedido no encontrado"
                ));

        if (dto.getCantidad() != null) detalleExistente.setCantidad(dto.getCantidad());
        if (dto.getPrecioUnitario() != null) detalleExistente.setPrecioUnitario(dto.getPrecioUnitario());
        if (dto.getSubtotal() != null) detalleExistente.setSubtotal(dto.getSubtotal());
        if (dto.getEstado() != null) detalleExistente.setEstado(dto.getEstado());

        if (dto.getIdPedido() != null) {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(dto.getIdPedido());
            detalleExistente.setIdPedido(pedido);
        }

        if (dto.getIdProducto() != null) {
            Producto producto = new Producto();
            producto.setIdProducto(dto.getIdProducto());
            detalleExistente.setIdProducto(producto);
        }

        return convertToDTO(pedidoDetalleRepository.save(detalleExistente));
    }

    public void eliminar(Integer idPedidoDetalle) {
        if (!pedidoDetalleRepository.existsById(idPedidoDetalle)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Detalle de pedido no encontrado");
        }
        pedidoDetalleRepository.deleteById(idPedidoDetalle);
    }

    public PedidoDetalleDTO anular(Integer idPedidoDetalle) {
        PedidoDetalle detalleExistente = pedidoDetalleRepository.findById(idPedidoDetalle)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Detalle de pedido no encontrado"
                ));
        detalleExistente.setEstado(false);
        return convertToDTO(pedidoDetalleRepository.save(detalleExistente));
    }

    // CONVERSORES

    private PedidoDetalleDTO convertToDTO(PedidoDetalle c) {
        PedidoDetalleDTO dto = new PedidoDetalleDTO();
        dto.setIdPedidoDetalle(c.getIdPedidoDetalle());
        
        if (c.getIdPedido() != null) {
            dto.setIdPedido(c.getIdPedido().getIdPedido());
        }
        if (c.getIdProducto() != null) {
            dto.setIdProducto(c.getIdProducto().getIdProducto());
        }
        
        dto.setCantidad(c.getCantidad());
        dto.setPrecioUnitario(c.getPrecioUnitario());
        dto.setSubtotal(c.getSubtotal());
        dto.setEstado(c.getEstado());
        return dto;
    }

    private PedidoDetalle convertToEntity(PedidoDetalleDTO c) {
        PedidoDetalle dto = new PedidoDetalle();
        dto.setIdPedidoDetalle(c.getIdPedidoDetalle());
        
        if (c.getIdPedido() != null) {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(c.getIdPedido());
            dto.setIdPedido(pedido);
        }
        
        if (c.getIdProducto() != null) {
            Producto producto = new Producto();
            producto.setIdProducto(c.getIdProducto());
            dto.setIdProducto(producto);
        }
        
        dto.setCantidad(c.getCantidad());
        dto.setPrecioUnitario(c.getPrecioUnitario());
        dto.setSubtotal(c.getSubtotal());
        dto.setEstado(c.getEstado());
        return dto;
    }
}