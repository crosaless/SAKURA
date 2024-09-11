package com.sakura.Services;

import com.sakura.Services.IService.ProductoService;
import com.sakura.Entities.DetalleCarrito;
import com.sakura.Entities.Carrito;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sakura.DTO.DTOAddItem;
import com.sakura.DTO.DTOCreateCarrito;
import com.sakura.Entities.Producto;
import com.sakura.Entities.Cliente;
import com.sakura.Entities.Talle;
import com.sakura.Entities.TalleCalzado;
import com.sakura.Entities.TalleRopa;
import com.sakura.Services.IService.CarritoService;
import com.sakura.repository.CarritoRepository;
import com.sakura.repository.DetalleCarritoRepository;
import com.sakura.repository.ClienteRepository;
import com.sakura.repository.TalleCalzadoRepository;
import com.sakura.repository.TalleRopaRepository;
import com.sakura.security.services.UserService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Service

public class CarritoServiceImpl extends BaseServiceImpl<Carrito, Long, CarritoRepository> implements CarritoService{

    ProductoService productoService;
    @Autowired
    DetalleCarritoRepository detalle_carrito_repo;
    UserService userService;
    @Autowired
    CarritoRepository repo;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    TalleCalzadoRepository tcalzadoRepository;
    @Autowired
    TalleRopaRepository tropaRepository;
    
    @Autowired
    CarritoServiceImpl(CarritoRepository repository, ProductoService productoService,
            UserService userService) {
        super(repository);
        this.productoService = productoService;
        this.userService = userService;
    }

    // ? Cuando se implemente la autenticacion. Hay que reemplazar
    // ? el idCarrito por data de autentiocacion
    @Override
    public void addItem(long idUser, DTOAddItem dtoAdd) throws Exception {
        System.out.println(dtoAdd.getTalle());
        Carrito carrito = this.findOrCreate(idUser);
        Producto producto = this.productoService.findById(dtoAdd.getProducto());
        Talle talle = null;
        
        if(tcalzadoRepository.existsByNumero(dtoAdd.getTalle())){
            talle = tcalzadoRepository.findByNumero(dtoAdd.getTalle()).get();
        }
        
        if(tropaRepository.existsByNombre(dtoAdd.getTalle())){
            talle = tropaRepository.findByNombre(dtoAdd.getTalle()).get();
        }
        
        if (dtoAdd.getCantidad() > producto.getProductoTalle(talle).getStock() || dtoAdd.getCantidad() < 0) {
            throw new Exception("Non valid stock amount");
        }

        DetalleCarrito dCarrito = DetalleCarrito.builder()
                .cantidad(dtoAdd.getCantidad())
                .producto(producto)
                .talle(talle)
                .build();
        
        
        //en caso de ya existir un detalle para ese producto, se acumulan las cantidades
        boolean esDetalleNuevo = true;
        List<DetalleCarrito> detalles = carrito.getDetalles();
        for(DetalleCarrito d : detalles){
            if(d.getProducto() == dCarrito.getProducto() && d.getTalle().equals(talle)){
                d.setCantidad(d.getCantidad() + dCarrito.getCantidad());
                carrito.setDetalles(detalles);
                esDetalleNuevo = false;
            }
        }
        if(esDetalleNuevo){
            carrito.addDettale(dCarrito);
        }
        this.update(carrito.getId(), carrito);
        
        
    };

    // Todo: manejar el tema del usuario
    Carrito findOrCreate(Long id) throws Exception {
        Optional<Carrito> opt = this.repository.findByClienteId(id);
        if (!opt.isPresent()) {
            return this.save(new Carrito());
        }
        return opt.get();
    }

    // 1 Cliente solo puede tener un carrito, si ya lo tiene. Se devuelve el que
    // esta
   public Carrito save(DTOCreateCarrito dto) throws Exception {

        Optional<Carrito> optCarrito = repository.findByClienteId(dto.getCliente());

        if (optCarrito.isPresent())
            return optCarrito.get();

        Carrito c = new Carrito();
        Cliente client = clienteRepository.findById(dto.getCliente()).get();

        c.setCliente(client);
        return repository.save(c);
    }
    
    public Carrito findByUsuarioId(Long id){
        return repo.findByClienteId(id).get();
    }
    
    @Transactional
    public void deleteDetalleById(Long id){
        this.detalle_carrito_repo.deleteFromJoinTable(id);
        this.detalle_carrito_repo.deleteById(id);
    }


    @Override
    @Transactional
    public void vaciarCarrito(Carrito carrito) {
        for(DetalleCarrito detalle_carrito : carrito.getDetalles()){
            detalle_carrito_repo.deleteFromJoinTable(detalle_carrito.getId());
            detalle_carrito_repo.delete(detalle_carrito);
        }
    }
}

