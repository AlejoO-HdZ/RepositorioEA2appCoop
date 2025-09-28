package coopRKC.excepciones;

/** Se lanza cuando el monto de una operación es inválido (por ejemplo, negativo o cero).*/
public class MontoInvalidoException extends Exception {

    /** Constructor que recibe un mensaje personalizado. param "mensaje" Mensaje que describe el error. */
    public MontoInvalidoException(String mensaje) {
        super(mensaje);
    }
}