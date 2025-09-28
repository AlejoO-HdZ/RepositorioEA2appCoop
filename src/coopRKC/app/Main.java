/** MAIN DEL PROTOTIPO EN JAVA. Ejercicio Integrador. EA2. PROGRAMACION FUNCIONAL */
package coopRKC.app;
import java.util.*;
import coopRKC.modelo.*;
import coopRKC.transacciones.*;

/** CLASE PRINCIPAL, gestiona el menú de operaciones de la cooperativa.
 * Aplica programación funcional mediante el uso de Map<Integer, Runnable> y métodos funcionales.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cooperativa cooperativa = new Cooperativa();

        /* MENU FUNCIONAL. Mapa funcional que asocia cada opción del menú con una acción.
         * Uso de Runnable como interfaz funcional para encapsular cada operación */
        Map<Integer, Runnable> acciones = new HashMap<>(); // mapa de acciones que asocia números enteros con tareas específicas representadas por expresiones lambda
        acciones.put(1, () -> registrarSocio(scanner, cooperativa));
        acciones.put(2, () -> abrirCuentaAhorro(scanner, cooperativa));
        acciones.put(3, () -> realizarDeposito(scanner, cooperativa));
        acciones.put(4, () -> realizarRetiro(scanner, cooperativa));
        acciones.put(5, () -> aplicarInteres(scanner, cooperativa));
        acciones.put(6, () -> listarNombresSocios(cooperativa));
        acciones.put(7, () -> mostrarCuentasConSaldoMayor(scanner, cooperativa));
        acciones.put(8, () -> calcularTotalDinero(cooperativa));

        int opcion;
        do {
            mostrarOpciones(); // Menú principal
            opcion = leerOpcion(scanner); // Validación de entrada
            if (opcion == 0) {
                System.out.println(" Saliendo del sistema...");
                break;
            }
            Runnable accion = acciones.get(opcion); // Programación funcional: ejecución dinámica
            if (accion != null) {
                accion.run(); // Ejecuta la acción correspondiente
                if (!continuarMenu(scanner)) break;
            } else {
                System.out.println(" Opción inválida.");
            }

        } while (opcion != 0);
    }

    // ======================= MÉTODOS AUXILIARES =======================

    private static void mostrarOpciones() {
        System.out.println("\n*================ ***MENÚ PRINCIPAL*** =========================");
        System.out.println("1. Registrar socio");
        System.out.println("2. Abrir cuenta de ahorro");
        System.out.println("3. Realizar depósito");
        System.out.println("4. Realizar retiro");
        System.out.println("5. Aplicar interés a cuentas de ahorro");
        System.out.println("6. Listar nombres de socios y sus cuentas");
        System.out.println("7. Mostrar cuentas con saldo > $500,000 u Otro Valor");
        System.out.println("8. Calcular total de dinero en la cooperativa");
        System.out.println("0. Salir");
        System.out.println("================================================================");
    }

    private static int leerOpcion(Scanner scanner) {
        while (true) {
            System.out.print("\nSeleccione una opción: ");
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Entrada inválida. Por favor ingrese un número.");
            }
        }
    }

    public static boolean continuarMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n¿Desea volver al menú principal o salir?");
            System.out.println("1. Volver al menú");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            String entrada = scanner.nextLine();

            if (entrada.equals("1")) {
                return true;
            } else if (entrada.equals("0")) {
                System.out.println(" Saliendo del sistema...");
                return false;
            } else {
                System.out.println(" Entrada inválida. Intente nuevamente.");
            }
        }
    }

