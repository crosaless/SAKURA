package com.sakura.controllers;

import com.sakura.Services.CarritoServiceImpl;
import com.sakura.Entities.Carrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sakura.controllers.IControllers.CarritoController;
import com.sakura.DTO.DTOAddItem;
import com.sakura.DTO.DTOCreateCarrito;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/carrito")
public class CarritoControllerImpl extends BaseControllerImpl<Carrito, Long, CarritoServiceImpl> implements
        CarritoController {
    @Autowired
    public CarritoControllerImpl(CarritoServiceImpl service) {
        super(service);

    }

    @PostMapping("")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Carrito save(@RequestBody DTOCreateCarrito dto) throws Exception {
        return this.service.save(dto);
    }

    @PostMapping("/{id}/item")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addItem(
            @PathVariable long id,
            @RequestBody DTOAddItem addItem)
            throws Exception {
        try{
            this.service.addItem(id, addItem);
            return ResponseEntity.status(HttpStatus.CREATED).body("Detalle agregado correctamente");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Se produjo un error al agregar el detalle al carrito");
        }
    }

    @Override
    @PostMapping("/detalle/eliminar/{id}")
    public void deleteDetalleById(@PathVariable Long id) {
         this.service.deleteDetalleById(id);
    }

}
