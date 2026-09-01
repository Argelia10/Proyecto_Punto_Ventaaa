package com.punto.venta.repository;

import com.punto.venta.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByEstadoTrue();

    // Se cambia String por Boolean y se quita ContainingIgnoreCase
    List<Pedido> findByEstadoTrueAndEstadoPedido(Boolean estadoPedido);

    List<Pedido> findTop2ByEstadoTrueAndEstadoPedido(Boolean estadoPedido);
}