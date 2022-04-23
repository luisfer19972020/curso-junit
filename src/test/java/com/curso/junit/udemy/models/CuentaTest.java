package com.curso.junit.udemy.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class CuentaTest {
    
    @Test
    void test_nombre_cuenta(){
        Cuenta cuenta= new Cuenta("Luis",new BigDecimal("4200.23"));
        String esperado ="Luis";
        String real = cuenta.getPersona();
        assertEquals(esperado, real);
        assertTrue(real.equals("Luis"));
    }
}
