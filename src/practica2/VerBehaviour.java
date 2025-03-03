/* package practica2;

import jade.core.Agent;
import jade.core.Behaviour;
import practica2.*;

import java.util.List;
import java.util.ArrayList;


// Comportamiento para que el agente visualice las casillas a las que puede moverse
public class VerBehaviour extends Behaviour{
    
    Entorno entorno;
    AgenteP2 agente;

    public VerBehaviour(Entorno entorno, AgenteP2 agente) {
        this.entorno = entorno;
        this.agente = agente;
    }

    public void action() {
        agente.setMov_pos(entorno.posiblesMovimientos());
    }

    public boolean done() {
        if(agente.getMov_pos().size()>0)
            return true;
        else
            return false;
    }
}
 */