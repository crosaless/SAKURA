package com.sakura.repository;

import com.sakura.Entities.Producto;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends BaseRepository<Producto, Long> {

    @Override
    List<Producto> findAll();

    @Query(value = "Select distinct p.* from producto p, producto_talle pt where pt.stock>0 and p.fecha_hora_baja is null and fk_categoria = :id", nativeQuery = true)
    List<Producto> findByCategoriaId(@Param("id") Long id);

    @Query(value = "Select distinct p.* from producto p, producto_talle pt where pt.stock>0 and p.fecha_hora_baja is null and nombre like %:name%", nativeQuery = true)
    List<Producto> findByNombreContaining(@Param("name") String name);

    List<Producto> findByNombreOrCategoriaId(String name, Long id);

    @Query(value = "Select distinct p.* from producto p, producto_talle pt where pt.stock>0 and p.fecha_hora_baja is null and pt.fk_producto = p.id", nativeQuery = true)
    List<Producto> findAllDisponibles();
    
    //metodo para buscar por marca
    @Query(value = "Select distinct p.* from producto p, producto_talle pt where pt.stock>0 and p.fecha_hora_baja is null and fk_marca = :id", nativeQuery = true)
    List<Producto> findByMarca(@Param("id") Long id);
    
    //buscar por marca y categoria
    @Query(value = "select * from producto p where p.marca like %:nombreMarca% or p.categoria like %:nombreCategoria%", nativeQuery = true)
    List<Producto>findByMarcaOrCategoria(String nombreMarca, String nombreCategoria);
    
    //metodo para traer un solo producto
    @Query(value = "select * from producto p where p.id = :id", nativeQuery = true)
    Producto findByID(@Param("id") Long id);
}
