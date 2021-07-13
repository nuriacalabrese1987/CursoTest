package org.nuria.junit5app.ejemplos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    public void testNombreCuenta() {

        // Arrange
        Cuenta cuenta = new Cuenta("Nuria", new BigDecimal("1000.12345"));
        String esperado = "Nuria";

       // Act
        String real = cuenta.getPersona();

       // Assert (puedo poner Assertions.assertEquals
        assertNotNull(real);
        assertEquals(esperado, real);
        assertTrue(real.equals("Nuria"));

    }

    @Test
    public void testSaldoCuenta() {

        //Arrange
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));

        //Assert
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        Assertions.assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        //mira q sea mayor que cero con el compareTo, devuelve 1 porque es true, como es mayor que cero es true
        Assertions.assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        //Lo mismo pero dando la vuelta
    }

  @Test
  void testReferenciaCuenta() {

      //Arrange
      Cuenta cuenta = new Cuenta("Nuria Calabrese", new BigDecimal("8900.9777"));
      Cuenta cuenta2 = new Cuenta("Nuria Calabrese", new BigDecimal("8900.9777"));

      //Asserts
      //Este compara si son el mismo objeto, no sus valores
      //assertNotEquals(cuenta,cuenta2);
      //Sobreescribimos el metodo y ahora comparamos por sus valores
      assertEquals(cuenta2, cuenta);
  }

  @Test
  void testDebitoCuenta() {
      Cuenta cuenta = new Cuenta("Nuria Calabrese", new BigDecimal("1000.9777"));
      cuenta.debito(new BigDecimal(100));
      assertNotNull(cuenta.getSaldo());
      assertEquals(900, cuenta.getSaldo().intValue());
      //aqui debe ser 900 porque ya hemos restado 100, plain porque tiene q coger el valor del saldo con decimales
      assertEquals("900.9777", cuenta.getSaldo().toPlainString());
  }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Nuria Calabrese", new BigDecimal("1000.9777"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        //aqui debe ser 900 porque ya hemos restado 100, plain porque tiene q coger el valor del saldo con decimales
        assertEquals("1100.9777", cuenta.getSaldo().toPlainString());
    }
}