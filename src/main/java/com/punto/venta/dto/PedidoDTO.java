package com.punto.venta.dto;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class PedidoDTO {

    private Integer idPedido;
    private Boolean estado;
    private Date fechaPedido;
    private Boolean estadoPedido;
    private BigDecimal total;
    private Integer idCliente;
}