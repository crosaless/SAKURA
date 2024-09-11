package com.sakura.Entities;


import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Setter
@Getter
@Entity
@Audited
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email"),
       @UniqueConstraint(columnNames = "passwordToken")
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  @Column(unique = true)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_rol")
  private Set<Role> roles;
  
  private LocalDateTime fechaHoraBaja;
  @Column(unique=true)
  private String passwordToken;
  
  private LocalDateTime fechaExpToken;

  public User() {
  }

  public User(String username, String email, String password, Set<Role> roles) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.roles = roles;
  }

}
