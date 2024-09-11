
package com.sakura.controllers;

import com.sakura.DTO.DTOLocalidad;
import com.sakura.DTO.DTOMostrarProvincia;
import com.sakura.DTO.DTOProvincia;
import com.sakura.Entities.Provincia;
import com.sakura.Services.LocalidadServiceImpl;
import com.sakura.Services.ProvinciaServiceImpl;
import com.sakura.repository.ProvinciaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/provincia")
public class ProvinciaControllerImpl extends BaseControllerImpl<Provincia, Long, ProvinciaServiceImpl> implements ProvinciaController {
    
    @Autowired
    LocalidadServiceImpl ServiceLocalidad;
    public ProvinciaControllerImpl(ProvinciaServiceImpl service) {
        super(service);
    }
    
    //primero solo necesito un DTOProvincia, cuando entro al detalle necesito las localidades DTOMostrarProvincia
    @GetMapping("/admin/disponibles")
    public String VerListaProvincias(Model model){
    
        //debo armar un dto con todas las localides pero aca solo lo recibo
        List<DTOProvincia> provincias = service.ProvinciasDisponibles();
       
        System.out.println("el largo de la lista recuperada es:" + provincias.size());
        model.addAttribute("provincias", provincias);
        
        return "Provincias";
    }
    
    @GetMapping("/admin/update/{id}")
    public String ModificarProvincia(Model model, @PathVariable("id") Long id) throws Exception{
        DTOMostrarProvincia edicionProvincia = service.EdicionProvincia(id);
        
      //  List<DTOLocalidad> localidades = ServiceLocalidad.mostrarLocalidades();
        //debo armar un dto con todas las marcas pero aca solo lo recibo
        
        model.addAttribute("edicionProvincia", edicionProvincia);
       // model.addAttribute("localidades", localidades);
        return "edicion_provincia"; 
    } 
    
    
    @PostMapping("/admin/update/correct/{id}")
    public String update(@PathVariable Long id, @RequestParam String nombre) throws Exception{
        try{
        System.out.println("controlador recibe solicitud de actualizar informacion");
        service.update(id, nombre);
        System.out.println("se actualizo bien");
        return "redirect:/provincia/admin/disponibles";
        }catch (Exception e){
            throw new Exception();
        }
    }
    
    @GetMapping("/admin/nueva")
    public String nuevaProvincia(Model model) throws Exception{
        return "nueva_provincia"; //falta estilizar el html
    }
    
    @PostMapping("/admin/nueva/save")
    public String SaveNewProvincia(@RequestParam String nombre) throws Exception{
        try{
            Provincia provincia = service.create(nombre);
            System.out.println("se creo la provincia" + provincia);
      
    return "redirect:/provincia/admin/disponibles";
        }catch(Exception e){
          throw new Exception();
        }  
    }
    
    @PostMapping("/admin/darbaja/{id}")
    public String darbaja(@PathVariable Long id) throws Exception{
        try{
            service.eliminar(id);
        return "redirect:/provincia/admin/disponibles";
        }catch (Exception e){
            throw new Exception();
        }
    }
    
}
