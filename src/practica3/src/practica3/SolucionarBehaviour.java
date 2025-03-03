package practica3;

import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.SimpleBehaviour;

public class SolucionarBehaviour extends SimpleBehaviour {

    private Entorno entorno;
    private AgenteP3 agente;

    public SolucionarBehaviour(Entorno entorno, AgenteP3 agente) {
        this.entorno = entorno;
        this.agente = agente;
    }

    @Override
    public void action() {
        //System.out.println(entorno.toString(agente));

        SequentialBehaviour secComportamientos = new SequentialBehaviour();

        if (this.agente.getComunicar()) {
            this.agente.getComunicacionesAgente().comunicacion();
        } else {

            // Decidir próxima posición a la que ir
            secComportamientos.addSubBehaviour(new DecidirBehaviour(entorno, agente));
            // Ejecutar decisión
            secComportamientos.addSubBehaviour(new MoverBehaviour(entorno, agente));
            //System.out.println(entorno.getPosAgente());

            agente.addBehaviourAgent(secComportamientos);

            System.out.println("Pos del agente: " + this.entorno.getPosAgente());
            System.out.println("Pos del reno: " + this.entorno.getPosMeta());
            System.out.println(entorno.toString(agente));

            if (this.entorno.getPosAgente().esIgualA(this.entorno.getPosMeta())) {
                this.agente.setComunicar(true);
                this.agente.borrarMapa();
            }
        }

    }

    @Override
    public boolean done() {

        // Este comportamiento termina cuando se ha solucionado el mapa, de ahí el nombre
        return (entorno.getPosAgente().esIgualA(entorno.getPosSanta()) && agente.getFinalizado());
    }

    @Override
    public int onEnd() {
        System.out.println("El agente ha resuelto el mapa gastando una energia de: " + agente.getEnergia());
        System.out.println(agente.getCaminoRecorrido());
        agente.doDelete();
        return 0;
    }
}
