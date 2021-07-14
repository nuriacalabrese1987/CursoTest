package org.nuria.junit5app.ejemplos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nuria.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.nuria.junit5app.ejemplos.models.Banco;
import org.nuria.junit5app.ejemplos.models.Cuenta;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

  @Test
  @DisplayName("Prueba de titular de la cuenta ")
  public void testNombreCuenta() {

    // Arrange
    Cuenta cuenta = new Cuenta("Nuria", new BigDecimal("1000.12345"));
    String esperado = "Nuria";

    // Act
    String real = cuenta.getPersona();

    // Assert (puedo poner Assertions.assertEquals, se puede añadir un mensaje
    assertNotNull(real, () -> "El nombre de la cuenta no es el esperado");
    assertEquals(esperado, real);
    assertTrue(real.equals("Nuria"));
  }

  @Test
  //@Disabled para deshabilitar la prueba y fail(); para forzar que falle 
  public void testSaldoCuenta() {

    // Arrange
    Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));

    // Assert
    assertNotNull(cuenta.getSaldo());
    assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
    Assertions.assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    // mira q sea mayor que cero con el compareTo, devuelve 1 porque es true, como es mayor que cero
    // es true
    Assertions.assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    // Lo mismo pero dando la vuelta
  }

  @Test
  void testReferenciaCuenta() {

    // Arrange
    Cuenta cuenta = new Cuenta("Nuria Calabrese", new BigDecimal("8900.9777"));
    Cuenta cuenta2 = new Cuenta("Nuria Calabrese", new BigDecimal("8900.9777"));

    // Asserts
    // Este compara si son el mismo objeto, no sus valores
    // assertNotEquals(cuenta,cuenta2);
    // Sobreescribimos el metodo y ahora comparamos por sus valores
    assertEquals(cuenta2, cuenta);
  }

  @Test
  void testDebitoCuenta() {
    Cuenta cuenta = new Cuenta("Nuria Calabrese", new BigDecimal("1000.9777"));
    cuenta.debito(new BigDecimal(100));
    assertNotNull(cuenta.getSaldo());
    assertEquals(900, cuenta.getSaldo().intValue());
    // aqui debe ser 900 porque ya hemos restado 100, plain porque tiene q coger el valor del saldo
    // con decimales
    assertEquals("900.9777", cuenta.getSaldo().toPlainString());
  }

  @Test
  void testCreditoCuenta() {
    Cuenta cuenta = new Cuenta("Nuria Calabrese", new BigDecimal("1000.9777"));
    cuenta.credito(new BigDecimal(100));
    assertNotNull(cuenta.getSaldo());
    assertEquals(1100, cuenta.getSaldo().intValue());
    // aqui debe ser 900 porque ya hemos restado 100, plain porque tiene q coger el valor del saldo
    // con decimales
    assertEquals("1100.9777", cuenta.getSaldo().toPlainString());
  }

  @Test
  void testDineroInsuficienteExceptionCuenta() {
    Cuenta cuenta = new Cuenta("Nuria Calabrese", new BigDecimal("1000.9777"));
    Exception exception =
        assertThrows(
            DineroInsuficienteException.class,
            () -> {
              cuenta.debito(new BigDecimal(1500));
            });
    String actual = exception.getMessage();
    String esperado = "Dinero Insuficiente";
    assertEquals(esperado, actual);
  }

  @Test
  void testTransferirDineroCuentas() {
    Cuenta cuenta1 = new Cuenta("Nuria Calabrese", new BigDecimal("2500"));
    Cuenta cuenta2 = new Cuenta("Barbara Calabrese", new BigDecimal("1500.8989"));
    Banco banco = new Banco();
    banco.setNombre("Banco del estado");
    banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
    assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
    assertEquals("3000", cuenta1.getSaldo().toPlainString());
  }

  @Test
  void testRelacionBancoCuentas() {
    Cuenta cuenta1 = new Cuenta("Nuria Calabrese", new BigDecimal("2500"));
    Cuenta cuenta2 = new Cuenta("Barbara Calabrese", new BigDecimal("1500.8989"));
    Banco banco = new Banco();
    banco.addCuenta(cuenta1);
    banco.addCuenta(cuenta2);
    banco.setNombre("Banco del Estado");
    banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

    assertAll(
        () -> { // Se comprueba que la transferencia se ha hecho
          assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        },
        () -> {
          assertEquals("3000", cuenta1.getSaldo().toPlainString());
        },
        () -> { // Se comprueba la relacion entre cuentas y bancos
          assertEquals(2, banco.getCuentas().size());
        },
        () -> {
          assertEquals("Banco del Estado", cuenta1.getBanco().getNombre());
        },
        () -> { // Comprobamos que el banco tiene una cuenta con el nombre que queremos
          assertEquals(
              "Nuria Calabrese",
              banco.getCuentas().stream()
                  .filter(c -> c.getPersona().equals("Nuria Calabrese"))
                  .findFirst()
                  .get()
                  .getPersona());
        },
        () -> {
          assertTrue(
              banco.getCuentas().stream()
                  .anyMatch(c -> c.getPersona().equals("Barbara Calabrese")));
        });
  }
}
