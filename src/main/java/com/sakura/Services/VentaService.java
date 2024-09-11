package com.sakura.Services;

import com.sakura.Entities.Venta;
import com.sakura.Services.IService.BaseService;

public interface VentaService extends BaseService<Venta, Long> {

    public Venta saveFromFactura(Long carritoId) throws Exception;
}
