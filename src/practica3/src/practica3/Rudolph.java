package practica3;

import java.util.ArrayList;
import java.util.List;

import jade.core.Agent;

public class Rudolph extends Agent {

    private List<Posicion> posRenos;
    private String claveSecreta;

    public String getClaveSecreta() {
        return claveSecreta;
    }

    @Override
    protected void setup() {
        claveSecreta = ClaveSecreta.getClaveSecreta();
        
        Object[] args = getArguments();
        posRenos = (ArrayList<Posicion>) args[0];

        System.out.println("Inicialización del agente Rudolph");
        addBehaviour(new MensajeRudolphAgente(posRenos));
    }

}
