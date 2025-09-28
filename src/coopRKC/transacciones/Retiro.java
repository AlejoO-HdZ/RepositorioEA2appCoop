package coopRKC.transacciones;

import coopRKC.excepciones.MontoInvalidoException;
import coopRKC.excepciones.SaldoInsuficienteException;
import coopRKC.modelo.Cuenta;


/** /** PUNTO 1) MODELO DE DOMINIO
 *  e) CLASE RETIRO, Clase que representa un retiro cuenta bancaria.*/
public class Retiro implements Transaccion { // Con palabra clave "implements", Clase Retiro Implementa la interfaz Transaccion
    private final Cuenta cuenta;
    private final double monto;

    /**
     * Constructor que inicializa el retiro con cuenta y monto.
     * param cuenta Cuenta origen del retiro
     * param monto Monto a retirar
     */
    public Retiro(Cuenta cuenta, double monto) {
        this.cuenta = cuenta;
        this.monto = monto;
    }

    /** 4) GESTION DE ERRORES, Controlar retiros cuando el saldo no sea suficiente.
     * Ejecuta el retiro si hay saldo suficiente.
     * Muestra un mensaje si el retiro no es posible, a partir de las coopRKC.excepciones
     */
    @Override
    public void ejecutar() {
        try {
            cuenta.retirar(monto);
        } catch (SaldoInsuficienteException | MontoInvalidoException e) {
            System.out.println(" Error en retiro: " + e.getMessage());
        }
    }

    @Override /* Se sobreescribe metodo ejecutar()*/
    public double getMonto() {
        return monto;
    }
}
