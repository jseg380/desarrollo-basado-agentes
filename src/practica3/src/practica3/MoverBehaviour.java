package practica3;

import jade.core.behaviours.OneShotBehaviour;

public class MoverBehaviour extends OneShotBehaviour {

    private Entorno entorno;
    private AgenteP3 agente;
    private boolean accionRealizada;

    public MoverBehaviour(Entorno entorno, AgenteP3 agente) {
        this.entorno = entorno;
        this.agente = agente;
        accionRealizada = false;
    }

    @Override
    public void action() {
        Posicion movimiento = agente.getMejorPaso();

        if (movimiento != null) {
            accionRealizada = entorno.setPosAgente(movimiento);

            if (accionRealizada) {
                agente.gastaEnergia();
                agente.actualizarMapa(movimiento);
                agente.getCaminoRecorrido().add(movimiento);
            }
        }
    }

}
