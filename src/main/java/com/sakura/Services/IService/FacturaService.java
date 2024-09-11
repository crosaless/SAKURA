package com.sakura.Services.IService;

import com.sakura.Entities.Venta;
import com.sakura.Services.IService.BaseService;

public interface FacturaService extends BaseService<Venta, Long> {

    public Venta saveFromFactura(Long carritoId) throws Exception;
}
