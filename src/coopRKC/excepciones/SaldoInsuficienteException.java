package coopRKC.excepciones;

/** Se lanza cuando se intenta retirar más dinero del que hay disponible en la cuenta. */
public class SaldoInsuficienteException extends Exception {

    /** Constructor que recibe un mensaje personalizado. param "mensaje" Mensaje que describe el error. */
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}

