package com.sakura.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Audited
@Table(name = "roles")
public class Role extends BaseEntity {
    
  @NotBlank
  @Column(length = 20,unique = true)
  private String name;
  
  private LocalDateTime fechaHoraBaja;
  
  @ManyToMany(cascade = CascadeType.PERSIST)
  private List<Vista> vistas = new ArrayList<Vista>();
  
  public Role(String name, List<Vista>vistas){
      this.name = name;
      this.vistas = vistas;
  }
}