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
        assertEquals(esperado, real);
        assertTrue(real.equals("Nuria"));

    }

    @Test
    public void testSaldoCuenta() {

        //Arrange
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));

        //Assert
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

}