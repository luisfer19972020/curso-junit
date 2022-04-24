package com.curso.junit.udemy.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.curso.junit.udemy.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaTest {
    Cuenta cuenta;

    @BeforeEach
    void beforeEach() {
        this.cuenta = new Cuenta("Luis", new BigDecimal("4200.23"));
        System.out.println("Iniciando el metodo de prueba");
    }

    @AfterEach
    void afterEach() {
        System.out.println("Finalizando el metodo de prueba");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Inciando la clase");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Cerrando la clase");
    }

    @Nested
    @DisplayName("Los getters de cuenta se ejecutan correctamente")
    class CuentaSaldoTest {
        @Test
        @DisplayName("Una cuenta puede retornar el nombre")
        void test_nombre_cuenta() {
            String esperado = "Luis";
            String real = cuenta.getPersona();
            assertEquals(esperado, real, () -> "El nombre de la cuenta no es lo que se esperaba");
            assertTrue(real.equals("Luis"), () -> "El nombre de la cuenta no es lo que se esperaba");
        }

        @Test
        @DisplayName("El saldo de una cuenta siempre es mayor o igual a cero")
        void saldo_cuenta() {
            assertEquals(4200.23, cuenta.getSaldo().doubleValue(), () -> "El saldo de la cuenta no es el correcto");
            // Valor saldo compare to con 0 sea -1(Simpre debe ser mayor igual a 0)
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0,
                    () -> "El saldo de la cuenta debe ser mayor a 0");
            // Valor saldo compare to con 0 sea -1(Simpre debe mayor igual a 0)
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0,
                    () -> "El saldo de la cuenta debe ser mayor a 0");
        }

        @Test
        @DisplayName("Una cuenta puede se comparada por valor")
        void test_referencia_cuenta() {
            Cuenta cuenta = new Cuenta("Luis", new BigDecimal("4200.23"));
            Cuenta cuenta2 = new Cuenta("Luis", new BigDecimal("4200.23"));

            // COmparar por referencias compara que sean el mismo objeto que referencia el
            // espacio de memoria
            // COmparar por valor compara que sean el mismo valor que referencia al valor
            // sea iguales aunque sean diferenets objetos
            // Funciona sin sobrescribir metodo equals
            // assertNotEquals(cuenta, cuenta2);

            // Despues de comparar metodo equals por valor en lugar de referencia
            assertEquals(cuenta, cuenta2, "Los valores de las cuentas no son iguales");
        }

    }

    @Nested
    @DisplayName("Las operaciones de cuenta se ejecutan correctamente")
    class CuentaOperacionesTest {
        @Test
        @DisplayName("Una cuenta puede gestionar un debito")
        void test_debito_cuenta() {
            // Given - teniendo una cuenta con saldo

            // When - cuando descontamos un debito a nuestra cuenta
            cuenta.debito(new BigDecimal("100"));
            // Then - entonces asertamos que el saldo no sea nulo y si se haya agregado el
            // saldo
            assertNotNull(cuenta.getSaldo(), () -> "El saldo de la cuenta no puede ser nulo");
            assertEquals(4100, cuenta.getSaldo().intValue(), () -> "El saldo de la cuenta no es correcto");
        }

        @Test
        @DisplayName("Una cuenta puede gestionar un credito")
        void test_credito_cuenta() {
            // Given - teniendo una cuenta con saldo

            // When - cuando agregamos un credito a nuestra cuenta
            cuenta.credito(new BigDecimal(100));
            // Then - entonces asertamos que el salddo no sea nulo y si se haya agregado el
            // saldo
            assertNotNull(cuenta.getSaldo(), () -> "El saldo no puede ser nulo");
            assertEquals(4300, cuenta.getSaldo().intValue(), () -> "El saldo de la cuenta no es correcto");
            assertEquals("4300.23", cuenta.getSaldo().toPlainString(), () -> "El saldo de la cuenta no es correcto");
        }

        @Test
        @DisplayName("Se puede transferir saldo entre cuentas")
        // @Disabled//Deshabilitar test en lo que solucionamos algo
        void test_trasferir_dinero_entre_cuentas() {
            // fail();//Simula un fallo
            // Given - teniendo dos cuentas y un banco
            Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("2500"));
            Banco banco = new Banco();
            banco.setNombre("Banorte");
            // When - cuando transferimos 500 de la cuenta 2 a la cuenta 1
            banco.transferir(cuenta, cuenta2, new BigDecimal(500));
            // Then - entonces tenemos las cantidades sumadas y restadas entre cuentas
            assertAll(
                    () -> assertEquals("3700.23", cuenta.getSaldo().toPlainString(),
                            () -> "El saldo de la cuenta no es correcto"),
                    () -> assertEquals("3000", cuenta2.getSaldo().toPlainString(),
                            () -> "El saldo de la cuenta no es correcto"));
        }
    }

    @Nested
    @DisplayName("Las operaciones de cuenta y banco se ejecutan correctamente")
    class CuentaBancoTest {
        @Test
        @DisplayName("Una cuenta y un banco pueden relacionarse bidereccionalmente")
        void test_relacion_banco_cuenta() {
            // Given - teniendo dos cuentas y un banco
            Banco banco = new Banco();
            Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("2500"));
            banco.setNombre("Banorte");
            // When - cuando relacionamos las cuentas
            banco.addCuenta(cuenta);
            banco.addCuenta(cuenta2);
            // Then - entonces tenemos las cuentas sumadas son la suma y que sus relaciones
            // sean inversas
            // assertEquals(2, banco.getCuentas().size());
            // assertEquals("Banorte", cuenta2.getBanco().getNombre());// Verificamos la
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
                    () -> assertEquals("Banorte", cuenta2.getBanco().getNombre(),
                            () -> "El nombre del banco es incorrecto"),
                    // Asertamos que el cliente tenga cuenta
                    () -> assertEquals("Luis", banco.getCuentas()
                            .stream()
                            .filter(c -> c
                                    .getPersona()
                                    .equals("Luis"))
                            .findFirst()
                            .get()
                            .getPersona(), () -> "El nombre del cliente no existe en la lista"),
                    () -> assertTrue(banco.getCuentas()// Assertamos que este presente la cuenta
                            .stream()
                            .filter(c -> c
                                    .getPersona()
                                    .equals("Luis"))
                            .findFirst()
                            .isPresent(), () -> "El  cliente no existe"),
                    () -> assertTrue(banco.getCuentas()// Assertamos que este presente la cuenta[Mejor forma]
                            .stream()
                            .anyMatch(c -> c
                                    .getPersona()
                                    .equals("John Doe")),
                            () -> "El  cliente no existe"));
        }
    }

    @Nested
    class CuentaExcepcionesTest {
        @Test
        @DisplayName("La cuenta puede generar un excepcion por saldo insuficiente")
        void test_dinero_insuficiente_exception() {
            // Given - teniendo una cuenta con saldo
            // When - cuando intentamos sacar mas saldo del que tenemos a nuestra cuenta
            BigDecimal bigDecimal = new BigDecimal(5000);
            // Then - Asertamos que recibimos una excepcion de dinero insuciente con el
            // mensaje adecuado
            Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
                cuenta.debito(bigDecimal);
            }, () -> "No se lanzo la excepcion esperada");
            assertEquals("Dinero Insuficiente!!!", exception.getMessage(),
                    () -> "El mesaje de excepcion es incorrecto");
        }
    }

    @Nested
    class SistemaOperativoTest {
        // Pruebas unitarias para que se ejecuten en ciertos Sistemas operativos o
        // versiones de java o en ambiente de produccion o desarrollo
        @Test
        @EnabledOnOs(OS.WINDOWS)
        void testWindows() {
        }

        @Test
        @EnabledOnOs({ OS.LINUX, OS.MAC })
        void testLinuxMac() {
        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        void testNoWindows() {
        }
    }

    @Nested
    class JavaVersionTest {
        @Test
        @EnabledOnJre(JRE.JAVA_8)
        void testSoloJDK8() {
        }

        @Test
        @DisabledOnJre(JRE.JAVA_8)
        void testNoJDK8() {
        }
    }

    @Nested
    class SystemPropertiesTest {
        @Test
        // @Disabled
        void imprimirSystemProperties() {
            Properties properties = System.getProperties();
            properties.forEach((k, v) -> {
                System.out.println(k + ":" + v);
            });

        }

        @Test
        @EnabledIfSystemProperty(named = "java.specification.version", matches = "17")
        public void testSoloJava17() {
        }

        @Test
        @EnabledIfSystemProperty(named = "user.country", matches = "MX")
        public void testSoloMX() {
        }

        @Test
        @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
        public void testSolo64Bits() {
        }
    }

    @Nested
    class VariablesAmbienteTest {
        @Test
        // @Disabled
        void imprimirVariablesAmbiente() {
            Map<String, String> variables = System.getenv();
            variables.forEach((k, v) -> {
                System.out.println(k + ":" + v);
            });
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-17.0.1.12-hotspot.*")
        void testEnMiJavaHome() {
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "4")
        void testOnly4Processors() {
        }
    }

    @Nested
    class pruebasAssumeTest {
        // Podemos ejecutar un test con una condicion por ejemplo que se ejecute un test
        // solo si por ejemplo hay un servcio levantado o no
        @Test
        @DisplayName("El saldo de una cuenta siempre es mayor o igual a cero en W11")
        void saldo_cuenta_w11() {
            boolean esWindows11 = "Windows 11".equals(System.getProperty("os.name"));// os.name:Windows 11
            assumeTrue(esWindows11);// Dependiendo de esto se ejecuta o se deshabilita el metodo
            assertEquals(4200.23, cuenta.getSaldo().doubleValue(), () -> "El saldo de la cuenta no es el correcto");
            // Valor saldo compare to con 0 sea -1(Simpre debe ser mayor igual a 0)
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0,
                    () -> "El saldo de la cuenta debe ser mayor a 0");
            // Valor saldo compare to con 0 sea -1(Simpre debe mayor igual a 0)
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0,
                    () -> "El saldo de la cuenta debe ser mayor a 0");
        }

        // Asuminedo que si se cumpla algo ejecuta o no un trozo del test
        @Test
        @DisplayName("El saldo de una cuenta siempre es mayor o igual a cero en W11")
        void saldo_cuenta_controlado_w11() {
            boolean esWindows11 = "Windows 11".equals(System.getProperty("os.name"));// os.name:Windows 11
            assumingThat(esWindows11, () -> {
                assertEquals(4200.23, cuenta.getSaldo().doubleValue(), () -> "El saldo de la cuenta no es el correcto");
                // Valor saldo compare to con 0 sea -1(Simpre debe ser mayor igual a 0)
                assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0,
                        () -> "El saldo de la cuenta debe ser mayor a 0");
            });// Dependiendo de esto se ejecuta o se deshabilita el metodo
               // Valor saldo compare to con 0 sea -1(Simpre debe mayor igual a 0)
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0,
                    () -> "El saldo de la cuenta debe ser mayor a 0");
        }
    }

    @Nested
    class pruebasRepetidasTest {
        @DisplayName("Probando Debito Cuenta Repetir!")
        @RepeatedTest(value = 5, name = "{displayName} - Repeticion numero {currentRepetition}")
        void test_debito_cuenta_repetido(RepetitionInfo info) {// Para usar la repiticon que vamos
            if (info.getCurrentRepetition() == 2) {
                System.out.println("Estamos en la repiticion: " + info.getCurrentRepetition());
            }
            // Given - teniendo una cuenta con saldo
            // When - cuando descontamos un debito a nuestra cuenta
            cuenta.debito(new BigDecimal("100"));
            // Then - entonces asertamos que el saldo no sea nulo y si se haya agregado el
            // saldo
            assertNotNull(cuenta.getSaldo(), () -> "El saldo de la cuenta no puede ser nulo");
            assertEquals(4100, cuenta.getSaldo().intValue(), () -> "El saldo de la cuenta no es correcto");
        }
    }

    @Nested
    class PruebasParametrizadasTest {
        @ParameterizedTest(name = "Numero: {index} ejecutando el valor {0} - {argumentsWithNames}")
        @DisplayName("Una cuenta puede gestionar un debito")
        @ValueSource(strings = { "100", "200", "300", "500", "700", "1000" })
        void test_debito_value_source(String dato) {
            // Given - teniendo una cuenta con saldo

            // When - cuando descontamos un debito a nuestra cuenta
            cuenta.debito(new BigDecimal(dato));
            // Then - entonces asertamos que el saldo no sea nulo y si se haya agregado el
            // saldo
            assertAll(
                    () -> assertNotNull(cuenta.getSaldo(), () -> "El saldo de la cuenta no puede ser nulo"),
                    () -> assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0,
                            () -> "El saldo de la cuenta no es correcto"));
        }

        @ParameterizedTest(name = "Numero: {index} ejecutando el valor {0} - {argumentsWithNames}")
        @DisplayName("Una cuenta puede gestionar un debito")
        @CsvSource({ "1,100", "2,200", "3,300", "4,500", "5,700", "6,1000" })
        void test_debito_csv_source(String index, String dato) {
            // Given - teniendo una cuenta con saldo
            System.out.println(index + "," + dato);
            // When - cuando descontamos un debito a nuestra cuenta
            cuenta.debito(new BigDecimal(dato));
            // Then - entonces asertamos que el saldo no sea nulo y si se haya agregado el
            // saldo
            assertAll(
                    () -> assertNotNull(cuenta.getSaldo(), () -> "El saldo de la cuenta no puede ser nulo"),
                    () -> assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0,
                            () -> "El saldo de la cuenta no es correcto"));
        }

        @ParameterizedTest(name = "Numero: {index} ejecutando el valor {0} - {argumentsWithNames}")
        @DisplayName("Una cuenta puede gestionar un debito")
        @CsvFileSource(resources = "/data.csv")
        void test_debito_csvFile_source(String dato) {
            // Given - teniendo una cuenta con saldo
            System.out.println(dato);
            // When - cuando descontamos un debito a nuestra cuenta
            cuenta.debito(new BigDecimal(dato));
            // Then - entonces asertamos que el saldo no sea nulo y si se haya agregado el
            // saldo
            assertAll(
                    () -> assertNotNull(cuenta.getSaldo(), () -> "El saldo de la cuenta no puede ser nulo"),
                    () -> assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0,
                            () -> "El saldo de la cuenta no es correcto"));
        }

        @ParameterizedTest(name = "Numero: {index} ejecutando el valor {0} - {argumentsWithNames}")
        @DisplayName("Una cuenta puede gestionar un debito")
        @MethodSource("montList")
        void test_debito_method_source(String dato) {
            // Given - teniendo una cuenta con saldo
            System.out.println(dato);
            // When - cuando descontamos un debito a nuestra cuenta
            cuenta.debito(new BigDecimal(dato));
            // Then - entonces asertamos que el saldo no sea nulo y si se haya agregado el
            // saldo
            assertAll(
                    () -> assertNotNull(cuenta.getSaldo(), () -> "El saldo de la cuenta no puede ser nulo"),
                    () -> assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0,
                            () -> "El saldo de la cuenta no es correcto"));
        }

        private static List<String> montList(){
            return Arrays.asList("100", "200", "300", "500", "700", "1000");
        }
    }
}
