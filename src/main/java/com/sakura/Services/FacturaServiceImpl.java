package com.sakura.Services;

import com.sakura.Entities.DetalleVenta;
import com.sakura.Entities.Venta;
import Exceptions.DadoBajaException;
import Exceptions.SinStockException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sakura.Entities.Carrito;
import com.sakura.Entities.DetalleCarrito;
import com.sakura.Entities.Producto;
import com.sakura.Entities.ProductoTalle;
import com.sakura.repository.FacturaRepository;
import com.sakura.repository.ProductoRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaServiceImpl
        extends BaseServiceImpl<Venta, Long, FacturaRepository> {

    CarritoServiceImpl carritoService;
    @Autowired
    ProductoRepository product_repo;

    @Autowired
    public  FacturaServiceImpl(FacturaRepository repository,
            CarritoServiceImpl carritoService) {
        super(repository);
        this.carritoService = carritoService;
    }

    public Venta saveFromFactura(Long userId) throws Exception {
        Carrito c = this.carritoService.findByUsuarioId(userId);
        Venta f = carritoFromFactura(c);

        return repository.save(f);
    };

    @Transactional
    private Venta carritoFromFactura(Carrito carrito) throws SinStockException, DadoBajaException, Exception {
        if(carrito.getDetalles().size()== 0){
         throw new Exception("Error: carrito vacio");
        }
        Venta nFactura = new Venta();
        nFactura.setFecha(new Date());
        nFactura.setCliente(carrito.getCliente());
        for (DetalleCarrito detalleC : carrito.getDetalles()) {

            DetalleVenta detalleF = DetalleVenta
                    .builder()
                    .producto(detalleC.getProducto())
                    .talle(detalleC.getTalle())
                    .cantidad(detalleC.getCantidad())
                    .precio(detalleC.getProducto().getPrecio())
                    .build();

            nFactura.addDetalle(detalleF);
        }

        double precioTotal = 0;
        for (DetalleVenta df : nFactura.getDetalles()) {
            precioTotal += df.getPrecio()*df.getCantidad();
            Producto producto = df.getProducto();
            ProductoTalle producto_talle = producto.getProductoTalle(df.getTalle());
            if(disponibilidadProducto(df)){
                int index = producto.getProducto_talles().indexOf(producto_talle);
                ProductoTalle producto_talle_lista = producto.getProducto_talles().get(index);
                producto_talle_lista.setStock(producto_talle_lista.getStock()- df.getCantidad());
                this.product_repo.save(producto);           
            }
        }
        carritoService.vaciarCarrito(carrito);
        nFactura.setMontoTotal(precioTotal);

        return nFactura;
    }
    
    
    boolean disponibilidadProducto(DetalleVenta df) throws SinStockException, DadoBajaException{
        boolean disponible=true;
        int talle_stock = 0;
        Producto producto = df.getProducto();
        for(ProductoTalle producto_talle : producto.getProducto_talles()){
            if(producto_talle.getTalle().equals(df.getTalle())){
                talle_stock = producto_talle.getStock();
                break;
            }
        }
            if(talle_stock < df.getCantidad()){
                disponible = false;
                throw (new SinStockException("No hay suficiente stock del producto: "+producto.getNombre()));
            }
            if(producto.getFechaHoraBaja() != null){
                disponible = false;
                throw (new DadoBajaException("El producto: "+producto.getNombre()+" ya no se encuentra disponible"));
            }
        
        return disponible;
    }
    
    public Venta findById(Long id){
        return this.repository.findById(id).get();
    }
    
    public List<Venta> findAllByUsuarioId(Long id){
        return this.repository.findAllByClienteId(id);
    }

    public List<Venta> findByFecha (LocalDate fecha1, LocalDate fecha2){
        return this.repository.findByFecha(fecha1, fecha2);
    }
}
