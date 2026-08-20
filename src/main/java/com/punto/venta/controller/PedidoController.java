package com.punto.venta.controller;

import com.punto.venta.dto.PedidoDTO;
import com.punto.venta.entity.Pedido;
import com.punto.venta.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido createPedido(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.save(pedidoDTO);
    }
}