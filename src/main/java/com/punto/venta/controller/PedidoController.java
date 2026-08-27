package com.punto.venta.controller;

import com.punto.venta.dto.MessageResponse;
import com.punto.venta.dto.PedidoDTO;
import com.punto.venta.entity.Pedido;
import com.punto.venta.service.PedidoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // GET
    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.findAll();
    }

    // POST
    @PostMapping
    public ResponseEntity<MessageResponse> crearPedido(
            @RequestBody PedidoDTO pedidoDTO) {

        try {

            pedidoService.save(pedidoDTO);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new MessageResponse(
                            "Pedido creado con éxito"
                    ));

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al crear el pedido: "
                                    + e.getMessage()
                    ));
        }
    }

    // PUT - ACTUALIZAR
    @PutMapping("/{idPedido}")
    public ResponseEntity<MessageResponse> actualizarPedido(
            @PathVariable Integer idPedido,
            @RequestBody PedidoDTO pedidoDTO) {

        try {

            pedidoService.actualizar(
                    idPedido,
                    pedidoDTO
            );

            return ResponseEntity.ok(
                    new MessageResponse(
                            "Pedido actualizado con éxito"
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al actualizar el pedido: "
                                    + e.getMessage()
                    ));
        }
    }

    // DELETE
    @DeleteMapping("/{idPedido}")
    public ResponseEntity<MessageResponse> eliminarPedido(
            @PathVariable Integer idPedido) {

        try {

            pedidoService.eliminar(idPedido);

            return ResponseEntity.ok(
                    new MessageResponse(
                            "Pedido eliminado con éxito"
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al eliminar el pedido: "
                                    + e.getMessage()
                    ));
        }
    }

    // ANULAR
    @PutMapping("/anular/{idPedido}")
    public ResponseEntity<MessageResponse> anularPedido(
            @PathVariable Integer idPedido) {

        try {

            pedidoService.anular(idPedido);

            return ResponseEntity.ok(
                    new MessageResponse(
                            "Pedido anulado con éxito"
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al anular el pedido: "
                                    + e.getMessage()
                    ));
        }
    }
}