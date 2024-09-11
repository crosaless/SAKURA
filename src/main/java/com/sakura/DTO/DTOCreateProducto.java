package com.sakura.DTO;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DTOCreateProducto {
    private String nombre;
    private float precio;
    private Long categoria;
    private Long marca;
    private String imagen;



}
