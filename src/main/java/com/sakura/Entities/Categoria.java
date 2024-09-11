package com.sakura.Entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "categorias")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Audited
public class Categoria extends BaseEntity {

    @Column(unique = true, name = "nombre")
    private String nombre;
    private String tipoTalle;
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    //@Column(name = "fk_marca")
    private Marca marca;

    //@ManyToMany(cascade = CascadeType.PERSIST) //preguntar esto!!!!!!!
    //@Column(name = "fk_talle")
   // private List<Talle> talle;
}
