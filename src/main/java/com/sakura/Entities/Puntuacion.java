package com.sakura.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Audited
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Puntuacion extends BaseEntity{
    
    @Column(name = "puntuacion")
    private int puntuacion;
    //si no tiene puntuacion es null o cero
    //puntuacion positiva 1 
    //puntuacion negativa 2
    @OneToOne
    //@Column(name = "fk_cliente")
    private Cliente cliente;
    
    @OneToOne
    //@Column(name = "fk_producto")
    private Producto producto;
    
}
