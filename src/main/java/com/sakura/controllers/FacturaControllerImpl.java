/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakura.controllers;

import com.sakura.controllers.IControllers.FacturaController;
import com.sakura.Services.FacturaServiceImpl;
import com.sakura.controllers.VentaController;
import com.sakura.Services.FacturaServiceImpl;
import com.sakura.Entities.Venta;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.sakura.controllers.BaseControllerImpl;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/facturas")
public class FacturaControllerImpl extends BaseControllerImpl<Venta, Long, FacturaServiceImpl> implements
        FacturaController {

    public FacturaControllerImpl(FacturaServiceImpl service) {
        super(service);
    }

    // dentro de carrito
    @PostMapping("/pagar-carrito/{userId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> save(@PathVariable Long userId) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.service.saveFromFactura(userId));
        } catch (Exception e) {
            Gson json = new Gson();          
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json.toJson(e.getMessage()));
        }

    }


}
