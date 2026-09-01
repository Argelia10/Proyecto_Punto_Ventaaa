package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.punto.venta.dto.CategoriaDTO;
import com.punto.venta.entity.Categoria;
import com.punto.venta.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // GET ALL
    public List<CategoriaDTO> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    // --- MÉTODOS AÑADIDOS ---

    public List<CategoriaDTO> obtenerActivos() {
        return categoriaRepository.findByEstadoTrue()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    public List<CategoriaDTO> obtenerActivosFiltro(String nombre) {
        return categoriaRepository.findByEstadoTrueAndNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    public List<CategoriaDTO> obtenerActivosFiltroTop(String nombre) {
        return categoriaRepository.findTop2ByEstadoTrueAndNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    // ------------------------

    // POST
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        Categoria categoria = convertToEntity(categoriaDTO);
        Categoria savedCategoria = categoriaRepository.save(categoria);
        return convertirDTO(savedCategoria);
    }

    // PUT - ACTUALIZAR
    public CategoriaDTO modificarCategoria(Integer idCategoria, CategoriaDTO dto) {
        Categoria categoriaExistente = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria no encontrada"
                ));

        if (dto.getNombre() != null) {
            categoriaExistente.setNombre(dto.getNombre());
        }

        if (dto.getDescripcion() != null) {
            categoriaExistente.setDescripcion(dto.getDescripcion());
        }

        if (dto.getEstado() != null) {
            categoriaExistente.setEstado(dto.getEstado());
        }

        return convertirDTO(categoriaRepository.save(categoriaExistente));
    }

    // DELETE
    public void eliminarCategoria(Integer idCategoria) {
        if (!categoriaRepository.existsById(idCategoria)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Categoria no encontrada"
            );
        }
        categoriaRepository.deleteById(idCategoria);
    }

    // ANULAR
    public CategoriaDTO anularCategoria(Integer idCategoria) {
        Categoria categoriaExistente = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria no encontrada"
                ));

        categoriaExistente.setEstado(false);
        return convertirDTO(categoriaRepository.save(categoriaExistente));
    }

    // ENTITY -> DTO
    private CategoriaDTO convertirDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        dto.setEstado(categoria.getEstado());
        return dto;
    }

    // DTO -> ENTITY
    private Categoria convertToEntity(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setEstado(true);
        return categoria;
    }
}