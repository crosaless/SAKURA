package com.sakura.controllers;

import com.sakura.DTO.DTOContraseña;
import com.sakura.DTO.DTOUserEdit;
import com.sakura.Entities.Role;
import com.sakura.Entities.User;
import com.sakura.Services.PasswordRecoveryService;
import com.sakura.repository.CarritoRepository;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakura.security.services.UserFactory;
import com.sakura.payload.request.LoginRequest;
import com.sakura.payload.request.SignupRequest;
import com.sakura.payload.response.JwtResponse;
import com.sakura.payload.response.MessageResponse;
import com.sakura.repository.RoleRepository;
import com.sakura.repository.UserRepository;
import com.sakura.security.jwt.JwtUtils;
import com.sakura.security.services.UserDetailsImpl;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    CarritoRepository repo;
    
    @Autowired
    PasswordRecoveryService passwordService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList()); 
        User user = this.userRepository.findByUsername(loginRequest.getUsername()).get();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: El nombre de usuario ya existe!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email ya esta en uso!"));
        }

        // Create new user account
        try {
            UserFactory.getUserFactory()
                    .createUser(signUpRequest.getUsername(),
                            encoder.encode(signUpRequest.getPassword()),
                            signUpRequest.getEmail(),
                            signUpRequest.getRoles());

            return ResponseEntity.ok(new MessageResponse("Usuario registrado exitosamente!"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Se produjo un error al intentar registrar el usuario");
        }
    }

    @PostMapping("/editUser")
    public ResponseEntity<?> editUser(@Valid @RequestBody DTOUserEdit dtoUser) {
        try {
            System.out.println(dtoUser.getId());
            if (userRepository.existsById(dtoUser.getId())) {
                User user = userRepository.findById(dtoUser.getId()).get();
                
                if (!user.getUsername().equals(dtoUser.getUsername())) {
                    if(!userRepository.existsByUsername(dtoUser.getUsername())){
                        user.setUsername(dtoUser.getUsername());
                    }
                    else{
                    throw new Exception("El nombre de usuario ya existe");
                    }
                }
                
                if (!user.getEmail().equals(dtoUser.getEmail())) {
                    if(!userRepository.existsByEmail(dtoUser.getEmail())){
                        user.setEmail(dtoUser.getEmail());
                    }
                    else{
                    throw new Exception("El email ya se encuentra registrado a otro usuario");
                    }
                }
                if (!dtoUser.getPassword().equals(' ')) {
                    user.setPassword(encoder.encode(dtoUser.getPassword()));
                }
                Set<Role> nuevosRoles = new HashSet<Role>();
                for (String strRol : dtoUser.getRol()){
                    Role nuevoRol = this.roleRepository.findByName(strRol).get();
                    nuevosRoles.add(nuevoRol);
                }
                user.setRoles(nuevosRoles);
                userRepository.save(user);
                return ResponseEntity.ok(new MessageResponse("Usuario editado exitosamente!"));
            
        }
        throw new Exception("El id de usuario no existe");

    } catch (Exception e) {
        System.out.println(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    }
    @GetMapping("/darBaja")
    public ResponseEntity<?> unsuscribeUser(@RequestParam Long id) {
        try {
            User user = this.userRepository.findById(id).get();
            user.setFechaHoraBaja(LocalDateTime.now());
            this.userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Usuario dado de baja exitosamente!"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/RestablecerContraseña/enviarMail")
    public ResponseEntity<?> enviarMailContraseña(@RequestParam String username) {
        try {
           this.passwordService.enviarMail(username);
            return ResponseEntity.ok(new MessageResponse("email enviado exitosamente!"));
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/contraseña/restablecer")
    public ResponseEntity<?> restablecerContraseña(@RequestBody DTOContraseña dto) {
        try {
           this.passwordService.restablecer(dto.getToken(),dto.getNewPassword());
            return ResponseEntity.ok(new MessageResponse("email enviado exitosamente!"));
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
