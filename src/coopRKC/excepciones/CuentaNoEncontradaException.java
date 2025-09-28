package coopRKC.excepciones;

/** Se lanza cuando no se encuentra una cuenta con el número especificado.*/
public class CuentaNoEncontradaException extends Exception {

    /** Constructor que recibe un mensaje personalizado. param "mensaje" Mensaje que describe el error. */
    public CuentaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}

