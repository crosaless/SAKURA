package com.sakura.Services;

import com.sakura.Entities.Producto;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sakura.Entities.Categoria;
import com.sakura.ImageStorage.IndireccionImageStorage;
import com.sakura.DTO.DTOCreateProducto;
import com.sakura.DTO.DTOTalleStock;
import com.sakura.DTO.DTOUpdateProducto;
import com.sakura.Entities.Marca;
import com.sakura.Entities.ProductoTalle;
import com.sakura.Entities.Talle;
import com.sakura.Entities.TalleCalzado;
import com.sakura.Entities.TalleRopa;
import com.sakura.repository.ProductoRepository;
import com.sakura.Services.IService.ProductoService;
import com.sakura.repository.MarcaRepository;
import com.sakura.repository.TalleCalzadoRepository;
import com.sakura.repository.TalleRopaRepository;
import java.util.ArrayList;

@Service
public class ProductoServiceImpl extends BaseServiceImpl<Producto, Long, ProductoRepository>
        implements ProductoService {

    CategoriaServiceImpl categoriaService;
    @Autowired
    public ProductoRepository producto_repository;
    
    @Autowired
    private TalleCalzadoRepository tcalzadoRepository;
    @Autowired
    private TalleRopaRepository tropaRepository;
    
    @Autowired
    private MarcaRepository MarcaRepo;

    @Autowired
    public ProductoServiceImpl(ProductoRepository repository, CategoriaServiceImpl categoriaService) {
        super(repository);
        this.categoriaService = categoriaService;
    }

    @Override
public Producto save(DTOCreateProducto productoNuevo) throws Exception {
    try {
        System.out.println("Servicio de guardar recibe el dtocreate");
        System.out.println("Categoría ID: " + productoNuevo.getCategoria());
        System.out.println("Marca ID: " + productoNuevo.getMarca());

        // Convertir los campos básicos del DTO a objeto
        ModelMapper mapper = new ModelMapper();
        Producto producto = mapper.map(productoNuevo, Producto.class);

        System.out.println("ID de Categoría del producto: " + productoNuevo.getCategoria());

        // Cargar la categoría (asegurarse de que exista o crearla)
        Categoria categoria = categoriaService.findById(productoNuevo.getCategoria());
        System.out.println("Encontré la categoría: " + categoria);

        Marca marca = MarcaRepo.findById(productoNuevo.getMarca()).orElseThrow(() -> new RuntimeException("No se encontró la marca"));
        System.out.println("Encontré la marca: " + marca);

        producto.setMarca(marca);
        System.out.println("Setié la marca");

        producto.setCategoria(categoria);
        System.out.println("Setié la categoría");

        producto.setImagen(productoNuevo.getImagen());
        System.out.println("Setié la imagen");

        // Verificar y inicializar talles si es necesario
        if (producto.getProducto_talles() == null || producto.getProducto_talles().isEmpty()) {
            System.out.println("Estoy en el if para inicializar talles");
            inicializarTalles(producto);
        }

        System.out.println("Estoy a punto de guardar el producto");
        return repository.save(producto);

    } catch (Exception e) {
        e.printStackTrace();
        throw new Exception("Error al guardar el producto", e);
    }
}

    public Producto update(DTOUpdateProducto p) throws Exception {
        try {
            Producto producto = this.repository.findById(p.getId()).get();
            producto.setNombre(p.getNombre());
            producto.setPrecio(p.getPrecio());
            Categoria cat = this.categoriaService.findById(p.getCategoria());
            producto.setCategoria(cat);
            producto.setImagen(p.getImagen());
            return repository.save(producto);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<Producto> findWithFilters(
            String nombre, Long categoria, Long marca) {

        if (nombre == null && categoria == null  && marca == null)
            return repository.findAllDisponibles();

        if (nombre == null && marca == null)
            return this.findAllByCategory(categoria);
        
        if (nombre == null && categoria == null)
            return this.findByMarca(marca);

        return repository.findByNombreContaining(nombre);
    }

    public List<Producto> findAllByCategory(Long categoria) {
        if (categoria == null) {
            return repository.findAllDisponibles();
        }
        return repository.findByCategoriaId(categoria);

    }

    public List<Producto> findAllByName(String name) {
        return repository.findByNombreContaining(name);
    }

    // Busca similares pero no el mismo item
    public List<Producto> findSimilar(Producto producto) {
        Categoria categoria = producto.getCategoria();
        List<Producto> similares = repository.findByCategoriaId(categoria.getId());

        return similares;
    }

    public Producto findById(Long id) throws Exception {
        if(repository.existsById(id)){
            return repository.findById(id).get();
        }
        else{
            throw new Exception("No existe producto con el ID especificado");
        }
    }

    public void Save(Producto p) {
        this.repository.save(p);
    }


    @Override
    public void actualizarStock(Long id, List<DTOTalleStock> lista_dtos) throws Exception {
        Producto producto = this.findById(id);
        if(producto.getProducto_talles().isEmpty() || producto.getProducto_talles() == null){
              inicializarTalles(producto);
          }
        for(ProductoTalle producto_talle : producto.getProducto_talles()){
            for(DTOTalleStock dto : lista_dtos){
                if(producto_talle.getTalle().getNombreTalle().equals(dto.getNombre())){
                    System.out.println("dtoTalle: "+dto.getNombre());
                    System.out.println("Talle: "+producto_talle.getTalle().getNombreTalle());
                    System.out.println("stock dto: "+dto.getStock());
                    producto_talle.setStock(dto.getStock());
                    break;
                }
            }
        }
        producto_repository.save(producto);
        
    }
    
    public void inicializarTalles(Producto producto) throws Exception {
    System.out.println("Estoy en el método para setear los talles");
    String tipoTalle = producto.getCategoria().getTipoTalle();
    System.out.println("Tipo de talle: " + tipoTalle);
    List<ProductoTalle> producto_talles = new ArrayList<>();
    if (producto.getProducto_talles().isEmpty()) {
        switch (tipoTalle) {
            case "CALZADO":
                List<TalleCalzado> talles_calzado = tcalzadoRepository.findAll();
                for (TalleCalzado talle : talles_calzado) {
                    producto_talles.add(new ProductoTalle(0, talle));
                    System.out.println("Setié el talle para calzado");
                }
                break;
            case "ROPA":
                List<TalleRopa> talles_ropa = tropaRepository.findAll();
                for (TalleRopa talle : talles_ropa) {
                    producto_talles.add(new ProductoTalle(0, talle));
                    System.out.println("Setié el talle para ropa");
                }
                break;
            default:
                System.out.println("Tipo de talle no reconocido");
                break;
        }
        producto.setProducto_talles(producto_talles);
    }
}

    
    public List<Producto> findByMarca(Long id){
        return this.repository.findByMarca(id);
    
    }
    
    public List<Producto> findAllDisponibles(){
        return this.repository.findAllDisponibles();
    }
}
