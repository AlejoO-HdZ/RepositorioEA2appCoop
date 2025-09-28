/** MAIN DEL PROTOTIPO EN JAVA. (Ejercicio Integrador. EA2. PROGRAMACION FUNCIONAL*/
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
