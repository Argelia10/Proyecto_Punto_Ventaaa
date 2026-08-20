package com.punto.venta.service;

import com.punto.venta.dto.PedidoDTO;
import com.punto.venta.entity.Cliente;
import com.punto.venta.entity.Pedido;
import com.punto.venta.repository.ClienteRepository;
import com.punto.venta.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido save(PedidoDTO dto) {

        Pedido pedido = new Pedido();

        pedido.setEstado(dto.getEstado());
        pedido.setFechaPedido(dto.getFechaPedido());
        pedido.setEstadoPedido(dto.getEstadoPedido());
        pedido.setTotal(dto.getTotal());

        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        pedido.setIdCliente(cliente);

        return pedidoRepository.save(pedido);
    }
}