package com.sakura.controllers;

import com.sakura.controllers.IControllers.ProductoController;
import com.sakura.Services.ProductoServiceImpl;
import com.sakura.Services.IService.ProductoService;
import com.sakura.Entities.Producto;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sakura.DTO.DTOCreateProducto;
import com.sakura.DTO.DTOTalleStock;
import com.sakura.DTO.DTOUpdateProducto;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/producto")
public class ProductoControllerImpl
        extends BaseControllerImpl<Producto, Long, ProductoServiceImpl>
        implements ProductoController {

    @Autowired
    public ProductoService productoServ;

    @Autowired
    public ProductoControllerImpl(ProductoServiceImpl service) {
        super(service);
    }

    @PostMapping(path = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Producto save(DTOCreateProducto entity) throws Exception {

        return this.service.save(entity);
    }


    @PostMapping("/admin/darBaja/{id}")
    public void darBajaProducto(@PathVariable Long id) throws Exception {
        Producto p = this.service.findById(id);
        p.setFechaHoraBaja(LocalDateTime.now());
        this.service.Save(p);
    }

    @PostMapping("/admin/actualizar")
    public String actualizarProducto(@RequestBody DTOUpdateProducto producto) {
        try {
            this.service.update(producto);
            return "producto actualizado correctamente";
        } catch (Exception e) {
            return "se ha producido un error";
        }
    }
    
    @PostMapping("/nuevo")
    public String crearProducto(@RequestBody DTOCreateProducto producto) {
        try {
            //System.out.println("Controlador de creacion recibe dto create:" + producto.getNombre());
            this.service.save(producto);
            return "producto creado correctamente";
        } catch (Exception e) {
            return "se ha producido un error";
        }
    }
    @PostMapping("/actualizar/stock")
    public ResponseEntity<?> actualizarStock(@RequestParam Long id,@RequestBody List<DTOTalleStock> lista_dtos){
        try{
            this.productoServ.actualizarStock(id,lista_dtos);
            return ResponseEntity.status(HttpStatus.OK).body("stock actualizado correctamente");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("se produjo un error al actualizar el stock");

        }
    }

}
