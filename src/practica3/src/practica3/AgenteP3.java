package practica3;

import java.util.ArrayList;
import java.util.HashMap;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class AgenteP3 extends Agent {

    private int energia;
    private Entorno entorno;
    private ArrayList<Posicion> movPosibles;
    private ArrayList<Posicion> caminoRecorrido;
    private Posicion mejorPaso;
    private HashMap<Posicion, Integer> mapaPercibido;
    private boolean renosEncontrados;
    private boolean comunicar;
    private ComunicacionesAgente comunicacionesAgente;
    private Posicion coordenadas;
    public static final String patronMensajeEntrante = "Bro (.*?) En Plan";
    public static final String plantillaRespuesta = "Bro %s En Plan";
    private boolean finalizado;

    public AgenteP3() {
        energia = 0;
        entorno = null; // Por defecto null indica que no hay ningún entorno cargado
        movPosibles = new ArrayList<Posicion>();
        caminoRecorrido = new ArrayList<Posicion>();
        mejorPaso = null;
        renosEncontrados = false;
        mapaPercibido = new HashMap<Posicion, Integer>();
        comunicar = true;
        comunicacionesAgente = new ComunicacionesAgente(this);
        finalizado = false;
    }

    public void actualizarMapa(Posicion pos) {

        int val = mapaPercibido.getOrDefault(pos, 0);

        if (mapaPercibido.containsKey(pos)) {
            mapaPercibido.put(pos, val + 1);
        } else {
            mapaPercibido.put(pos, 1);
        }
    }

    public ComunicacionesAgente getComunicacionesAgente() {
        return this.comunicacionesAgente;
    }

    public HashMap<Posicion, Integer> getMapaPercibido() {
        return mapaPercibido;
    }

    public boolean getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public void setCoordenadas(Posicion coordenadas) {
        this.coordenadas = coordenadas;
        this.entorno.setPosMeta(coordenadas);
        this.entorno.setPosSanta(coordenadas);
    }

    public ArrayList<Posicion> getMovPosibles() {
        return movPosibles;
    }

    public int getEnergia() {
        return energia;
    }

    public ArrayList<Posicion> getCaminoRecorrido() {
        return caminoRecorrido;
    }

    public Posicion getMejorPaso() {
        return mejorPaso;
    }

    public void setMejorPaso(Posicion mejorPaso) {
        this.mejorPaso = mejorPaso;
    }

    public void setMovPosibles(ArrayList<Posicion> movPosibles) {
        this.movPosibles = movPosibles;
    }

    public boolean getComunicar() {
        return comunicar;
    }

    public void setComunicar(boolean comunicar) {
        this.comunicar = comunicar;
    }

    public void gastaEnergia() {
        energia++;
    }

    public void addBehaviourAgent(Behaviour b) {
        addBehaviour(b);
    }

    public void borrarMapa() {
        mapaPercibido.clear();
    }

    @Override
    protected void setup() {
        Object[] args = getArguments();

        entorno = (Entorno) args[0];

        if (entorno == null) {
            System.err.println("No se ha podido cargar el entorno");
            return;
        }

        caminoRecorrido.add(entorno.getPosAgente());
        mapaPercibido.put(entorno.getPosAgente(), 1);

        // Este comportamiento se ejecuta mientras que el agente no esté en la meta
        addBehaviour(new SolucionarBehaviour(entorno, this));
    }
}
