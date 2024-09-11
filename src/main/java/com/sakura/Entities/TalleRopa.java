/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sakura.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class TalleRopa extends Talle{
    @Column(unique=true)
    private String nombre;
    private String descripcion;
    private int longitudPecho;
    private int longitudCintura;
    private int longitudCadera;
    private int largoHombroCintura;

    @Override
    public String getNombreTalle() {
        return this.getNombre();
    }
    

}
