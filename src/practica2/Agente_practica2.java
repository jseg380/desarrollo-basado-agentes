package practica2;
import java.util.ArrayList;
import java.util.List;

import jade.core.Agent;

public class Agente_practica2 extends Agent{
    
    // private Entorno entorno;
    int energia;
    private List<String> mov_pos = new ArrayList<String>(); 
    public ArrayList<ArrayList<Integer>> caminoRecorrido;

    public void setMov_pos(List<String> mov_pos) {
        this.mov_pos = mov_pos;
    }

    public List<String> getMov_pos() {
        return mov_pos;
    }

    @Override
    protected void setup() {

        System.out.println("Mensaje desde el agente, 'todo' está funcionando 'correctamente'");

        // String nombre_mapa = " mapWithoutObstacle.txt ";

        // entorno = new Entorno(nombre_mapa, 0, 0, 5, 7);
        
        // //2 comportamientos
        // //El 1 decide a que casilla moverte y lo añade a el camino
        // //El 2 ejecuta ese paso y vuelve otra vez al 1 hasta que llegue a la meta
        
        // addBehaviour(new VerBehaviour(entorno, this));
        // addBehaviour(new DecidirBehaviour(entorno,this));
        // addBehaviour(new MoverBehaviour(entorno,this));
        
    }

    @Override
    public void takeDown() {
        System.out.println("El agente ha resuelto el mapa en " + caminoRecorrido.size() + " pasos");
    }

    public void aumentarEnergia() {
        energia++;
    }

    public static void main(String[] args) {
        // //Ejecutamos
        // String host = "localhost";
        // String containerName = "container-69";
        // String agentName = "joseelmarikon";

        // try {
        //     jade.core.Runtime rt = jade.core.Runtime.instance();
            
        //     //Creamos un contenedor de agentes
        //     Profile p = new ProfileImpl();
            
        //     p.setParameter(Profile.MAIN_HOST, host);
        //     p.setParameter(Profile.CONTAINER_NAME, containerName);
            
        //     ContainerController cc = rt.createAgentContainer(p);
            
        //     //Creamos un nuevo agente y ejecutamos
        //     AgentController ac = cc.createNewAgent(agentName,
        //             AgenteP2.class.getCanonicalName(), null);
            
        //     ac.start();        
            
        //     } catch (StaleProxyException e) {
        //     e.printStackTrace();
        //     }
    }
}
