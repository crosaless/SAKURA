package com.sakura.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sakura.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  
  /*User findByID(Long id);*/
  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

    public boolean existsByPasswordToken(String generadorTokenPassword);

    public Object findByPasswordToken(String token);

    public User findUserByPasswordToken(String token);
  
}
