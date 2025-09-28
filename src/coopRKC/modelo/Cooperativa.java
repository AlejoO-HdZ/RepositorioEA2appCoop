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

    /** a) Metodo para registrar nuevos socios.
     * Registra un nuevo socio en la cooperativa. param "nombre" Nombre del socio y "cedula" Cédula del socio. */
    public void registrarSocio(String nombre, String cedula) {
        socios.add(new Socio(nombre, cedula));
    }
    /** Busca un socio por su cédula.*/
    public Socio buscarSocioPorCedula(String cedula) throws SocioNoEncontradoException {
        return socios.stream() // recorre todos los socios registrados y Convierte la lista Socio en un flujo de datos Stream .
                .filter(s -> s.getCedula().equals(cedula)) // Metodo de filtrado con expresión lambda para filtrar cedula.
                .findFirst() // Devuelve el primer elemento que pasó el filtro.
                .orElseThrow(() -> new SocioNoEncontradoException("No se encontró el socio con cédula " + cedula)); // Metodo con Lambda para manejar ausencia de resultado.
    }

    /** ENTREGABLE 5. APLICAR INTERES
     * Aplica interés a TODAS las cuentas de TODOS los socios, si la cuenta es de tipo CuentaAhorros.
     * @param "tasa" Tasa de interés a aplicar (por ejemplo, 0.05 para 5%), @return Número total de cuentas de ahorro actualizadas
     * (POLIMORFISMO). Permitir que Cuenta se comporte de distintas formas según su tipo real, aunque se trate como su tipo base. */
    public int aplicarInteresATodas(double tasa) {
        return socios.stream() // Recorre todos los socios en secuencia para los metodos y operaciones necesarias
                .flatMap(s -> s.getCuentas().stream()) // Accede a todas las cuentas de cada socio
                .filter(c -> c instanceof CuentaAhorros) // Filtra solo cuentas de ahorro. Polimorfismo que accede al metodo aplicarInteres().
                .peek(c -> ((CuentaAhorros) c).aplicarInteres(tasa)) // mira los elementos del stream y Aplica el interés con casting con expresion lambda
                .mapToInt(c -> 1) // Cuenta cada aplicación
                .sum(); // Devuelve el total de cuentas actualizadas
    }

    /**Aplica interés a CADA CUENTA de ahorro individualmente. Muestra el nombre del socio, número de cuenta y nuevo saldo tras aplicar el interés.
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

    /** Aplica interés a UNA SOLA CUENTA ESPECIFICA, si existe y es de tipo CuentaAhorros. útil para actualizaciones puntuales desde el menú.
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

    /** b) Metodo para listar todos los socios registrados.
     * return Lista de socios */
    public List<Socio> getSocios() {
        return Collections.unmodifiableList(socios); // Devuelve una lista inmodificable de los socios registrados.
    }

/** 3) PROGRAMACION FUNCIONAL.
 * Aplica programación funcional: stream + Map + forEach + metodo de referencia.
 * a) Se usa stream() para listar los socios registrados, Imprime nombres de todos los socios usando programación funcional.
 * Lista los nombres de todos los socios junto con sus cuentas y saldos.*/

    /** METODO PARA LISTAR SOCIOS */
    public void listarNombresSocios() {
        System.out.println("\nLISTA SOCIOS ( socios, cuentas y monto) ---------");
        socios.stream() // Recorre toda LA LISTA de los socios. El flujo de datos.
                .forEach(socio -> { // Metodo terminal de Referencia, imprime cada socio y sus cuentas usando la expresion lambda.
                    System.out.println("\n" + socio.getNombre() + ", Cédula #: " + socio.getCedula());
                    socio.getCuentas().forEach(cuenta -> {
                        System.out.println("    Cuenta: " + cuenta.getNumero() + " | Saldo: $" + cuenta.getSaldo());
                    });
                });
    }

    /** b) METODO FILTRO.
     * Filtrar cuentas con saldo mayor a un valor específico. Muestra las cuentas. param valor Monto mínimo para filtrar cuentas */
    public void mostrarCuentasConSaldoMayor(double valor) {
        socios.stream() // inicia el recorrido funcional
                .flatMap(socio -> socio.getCuentas().stream() // Transforma los elementos del stream, creando estructura de socios con múltiples cuentas.
                        .filter(cuenta -> cuenta.getSaldo() > valor) // Selecciona solo las cuentas con saldo mayor al valor dado.
                        .map(cuenta -> Map.entry(socio.getNombre(), cuenta))) // Map Transforma cada cuenta en un Map.Entry con una funcion lambda con el nombre del socio.
                .forEach(entry -> { // Imprime los resultados
                    String nombre = entry.getKey();
                    Cuenta cuenta = entry.getValue();
                    System.out.printf("\nSocio: %s | Cuenta: %s | Saldo: $%.2f%n",
                            nombre, cuenta.getNumero(), cuenta.getSaldo());
                });

    }
    /** METODO PARA CALCULO TOTAL COOPERATIVA.
     *  c) Calcula y Obtiene el total de dinero acumulado en todas las cuentas de la cooperativa.
     * Calcula total del dinero en la cooperativa usando REDUCE(). return "Total de saldo" */
    public double calcularTotalDinero() { // Metodo que es llamado desde el Main.
        return socios.stream()
                .flatMap(s -> s.getCuentas().stream()) // Transforma los elemntos del stream para accede a todas las cuentas de cada socio.
                .mapToDouble(Cuenta::getSaldo) // :: metodo de referencia, extrae el saldo de cada cuenta y lo convierte a un double.
                .reduce(0.0, Double::sum); // Metodo terminal: acumula y suma todos los saldos desde 0, retorna reducido de los elementos del stream
    }
    /**
     * Verifica si ya existe una cuenta con el número dado en toda la cooperativa.
     *@param numero Número de cuenta a verificar,  @return true si ya existe, false si está disponible
     */
    public boolean cuentaExiste(String numero) {
        return socios.stream()
                .flatMap(s -> s.getCuentas().stream())
                .anyMatch(c -> c.getNumero().equalsIgnoreCase(numero));
    }

}
