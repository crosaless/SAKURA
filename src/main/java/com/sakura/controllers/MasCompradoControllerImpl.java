package com.sakura.controllers;

import com.sakura.DTO.DTOCantidadVentas;
import com.sakura.DTO.DTOMasComprado;
import com.sakura.DTO.DTOMasCompradoUI;
import com.sakura.DTO.DTOPuntuacionCantidad;
import com.sakura.Entities.MasComprado;
import com.sakura.Services.MasCompradoService;
import com.sakura.Services.MasCompradoServiceImpl;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/admin/reportes")
public class MasCompradoControllerImpl extends BaseControllerImpl<MasComprado, Long, MasCompradoServiceImpl>
implements MasCompradoController {
    
    @Autowired
    MasCompradoService masCompradoService;
    
    public MasCompradoControllerImpl(MasCompradoServiceImpl service) {
        super(service);
    }
   
    @PostMapping(path = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE }, produces = 
            {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public MasComprado save(DTOMasComprado entity) throws Exception {
        return this.service.save(entity); //aca no se usa el save generico
    }
    
     //seccion "reportes generica" donde tenes las 2 opciones
    @GetMapping("/opciones")
    public String mostrarReportes(Model model) {
        return "reportes";
    }
    
    
    //buscar los productos mas comprados segun fecha
    @GetMapping("/mascomprados")
    public String getMasComprado(Model model, @RequestParam(name = "fechaDesde", required = true) 
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(name = "fechaHasta", required = true) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta) throws Exception{
        
        System.out.println("Fecha Desde: " + fechaDesde);
        System.out.println("Fecha Hasta: " + fechaHasta);
        if(service.isCorrectDate(fechaDesde, fechaHasta)){
        
        DTOMasComprado nuevaLista = new DTOMasComprado();
        nuevaLista.setFechaDesde(fechaDesde);
        nuevaLista.setFechaHasta(fechaHasta);
        
        DTOMasCompradoUI dto = service.buildDTOMasComprado(nuevaLista);
        List<DTOCantidadVentas> ventas = service.recuperarcantidadventas(nuevaLista);
        ventas.sort(Comparator.comparing(DTOCantidadVentas::getFecha));
        System.out.println("Cantidad ventas recuperada:"+ ventas);
        System.out.println("DTO construido: " + dto);
        System.out.println("Productos: " + dto.getListaProductosDTO());
        model.addAttribute("mascomprados", dto);
        model.addAttribute("ventas", ventas);
       
        return "masComprados";            
        }
        
        return "error";
    }
    
    //buscar los productos mas likeados y mostrarlos
    @GetMapping("/maslikeados") //dudas de esto
    public String verProductosMasLikeados(Model model) {
        
        List<DTOPuntuacionCantidad> likeados = service.buildDTOListPuntuacion();
         
        model.addAttribute("likeados",likeados);
        
        return "reportemaslikeados"; 
    }
    
}
