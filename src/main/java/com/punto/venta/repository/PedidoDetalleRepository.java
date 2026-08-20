package com.punto.venta.repository;

import com.punto.venta.entity.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Integer> {

}
