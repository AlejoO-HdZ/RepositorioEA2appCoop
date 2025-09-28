package coopRKC.modelo;

import coopRKC.excepciones.MontoInvalidoException;
import coopRKC.excepciones.SaldoInsuficienteException;

/** PUNTO 1) MODELO DE DOMINIO
 *  b) CLASE CUENTA, Clase base que representa una cuenta bancaria.
 * Contiene el número de cuenta y el saldo disponible.
 * Puede realizar depósitos y retiros.
 */
public class Cuenta {
    protected final String numero; /* Numero de cuenta, final para que sea inmutable- Atributo Protected para ser accedido por la clases hijas (CuentaAhorros) */
    protected double saldo; // Saldo de la cuenta (ENCAPSULAMIENTO)

    /**
     * Constructor que inicializa la cuenta con su número.
     * param numero Número único de la cuenta
     */
    public Cuenta(String numero) {
        this.numero = numero;
        this.saldo = 0.0;
    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    /**(POLIMORFISMO) Metodo que devuelve el tipo de cuenta. puede ser sobrescrito por las subclases como CuentaAhorros.
     * Se usa en programación funcional para filtrar por tipo ignorando mayúsculas.*/
    public String getTipo() {
        return "Genérica";
    }


    /** b) OPERACIONES DE DEPOSITO Y RETIRO.
     * - Realiza un depósito en la cuenta. param "monto" Monto a depositar (debe ser positivo) */
    public void depositar(double monto) throws MontoInvalidoException {
        if (monto <= 0) {
            throw new MontoInvalidoException("El monto debe ser mayor a cero.");
        }
        saldo += monto;
    }

    /* 4) GESTION DE ERRORES, Declarando metodos para lanzar las coopRKC.excepciones "twrow"
     * - Realiza un retiro si hay saldo suficiente. param "monto" Monto a retirar
     * @return true si el retiro fue exitoso, false si no hay saldo suficiente */

    public void retirar(double monto) throws SaldoInsuficienteException, MontoInvalidoException {
        if (monto <= 0) {
            throw new MontoInvalidoException("El monto debe ser mayor a cero.");
        }
        if (monto > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente para retirar $" + monto);
        }
        saldo -= monto;
    }
}

