package com.sakura.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DTOMostrarLocalidad {
    private String Provincia;
    private List<DTOLocalidad> localidades;
    
}
