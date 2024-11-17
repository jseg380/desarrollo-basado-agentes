package sucio-original.practica2;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

import java.util.*;

public class DecidirBehaviour2 extends Behaviour {

    private final Entorno entorno;
    private final Mapa mapa;
    private final AgenteP2 agente;

    public DecidirBehaviour2(Entorno entorno, Mapa mapa, AgenteP2 agente) {
        this.entorno = entorno;
        this.mapa = mapa;
        this.agente = agente;
    }


    // Distancia de Chebyshev
    private int chebyshevDistance(int x1, int y1, int x2, int y2) {
        return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    // Implementación de A* utilizando distancia de Chebyshev
    public List<int[]> ejecutarAStar(int startX, int startY, int goalX, int goalY) {
        PriorityQueue<Nodo> openSet = new PriorityQueue<>();
        Set<String> closedSet = new HashSet<>();

        Nodo startNode = new Nodo(startX, startY, 0, chebyshevDistance(startX, startY, goalX, goalY), null);
        openSet.add(startNode);

        int[][] movimientos = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, 
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        while (!openSet.isEmpty()) {
            Nodo current = openSet.poll();

            if (current.x == goalX && current.y == goalY) {
                // Reconstruir el camino
                List<int[]> path = new ArrayList<>();
                while (current != null) {
                    path.add(new int[]{current.x, current.y});
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            closedSet.add(current.x + "," + current.y);

            for (int[] move : movimientos) {
                int nx = current.x + move[0];
                int ny = current.y + move[1];

                if (mapa.esValido(nx, ny) && !closedSet.contains(nx + "," + ny)) {
                    int gCost = current.gCost + 1;
                    int hCost = chebyshevDistance(nx, ny, goalX, goalY);

                    Nodo neighbor = new Nodo(nx, ny, gCost, hCost, current);
                    openSet.add(neighbor);
                }
            }
        }

        return null; // Camino no encontrado
    }

    @Override
    public void action() {
        int startX = entorno.getFilAgente();
        int startY = entorno.getColAgente();
        int goalX = mapa.filaMeta();
        int goalY = mapa.columnaMeta();

        List<int[]> camino = ejecutarAStar(startX, startY, goalX, goalY);

        if (camino != null) {
            for (int[] paso : camino) {
                System.out.println("Paso: " + Arrays.toString(paso));
                agente.moverXY(paso[0], paso[1]);
                agente.caminoRecorrido.add(Arrays.asList(paso[0], paso[1]));
            }
        } else {
            System.out.println("No se encontró un camino.");
        }
    }

    @Override
    public boolean done() {
        return entorno.getFilAgente() == mapa.filaMeta() && entorno.getColAgente() == mapa.columnaMeta();
    }
}