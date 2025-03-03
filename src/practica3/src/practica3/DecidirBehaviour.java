package practica3;

import java.util.ArrayList;

import jade.core.behaviours.OneShotBehaviour;

public class DecidirBehaviour extends OneShotBehaviour {

    private Entorno entorno;
    private AgenteP3 agente;

    public DecidirBehaviour(Entorno entorno, AgenteP3 agente) {
        this.entorno = entorno;
        this.agente = agente;
        //comunicacion = new ComunicacionesAgente(agente)
    }

    private Integer chebyshevDistance(int x1, int y1, int x2, int y2) {
        return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    @Override
    public void action() {

        Posicion posMeta = entorno.getPosMeta();
        Posicion posActual = entorno.getPosAgente();

        if (posMeta.esIgualA(posActual)) {
            return;
        }

        // Obtenemos las posiciones adyacentes
        ArrayList<Posicion> posiblesPosiciones = entorno.getPosicionesTransitables();

        Posicion mejorPosicion = null;
        Integer mejorHeuristica = Integer.MAX_VALUE;

        for (Posicion posicion : posiblesPosiciones) {
            Integer val = chebyshevDistance(posicion.x, posicion.y, posMeta.x, posMeta.y);
            // Double val = euclideanDistance(posicion.x, posicion.y, posMeta.x, posMeta.y );

            int visitas = agente.getMapaPercibido().getOrDefault(posicion, 0);

            // Integer heuristicaTotal = val + (int) Math.pow(visitas, 2);
            Integer heuristicaTotal = val + visitas * 3;

            if (heuristicaTotal < mejorHeuristica) {
                mejorHeuristica = heuristicaTotal;
                mejorPosicion = posicion;
            }
        }

        if (mejorPosicion != null) {
            agente.setMejorPaso(mejorPosicion);
        }
    }
}
