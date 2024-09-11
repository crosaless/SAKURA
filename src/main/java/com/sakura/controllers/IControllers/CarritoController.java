package com.sakura.controllers.IControllers;

import com.sakura.Entities.Carrito;
import com.sakura.controllers.IControllers.BaseController;

public interface CarritoController extends BaseController<Carrito, Long> {
public void deleteDetalleById(Long id);
}
