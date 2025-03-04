public class ErrorEnCodigoException extends RuntimeException {

    private String mensaje;

    public ErrorEnCodigoException(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String getMessage() {
        return this.mensaje;
    }
}
