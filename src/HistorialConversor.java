
public class HistorialConversor {
    private String monedaOrigen;
    private String monedaDestino;
    private double montoOriginal;
    private double montoConvertido;
    private String fecha;

    public HistorialConversor(String monedaOrigen, String monedaDestino, double montoOriginal, double montoConvertido, String fecha) {
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.montoOriginal = montoOriginal;
        this.montoConvertido = montoConvertido;
        this.fecha = fecha;
    }

    public String getMonedaOrigen() {
        return monedaOrigen;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }

    public double getMontoOriginal() {
        return montoOriginal;
    }

    public double getMontoConvertido() {
        return montoConvertido;
    }

    public String getFecha() {
        return fecha;
    }
}


