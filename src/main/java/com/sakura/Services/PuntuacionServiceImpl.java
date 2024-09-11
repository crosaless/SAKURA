package com.sakura.Services;

import com.sakura.DTO.DTOCantidadLikes;
import com.sakura.DTO.DTOProductoMUI;
import com.sakura.DTO.DTOPuntuacion;
import com.sakura.DTO.DTOPuntuacionCantidad;
import com.sakura.Entities.Cliente;
import com.sakura.Entities.Producto;
import com.sakura.Entities.Puntuacion;
import com.sakura.repository.ClienteRepository;
import com.sakura.repository.ProductoRepository;
import com.sakura.repository.PuntuacionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PuntuacionServiceImpl extends BaseServiceImpl<Puntuacion, Long, PuntuacionRepository> {
    
    @Autowired
    public PuntuacionRepository puntuacionRepo;
    
    @Autowired
    public ClienteRepository clienteRepo;
    
    @Autowired
    public ProductoRepository productoRepo;
    
    public PuntuacionServiceImpl(PuntuacionRepository repository) {
        super(repository);
    }
    
     public Puntuacion findByClienteAndProduct(Long idCliente, Long idProducto) {
        return puntuacionRepo.findByClienteAndProduct(idCliente, idProducto);
    }

    public void save(DTOPuntuacion puntuacion) throws Exception {
        try {
            Puntuacion nuevaPuntuacion = new Puntuacion();
            Cliente cliente = clienteRepo.findById(puntuacion.getIdCliente()).orElseThrow();
            Producto producto = productoRepo.findById(puntuacion.getIdProducto()).orElseThrow();

            nuevaPuntuacion.setCliente(cliente);
            nuevaPuntuacion.setProducto(producto);
            nuevaPuntuacion.setPuntuacion(puntuacion.getPuntuacion());

            puntuacionRepo.save(nuevaPuntuacion);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void borrar(DTOPuntuacion puntuacion) {
        Puntuacion p = puntuacionRepo.findByClienteAndProduct(puntuacion.getIdCliente(), puntuacion.getIdProducto());
        puntuacionRepo.delete(p);
    }

    public void update(Puntuacion puntuacion) {
        puntuacionRepo.save(puntuacion);
    }

    public List<Puntuacion> findByProducto(Long idProducto){
            
        List<Puntuacion> puntuacion = puntuacionRepo.findByProduct(idProducto);
                
        return puntuacion; 
    }
    
    public DTOCantidadLikes calcularPuntuacion(Long id){
        System.out.println("llegue a calcularPuntuacion");
        
        List<Puntuacion> puntuaciones = this.findByProducto(id);
        System.out.println("Busque las puntuaciones para 1 producto, en total:" + puntuaciones.size());
        
        int cantidadMegusta = 0;
        int cantidadNomeGusta = 0;
        
        System.out.println("Empiezo a contar");
        for (Puntuacion puntuacion : puntuaciones) {
            if (puntuacion.getPuntuacion()==2) {
                cantidadNomeGusta++;
            } else if (puntuacion.getPuntuacion()==1) {
                cantidadMegusta++;
            }
        }
        
        DTOCantidadLikes resultado = new DTOCantidadLikes();
        resultado.setCantidadLikes(cantidadMegusta);
        resultado.setCantidadDisLike(cantidadNomeGusta);
        
        return resultado;
    }
}
