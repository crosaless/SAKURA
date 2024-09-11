package com.sakura.Entities;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "domicilio")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Domicilio extends BaseEntity {
    
    @Column(name = "nombreCalle")
    private String calle;
    
    @Column(name = "nroCalle")
    private int numero;
    
    @Column(name = "referencia")
    private String referencia; 
    
    private LocalDateTime FechaHoraBaja;
    
    @OneToOne(cascade = CascadeType.REFRESH)
    //@Column(name = "fk_localidad")
    private Localidad localidad; 
}
