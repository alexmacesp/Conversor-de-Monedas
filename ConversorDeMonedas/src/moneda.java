import com.sun.management.UnixOperatingSystemMXBean;

public class moneda {
    private String resultado;
    private String base;
    private String objetivo;
    private float tasaCambio;
    public  float valorCambio;
    public float valorFinal;
    public String fecha;


    public moneda(monedaAPI miMoneda){
        this.resultado = miMoneda.result();
        this.base = miMoneda.base_code();
        this.objetivo = miMoneda.target_code();
        this.tasaCambio = miMoneda.conversion_rate();
    }

    public String getResultado() {
        return resultado;
    }

    public String getBase() {
        return base;
    }
    public String getObjetivo() {
        return objetivo;
    }
    public float getTasaCambio() {
        return tasaCambio;
    }

    public float getValorCambio() {
        return valorCambio;
    }

    public void setValorCambio(float valorCambio) {
        this.valorCambio = valorCambio;
    }

    public float getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(float valorFinal) {
        this.valorFinal = valorFinal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "(Base= " + base +
                ", Objetivo= " + objetivo +
                ", Tasa de Cambio = " + tasaCambio +
                ", Valor a cambiar = " + valorCambio +
                ", Valor Final = " + valorFinal +
                ")";
    }
}
