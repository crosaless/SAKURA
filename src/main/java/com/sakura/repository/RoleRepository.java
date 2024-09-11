package com.sakura.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sakura.Entities.Role;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface RoleRepository extends BaseRepository<Role,Long>{ //JpaRepository<Role, Long> {
  Optional<Role> findByName(String name);
  
  @Modifying
  @Query(value = "delete from roles_vistas where role_id = :id", nativeQuery = true)
    public void deleteFromJoinTable(@Param("id")Long id);
    
   @Modifying
   @Query(value= "update roles set name = :name where id = :id",nativeQuery = true)
   public void updateRole(@Param("id")Long id,@Param("name")String name);
   
   @Modifying
   @Query(value= "insert into roles_vistas values (:idRol,:idVista)",nativeQuery = true)
   public void updateVista(@Param("idRol")Long id, @Param("idVista")Long name);
   

  @Override
  @Query(value="select * from roles where fecha_hora_baja is null", nativeQuery=true)
  public List<Role> findAll();
}
