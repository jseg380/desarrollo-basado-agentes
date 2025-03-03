package practica3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jade.core.Agent;

public class Santa extends Agent {

    private int contadorRenos;
    private Posicion posicion;
    private String claveSecreta;
    public static final String patronMensajeEntrante = "Rakas Joulupukki (.*?) Kiitos";
    public static final String plantillaRespuesta = "Hyvää joulua %s Nähdään pian";

    public int getRenos() {
        return contadorRenos;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void sumaContadorRenos() {
        contadorRenos++;
    }

    public String getClaveSecreta() {
        return claveSecreta;
    }

    public String formulaRespuesta(String contenido) {
        return String.format(plantillaRespuesta, contenido);
    }

    public String interpretaMensajeEntrante(String mensajeEntrante) {
        Pattern pattern = Pattern.compile(patronMensajeEntrante);
        Matcher matcher = pattern.matcher(mensajeEntrante);

        return matcher.group(1);
    }

    @Override
    protected void setup() {
        System.out.println("Inicialización del agente Santa");
        Object[] args = getArguments();
        this.posicion = (Posicion) args[0];
        this.claveSecreta = ClaveSecreta.getClaveSecreta();
        addBehaviour(new MensajeSantaAgente());
    }
}
