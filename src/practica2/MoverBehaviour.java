/* package practica2;

public class MoverBehaviour extends Behaviour{

    private int filMover = -1;
    private int colMover = -1;
    private AgenteP2 agente;
    private Entorno entorno;
    private Mapa mapa;

    public MoverBehaviour(int filMover, int colMover, AgenteP2 agente, Entorno entorno) {
        this.filMover = filMover;
        this.colMover = colMover;
        this.agente = agente;
        this.entorno = entorno;
    }
    
    public void action(){
        if(miAgente.caminoRecorrido.size()>0){
            filMover = agente.caminoRecorrido.get(agente.caminoRecorrido.size()-1).get(0);
            colMover = agente.caminoRecorrido.get(agente.caminoRecorrido.size()-1).get(1);
        }

        entorno.setPosAgente(filMover, colMover);
    }

    @Override
    public boolean done() {
        if (entorno.getFilAgente() == mapa.filaMeta() && entorno.getColAgente() == mapa.columnaMeta())
            return true;
        else
            return false;
    }
}
 */