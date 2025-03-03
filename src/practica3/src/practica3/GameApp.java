package practica3;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import javax.management.InvalidAttributeValueException;

public class GameApp {

    public static void main(String[] args) {
        try {
            if (args.length < 4) {
                System.out.println("Uso: <archivo_mapa> <pos_renos_mapa> <pos_santa> <pos_jugador>");
                return;
            }

            String mapFile = args[0];
            String archivoPosRenos = args[1];

            String[] coordsSanta = args[2].split(",");
            Point pointSanta = new Point(
                    Integer.parseInt(coordsSanta[0]),
                    Integer.parseInt(coordsSanta[1])
            );

            String[] coordsJugador = args[3].split(",");
            Point player = new Point(
                    Integer.parseInt(coordsJugador[0]),
                    Integer.parseInt(coordsJugador[1])
            );

            new GameFrame(mapFile, archivoPosRenos, pointSanta, player).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class GameFrame extends JFrame {
    private JButton startButton;
    private Entorno entorno;

    public GameFrame(String archivoMapa, String archivoPosRenos, Point pointSanta, Point pointPlayer) throws IOException, InvalidAttributeValueException {
        setTitle("Practica 3 DBA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        startButton = new JButton("Start Game");
        startButton.addActionListener(e -> startGameLogic(archivoMapa, archivoPosRenos, pointSanta, pointPlayer));
        buttonPanel.add(startButton);

        buttonPanel.setPreferredSize(new Dimension(100, getHeight()));
        add(buttonPanel, BorderLayout.WEST);
        
        Mapa mapa = new Mapa(archivoMapa);
        Posicion posInicial = new Posicion(pointPlayer.x, pointPlayer.y);
        this.entorno = new Entorno(mapa, posInicial);

        // Game panel
        GamePanel gamePanel = new GamePanel(archivoMapa, archivoPosRenos, pointSanta, pointPlayer, entorno);
        add(gamePanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }
    
    private static ArrayList<Posicion> leerArchivoPosRenos(String archivoPosRenos) throws IOException {
        ArrayList<Posicion> posiciones = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoPosRenos))) {
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

    private void startGameLogic(String archivoMapa, String archivoPosRenos, Point pointSanta, Point pointPlayer) {
        // Run the logic in a separate thread to avoid blocking the UI
        new Thread(() -> {
            try {
                System.out.println("practica3.GameFrame.startGameLogic()");
                // Simulate the logic from the second Main class here
                Runtime rt = Runtime.instance();
                Profile mainProfile = new ProfileImpl();
                mainProfile.setParameter(Profile.MAIN, "true");
                mainProfile.setParameter(Profile.GUI, "false");
                ContainerController mainContainer = rt.createMainContainer(mainProfile);

                Posicion posSanta = new Posicion(pointSanta.x, pointSanta.y);
                Object[] parametros = {entorno};

                AgentController agent = mainContainer.createNewAgent("agenteP3", AgenteP3.class.getCanonicalName(), parametros);

                Object[] parametrosSanta = {posSanta};
                AgentController agent_santa = mainContainer.createNewAgent("santa", Santa.class.getCanonicalName(), parametrosSanta);

                ArrayList<Posicion> posRenos = leerArchivoPosRenos(archivoPosRenos);
                Object[] parametrosRudolph = {posRenos};
                AgentController agent_rudolph = mainContainer.createNewAgent("rudolph", Rudolph.class.getCanonicalName(), parametrosRudolph);

                AgentController agent_elfo = mainContainer.createNewAgent("elfo", Elfo.class.getCanonicalName(), null);
                
                
                agent.start();
                agent_santa.start();
                agent_rudolph.start();
                agent_elfo.start();

                while (agent.getState().getName().equalsIgnoreCase("Active")) {
                    Thread.sleep(1000);
                }

                System.out.println("El agente ha acabado. Finalizando la ejecucion de JADE...");
                rt.shutDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}

class GamePanel extends JPanel {

    private static final int TILE_SIZE = 8;
    private int rows;
    private int cols;
    private int[][] map;
    private ArrayList<Point> posRenos = new ArrayList<>();
    private Point pointSanta;
    private Point pointJugador;
    private Timer movementTimer;
    private Entorno entornoGUI;

    public GamePanel(String mapFile, String archivoPosRenos, Point pointSanta, Point pointJugador, Entorno entorno) throws IOException {
        this.pointSanta = pointSanta;
        this.pointJugador = pointJugador;
        this.entornoGUI = entorno;

        loadMap(mapFile);
        cargarArchivosRenos(archivoPosRenos);

        setPreferredSize(new Dimension(cols * TILE_SIZE, rows * TILE_SIZE));

        // Set up the Timer to update the player's position every 20 ms
        movementTimer = new Timer(20, e -> updatePlayerPosition());
        movementTimer.start();
    }

    private void loadMap(String mapFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(mapFile))) {
            rows = Integer.parseInt(br.readLine().trim());
            cols = Integer.parseInt(br.readLine().trim());
            map = new int[rows][cols];

            for (int r = 0; r < rows; r++) {
                String[] line = br.readLine().trim().split("\\s+");
                if (line.length != cols) {
                    throw new IOException("Formato de mapa invalido: se esperaban " + cols + " columnas pero hay " + line.length + " en la fila " + r);
                }
                for (int c = 0; c < cols; c++) {
                    map[r][c] = Integer.parseInt(line[c]);
                }
            }
        }
    }

    private void cargarArchivosRenos(String archivoPosRenos) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoPosRenos))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coords = line.split(",");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                posRenos.add(new Point(x, y));
            }
        }
    }

    // Update the player's position automatically
    private void updatePlayerPosition() {
        pointJugador = getPlayerPos(); // Update the player's position using the method
        repaint(); // Repaint the panel to reflect the new position
    }
    
    private Point getPlayerPos() {
        Posicion p = entornoGUI.getPosAgente();
        return new Point(p.x, p.y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Mapa
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (map[r][c] == -1) {
                    g.setColor(Color.LIGHT_GRAY); // Obstaculo
                } else {
                    g.setColor(Color.WHITE); // Casilla vacia
                }
                g.fillRect(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // Renos
        g.setColor(new Color(247, 34, 233));
        for (Point reno : posRenos) {
            g.fillRect(reno.x * TILE_SIZE, reno.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // Santa Claus
        g.setColor(Color.RED);
        g.fillRect(pointSanta.x * TILE_SIZE, pointSanta.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Draw the player at the position returned by getPlayerPos()
        Point playerPos = getPlayerPos();
        g.setColor(Color.BLUE);
        g.fillRect(playerPos.x * TILE_SIZE, playerPos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
}