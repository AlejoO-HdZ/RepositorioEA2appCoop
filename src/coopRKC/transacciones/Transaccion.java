package coopRKC.transacciones;

/** PUNTO 1) MODELO DE DOMINIO
 *  d) INTERFAZ TRANSACCION, Interfaz que define el comportamiento de una transacción financiera.
 * Las clases que la implementan deben definir cómo se ejecuta la transacción y cuál es su monto. */
public interface Transaccion {

    void ejecutar(); // d) METODO EJECUTAR(), Ejecuta la transacción (depósito o retiro) sobre una cuenta.

    double getMonto(); // d) METODO GETMONTO(), Devuelve el monto involucrado en la transacción.
}

