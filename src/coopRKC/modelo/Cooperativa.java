package coopRKC.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import coopRKC.excepciones.*;

/** 2) GESTION DE LA COOPERATIVA.
 * Clase que representa la cooperativa financiera. Administra socios, sus cuentas y operaciones agregadas. */

public class Cooperativa {
    private final List<Socio> socios = new ArrayList<>();

    /**
     * a) Metodo para registrar nuevos socios.
     * Registra un nuevo socio en la cooperativa. param "nombre" Nombre del socio y "cedula" Cédula del socio.
     */
    public void registrarSocio(String nombre, String cedula) {
        socios.add(new Socio(nombre, cedula));
    }

    /**
     * Busca un socio por su cédula.
     */
    public Socio buscarSocioPorCedula(String cedula) throws SocioNoEncontradoException {
        return socios.stream() // recorre todos los socios registrados y Convierte la lista Socio en un flujo de datos Stream .
                .filter(s -> s.getCedula().equals(cedula)) // Metodo de filtrado con expresión lambda para filtrar cedula.
                .findFirst() // Devuelve el primer elemento que pasó el filtro.
                .orElseThrow(() -> new SocioNoEncontradoException("No se encontró el socio con cédula " + cedula)); // Metodo con Lambda para manejar ausencia de resultado.
    }

    /**
     * ENTREGABLE 5. APLICAR INTERES
     * Aplica interés a TODAS las cuentas de TODOS los socios, si la cuenta es de tipo CuentaAhorros.
     *
     * @param "tasa" Tasa de interés a aplicar (por ejemplo, 0.05 para 5%), @return Número total de cuentas de ahorro actualizadas
     *               (POLIMORFISMO). Permitir que Cuenta se comporte de distintas formas según su tipo real, aunque se trate como su tipo base.
     */
    public int aplicarInteresATodas(double tasa) {
        return socios.stream() // Recorre todos los socios en secuencia para los metodos y operaciones necesarias
                .flatMap(s -> s.getCuentas().stream()) // Accede a todas las cuentas de cada socio
                .filter(c -> c instanceof CuentaAhorros) // Filtra solo cuentas de ahorro. Polimorfismo que accede al metodo aplicarInteres().
                .peek(c -> ((CuentaAhorros) c).aplicarInteres(tasa)) // mira los elementos del stream y Aplica el interés con casting con expresion lambda
                .mapToInt(c -> 1) // Cuenta cada aplicación
                .sum(); // Devuelve el total de cuentas actualizadas
    }

    /**
     * Aplica interés a CADA CUENTA de ahorro individualmente. Muestra el nombre del socio, número de cuenta y nuevo saldo tras aplicar el interés.
     *
     * @param tasa Tasa de interés a aplicar (por ejemplo, 0.05 para 5%)
     */
    public void aplicarInteresUnaPorUna(double tasa) {
        socios.stream() // Recorre todos los socios
                .flatMap(socio -> socio.getCuentas().stream()
                        .filter(c -> c instanceof CuentaAhorros) // Filtra solo cuentas de ahorro
                        .map(c -> Map.entry(socio.getNombre(), (CuentaAhorros) c))) // Asocia socio con cuenta
                .forEach(entry -> {
                    String nombre = entry.getKey();
                    CuentaAhorros cuenta = entry.getValue();
                    cuenta.aplicarInteres(tasa); // Aplica el interés
                    System.out.printf(" Interés aplicado a cuenta %s del socio %s. Nuevo saldo: $%.2f%n",
                            cuenta.getNumero(), nombre, cuenta.getSaldo());
                });
    }

    /**
     * Aplica interés a UNA SOLA CUENTA ESPECIFICA, si existe y es de tipo CuentaAhorros. útil para actualizaciones puntuales desde el menú.
     *
     * @param numero Número de cuenta a actualizar, * @param tasa Tasa de interés a aplicar (por ejemplo, 0.05 para 5%)
     * @return true si se aplicó el interés correctamente, false si no se encontró o no era de ahorro
     */
    public boolean aplicarInteresCuentaEspecifica(String numero, double tasa) {
        return socios.stream() // Recorre todos los socios
                .flatMap(s -> s.getCuentas().stream()) // Accede a todas las cuentas
                .filter(c -> c instanceof CuentaAhorros &&
                        c.getNumero().equalsIgnoreCase(numero)) // Busca por número y tipo
                .peek(c -> ((CuentaAhorros) c).aplicarInteres(tasa)) // Aplica el interés
                .findFirst() // Solo una cuenta
                .isPresent(); // Devuelve true si se aplicó
    }

    /**
     * b) Metodo para listar todos los socios registrados.
     * return Lista de socios
     */
    public List<Socio> getSocios() {
        return Collections.unmodifiableList(socios); // Devuelve una lista inmodificable de los socios registrados.
    }
}