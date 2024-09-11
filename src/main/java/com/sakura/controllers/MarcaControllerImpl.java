package com.sakura.controllers;
import com.sakura.DTO.DTOMostrarMarca;
import com.sakura.Entities.Marca;
import com.sakura.Services.MarcaServiceImpl;
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

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/marcas")
public class MarcaControllerImpl extends BaseControllerImpl<Marca, Long, MarcaServiceImpl> implements MarcaController {

 
    @Autowired
    public MarcaControllerImpl(MarcaServiceImpl service){
        super(service);
    }
       
    @GetMapping("/admin")
    public String VerListaMarca(Model model){
    
        //debo armar un dto con todas las marcas pero aca solo lo recibo
        List<DTOMostrarMarca> marcas = service.builDTOMarcas();
       System.out.println("el largo de la lista recuperada es:" + marcas.size());
        model.addAttribute("marcas", marcas);
        
        return "Marcas";
    }
    
    @GetMapping("/admin/update/{id}")
    public String ModificarMarca(Model model, @PathVariable("id") Long id) throws Exception{
        DTOMostrarMarca edicionMarca = this.service.EdicionMarca(id);
        //debo armar un dto con todas las marcas pero aca solo lo recibo
        
        model.addAttribute("marca", edicionMarca);
        return "edicion_marca";
    } 
    
    @GetMapping("/admin/nueva")
    public String nuevaMarca(Model model) throws Exception{
        return "nueva_marca";
    }
    
    @PostMapping("/admin/nueva/save")
    public String SaveNewMarca(@RequestParam String nombre, @RequestParam String imagen) throws Exception{
        try{
            Marca marca = service.create(nombre, imagen);
      
            return "redirect:/marcas/admin";
        }catch(Exception e){
          throw new Exception();
        }  
    }
    
    
    @PostMapping("/admin/update/correct/{id}")
    public String update(@PathVariable Long id, @RequestParam String nombre, @RequestParam String imagen) throws Exception{
        try{
        Marca marca = service.findById(id);
        if (marca != null) {
            marca.setNombre(nombre);
            marca.setImagen(imagen);
            service.save(marca);
        }
        return "redirect:/marcas/admin";
        }catch (Exception e){
            throw new Exception();
        }
    }
    
    @PostMapping("/admin/darbaja/{id}")
    public String darbaja(@PathVariable Long id) throws Exception{
        try{
        Marca m = service.findById(id);
        m.setFechaHoraBaja(LocalDateTime.now());
        this.service.save(m);
        return "redirect:/marcas/admin";
        }catch (Exception e){
            throw new Exception();
        }
    }
    
    
    
}
