/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakura.DTO;

import com.sakura.Entities.DetalleVenta;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DTOFactura {
    private Long id;
    private Date fecha;
    private double montoTotal;
    private int cantidadItems;
    private String usuario;
    private String estado;
    List<DetalleVenta> detalles;
}
