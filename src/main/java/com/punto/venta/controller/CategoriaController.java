package com.punto.venta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.punto.venta.dto.CategoriaDTO;
import com.punto.venta.dto.MessageResponse;
import com.punto.venta.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // GET
    @GetMapping
    public List<CategoriaDTO> getAllCategorias() {
        return categoriaService.findAll();
    }

    // POST
    @PostMapping
    public ResponseEntity<MessageResponse> crearCategoria(
            @RequestBody CategoriaDTO categoriaDTO) {

        try {

            categoriaService.save(categoriaDTO);

            return ResponseEntity.ok(
                    new MessageResponse("Categoria creada con éxito")
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al crear la categoria: " + e.getMessage()
                    ));
        }
    }

    // PUT - ACTUALIZAR
    @PutMapping("/{idCategoria}")
    public ResponseEntity<MessageResponse> actualizarCategoria(
            @PathVariable Integer idCategoria,
            @RequestBody CategoriaDTO categoriaDTO) {

        try {

            categoriaService.modificarCategoria(
                    idCategoria,
                    categoriaDTO
            );

            return ResponseEntity.ok(
                    new MessageResponse(
                            "Categoria actualizada con éxito"
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al actualizar la categoria: "
                                    + e.getMessage()
                    ));
        }
    }

    // DELETE
    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<MessageResponse> eliminarCategoria(
            @PathVariable Integer idCategoria) {

        try {

            categoriaService.eliminarCategoria(idCategoria);

            return ResponseEntity.ok(
                    new MessageResponse(
                            "Categoria eliminada con éxito"
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al eliminar la categoria: "
                                    + e.getMessage()
                    ));
        }
    }

    // ANULAR
    @PutMapping("/anular/{idCategoria}")
    public ResponseEntity<MessageResponse> anularCategoria(
            @PathVariable Integer idCategoria) {

        try {

            categoriaService.anularCategoria(idCategoria);

            return ResponseEntity.ok(
                    new MessageResponse(
                            "Categoria anulada con éxito"
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            "Error al anular la categoria: "
                                    + e.getMessage()
                    ));
        }
    }
}