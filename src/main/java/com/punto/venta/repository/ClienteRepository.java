package com.punto.venta.repository;

import com.punto.venta.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    boolean existsByNombreIgnoreCaseAndApellidoIgnoreCase(String nombre, String apellido);

    // --- NUEVOS MÉTODOS DE BÚSQUEDA ---
    List<Cliente> findByEstadoTrue();

    List<Cliente> findByEstadoTrueAndNombreContainingIgnoreCase(String nombre);

    List<Cliente> findTop2ByEstadoTrueAndNombreContainingIgnoreCase(String nombre);
}