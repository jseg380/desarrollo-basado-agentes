package practica3;

import jade.core.Agent;

public class Elfo extends Agent {

    @Override
    protected void setup() {
        System.out.println("Inicialización del agente Elfo");
        addBehaviour(new MensajeElfoAgente());
    }
}
