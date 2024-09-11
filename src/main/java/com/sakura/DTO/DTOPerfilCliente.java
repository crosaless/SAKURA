
package com.sakura.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DTOPerfilCliente {
    public Long idUser;
    public String username;
    public String email;
    public String password;
    public Long idDomicilio;
    public String nombrecalle;
    public int nroCalle;
    public String referenciaDomicilio;
    public Long idLocalidad;
    public String nombreLocalidad;
    public Long CodigoPostal;
    public Long idProvincia;
    public String nombreProvincia;
        
}
