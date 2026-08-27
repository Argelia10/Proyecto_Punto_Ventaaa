package com.punto.venta.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PedidoDetalleDTO {

    private Integer idPedidoDetalle;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private Integer idPedido;
    private Integer idProducto;
    private Boolean estado;
}