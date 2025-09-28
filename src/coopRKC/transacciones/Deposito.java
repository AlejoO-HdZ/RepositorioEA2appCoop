package coopRKC.transacciones;

import coopRKC.excepciones.MontoInvalidoException;
import coopRKC.modelo.Cuenta;

/** /** PUNTO 1) MODELO DE DOMINIO
 *  e) CLASE DEPOSITO, Clase que representa un depósito cuenta bancaria.*/
public class Deposito implements Transaccion { // Con palabra clave "implements", Clase Deposito Implementa la interfaz Transaccion
    private final Cuenta cuenta; /* Cuenta del depósito */
    private final double monto; /* Monto a depositar */
    /**
     * Constructor que inicializa el depósito con cuenta y monto.
     * param "cuenta" Cuenta destino del depósito
     * param "monto" Monto a depositar. */
    public Deposito(Cuenta cuenta, double monto) {
        this.cuenta = cuenta;
        this.monto = monto;
    }

    /** Herencia y Polimorfismo, "Deposito" implementa el metodo ejecutar(), definido en la interfaz "Transaccion".
     * Ejecuta el depósito en la cuenta. */

    @Override /* Se sobreescribe metodo ejecutar() */
    public void ejecutar() {
        try {
            cuenta.depositar(monto); /* Llama al metodo depositar de la cuenta. */
        } catch (MontoInvalidoException e) {
            System.out.println(" Error en depósito: " + e.getMessage());
        }
    }

    @Override /* Se sobreescribe metodo getmonto()*/
    public double getMonto() {
        return monto;
    }
}
