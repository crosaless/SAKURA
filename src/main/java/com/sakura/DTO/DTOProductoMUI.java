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
public class DTOProductoMUI {
    private Long id;
    private float precio;
    private String nombre;
    private String imagen; 
}
