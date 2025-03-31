package dev.nau.kata_cuenta_bancaria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CuentaAhorrosTest {
    private CuentaAhorros cuenta;

    @BeforeEach
    void setUp() {

        cuenta = new CuentaAhorros(12000, 0.05f);
    }

    @Test
    void testCrearCuenta() {

        assertTrue(cuenta.activa, "La cuenta debe estar activa");
    }

    @Test
    void testConsignarCuentaActiva() {

        cuenta.consignar(2000);

        assertThat(cuenta.getSaldo(), is(equalTo(14000f)));
    }

    @Test
    void testConsignarCuentaInactiva() {

        cuenta = new CuentaAhorros(8000, 0.05f);

        cuenta.consignar(2000);

        assertThat(cuenta.getSaldo(), is(equalTo(8000f)));
    }

    @Test
    void testRetirarCuentaActiva() {

        boolean resultado = cuenta.retirar(3000);

        assertTrue(resultado);
        assertThat(cuenta.getSaldo(), is(equalTo(9000f)));
    }

    @Test
    void testRetirarCuentaInactiva() {

        cuenta = new CuentaAhorros(8000, 0.05f);

        boolean resultado = cuenta.retirar(2000);

        assertFalse(resultado);
        assertThat(cuenta.getSaldo(), is(equalTo(8000f)));
    }

    @Test
    void testExtractoMensualCuentaActiva() {

        cuenta.retirar(1000);
        cuenta.retirar(1000);
        cuenta.retirar(1000);
        cuenta.retirar(1000);
        cuenta.retirar(1000);

        cuenta.extractoMensual();

        assertThat(cuenta.comisionMensual, is(equalTo(1000f)));
    }

    @Test
    void testExtractoMensualCuentaInactiva() {

        cuenta = new CuentaAhorros(8000, 0.05f);

        cuenta.extractoMensual();

        assertFalse(cuenta.activa);
    }

    @Test
    void testExtractoMensualCuentaActivaSaldoMayorQue10000() {
        cuenta = new CuentaAhorros(15000, 0.05f);

        assertTrue(cuenta.activa);

        cuenta.extractoMensual();

        assertTrue(cuenta.activa, "La cuenta debe seguir activa con saldo mayor a 10,000");
    }

    @Test
    void testExtractoMensualCuentaInactivaSaldoMenorQue10000() {
        cuenta = new CuentaAhorros(8000, 0.05f);

        assertFalse(cuenta.activa);

        cuenta.extractoMensual();

        assertFalse(cuenta.activa, "La cuenta debe estar inactiva con saldo menor a 10,000");
    }

    @Test
    void testImprimirExtracto() {

        cuenta.consignar(5000);
        cuenta.retirar(2000);

        assertDoesNotThrow(() -> cuenta.imprimir());
    }
}