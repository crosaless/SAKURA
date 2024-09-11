package com.sakura.Entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name="Marca")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Marca extends BaseEntity {
    
    @Column(unique = true, name = "Nombre")
    private String nombre;
    
    private String imagen;
    private LocalDateTime FechaHoraBaja;


}
