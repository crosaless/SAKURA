/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.sakura.controllers;

import com.sakura.DTO.DTOContraseña;
import com.sakura.DTO.DTOUserEdit;
import com.sakura.payload.request.LoginRequest;
import com.sakura.payload.request.SignupRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author marci
 */
public class AuthControllerTest {
    
    public AuthControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of authenticateUser method, of class AuthController.
     */
    @Test
    public void testAuthenticateUser() {
        System.out.println("authenticateUser");
        LoginRequest loginRequest = null;
        AuthController instance = new AuthController();
        ResponseEntity expResult = null;
        ResponseEntity result = instance.authenticateUser(loginRequest);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerUser method, of class AuthController.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        SignupRequest signUpRequest = null;
        AuthController instance = new AuthController();
        ResponseEntity expResult = null;
        ResponseEntity result = instance.registerUser(signUpRequest);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editUser method, of class AuthController.
     */
    @Test
    public void testEditUser() {
        System.out.println("editUser");
        DTOUserEdit dtoUser = null;
        AuthController instance = new AuthController();
        ResponseEntity expResult = null;
        ResponseEntity result = instance.editUser(dtoUser);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unsuscribeUser method, of class AuthController.
     */
    @Test
    public void testUnsuscribeUser() {
        System.out.println("unsuscribeUser");
        Long id = null;
        AuthController instance = new AuthController();
        ResponseEntity expResult = null;
        ResponseEntity result = instance.unsuscribeUser(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enviarMailContraseña method, of class AuthController.
     */
    @Test
    public void testEnviarMailContraseña() {
        System.out.println("enviarMailContrase\u00f1a");
        String username = "";
        AuthController instance = new AuthController();
        ResponseEntity expResult = null;
        ResponseEntity result = instance.enviarMailContraseña(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of restablecerContraseña method, of class AuthController.
     */
    @Test
    public void testRestablecerContraseña() {
        System.out.println("restablecerContrase\u00f1a");
        DTOContraseña dto = null;
        AuthController instance = new AuthController();
        ResponseEntity expResult = null;
        ResponseEntity result = instance.restablecerContraseña(dto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
