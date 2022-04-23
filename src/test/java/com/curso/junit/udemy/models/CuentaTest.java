package com.curso.junit.udemy.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class CuentaTest {

    @Test
    void test_nombre_cuenta() {
        Cuenta cuenta = new Cuenta("Luis", new BigDecimal("4200.23"));
        String esperado = "Luis";
        String real = cuenta.getPersona();
        assertEquals(esperado, real);
        assertTrue(real.equals("Luis"));
    }

    @Test
    void saldo_cuenta() {
        Cuenta cuenta = new Cuenta("Luis", new BigDecimal("4200.23"));
        assertEquals(4200.23, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO)<0);//Valor saldo compare to con 0 sea -1(Simpre debe amyor igual a 0)
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO)>0);//Valor saldo compare to con 0 sea -1(Simpre debe amyor igual a 0)
    }
}
