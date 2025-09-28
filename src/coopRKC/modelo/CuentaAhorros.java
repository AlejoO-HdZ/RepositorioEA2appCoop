package coopRKC.modelo;

/** PUNTO 1) MODELO DE DOMINIO
 * c) SUBCLASE CUENTAAHORROS, Subclase de Cuenta, (HERENCIA) Hereda de Cuenta, atributos y metodos. Representa una cuenta de ahorros.
 * Incluye un porcentaje de interés que se puede aplicar al saldo.
 */
public class CuentaAhorros extends Cuenta { /* Hereda todos los atributos y métodos de Cuenta */
    private final double interes; /* Porcentaje de interes asociado */

    /**
     * Constructor que inicializa la cuenta de ahorros con número e interés.
     * param "numero" Número único de la cuenta
     * param "interes" Porcentaje de interés (ej. 0.02 para 2%)
     */
    public CuentaAhorros(String numero, double interes) {
        super(numero); /* super(numero) inicializa número en la clase padre */
        this.interes = interes;
    }

    /**
     * Aplica el interés al saldo actual.
     * Incrementa el saldo según el porcentaje definido.
     */
    public void aplicarInteres() {
        saldo += saldo * interes;
    }

    /*(POLIMORFISMO) Metodo getTipo()se comporta distinto según el tipo real del objeto.*/
    @Override
    public String getTipo() {
        return "Ahorros";
    }
    /**
     * Aplica interés usando una tasa externa proporcionada.
     * Este metodo permite aplicar una tasa distinta a la definida en la cuenta.
     * param "tasa" Tasa de interés a aplicar (ej. 0.05 para 5%)
     */
    public void aplicarInteres(double tasa) {
        saldo += saldo * tasa;
    }
}
