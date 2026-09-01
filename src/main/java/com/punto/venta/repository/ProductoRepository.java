package com.punto.venta.repository;

import com.punto.venta.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // --- NUEVOS MÉTODOS DE BÚSQUEDA ---
    List<Producto> findByEstadoTrue();

    List<Producto> findByEstadoTrueAndNombreContainingIgnoreCase(String nombre);

    List<Producto> findTop2ByEstadoTrueAndNombreContainingIgnoreCase(String nombre);
}