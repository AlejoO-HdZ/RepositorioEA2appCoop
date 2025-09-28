package coopRKC.excepciones;

/** Se lanza cuando se intenta agregar una cuenta que ya existe en el socio. */
public class CuentaDuplicadaException extends Exception {

    /** Constructor que recibe un mensaje personalizado. param "mensaje" Mensaje que describe el error. */
    public CuentaDuplicadaException(String mensaje) {
        super(mensaje);
    }
}
