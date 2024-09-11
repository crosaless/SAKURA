package com.sakura.Services;

import com.sakura.DTO.DTOMasComprado;
import com.sakura.Entities.MasComprado;
import com.sakura.Services.IService.BaseService;


public interface MasCompradoService extends BaseService<MasComprado, Long> {
    
    public MasComprado save(DTOMasComprado nuevaLista) throws Exception;
}
