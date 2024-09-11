
package com.sakura.Services;

import com.sakura.DTO.DTOCantidadLikes;
import com.sakura.DTO.DTOCantidadVentas;
import com.sakura.DTO.DTOMasComprado;
import com.sakura.DTO.DTOMasCompradoUI;
import com.sakura.DTO.DTOProductoMUI;
import com.sakura.DTO.DTOPuntuacionCantidad;
import com.sakura.Entities.DetalleVenta;
import com.sakura.Entities.EstadoVenta;
import com.sakura.Entities.MasComprado;
import com.sakura.Entities.Producto;
import com.sakura.Entities.Venta;
import com.sakura.repository.EstadoVentaRepository;
import com.sakura.repository.MasCompradoRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasCompradoServiceImpl extends BaseServiceImpl<MasComprado, Long, MasCompradoRepository>
implements MasCompradoService {
    @Autowired
    FacturaServiceImpl ventaService;
    @Autowired
    public MasCompradoRepository repo;
    @Autowired
    ProductoServiceImpl PServiceImpl;
    @Autowired
    PuntuacionServiceImpl PuntuacionService;
    
    @Autowired
    EstadoVentaRepository EstadoVentaRepo;
    
    public MasCompradoServiceImpl(MasCompradoRepository repository) {
        super(repository);
    }

    @Override
    public MasComprado save(DTOMasComprado nuevaLista) throws Exception {
        try{
            //paso del dto a objeto con las fechas
             ModelMapper mapper = new ModelMapper();
             MasComprado mascomprado = mapper.map(nuevaLista, MasComprado.class);

             //busco las ventas que coincidan con el rango de fechas
             List<Venta> ventas = ventaService.findByFecha(nuevaLista.getFechaDesde(), nuevaLista.getFechaHasta());
            System.out.println("Ventas encontradas: " + ventas.size());
             
            //verifico que las ventas sean validas y esten em estado "entregada"
            EstadoVenta entregado = EstadoVentaRepo.findByState("entregada");
            
            //filtro las ventas que tienen el estado terminado
            List<Venta> VentasEntregadas = new ArrayList<Venta>();
            for(Venta v : ventas){
                if(v.getEstado()==entregado){
                    VentasEntregadas.add(v);
                }
            }
            
            
            Map<Producto, Long> conteoProductos = new HashMap();

             for (Venta ventaActual : VentasEntregadas){
               System.out.println("Procesando venta con ID: " + ventaActual.getId());
               
                 for(DetalleVenta detalle : ventaActual.getDetalles()){
                     Producto producto = detalle.getProducto();
                     int cantidad = detalle.getCantidad();
                     conteoProductos.put(producto, conteoProductos.getOrDefault(producto, 0L)+Long.parseLong(cantidad+""));
                 }
             }

             List<Producto> productosMasComprados = conteoProductos.entrySet().stream()
                     .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                     .limit(10)
                     .map(Map.Entry::getKey)
                     .collect(Collectors.toList());
             
             mascomprado.setProducto(productosMasComprados);
             return repository.save(mascomprado); //una vez creado el objeto, copio sus atributos y los seteo al DTOUI y devuelvo en lugar del objeto
        }catch (Exception e){
            throw new Exception("Error al obtener los productos mas comprados", e);
        
        } 
        
    }
    
    public DTOMasCompradoUI buildDTOMasComprado (DTOMasComprado nuevaLista) throws Exception{
        
        MasComprado m =  save(nuevaLista);
          
          //Lista de DTO de productos mas comprados
          List<DTOProductoMUI> listaProductosDTO = m.getProducto().stream().map(producto -> DTOProductoMUI.builder()
                  .imagen(producto.getImagen())
                  .precio(producto.getPrecio())
                  .id(producto.getId())
                  .nombre(producto.getNombre())
                  .build())
                  .collect(Collectors.toList());
          
          DTOMasCompradoUI dto = DTOMasCompradoUI.builder()
                .fechaDesde(m.getFechaDesde())
                .fechaHasta(m.getFechaHasta())
                .listaProductosDTO(listaProductosDTO).build();
            return dto;
        //Aca retornaria el DTOUI cerado en el servico de "mascomprado"
    }
   
    
    public List<DTOCantidadVentas> recuperarcantidadventas(DTOMasComprado m){
            // Recuperar las ventas dentro del rango de fechas
        List<Venta> ventas = ventaService.findByFecha(m.getFechaDesde(), m.getFechaHasta());
        System.out.println("Ventas encontradas: " + ventas.size());

        // Verificar que las ventas sean válidas y estén en estado "entregada"
        EstadoVenta entregado = EstadoVentaRepo.findByState("entregada");

        // Filtrar las ventas que tienen el estado entregada
        List<Venta> ventasEntregadas = ventas.stream()
                .filter(v -> v.getEstado().equals(entregado))
                .collect(Collectors.toList());

        // Crear un mapa para contar las ventas por fecha
        Map<LocalDate, Integer> conteoVentasPorFecha = new HashMap<>();

        for (Venta venta : ventasEntregadas) {
            LocalDate fecha = venta.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            conteoVentasPorFecha.put(fecha, conteoVentasPorFecha.getOrDefault(fecha, 0) + 1);
        }

        // Crear una lista de DTOCantidadVentas a partir del mapa
        List<DTOCantidadVentas> resultado = new ArrayList<>();
        for (Map.Entry<LocalDate, Integer> entry : conteoVentasPorFecha.entrySet()) {
            DTOCantidadVentas dto = DTOCantidadVentas.builder()
                    .fecha(entry.getKey())
                    .cantidad(entry.getValue())
                    .build();
            System.out.println("DTOCantidadventa generado:" + dto.getFecha() +" "+ dto.getCantidad());
            resultado.add(dto);
        }

        return resultado;
    }
    
    public List<DTOPuntuacionCantidad> buildDTOListPuntuacion(){
         
        System.out.println("Iniciando método builDTOListPuntuacion en FrontendService");

        List<Producto> listaProductos = PServiceImpl.findAllDisponibles();
        System.out.println("Armo lista de productos" + listaProductos.size());
        List<DTOPuntuacionCantidad> ListaLikeados = new ArrayList<DTOPuntuacionCantidad>();
        System.out.println("Creo la lista de likeados");
        
        for(Producto p : listaProductos ){
            System.out.println("Empiezo a loopear producto y le mando a puntService el id de:" + p.getNombre());
            DTOCantidadLikes resultado = PuntuacionService.calcularPuntuacion(p.getId());
            
            DTOPuntuacionCantidad dto = DTOPuntuacionCantidad.builder()
                    .cantidadMegusta(resultado.getCantidadLikes())
                    .cantidadNoMegusta(resultado.getCantidadDisLike())
                    .idProducto(p.getId())
                    .nombreProducto(p.getNombre())
                    .imagen(p.getImagen())
                    .build();
            ListaLikeados.add(dto);
        }
    return ListaLikeados;
    }
    
    //aca va un metodo que valida los rangos de fecha ingresados 
    public boolean isCorrectDate(LocalDate fechaDesde, LocalDate fechaHasta){
        LocalDate fechaActual = LocalDate.now();
    
        // Verificar que fechaDesde no sea mayor que fechaHasta
        if (fechaDesde.isAfter(fechaHasta)) {
            return false;
        }
        // Verificar que fechaDesde no sea mayor que la fecha actual
        if (fechaDesde.isAfter(fechaActual)) {
            return false;
        }
        // Verificar que fechaHasta no sea mayor que la fecha actual
        if (fechaHasta.isAfter(fechaActual)) {
            return false;
        }

    return true;
    }
    
    
}
