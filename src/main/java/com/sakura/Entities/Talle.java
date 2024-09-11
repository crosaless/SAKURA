/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sakura.Entities;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Talle extends BaseEntity{

    public abstract String getNombreTalle();

}
