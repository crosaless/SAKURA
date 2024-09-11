
package com.sakura.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class Venta extends BaseEntity {

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "montoTotal")
    private double montoTotal;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_cliente", nullable = false)
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<DetalleVenta>();
    
    @ManyToOne
    private EstadoVenta estado;

    public void addDetalle(DetalleVenta detalle) {
        if (this.detalles == null) {
            this.detalles = new ArrayList<DetalleVenta>();
        }

        this.detalles.add(detalle);
    }
}