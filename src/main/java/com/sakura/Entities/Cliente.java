/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sakura.Entities;




import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Cliente extends User {
    public Long codCliente;
    public LocalDateTime fechaRegistro;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Domicilio domicilio;

    public Cliente(String username, String email, String password, Set<Role> roles){
      super(username,email, password,roles);
      this.fechaRegistro = LocalDateTime.now();
      //this.fechaNacimiento = fechaNacimiento; solucionar lo de la fecha
    }
}
