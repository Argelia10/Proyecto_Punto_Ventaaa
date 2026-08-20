package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.CategoriaDTO;
import com.punto.venta.entity.Categoria;
import com.punto.venta.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        Categoria categoria = convertToEntity(categoriaDTO);
        Categoria savedCategoria = categoriaRepository.save(categoria);
        return convertirDTO(savedCategoria);
    }

    public void eliminarCategoria(Integer idCategoria){
        if(!categoriaRepository.existsById(idCategoria)){
            throw new RuntimeException("La categoria no existe con id " + idCategoria);
        }
        categoriaRepository.deleteById(idCategoria);
    }

    public CategoriaDTO anularCategoria(Integer idCategoria){
        Categoria categoria = categoriaRepository.findById(idCategoria)
            .orElseThrow(() -> new RuntimeException("La categoria no existe con id " + idCategoria));

        categoria.setEstado(false);

        Categoria savedCategoria = categoriaRepository.save(categoria);
        return convertirDTO(savedCategoria);
    }

    public CategoriaDTO modificarCategoria(Integer idCategoria, CategoriaDTO dto) {

        Categoria categoria = categoriaRepository.findById(idCategoria)
            .orElseThrow(() -> new RuntimeException("La categoria no existe con id " + idCategoria));

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());


        Categoria actualizada = categoriaRepository.save(categoria);

        return convertirDTO(actualizada);
    }

    private CategoriaDTO convertirDTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setIdCategoria(categoria.getIdCategoria());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setDescripcion(categoria.getDescripcion());
        categoriaDTO.setEstado(categoria.getEstado());
        return categoriaDTO;
    }

    private Categoria convertToEntity (CategoriaDTO dto){
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(dto.getIdCategoria());
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setEstado(true);
        return categoria;
    }
}