
package com.sakura.controllers;

import com.sakura.DTO.DTOLocalidad;
import com.sakura.DTO.DTOProvincia;
import com.sakura.Entities.Localidad;
import com.sakura.Services.LocalidadServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/localidad")
public class LocalidadControllerImpl extends BaseControllerImpl<Localidad, Long, LocalidadServiceImpl> implements LocalidadController{
    
    public LocalidadControllerImpl(LocalidadServiceImpl service) {
        super(service);
    }
    
    
    @GetMapping("/admin/disponibles")
    public String VerListaLocalidades(Model model){
    
        //debo armar un dto con todas las localides pero aca solo lo recibo
        List<DTOLocalidad> localidades = service.mostrarLocalidades();
       
        System.out.println("el largo de la lista recuperada es:" + localidades.size());
        model.addAttribute("localidades", localidades);
        
        return "Localidades"; //falta estilizaR el html
    }
    
    @GetMapping("/admin/update/{cp}")
    public String ModificarLocalidad(Model model, @PathVariable("cp") Long cp) throws Exception{
        DTOLocalidad edicionLocalidad = service.EdicionLocalidad(cp);
        
        List<DTOProvincia> provincias = service.mostrarProvincias();
        //debo armar un dto con todas las marcas pero aca solo lo recibo
        
        model.addAttribute("edicionLocalidad", edicionLocalidad);
        model.addAttribute("provincias", provincias);
        return "edicion_localidad"; //falta estilizar el html
    } 
    
   
    @GetMapping("/admin/nueva")
    public String nuevaLocalidad(Model model) throws Exception{
        List<DTOProvincia> provincias = service.mostrarProvincias();
        model.addAttribute("provincias", provincias);
        return "nueva_localidad"; //falta estilizar el html
    }
    
    @PostMapping("/admin/nueva/save")
    public String SaveNewLocalidad(@RequestParam String nombre, @RequestParam Long cp,@RequestParam Long idProvincia) throws Exception{
        try{
            Localidad localidad = service.create(cp, nombre, idProvincia);
            System.out.println("se creo la localidad" + localidad);
      
    return "redirect:/localidad/admin/disponibles";
        }catch(Exception e){
          throw new Exception();
        }  
    }
    
    //ya funciona
    @PostMapping("/admin/update/correct/{cp}")
    public String update(@PathVariable Long cp, @RequestParam String nombre, @RequestParam Long idProvincia, @RequestParam Long codigoPostal, @RequestParam Long oldCodigoPostal) throws Exception{
        try{
        System.out.println("controlador recibe solicitud de actualizar informacion");
        service.update(oldCodigoPostal, nombre, idProvincia, codigoPostal);
        System.out.println("se actualizo bien");
        return "redirect:/localidad/admin/disponibles";
        }catch (Exception e){
            throw new Exception();
        }
    }
    
    @PostMapping("/admin/darbaja/{cp}")
    public String darbaja(@PathVariable Long cp) throws Exception{
        try{
        Localidad m = service.findByCP(cp);
        m.setFechaHoraBaja(LocalDateTime.now());
        this.service.save(m);
        this.service.delete(cp);
        return "redirect:/localidad/admin/disponibles";
        }catch (Exception e){
            throw new Exception();
        }
    }
    
    @GetMapping("/admin/buscar/{cp}")
    public String BuscarLocalidades(Model model){
    
        //debo armar un dto con todas las localides pero aca solo lo recibo
        List<DTOLocalidad> localidades = service.mostrarLocalidades();
       
        System.out.println("el largo de la lista recuperada es:" + localidades.size());
        model.addAttribute("localidades", localidades);
        
        return "Localidades"; //falta estilizaR el html
    }
    
}
