
package com.sakura.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import org.hibernate.envers.Audited;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class Carrito extends BaseEntity {

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_cliente", nullable = false,unique=true)
    private Cliente cliente;

    @Singular
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCarrito> detalles = new ArrayList<DetalleCarrito>();

    public void addDettale(DetalleCarrito detalleCarrito) {

        if (this.detalles == null)
            this.detalles = new ArrayList<DetalleCarrito>();

        this.detalles.add(detalleCarrito);
    }

}
