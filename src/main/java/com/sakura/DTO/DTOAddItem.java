package com.sakura.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DTOAddItem {

    private long producto;
    private int cantidad;
    private String talle;
}
