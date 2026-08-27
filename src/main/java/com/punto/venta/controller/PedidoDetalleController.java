package com.punto.venta.controller;

import com.punto.venta.dto.MessageResponse;
import com.punto.venta.dto.PedidoDetalleDTO;
import com.punto.venta.entity.PedidoDetalle;
import com.punto.venta.service.PedidoDetalleService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido-detalles")
public class PedidoDetalleController {

    private final PedidoDetalleService pedidoDetalleService;

    public PedidoDetalleController(
            PedidoDetalleService pedidoDetalleService) {

        this.pedidoDetalleService = pedidoDetalleService;
    }

    // GET
    @GetMapping
    public List<PedidoDetalle> getAllPedidoDetalles() {
        return pedidoDetalleService.findAll();
    }

    // POST
    @PostMapping
    public ResponseEntity<MessageResponse> crearPedidoDetalle(
            @RequestBody PedidoDetalleDTO pedidoDetalleDTO) {

        try {

            pedidoDetalleService.save(pedidoDetalleDTO);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new MessageResponse(
                            "Detalle de pedido creado con éxito"
                    ));

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al crear el detalle de pedido: "
                                    + e.getMessage()
                    ));
        }
    }

    // PUT - ACTUALIZAR
    @PutMapping("/{idPedidoDetalle}")
    public ResponseEntity<MessageResponse> actualizarPedidoDetalle(
            @PathVariable Integer idPedidoDetalle,
            @RequestBody PedidoDetalleDTO pedidoDetalleDTO) {

        try {

            pedidoDetalleService.actualizar(
                    idPedidoDetalle,
                    pedidoDetalleDTO
            );

            return ResponseEntity.ok(
                    new MessageResponse(
                            "Detalle de pedido actualizado con éxito"
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al actualizar el detalle de pedido: "
                                    + e.getMessage()
                    ));
        }
    }

    // DELETE
    @DeleteMapping("/{idPedidoDetalle}")
    public ResponseEntity<MessageResponse> eliminarPedidoDetalle(
            @PathVariable Integer idPedidoDetalle) {

        try {

            pedidoDetalleService.eliminar(idPedidoDetalle);

            return ResponseEntity.ok(
                    new MessageResponse(
                            "Detalle de pedido eliminado con éxito"
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al eliminar el detalle de pedido: "
                                    + e.getMessage()
                    ));
        }
    }
    // ANULAR
    @PutMapping("/anular/{idPedidoDetalle}")
    public ResponseEntity<MessageResponse> anularPedidoDetalle(
        @PathVariable Integer idPedidoDetalle) {

    try {

        pedidoDetalleService.anular(idPedidoDetalle);

        return ResponseEntity.ok(
                new MessageResponse(
                        "Detalle de pedido anulado con éxito"
                )
        );

    } catch (Exception e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse(
                        "Error al anular el detalle de pedido: "
                                + e.getMessage()
                ));
    }
}
}