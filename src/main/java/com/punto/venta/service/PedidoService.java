package com.punto.venta.service;

import com.punto.venta.dto.PedidoDTO;
import com.punto.venta.entity.Cliente;
import com.punto.venta.entity.Pedido;
import com.punto.venta.repository.ClienteRepository;
import com.punto.venta.repository.PedidoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository) {

        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    // GET
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    // --- MÉTODOS ACTUALIZADOS ---

    public List<PedidoDTO> mostrarActivos() {
        return pedidoRepository.findByEstadoTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> mostrarActivosFiltro(Boolean estadoPedido) {
        return pedidoRepository.findByEstadoTrueAndEstadoPedido(estadoPedido)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> mostrarActivosFiltroTop(Boolean estadoPedido) {
        return pedidoRepository.findTop2ByEstadoTrueAndEstadoPedido(estadoPedido)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ------------------------

    // POST
    public Pedido save(PedidoDTO dto) {

        Pedido pedido = new Pedido();

        pedido.setEstado(dto.getEstado());
        pedido.setFechaPedido(dto.getFechaPedido());
        pedido.setEstadoPedido(dto.getEstadoPedido());
        pedido.setTotal(dto.getTotal());

        Cliente cliente = clienteRepository
                .findById(dto.getIdCliente())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente no encontrado"
                ));

        pedido.setIdCliente(cliente);

        return pedidoRepository.save(pedido);
    }

    // PUT - ACTUALIZAR
    public Pedido actualizar(
            Integer idPedido,
            PedidoDTO dto) {

        Pedido pedidoExistente = pedidoRepository
                .findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pedido no encontrado"
                ));

        if (dto.getEstado() != null) {
            pedidoExistente.setEstado(dto.getEstado());
        }

        if (dto.getFechaPedido() != null) {
            pedidoExistente.setFechaPedido(dto.getFechaPedido());
        }

        if (dto.getEstadoPedido() != null) {
            pedidoExistente.setEstadoPedido(dto.getEstadoPedido());
        }

        if (dto.getTotal() != null) {
            pedidoExistente.setTotal(dto.getTotal());
        }

        if (dto.getIdCliente() != null) {

            Cliente cliente = clienteRepository
                    .findById(dto.getIdCliente())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Cliente no encontrado"
                    ));

            pedidoExistente.setIdCliente(cliente);
        }

        return pedidoRepository.save(pedidoExistente);
    }

    // DELETE
    public void eliminar(Integer idPedido) {

        if (!pedidoRepository.existsById(idPedido)) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pedido no encontrado"
            );
        }

        pedidoRepository.deleteById(idPedido);
    }

    // ANULAR
    public Pedido anular(Integer idPedido) {

        Pedido pedidoExistente = pedidoRepository
                .findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pedido no encontrado"
                ));

        pedidoExistente.setEstado(false);

        return pedidoRepository.save(pedidoExistente);
    }

    // CONVERTIDOR DTO
    private PedidoDTO convertToDTO(Pedido p) {
        PedidoDTO dto = new PedidoDTO();
        dto.setIdPedido(p.getIdPedido());
        dto.setFechaPedido(p.getFechaPedido());
        dto.setEstadoPedido(p.getEstadoPedido());
        dto.setTotal(p.getTotal());
        dto.setEstado(p.getEstado());
        if (p.getIdCliente() != null) {
            dto.setIdCliente(p.getIdCliente().getIdCliente());
        }
        return dto;
    }
}