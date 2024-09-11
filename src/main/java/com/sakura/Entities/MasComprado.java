package com.sakura.Entities;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "mascomprado")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MasComprado extends BaseEntity{
    
    @Column(name = "fechaDesde")
    private LocalDate fechaDesde;
    
    @Column(name = "fechaHasta")
    private LocalDate fechaHasta;
    
    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<Venta> venta;
    
    
   @OneToMany(cascade = CascadeType.ALL)
   @JoinColumn(name = "id_mascomprado")
    private List<Producto> producto;
    
}
