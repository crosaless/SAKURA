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
public class DTOLocalidad {
    
    private Long CodigoPostal;
    private String NombreLocalidad;
    private String nombreProvincia;
    private Long id_provincia;
}
