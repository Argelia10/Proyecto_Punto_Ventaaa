package com.punto.venta.service;

import com.punto.venta.dto.ProductoDTO;
import com.punto.venta.entity.Producto;
import com.punto.venta.entity.Categoria;
import com.punto.venta.repository.ProductoRepository;
import com.punto.venta.repository.CategoriaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository) {

        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // GET
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    // POST
    public Producto save(ProductoDTO dto) {

        Producto producto = new Producto();

        producto.setEstado(dto.getEstado());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());

        Categoria categoria = categoriaRepository
                .findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria no encontrada"
                ));

        producto.setIdCategoria(categoria);

        return productoRepository.save(producto);
    }

    // PUT - ACTUALIZAR
    public Producto actualizar(
            Integer idProducto,
            ProductoDTO dto) {

        Producto productoExistente = productoRepository
                .findById(idProducto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto no encontrado"
                ));

        if (dto.getNombre() != null) {
            productoExistente.setNombre(dto.getNombre());
        }

        if (dto.getDescripcion() != null) {
            productoExistente.setDescripcion(dto.getDescripcion());
        }

        if (dto.getPrecio() != null) {
            productoExistente.setPrecio(dto.getPrecio());
        }

        if (dto.getStock() != null) {
            productoExistente.setStock(dto.getStock());
        }

        if (dto.getEstado() != null) {
            productoExistente.setEstado(dto.getEstado());
        }

        if (dto.getIdCategoria() != null) {

            Categoria categoria = categoriaRepository
                    .findById(dto.getIdCategoria())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Categoria no encontrada"
                    ));

            productoExistente.setIdCategoria(categoria);
        }

        return productoRepository.save(productoExistente);
    }

    // DELETE
    public void eliminar(Integer idProducto) {

        if (!productoRepository.existsById(idProducto)) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Producto no encontrado"
            );
        }

        productoRepository.deleteById(idProducto);
    }

    // ANULAR
    public Producto anular(Integer idProducto) {

        Producto productoExistente = productoRepository
                .findById(idProducto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto no encontrado"
                ));

        productoExistente.setEstado(false);

        return productoRepository.save(productoExistente);
    }
}