package practica3;

public class ClaveSecreta {

    private static final String claveSecreta = Long.toString(java.time.Instant.now().getEpochSecond());

    // Constructor privado para prevenir la inicialización de la clase
    private ClaveSecreta() {
    }

    public static String getClaveSecreta() {
        return claveSecreta;
    }
}
