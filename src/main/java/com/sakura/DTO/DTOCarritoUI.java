package com.sakura.DTO;

import java.util.List;

import com.sakura.Entities.DetalleCarrito;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;




@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DTOCarritoUI {
    @Singular
    private List<DTODetalleCarrito> detalles;
    private float total;
    private int cantidadItems;
}
