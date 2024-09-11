
package com.sakura.controllers;

import com.sakura.DTO.DTOLocalidad;
import com.sakura.DTO.DTOMostrarProvincia;
import com.sakura.DTO.DTOPerfilCliente;
import com.sakura.Services.ClienteServiceImpl;
import com.sakura.Services.LocalidadServiceImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/cliente")
public class ClienteControllerImpl implements ClienteController{
    @Autowired
    ClienteServiceImpl ClienteService;
    @Autowired
    LocalidadServiceImpl LocalidadService;
    
    
    @GetMapping("/{id}/perfil")
    public String VerPerfil(Model model, @PathVariable Long id) throws Exception{
        try{
            
            System.out.println("Recibi la petición, controlador OK");
            
            DTOPerfilCliente cliente = ClienteService.dtoPerfil(id);
            System.out.println("Recibi el Dto cliente en el controlador");
            
            List<DTOMostrarProvincia> lugares = LocalidadService.mostrarLugares();
            System.out.println("yo, controlador, Recibi el dto de lugares");
            
            // Filtrar las localidades de la provincia del cliente
            List<DTOLocalidad> localidades = lugares.stream()
                .filter(p -> p.getIdProvincia().equals(cliente.getIdProvincia()))
                .flatMap(p -> p.getLocalidades().stream())
                .collect(Collectors.toList());
            
            model.addAttribute("cliente",cliente);
            model.addAttribute("lugares", lugares);
            model.addAttribute("localidades", localidades);
            
            return "PerfilCliente";
        } catch (Exception e){
            return "error";
        }
    }
    
    // Endpoint para obtener localidades basadas en provincia
    @GetMapping("/localidades/{idProvincia}")
    @ResponseBody
    public List<DTOLocalidad> getLocalidadesPorProvincia(@PathVariable Long idProvincia) {
        List<DTOMostrarProvincia> lugares = LocalidadService.mostrarLugares();
        return lugares.stream()
                .filter(p -> p.getIdProvincia().equals(idProvincia))
                .flatMap(p -> p.getLocalidades().stream())
                .collect(Collectors.toList());
    }
    
    @PostMapping("/{id}/perfil/savechanges")
    public String saveChanges(Model model, @ModelAttribute("cliente") DTOPerfilCliente cliente){
        try {
            System.out.println("Controlador recibe peticion de guardado");
            System.out.println(cliente); 
            //System.out.println(cliente.getIdDomicilio()); //lo q retorna es el Codigo postal de la localidas, supongo sera unica en todo el pais
            //System.out.println(cliente.getIdLocalidad());
            //System.out.println(cliente.getNombrecalle());
            //System.out.println(cliente.getNroCalle());    
            this.ClienteService.updateInfo(cliente);
            System.out.println("Datos actualizados correctamente");
               
            List<DTOMostrarProvincia> lugares = LocalidadService.mostrarLugares();
            System.out.println("yo, controlador, Recibi el dto de lugares");
            
            // Filtrar las localidades de la provincia del cliente
            List<DTOLocalidad> localidades = lugares.stream()
                .filter(p -> p.getIdProvincia().equals(cliente.getIdProvincia()))
                .flatMap(p -> p.getLocalidades().stream())
                .collect(Collectors.toList());
            model.addAttribute("lugares", lugares);
            model.addAttribute("localidades", localidades);
            return "redirect:/cliente/{id}/perfil";
        } catch (Exception e) {
            return "error";
        }
    
    }
}
