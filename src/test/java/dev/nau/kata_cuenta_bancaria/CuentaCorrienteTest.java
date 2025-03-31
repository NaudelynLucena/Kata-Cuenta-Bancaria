package dev.nau.kata_cuenta_bancaria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuentaCorrienteTest {
    private CuentaCorriente cuenta;

    @BeforeEach
    void setUp() {
        cuenta = new CuentaCorriente(5000, 0.05f);
    }

    @Test
    void testRetiroDentroDelSaldo() {

        boolean resultado = cuenta.retirar(2000);

        assertTrue(resultado, "El retiro debe ser exitoso.");
        assertEquals(3000, cuenta.getSaldo(), "El saldo debe ser 3000.");
    }

    @Test
    void testRetiroConSobregiro() {
        boolean resultado = cuenta.retirar(7000);

        assertTrue(resultado, "El retiro debe ser exitoso incluso con sobregiro.");
        assertEquals(0, cuenta.getSaldo(), "El saldo debe ser 0 después del retiro.");
        assertEquals(2000, cuenta.sobregiro, "El sobregiro debe ser 2000.");
    }

    @Test
    void testConsignarSinPagarTodoElSobregiro() {
        cuenta.retirar(7000);

        cuenta.consignar(1000);

        assertEquals(1000, cuenta.getSaldo(), "El saldo debe ser 1000 después de la consignación parcial.");
        assertEquals(1000, cuenta.sobregiro, "El sobregiro debe ser 1000 aún.");
    }

    @Test
    void testImprimirExtracto() {

        cuenta.retirar(2000);
        cuenta.consignar(1000);

        assertDoesNotThrow(() -> cuenta.imprimir());
    }

    @Test
    void testRetiroExcesivoSinSuficienteSaldo() {
        boolean resultado = cuenta.retirar(10000);

        assertTrue(resultado, "El retiro debe ser exitoso, ya que se utiliza el sobregiro.");
        assertEquals(0, cuenta.getSaldo(), "El saldo debe ser 0 después del retiro.");
        assertEquals(5000, cuenta.sobregiro, "El sobregiro debe ser 5000.");
    }

    @Test
    void testConsignarExcesivoSinSobregiro() {

        cuenta.consignar(5000);

        assertEquals(10000, cuenta.getSaldo(), "El saldo debe ser 10000 después de la consignación.");
        assertEquals(0, cuenta.sobregiro, "El sobregiro debe ser 0.");
    }
}