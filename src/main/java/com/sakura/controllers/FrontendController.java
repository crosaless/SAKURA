package com.sakura.controllers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.sakura.Services.CategoriaServiceImpl;
import com.sakura.Services.ProductoServiceImpl;
import com.sakura.Entities.Producto;
import com.sakura.Entities.Categoria;
import com.sakura.DTO.DTOFactura;
import com.sakura.DTO.DTOCarritoUI;
import com.sakura.DTO.DTOProductoUI;
import com.sakura.Services.FrontendService;
import com.sakura.DTO.DTOCreateProducto;
import com.sakura.DTO.ListaTalles;
import com.sakura.Entities.Role;
import com.sakura.Entities.User;
import com.sakura.Entities.Vista;
import com.sakura.Services.IService.VistaService;
import com.sakura.Services.RoleService;
import com.sakura.security.services.UserService;
import java.util.HashMap;
import java.util.Map;
import com.sakura.DTO.DTOPuntuacion;
import com.sakura.Entities.Marca;
import com.sakura.Entities.Puntuacion;
import com.sakura.Services.AuthService;
import com.sakura.Services.MarcaServiceImpl;
import com.sakura.Services.PuntuacionServiceImpl;

@Controller
public class FrontendController {

    @Autowired
    CategoriaServiceImpl categoriaService;
    
    @Autowired
    MarcaServiceImpl marcaService;
    
    ProductoServiceImpl productoService;
    @Autowired
    RoleService roleService;
    @Autowired
    FrontendService service;
    @Autowired
    VistaService vistaService;
    @Autowired
    UserService userService;
    PuntuacionServiceImpl PuntuacionService;
    @Autowired 
    AuthService authService;
    
