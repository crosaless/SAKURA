package com.sakura.Entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "producto")
@Setter
@Getter
@Audited
@NoArgsConstructor
@AllArgsConstructor
public class Producto extends BaseEntity {

    @Column(name = "precio")
    private float precio;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "imagen")
    @Size(max=5000)
    private String imagen;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "fk_categoria")
    private Categoria categoria;
    private LocalDateTime fechaHoraBaja;
    
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_producto")
    private List<ProductoTalle> producto_talles = new ArrayList<>();

    
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "fk_marca")
    private Marca marca;
    
    public ProductoTalle getProductoTalle(Talle talle) {
        for (ProductoTalle producto_talle : producto_talles) {
            if (producto_talle.getTalle().equals(talle)) {
                return producto_talle;
            }
        }
        return null;
    }
 
}
