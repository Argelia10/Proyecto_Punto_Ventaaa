package com.punto.venta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.punto.venta.dto.MessageResponse;
import com.punto.venta.dto.PedidoDetalleDTO;
import com.punto.venta.service.PedidoDetalleService;

@RestController
@RequestMapping("/pedido-detalles")
@CrossOrigin(origins = "*")
public class PedidoDetalleController {

    private final PedidoDetalleService pedidoDetalleService;

    public PedidoDetalleController(PedidoDetalleService pedidoDetalleService) {
        this.pedidoDetalleService = pedidoDetalleService;
    }

    @GetMapping
    public List<PedidoDetalleDTO> listarTodos() {
        return pedidoDetalleService.listarTodos();
    }

    // --- ENDPOINTS DE BÚSQUEDA ---

    @GetMapping("/mostrarActivos")
    public List<PedidoDetalleDTO> mostrarActivos() {
        return pedidoDetalleService.mostrarActivos();
    }

    @GetMapping("/mostrarActivosFiltro")
    public List<PedidoDetalleDTO> mostrarActivosFiltro(@RequestParam String nombreProducto) {
        return pedidoDetalleService.mostrarActivosFiltro(nombreProducto);
    }

    @GetMapping("/mostrarActivosFiltroTop")
    public List<PedidoDetalleDTO> mostrarActivosFiltroTop(@RequestParam String nombreProducto) {
        return pedidoDetalleService.mostrarActivosFiltroTop(nombreProducto);
    }

    // -----------------------------

    @PostMapping
    public ResponseEntity<MessageResponse> crearDetalle(@RequestBody PedidoDetalleDTO pedidoDetalleDTO) {
        try {
            pedidoDetalleService.crear(pedidoDetalleDTO);
            return ResponseEntity.ok(new MessageResponse("Detalle de pedido creado con éxito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al crear el detalle de pedido: " + e.getMessage()));
        }
    }

    @PutMapping("/{idPedidoDetalle}")
    public ResponseEntity<MessageResponse> actualizarDetalle(
            @PathVariable Integer idPedidoDetalle,
            @RequestBody PedidoDetalleDTO pedidoDetalleDTO) {
        try {
            pedidoDetalleService.actualizar(idPedidoDetalle, pedidoDetalleDTO);
            return ResponseEntity.ok(new MessageResponse("Detalle de pedido actualizado con éxito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al actualizar el detalle de pedido: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{idPedidoDetalle}")
    public ResponseEntity<MessageResponse> eliminarDetalle(@PathVariable Integer idPedidoDetalle) {
        try {
            pedidoDetalleService.eliminar(idPedidoDetalle);
            return ResponseEntity.ok(new MessageResponse("Detalle de pedido eliminado con éxito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al eliminar el detalle de pedido: " + e.getMessage()));
        }
    }

    @PutMapping("/anular/{idPedidoDetalle}")
    public ResponseEntity<MessageResponse> anularDetalle(@PathVariable Integer idPedidoDetalle) {
        try {
            pedidoDetalleService.anular(idPedidoDetalle);
            return ResponseEntity.ok(new MessageResponse("Detalle de pedido anulado con éxito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al anular el detalle de pedido: " + e.getMessage()));
        }
    }
}