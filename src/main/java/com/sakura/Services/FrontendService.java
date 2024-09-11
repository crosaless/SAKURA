package com.sakura.Services;

import com.sakura.DTO.DTOCantidadLikes;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sakura.Entities.Carrito;
import com.sakura.Entities.DetalleCarrito;
import com.sakura.Entities.Venta;
import com.sakura.DTO.DTOCarritoUI;
import com.sakura.DTO.DTODetalleCarrito;
import com.sakura.DTO.DTOFactura;
import com.sakura.DTO.DTOProductoUI;
import com.sakura.DTO.DTOTalle;
import com.sakura.DTO.DTOTalleStock;
import com.sakura.Entities.EstadoVenta;
import com.sakura.Entities.Producto;
import com.sakura.Entities.ProductoTalle;
import com.sakura.Entities.Role;
import com.sakura.Entities.TalleCalzado;
import com.sakura.Entities.TalleRopa;
import com.sakura.Entities.User;
import com.sakura.Entities.Vista;
import com.sakura.Services.IService.ProductoService;
import com.sakura.Services.IService.VistaService;
import com.sakura.repository.EstadoVentaRepository;
import com.sakura.repository.TalleCalzadoRepository;
import com.sakura.repository.TalleRopaRepository;
import com.sakura.repository.UserRepository;
import java.util.ArrayList;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class FrontendService {

   
    ProductoService productoService;
   
    CarritoServiceImpl carritoService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FacturaServiceImpl facturaService;
    @Autowired
    VistaService vistaService;
    @Autowired
    TalleCalzadoRepository tcalzadoRepository;
    @Autowired
    TalleRopaRepository tropaRepository;
    
    @Autowired
    EstadoVentaRepository EstadoVentaRepo;
    
    @Autowired
    public FrontendService(ProductoServiceImpl productoService, CarritoServiceImpl carritoService) {
        this.productoService = productoService;
        this.carritoService = carritoService;
    }

    public DTOProductoUI buildProductData(Long id) throws Exception {
        Producto producto = productoService.findById(id);
        List<Producto> similares = productoService.findSimilar(producto);

        System.out.println("Similares   ");
        System.out.println(similares.size());
        DTOProductoUI dto = DTOProductoUI.builder()
                .imagen(producto.getImagen())
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .similares(similares)
                .build();

        return dto;
    }

public DTOCarritoUI buildCarritoData(Long id) throws Exception {
        Carrito c = carritoService.findByUsuarioId(id);
        List<DTODetalleCarrito> dto_detalles = new ArrayList<>();
        float total = 0;
        for (DetalleCarrito d : c.getDetalles()) {
            float precioItem = d.getProducto().getPrecio() * d.getCantidad();
            System.out.println(precioItem);
            total += precioItem;
            
            String talle = "";
            if(tcalzadoRepository.existsById(d.getTalle().getId())){
                talle = tcalzadoRepository.findById(d.getTalle().getId()).get().getNumero();
            }
             if(tropaRepository.existsById(d.getTalle().getId())){
                talle = tropaRepository.findById(d.getTalle().getId()).get().getNombre();
            }
            DTODetalleCarrito dto_detalle = new DTODetalleCarrito(d.getId(),d.getProducto().getNombre(),d.getProducto().getImagen(),   
                                            d.getProducto().getPrecio(),talle,d.getCantidad());
            dto_detalles.add(dto_detalle);
        }

        DTOCarritoUI dto = DTOCarritoUI.builder()
                .total(total)
                .detalles(dto_detalles)
                .cantidadItems(c.getDetalles().size())
                .build();

        return dto;
    }

    public DTOFactura buildDtoFactura(Long id) {
        Venta f = this.facturaService.findById(id);
        EstadoVenta e = f.getEstado();

        DTOFactura dto = DTOFactura.builder()
                .cantidadItems(f.getDetalles().size())
                .detalles(f.getDetalles())
                .fecha(f.getFecha())
                .montoTotal(f.getMontoTotal())
                .usuario(f.getCliente().getUsername())
                .estado(e.getNombre())
                .id(f.getId())
                .build();
        return dto;
    }

    public List<DTOFactura> buildListDtoFactura(Long id) {
        List<Venta> facturas = this.facturaService.findAllByUsuarioId(id);
        List<DTOFactura> dtoFacturas = new ArrayList<DTOFactura>();
       for(Venta f : facturas){
        EstadoVenta e = f.getEstado();
        
        //EstadoVenta es = EstadoVentaRepo.findById(e.getId()).orElseThrow(()->new RuntimeException("No se encontro el estado"));
        
        DTOFactura dto = DTOFactura.builder()
                .cantidadItems(f.getDetalles().size())
                .detalles(f.getDetalles())
                .fecha(f.getFecha())
                .montoTotal(f.getMontoTotal())
                .usuario(f.getCliente().getUsername())
                .estado(e.getNombre())
                .id(f.getId())
                .build();
        dtoFacturas.add(dto);
       }
        return dtoFacturas;
    }
    
    public List<DTOTalle> buildDTOTalles(Long id) throws Exception{
        List<DTOTalle> dtoTalles = new ArrayList<>();
        Producto producto = this.productoService.findById(id);
        for(ProductoTalle producto_talle : producto.getProducto_talles()){
            DTOTalle dtoTalle = new DTOTalle();
            dtoTalle.setStock(producto_talle.getStock());
            if(producto_talle.getTalle() instanceof TalleCalzado){    
                TalleCalzado tcalzado = this.tcalzadoRepository.findById(producto_talle.getTalle().getId()).get();
                 dtoTalle.setNombre(tcalzado.getNumero());
                 dtoTalle.setId_talle(id);
            }
            if(producto_talle.getTalle() instanceof TalleRopa){    
                TalleRopa tRopa = this.tropaRepository.findById(producto_talle.getTalle().getId()).get();
                dtoTalle.setNombre(tRopa.getNombre());
                dtoTalle.setId_talle(id);
            }
            dtoTalles.add(dtoTalle);
            
        }
        return dtoTalles;
        
    }
    
    public List<DTOTalleStock> buildDtoTalleStock(Long id) throws Exception {
        Producto producto = this.productoService.findById(id);
        List<DTOTalleStock> dtoTalles = new ArrayList<>();
        if(producto.getProducto_talles().isEmpty() || producto.getProducto_talles() == null){
            productoService.inicializarTalles(producto);
        }
        for(ProductoTalle prod_talle : producto.getProducto_talles()){
             DTOTalleStock dto = DTOTalleStock.builder()
                .nombre(prod_talle.getTalle().getNombreTalle())
                .stock(prod_talle.getStock())
                .build();
             dtoTalles.add(dto);
        }
       
        return dtoTalles;
    }
    
}
