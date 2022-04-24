package com.curso.junit.udemy.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import com.curso.junit.udemy.exceptions.DineroInsuficienteException;

import org.junit.jupiter.api.Test;

public class CuentaTest {

    @Test
    void test_nombre_cuenta() {
        Cuenta cuenta = new Cuenta("Luis", new BigDecimal("4200.23"));
        String esperado = "Luis";
        String real = cuenta.getPersona();
        assertEquals(esperado, real, () -> "El nombre de la cuenta no es lo que se esperaba");
        assertTrue(real.equals("Luis"), () -> "El nombre de la cuenta no es lo que se esperaba");
    }

    @Test
    void saldo_cuenta() {
        Cuenta cuenta = new Cuenta("Luis", new BigDecimal("4200.23"));
        assertEquals(4200.23, cuenta.getSaldo().doubleValue(), () -> "El saldo de la cuenta no es el correcto");
        // Valor saldo compare to con 0 sea -1(Simpre debe ser mayor igual a 0)
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0, () -> "El saldo de la cuenta debe ser mayor a 0");
        // Valor saldo compare to con 0 sea -1(Simpre debe mayor igual a 0)
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0, () -> "El saldo de la cuenta debe ser mayor a 0");
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta1 = new Cuenta("John Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));

        // COmparar por referencias compara que sean el mismo objeto que referencia el
        // espacio de memoria
        // COmparar por valor compara que sean el mismo valor que referencia al valor
        // sea iguales aunque sean diferenets objetos
        // Funciona sin sobrescribir metodo equals
        // assertNotEquals(cuenta1, cuenta2);

        // Despues de comparar metodo equals por valor en lugar de referencia
        assertEquals(cuenta1, cuenta2, "Los valores de las cuentas no son iguales");
    }

    @Test
    void testDebitoCuenta() {
        // Given - teniendo una cuenta con saldo
        Cuenta cuenta = new Cuenta("Luis", new BigDecimal("1000.17"));
        // When - cuando descontamos un debito a nuestra cuenta
        cuenta.debito(new BigDecimal(100));
        // Then - entonces asertamos que el saldo no sea nulo y si se haya agregado el
        // saldo
        assertNotNull(cuenta.getSaldo(), () -> "El saldo de la cuenta no puede ser nulo");
        assertEquals(900, cuenta.getSaldo().intValue(), () -> "El saldo de la cuenta no es correcto");
    }

    @Test
    void testCreditoCuenta() {
        // Given - teniendo una cuenta con saldo
        Cuenta cuenta = new Cuenta("Luis", new BigDecimal("1000.17"));
        // When - cuando agregamos un credito a nuestra cuenta
        cuenta.credito(new BigDecimal(100));
        // Then - entonces asertamos que el salddo no sea nulo y si se haya agregado el
        // saldo
        assertNotNull(cuenta.getSaldo(), () -> "El saldo no puede ser nulo");
        assertEquals(1100, cuenta.getSaldo().intValue(), () -> "El saldo de la cuenta no es correcto");
        assertEquals("1100.17", cuenta.getSaldo().toPlainString(), () -> "El saldo de la cuenta no es correcto");
    }

    @Test
    void testDineroInsuficienteException() {
        // Given - teniendo una cuenta con saldo
        Cuenta cuenta = new Cuenta("Luis", new BigDecimal("1000.17"));
        // When - cuando intentamos sacar mas saldo del que tenemos a nuestra cuenta
        BigDecimal bigDecimal = new BigDecimal(1500);
        // Then - Asertamos que recibimos una excepcion de dinero insuciente con el
        // mensaje adecuado
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(bigDecimal);
        }, () -> "No se lanzo la excepcion esperada");
        assertEquals("Dinero Insuficiente!!!", exception.getMessage(), () -> "El mesaje de excepcion es incorrecto");
    }

    @Test
    void testTrasferirDineroEntreCuentas() {
        // Given - teniendo dos cuentas y un banco
        Cuenta cuenta1 = new Cuenta("John Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Luis", new BigDecimal("1500.8989"));
        Banco banco = new Banco();
        banco.setNombre("Banorte");
        // When - cuando transferimos 500 de la cuenta 2 a la cuenta 1
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        // Then - entonces tenemos las cantidades sumadas y restadas entre cuentas
        assertAll(
                () -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(),
                        () -> "El saldo de la cuenta no es correcto"),
                () -> assertEquals("3000", cuenta1.getSaldo().toPlainString(),
                        () -> "El saldo de la cuenta no es correcto"));
    }

    @Test
    void testRelacionBancoCuenta() {
        // Given - teniendo dos cuentas y un banco
        Banco banco = new Banco();
        Cuenta cuenta1 = new Cuenta("John Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Luis", new BigDecimal("1500.8989"));
        banco.setNombre("Banorte");
        // When - cuando relacionamos las cuentas
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        // Then - entonces tenemos las cuentas sumadas son la suma y que sus relaciones
        // sean inversas
        // assertEquals(2, banco.getCuentas().size());
        // assertEquals("Banorte", cuenta1.getBanco().getNombre());// Verificamos la
        // relacion inversa
        // assertEquals("Luis", banco.getCuentas()// Asertamos que el cliente tenga
        // cuenta
        // .stream()
        // .filter(cuenta -> cuenta
        // .getPersona()
        // .equals("Luis"))
        // .findFirst()
        // .get()
        // .getPersona());
        // assertTrue(banco.getCuentas()// Assertamos que este presente la cuenta
        // .stream()
        // .filter(cuenta -> cuenta
        // .getPersona()
        // .equals("Luis"))
        // .findFirst()
        // .isPresent());
        // assertTrue(banco.getCuentas()// Assertamos que este presente la cuenta[Mjor
        // forma]
        // .stream()
        // .anyMatch(cuenta -> cuenta
        // .getPersona()
        // .equals("John Doe")));
        // }
        assertAll(
                () -> assertEquals(2, banco.getCuentas().size(), () -> "El numero de cuentas es incorrecto"),
                // Verificamos la relacion inversa,
                () -> assertEquals("Banorte", cuenta1.getBanco().getNombre(),
                        () -> "El nombre del banco es incorrecto"),
                // Asertamos que el cliente tenga cuenta
                () -> assertEquals("Luis", banco.getCuentas()
                        .stream()
                        .filter(cuenta -> cuenta
                                .getPersona()
                                .equals("Luis"))
                        .findFirst()
                        .get()
                        .getPersona(), () -> "El nombre del cliente no existe en la lista"),
                () -> assertTrue(banco.getCuentas()// Assertamos que este presente la cuenta
                        .stream()
                        .filter(cuenta -> cuenta
                                .getPersona()
                                .equals("Luis"))
                        .findFirst()
                        .isPresent(), () -> "El  cliente no existe"),
                () -> assertTrue(banco.getCuentas()// Assertamos que este presente la cuenta[Mejor forma]
                        .stream()
                        .anyMatch(cuenta -> cuenta
                                .getPersona()
                                .equals("John Doe")),
                        () -> "El  cliente no existe"));
    }
}
