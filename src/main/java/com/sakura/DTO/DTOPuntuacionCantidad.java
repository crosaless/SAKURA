package com.sakura.DTO;

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
public class DTOPuntuacionCantidad {
    private int cantidadMegusta;
    private int cantidadNoMegusta;
    private Long idProducto;
    private String nombreProducto;
    private String imagen;
}
