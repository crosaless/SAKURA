package com.sakura.Entities;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Audited
@Entity
@Table(name = "localidad")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//hacer ABM de esto
public class Localidad extends BaseEntity{
    
    @Column(name = "CodPos")
    private Long CodigoPostal;
    
    @Column(name = "NombreLocalidad")
    private String NombreLocalidad;
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    //Column(name = "fk_provincia")    
    private Provincia provincia;
    
    private LocalDateTime FechaHoraBaja;
}
