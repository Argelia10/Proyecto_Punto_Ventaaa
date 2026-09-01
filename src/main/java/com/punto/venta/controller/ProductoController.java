package com.punto.venta.controller;

import com.punto.venta.dto.MessageResponse;
import com.punto.venta.dto.ProductoDTO;
import com.punto.venta.entity.Producto;
import com.punto.venta.service.ProductoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // GET
    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.findAll();
    }

    // --- NUEVOS ENDPOINTS AÑADIDOS ---

    @GetMapping("/mostrarActivos")
    public List<ProductoDTO> mostrarActivos() {
        return productoService.mostrarActivos();
    }

    @GetMapping("/mostrarActivosFiltro")
    public List<ProductoDTO> mostrarActivosFiltro(@RequestParam String nombre) {
        return productoService.mostrarActivosFiltro(nombre);
    }

    @GetMapping("/mostrarActivosFiltroTop")
    public List<ProductoDTO> mostrarActivosFiltroTop(@RequestParam String nombre) {
        return productoService.mostrarActivosFiltroTop(nombre);
    }

    // ---------------------------------

    // POST
    @PostMapping
    public ResponseEntity<MessageResponse> crearProducto(
            @RequestBody ProductoDTO productoDTO) {

        try {

            productoService.save(productoDTO);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new MessageResponse(
                            "Producto creado con éxito"
                    ));

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al crear el producto: "
                                    + e.getMessage()
                    ));
        }
    }

    // PUT - ACTUALIZAR
    @PutMapping("/{idProducto}")
    public ResponseEntity<MessageResponse> actualizarProducto(
            @PathVariable Integer idProducto,
            @RequestBody ProductoDTO productoDTO) {

        try {

            productoService.actualizar(
                    idProducto,
                    productoDTO
            );

            return ResponseEntity.ok(
                    new MessageResponse(
                            "Producto actualizado con éxito"
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al actualizar el producto: "
                                    + e.getMessage()
                    ));
        }
    }

    // DELETE
    @DeleteMapping("/{idProducto}")
    public ResponseEntity<MessageResponse> eliminarProducto(
            @PathVariable Integer idProducto) {

        try {

            productoService.eliminar(idProducto);

            return ResponseEntity.ok(
                    new MessageResponse(
                            "Producto eliminado con éxito"
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al eliminar el producto: "
                                    + e.getMessage()
                    ));
        }
    }

    // ANULAR
    @PutMapping("/anular/{idProducto}")
    public ResponseEntity<MessageResponse> anularProducto(
            @PathVariable Integer idProducto) {

        try {

            productoService.anular(idProducto);

            return ResponseEntity.ok(
                    new MessageResponse(
                            "Producto anulado con éxito"
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al anular el producto: "
                                    + e.getMessage()
                    ));
        }
    }
}