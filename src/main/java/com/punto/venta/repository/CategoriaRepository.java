package com.punto.venta.repository;

import com.punto.venta.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByEstadoTrueOrderByIdCategoriaDesc();

    // Métodos añadidos para la tarea:
    List<Categoria> findByEstadoTrue();

    List<Categoria> findByEstadoTrueAndNombreContainingIgnoreCase(String nombre);

    List<Categoria> findTop2ByEstadoTrueAndNombreContainingIgnoreCase(String nombre);
}
