package com.sakura.Entities;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Audited
@Entity
@Table(name = "provincia")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Provincia extends BaseEntity {
    
    @Column(unique = true, name = "NombreProvincia")
    private String NombreProvincia;
    
    private LocalDate FechaHoraBaja; 
}
