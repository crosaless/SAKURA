/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sakura.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Audited
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EstadoVenta extends BaseEntity {

    @Column(unique = true)
    private String nombre;
    @Column(unique = true)
    private String codigo;

}