// ======================= MÉTODOS FUNCIONALES =======================

    /**Registra un nuevo socio en la cooperativa. */
    private static void registrarSocio(Scanner scanner, Cooperativa cooperativa) {
        String nombre = leerTextoObligatorio(scanner, "Nombre del socio: ").toLowerCase();
        String cedula = leerCedulaValida(scanner);
        cooperativa.registrarSocio(nombre, cedula);
        System.out.println(" Socio registrado exitosamente.");
    }

    /**Abre una cuenta de ahorro para un socio existente. */
    private static void abrirCuentaAhorro(Scanner scanner, Cooperativa cooperativa) {
        String cedula = leerCedulaValida(scanner);
        try {
            Socio socio = cooperativa.buscarSocioPorCedula(cedula);
            String numero = leerTextoObligatorio(scanner, "Número de cuenta: ").toLowerCase();

            // Validación de unicidad
            if (cooperativa.cuentaExiste(numero)) {
                System.out.println("Error. Ya existe una cuenta con ese número. Intente con otro.");
                return;
            }

            double interes = leerNumero(scanner, "Interés anual (%): ", 0.01);
            Cuenta cuenta = new CuentaAhorros(numero, interes / 100);
            socio.agregarCuenta(cuenta);
            System.out.println("  Cuenta de ahorro creada exitosamente.");
        } catch (Exception e) {
            System.out.println("-X- Error: " + e.getMessage());
        }
    }

    /**Realiza un depósito en una cuenta existente.
     (ABSTRACCION). Oculta la Complejidad interna y muestra solo lo esencial para su uso, en este caso la Interfaz (menu)*/
    private static void realizarDeposito(Scanner scanner, Cooperativa cooperativa) {
        String cedula = leerCedulaValida(scanner);
        try {
            Socio socio = cooperativa.buscarSocioPorCedula(cedula);
            String numero = leerTextoObligatorio(scanner, "Número de cuenta: ").toLowerCase();
            Cuenta cuenta = socio.getCuenta(numero);
            double monto = leerNumero(scanner, "Monto a depositar $: ", 0.01);
            new Deposito(cuenta, monto).ejecutar(); // (ABSTRACCION) Usuario no nesecita saber los detalles ni ocmo se actualiza el saldo
            System.out.println("  Monto depositado correctamente.");
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    /**
     * Realiza un retiro en una cuenta existente.
     */
    private static void realizarRetiro(Scanner scanner, Cooperativa cooperativa) {
        String cedula = leerCedulaValida(scanner);
        try {
            Socio socio = cooperativa.buscarSocioPorCedula(cedula);
            String numero = leerTextoObligatorio(scanner, "Número de cuenta: ").toLowerCase();
            Cuenta cuenta = socio.getCuenta(numero);
            double monto = leerNumero(scanner, "Monto a retirar $: ", 0.01);
            new Retiro(cuenta, monto).ejecutar();
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    /**
     * Aplica interés a cuentas de ahorro según opción elegida por el usuario.
     */
    private static void aplicarInteres(Scanner scanner, Cooperativa cooperativa) {
        System.out.println("\nAplicacion de interés a cuentas");
        System.out.println(" 1. *Aplicar a todas las cuentas de ahorro");
        System.out.println(" 2. *Aplicar a cada cuenta individualmente");
        System.out.println(" 3. *Aplicar a una sola cuenta específica");

        int opcion = leerOpcion(scanner);
        double porcentaje = leerNumero(scanner, "Ingrese el porcentaje de interés (%): ", 0.01);
        double tasa = porcentaje / 100;

        switch (opcion) {
            case 1 -> {
                int total = cooperativa.aplicarInteresATodas(tasa);
                System.out.println("Interés aplicado exitosamente a " + total + " cuenta(s) de ahorro.");
            }
            case 2 -> {
                cooperativa.aplicarInteresUnaPorUna(tasa);
            }
            case 3 -> {
                String numero = leerTextoObligatorio(scanner, "Número de cuenta a actualizar: ").toLowerCase();
                boolean aplicado = cooperativa.aplicarInteresCuentaEspecifica(numero, tasa);
                if (aplicado) {
                    System.out.println("Interés aplicado correctamente a la cuenta:"+ numero);
                } else {
                    System.out.println("Error. No se encontró la cuenta o no es de tipo ahorro.");
                }
            }
            default -> System.out.println(" Opción inválida. No se aplicó ningún interés.");
        }
    }

    /**
     * Muestra las cuentas con saldo mayor a un valor, incluyendo nombre del socio.
     */
    private static void mostrarCuentasConSaldoMayor(Scanner scanner, Cooperativa cooperativa) {
        double valor = leerNumero(scanner, "Ingrese el valor mínimo de saldo: COP$ ", 0);
        System.out.println("\n CUENTAS con SALDO MAYOR a $ " + valor + ":");
        cooperativa.mostrarCuentasConSaldoMayor(valor);
    }

    /**
     * Lista los nombres de todos los socios y sus cuentas.
     */
    private static void listarNombresSocios(Cooperativa cooperativa) {
        cooperativa.listarNombresSocios();
    }

    /**
     * Calcula el total de dinero acumulado en la cooperativa.
     */
    private static void calcularTotalDinero(Cooperativa cooperativa) {
        double total = cooperativa.calcularTotalDinero();
        System.out.printf("\nTOTAL EN LA COPPERATIVA: $%.2f%n", total);
    }

// ======================= MÉTODOS DE VALIDACIÓN =======================

    /**
     * Solicita un texto obligatorio al usuario.
     *
     * @param scanner Scanner para entrada
     * @param mensaje Mensaje a mostrar
     * @return Texto ingresado, no vacío
     */
    private static String leerTextoObligatorio(Scanner scanner, String mensaje) {
        String entrada;
        do {
            System.out.print(mensaje);
            entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("  Este campo no puede estar vacío.");
            }
        } while (entrada.isEmpty());
        return entrada;
    }

    /**
     * Solicita un número mayor o igual al mínimo especificado.
     *
     * @param scanner Scanner para entrada
     * @param mensaje Mensaje a mostrar
     * @param minimo Valor mínimo permitido
     * @return Número válido ingresado
     */
    private static double leerNumero(Scanner scanner, String mensaje, double minimo) {
        while (true) {
            System.out.print(mensaje);
            try {
                double valor = Double.parseDouble(scanner.nextLine());
                if (valor < minimo) {
                    System.out.println("  El número debe ser mayor a " + minimo);
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("  Entrada inválida. Ingrese un número válido.");
            }
        }
    }

    /**
     * Solicita una cédula válida (entre 6 y 10 dígitos numéricos).
     *
     * @param scanner Scanner para entrada
     * @return Cédula válida
     */
    private static String leerCedulaValida(Scanner scanner) {
        String cedula;
        do {
            System.out.print("Cédula del socio: ");
            cedula = scanner.nextLine().trim();
            if (!cedula.matches("\\d{6,10}")) {
                System.out.println(" Cédula inválida. Debe tener entre 6 y 10 dígitos numéricos.");
                cedula = "";
            }
        } while (cedula.isEmpty());
        return cedula;
    }
}
