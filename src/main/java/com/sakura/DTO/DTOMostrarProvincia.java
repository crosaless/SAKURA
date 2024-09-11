
package com.sakura.DTO;

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

public class DTOMostrarProvincia {
    private String nombreProvincia;
    private Long idProvincia;
    
    private List<DTOLocalidad> localidades;
}
