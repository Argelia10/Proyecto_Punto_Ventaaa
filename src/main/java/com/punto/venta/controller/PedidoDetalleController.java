package com.punto.venta.controller;

import com.punto.venta.dto.PedidoDetalleDTO;
import com.punto.venta.entity.PedidoDetalle;
import com.punto.venta.service.PedidoDetalleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido-detalles")
public class PedidoDetalleController {

    private final PedidoDetalleService pedidoDetalleService;

    public PedidoDetalleController(PedidoDetalleService pedidoDetalleService) {
        this.pedidoDetalleService = pedidoDetalleService;
    }

    @GetMapping
    public List<PedidoDetalle> getAllPedidoDetalles() {
        return pedidoDetalleService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDetalle createPedidoDetalle(
            @RequestBody PedidoDetalleDTO pedidoDetalleDTO) {
        return pedidoDetalleService.save(pedidoDetalleDTO);
    }
}