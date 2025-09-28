package coopRKC.excepciones;

/** Excepción que se lanza cuando no se encuentra un socio en la cooperativa.*/
public class SocioNoEncontradoException extends Exception {

    /** Constructor que recibe un mensaje personalizado. param "mensaje" Mensaje que describe el error. */
    public SocioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
