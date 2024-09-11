package com.sakura.DTO;

import java.util.List;

import com.sakura.Entities.Producto;

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
public class DTOProductoUI {
    private String nombre;
    private float precio;
    private String imagen;
    private Long categoria;
    private List<Producto> similares;
}
