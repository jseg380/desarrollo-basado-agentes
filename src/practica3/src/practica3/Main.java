package practica3;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static ArrayList<Posicion> cargarPosRenos(String brownObstacleFile) throws IOException {
        ArrayList<Posicion> posiciones = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(brownObstacleFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coords = line.split(",");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                posiciones.add(new Posicion(x, y));
            }
        }

        return posiciones;
    }

    public static void main(String[] args) {
        // Obtener la instancia del Runtime de JADE
        Runtime rt = Runtime.instance();

        // Perfil del main container
        Profile mainProfile = new ProfileImpl();
        mainProfile.setParameter(Profile.MAIN, "true");
        mainProfile.setParameter(Profile.GUI, "false");

        // Crear el mainContainer (solo 1)
        ContainerController mainContainer = rt.createMainContainer(mainProfile);

        // Crear los agentContainer (tantos como se desee)
        try {
            if (args.length < 4) {
                System.out.println("Uso: <archivo_mapa> <pos_renos_mapa> <pos_santa> <pos_jugador>");
                return;
            }

            Mapa mapa = new Mapa(args[0]);

            // Archivo con las posiciones de todos los renos
            String archivoPosRenos = args[1];
            ArrayList<Posicion> posRenos = cargarPosRenos(archivoPosRenos);

            // Coordenadas de Santa y el jugador
            String[] coordsSanta = args[2].split(",");
            String[] coordsJugador = args[3].split(",");

            // Recibir posicion inicial y final como parámetros
            Posicion posInicial = new Posicion(
                    Integer.parseInt(coordsJugador[0]),
                    Integer.parseInt(coordsJugador[1])
            );
            Posicion posSanta = new Posicion(
                    Integer.parseInt(coordsSanta[0]),
                    Integer.parseInt(coordsSanta[1])
            );

            Entorno entorno = new Entorno(mapa, posInicial);

            Object[] parametrosEntorno = {entorno};
            AgentController agent = mainContainer.createNewAgent("agenteP3", AgenteP3.class.getCanonicalName(),
                    parametrosEntorno);

            Object[] parametrosSanta = {posSanta};
            AgentController agent_santa = mainContainer.createNewAgent("santa", Santa.class.getCanonicalName(),
                    parametrosSanta);

            Object[] parametrosRudolph = {posRenos};
            AgentController agent_rudolph = mainContainer.createNewAgent("rudolph", Rudolph.class.getCanonicalName(),
                    parametrosRudolph);

            AgentController agent_elfo = mainContainer.createNewAgent("elfo", Elfo.class.getCanonicalName(), null);

            agent.start();
            agent_santa.start();
            agent_rudolph.start();
            agent_elfo.start();

            // Wait for the agent to finish its task
            while (agent.getState().getName().equalsIgnoreCase("Active")) {
                Thread.sleep(1000); // Check agent state periodically
            }

            // Gracefully terminate JADE runtime after the agent finishes
            System.out.println("Agent has finished. Shutting down JADE...");
            rt.shutDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