    @Autowired
    public FrontendController(
            FrontendService service,
            ProductoServiceImpl productoService,
            CategoriaServiceImpl categoriaService) {
        this.service = service;
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/")
    public String index(Model model,
            @RequestParam(name = "categoria", required = false) Long categoria) {
        try {
            List<Producto> productos = productoService.findAllByCategory(categoria);
            List<Categoria> c = categoriaService.findAll();
            List<Categoria> categorias = c.stream().limit(5).collect(Collectors.toList());

            model.addAttribute("productos", productos);
            model.addAttribute("categorias", categorias);
            return "index";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/productos")
    public String getProductos(Model model,
            @RequestParam(name = "categoria", required = false) Long categoria,
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "marca", required = false)Long marca) {
        try {

            List<Producto> productos = productoService.findWithFilters(nombre, categoria, marca);
          
            List<Categoria> c = categoriaService.findAll();
            List<Categoria> categorias = c.stream().limit(5).collect(Collectors.toList());
            List<Marca> m = marcaService.findAll();
            List<Marca> marcas = m.stream().limit(6).collect(Collectors.toList());

            model.addAttribute("productos", productos);
            model.addAttribute("categorias", categorias);
            model.addAttribute("marcas", marcas);
            return "productos";
        } catch (Exception e) {
            return "error";
        }
    }
    
    @GetMapping("/admin/ventas")
    public String getVentas(Model model) {
        try {
            authService.validateUserPermissions("Ventas");
            return "listado-ventas";
        } catch (Exception e) {
            return "error";
        }
    }
    
    
    @GetMapping("/admin/producto/editar/stock")
    public String editarStock(Model model, @RequestParam Long id) {
        try {
            authService.validateUserPermissions("Mod_Producto");
            Producto producto = this.productoService.findById(id);
            model.addAttribute("producto", producto);
            model.addAttribute("talles", this.service.buildDtoTalleStock(id));
            return "editar_stock";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/productos/{id}")
    public String getOne(Model model, @PathVariable Long id) {
        try {
            DTOProductoUI dto = service.buildProductData(id);
            ListaTalles lista = new ListaTalles(service.buildDTOTalles(id));
            //DTOTalle dtoTalle = new DTOTalle("45",500,Long.valueOf(56));
            //List<DTOTalle> dtoTalles = new ArrayList<>();
           // dtoTalles.add(dtoTalle);
           // ListaTalles lista = new ListaTalles(dtoTalles);
            model.addAttribute("data", dto);
            model.addAttribute("lista", lista);
            // Agrego categorias
            List<Categoria> c = categoriaService.findAll();
            List<Categoria> categorias = c.stream().limit(5).collect(Collectors.toList());
            //muestro la calse puntuacion en caso de exisitir

            model.addAttribute("categorias", categorias);

            return "producto";
        } catch (Exception e) {
            return "error";
        }
    }
    
    @PostMapping("/productos/{id}/puntuacion")
    public String puntuar(Model model, @PathVariable Long id, @RequestParam Long idCliente, @RequestParam int puntuacion) throws Exception {
    try {
        DTOPuntuacion dtoPuntuacion = new DTOPuntuacion();
        dtoPuntuacion.setIdCliente(idCliente);
        dtoPuntuacion.setIdProducto(id);
        dtoPuntuacion.setPuntuacion(puntuacion);

        Puntuacion existingPuntuacion = PuntuacionService.findByClienteAndProduct(idCliente, id);

        if (existingPuntuacion != null) {
            if (existingPuntuacion.getPuntuacion() == puntuacion) {
                // Eliminar la puntuación si es la misma
                PuntuacionService.borrar(dtoPuntuacion);
            } else {
                // Actualizar la puntuación si es diferente
                existingPuntuacion.setPuntuacion(puntuacion);
                PuntuacionService.update(existingPuntuacion);
            }
        } else {
            // Guardar nueva puntuación
            PuntuacionService.save(dtoPuntuacion);
        }

        return "redirect:/productos/" + id + "?idCliente=" + idCliente;
    } catch (Exception e) {
        return "error";
    }
}

    @GetMapping("/login")
    public String login(Model model) {
        try {
            return "login";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/registro")
    public String register(Model model) {
        try {
            return "registro";
        } catch (Exception e) {
            return "error";
        }
    }
    
    @GetMapping("/carrito/{id}")
    public String carrito(Model model, @PathVariable Long id) { //CookieValue @CookieValue(name = "autenticacion")String token

        try {
            authService.validateUserPermissions("Carrito");
            authService.validateUserMatching(id);
            DTOCarritoUI dto = service.buildCarritoData(id);
            model.addAttribute("data", dto);
            model.addAttribute("items", dto.getDetalles());

            return "carrito";
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }

    @GetMapping("/compra/{id}")
    public String carrito(Model model, @PathVariable long id) {
        try {
            authService.validateUserPermissions("Compra");
            return "compra";
        } catch (Exception e) {
            return "error";
        }
    }
    
    @GetMapping("/productos/nuevo")
    public String newProd(Model model) {
        try {
            authService.validateUserPermissions("Alta_Producto");
            List<Categoria> categorias = categoriaService.findAll();
            List<Marca> marcas = marcaService.findAll();
            
            System.out.println(marcas);
            
            model.addAttribute("categorias", categorias);
            model.addAttribute("marcas", marcas);
            //model.addAttribute("producto", new DTOCreateProducto());

            return "nuevo";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/compras/{id}")
    public String mostrarFacturas(Model model,@PathVariable("id") Long idUser) {
        try {
            authService.validateUserPermissions("Facturas");
            List<DTOFactura> facturas = this.service.buildListDtoFactura(idUser);
            model.addAttribute("facturas", facturas);
            return "misCompras";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/factura/{id}")
    public String mostrarFactura(Model model,@PathVariable("id") Long idFactura) {
        try {
            authService.validateUserPermissions("Facturas");
            DTOFactura dtoFactura = this.service.buildDtoFactura(idFactura);
            model.addAttribute("factura", dtoFactura);
            model.addAttribute("items", dtoFactura.getDetalles());
            return "factura";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/nuevo")
    public String saveProd(
            Model model,
            @ModelAttribute("producto") DTOCreateProducto producto) {
        try {
            authService.validateUserPermissions("Alta_Producto");
            Producto nuevo = productoService.save(producto);

            List<Categoria> categorias = categoriaService.findAll();

            model.addAttribute("categorias", categorias);
            model.addAttribute("producto", new DTOCreateProducto());
            model.addAttribute("creado", nuevo);

            return "nuevo";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/productos/listado")
    public String listadoDeProductos(Model model,
            @RequestParam(name = "categoria", required = false) Long categoria) {
        try {
            authService.validateUserPermissions("Listado de productos");
            List<Producto> productos = productoService.findAllByCategory(categoria); //ver xq no esta devolviendo todos los productos indep. de la categoria
            List<Categoria> c = categoriaService.findAll();
            List<Categoria> categorias = c.stream().limit(7).collect(Collectors.toList());

            model.addAttribute("productos", productos);
            model.addAttribute("categorias", categorias);
            return "lista-productos";
        } catch (Exception e) {
            return "error";
        }
    }
    
    @GetMapping("/admin/inicio")
    public String inicioAdmin(Model model) {
        try {
            authService.validateUserPermissions("Inicio Administrador del negocio");
            return "inicio-admin-negocio";
        } catch (Exception e) {
            return "error";
        }
    }
    

    @GetMapping("/admin/producto/editar/{id}")
    public String editarProducto(Model model,@PathVariable Long id) {
        try {
            authService.validateUserPermissions("Mod_Producto");
            List<Categoria> categorias = categoriaService.findAll();
            Producto p = productoService.findById(id);
            model.addAttribute("categorias", categorias);
            model.addAttribute("producto", p);
            
            return "edicion_producto";
        } catch (Exception e) {
            return "error";
        }
    }
    
    @GetMapping("/sistema/administracion/editar/rol")
    public String editarRol(Model model, @RequestParam String name){
        try{
            authService.validateUserPermissions("Mod_Rol");
            Role rol = roleService.findByName(name);
            Map<String, Boolean> listParams = new HashMap<String, Boolean>();
            List<Vista> vistas = vistaService.findAll();
            for(Vista v: vistas){
                if(rol.getVistas().contains(v)){
                    listParams.put(v.getNombreVista(), true);
                }else{
                    listParams.put(v.getNombreVista(), false);
                }
            }
            model.addAttribute("rol",rol);
            model.addAttribute("vistas",listParams);
            return "editar_rol";
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return "error";
        }
    }
    
    @GetMapping("/sistema/administracion/crear/rol")
    public String crearRol(Model model){
        try{
            authService.validateUserPermissions("Alta_Rol");
            List<Vista> vistas = vistaService.findAll();
            model.addAttribute("vistas",vistas);
            return "alta_rol";
        }catch(Exception e){
            return "error";
        }
    }
    
    @GetMapping("/sistema/administracion/crear/vista")
    public String crearVista(Model model){
        try{
            authService.validateUserPermissions("Alta_Vista");
            return "crear_vista";
        }catch(Exception e){
            return "error";
        }
    }
    
    @GetMapping("/sistema/administracion/editar/vista")
    public String editarVista(Model model,@RequestParam String name){
        try{
            authService.validateUserPermissions("Mod_Vista");
            Vista vista = vistaService.findByNombreVista(name);
            model.addAttribute("vista",vista);
            return "editar_vista";
        }catch(Exception e){
            return "error";
        }
    }

    @GetMapping("/sistema/administracion/editar/usuario")
    public String editarUsuario(Model model,@RequestParam String username){
        try{
            authService.validateUserPermissions("Mod_Usuario");
            User user = userService.findByUsername(username);
            List<Role> roles = roleService.findAll();
            Map<String,Boolean> userRoles = new HashMap<>();
            for(Role rol : roles){
                userRoles.put(rol.getName(), false);
                for(Role userRol : user.getRoles()){
                    if(rol.getName() == userRol.getName()){
                        userRoles.put(rol.getName(), true);
                    }
                }
            }
            model.addAttribute("user",user);
            model.addAttribute("roles",userRoles);
            
            return "Mod_usuario";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "error";
        }
    }
    
    @GetMapping("/sistema/administracion/crear/usuario")
    public String crearUsuario(Model model){
        try{
            authService.validateUserPermissions("Alta_Usuario");
           List<Role> roles = this.roleService.findAll();
           model.addAttribute("roles",roles);
            return "Alta_usuario";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "error";
        }
    }
    
    @GetMapping("/sistema/administracion")
    public String vistaAdminSistema(Model model){
        try{
            authService.validateUserPermissions("Inicio Administracion del Sistema");
            System.out.println(System.getProperty("user.dir"));
            return "administracion_sistema";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "error";
        }
    }
    
    @GetMapping("/restablecerContraseña")
    public String pantallaRestablecerContraseña(Model model, @RequestParam(required=false)String token){
        try{
            return "restablecerContraseña";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "error";
        }
    }
    
    @GetMapping("/categoria/nuevo")
    public String nuevaCategoria(Model model) {
        try {
            authService.validateUserPermissions("Alta_Categoria");
            return "crear_categoria";
        } catch (Exception e) {
            return "error";
        }
    }
    
    @GetMapping("/admin/categoria/editar")
    public String editarCategoria(Model model,@RequestParam Long id) {
        try {
            authService.validateUserPermissions("Mod_Categoria");
            Categoria categoria = this.categoriaService.findById(id);
            model.addAttribute("categoria", categoria);
            return "editar_categoria";
        } catch (Exception e) {
            return "error";
        }
    }
}
