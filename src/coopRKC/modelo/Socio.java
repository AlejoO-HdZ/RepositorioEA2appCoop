package coopRKC.modelo;

import java.util.*;
import coopRKC.excepciones.CuentaDuplicadaException;
import coopRKC.excepciones.CuentaNoEncontradaException;

/** 1) MODELO DE DOMINIO
 *  a) CLASE SOCIO, Clase que representa a un socio de la cooperativa. Cada socio tiene un nombre, una cédula única y una lista de cuentas asociadas.
 * Esta clase permite agregar cuentas y consultar la información del socio. */
public class Socio {
    private final String nombre; // Nombre del socio (ENCAPSULAMIENTO, solo puede usarse dentro de la clase. Su valor no puede cambiar una vez asignado.)
    private final String cedula; // Cedula del socio, String por estandar (ENCAPSULAMIENTO)
    private final List<Cuenta> cuentas = new ArrayList<>(); /* Lista de las cuentas del socio */

    /**(ENCAPSULAMIENTO. Ocultar detalles internos de clase Cuenta, solo expone lo nesesairo public)
     * Constructor que inicializa un socio con nombre y cédula.
     * param "nombre" Nombre completo del soci, param "cedula" Número de identificación del socio
     */
    public Socio(String nombre, String cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    /** LISTA.
     * Devuelve una lista inmodificable de las cuentas del socio. Esto protege la integridad de la lista desde el exterior.
     * @return Lista de cuentas del socio */
    public List<Cuenta> getCuentas() {
        return Collections.unmodifiableList(cuentas);
    }

    /**4) GESTION DE ERRORES, Captura de errores.
     * b) Validar que un socio no pueda abrir una cuenta con un número repetido.
     * Verifica si ya existe una cuenta con el mismo número
     * Agrega una cuenta al socio si no existe otra con el mismo número.
     * param cuenta Cuenta a agregar
     * return true si se agregó correctamente, false si ya existía */

    public void agregarCuenta(Cuenta cuenta) throws CuentaDuplicadaException { /*  metodo público que recibe una cuenta como parámetro y un twrow de las coopRKC.excepciones*/
        boolean existe = cuentas.stream() /* Inicia el Stream y Recorre las cuentas del socio */
                .anyMatch(c -> c.getNumero().equals(cuenta.getNumero()));
        if (existe) { /* Verifica si ya existe una cuenta con ese número */
            throw new CuentaDuplicadaException("Ya existe una cuenta con el número " + cuenta.getNumero()); /* Muestra en consola que la cuenta y el número ya está registrado. */
        }
        cuentas.add(cuenta); /* Si no hay duplicado, agrega la cuenta a la lista del socio. */
    }
    /*Metodo para buscar cuenta específica en lista cuentas de socio, usando número cuenta como criterio.
     *
     */
    public Cuenta getCuenta(String numero) throws CuentaNoEncontradaException {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumero().equals(numero)) {
                return cuenta;
            }
        }
        throw new CuentaNoEncontradaException("No se encontró la cuenta con número: " + numero);
    }

}