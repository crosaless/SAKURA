package com.sakura.DTO;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DTOMasCompradoUI {
    
    private LocalDate fechaDesde;
    
    private LocalDate fechaHasta;
    
    private List<DTOProductoMUI> listaProductosDTO;
    
    @Override
    public String toString() {
        return "DTOMasCompradoUI{" +
                "listaProductosDTO=" + listaProductosDTO +
                '}';
    }
}
