package practica3;

import java.util.ArrayList;
import java.util.Collections;

import javax.management.InvalidAttributeValueException;

public class Entorno {

    private Mapa mapa;
    private Posicion posAgente;
    private Posicion posMeta;
    private Posicion posSanta;

    /**
     * Constructor de entorno Se utiliza dependency injection para desacoplar el
     * módulo. (Se le pasa la clase mapa).
     *
     * @param mapa Mapa para asociar al entorno
     * @param posAgente Posición inicial del agente
     * @param posMeta Posición objetivo a la que tiene que llegar el agente
     * @throws InvalidAttributeValueException
     */
    public Entorno(Mapa mapa, Posicion posAgente) throws InvalidAttributeValueException {
        this.mapa = mapa;

        /* if (!this.mapa.esTransitable(posMeta) || !this.mapa.esTransitable(posAgente)) {
            throw new InvalidAttributeValueException("Posición de agente o de meta inválida");
        }
         */
        if (!this.mapa.esTransitable(posAgente)) {
            throw new InvalidAttributeValueException("Posición de agente o de meta inválida");
        }
        this.posAgente = posAgente;
        this.posMeta = new Posicion(0, 0);
        this.posSanta = new Posicion(1, 1);
    }

    /**
     * Obtener posición en la que se encuentra el agente actualmente
     *
     * @return Posición en la que se ecuentra el agente
     */
    public Posicion getPosAgente() {
        return posAgente;
    }

    /**
     * Obtener posición de la meta
     *
     * @return Posición a la que tiene que llegar el agente
     */
    public Posicion getPosMeta() {
        return posMeta;
    }

    /**
     * @param posMeta
     */
    public void setPosMeta(Posicion posMeta) {
        this.posMeta = posMeta;
    }

    /**
     * @param posSanta
     */
    public void setPosSanta(Posicion posSanta) {
        this.posSanta = posSanta;
    }

    public Posicion getPosSanta() {
        return this.posSanta;
    }

    /**
     * Comprobar si el agente puede ir a una posición teniendo en cuenta su
     * estado Hay casillas visibles y transitables, a las que no puede ir el
     * agente. Por ejemplo: una esquina libre, pero con muros en diagonal no es
     * accesible
     *
     * @param posObjetivo Posición a la que moverse
     * @return {@code true} Si el agente puede ir a esa casilla desde su
     * posición
     */
    private boolean esLegalMovimiento(Posicion posObjetivo) {
        // Si no se puede ir, es no transitable
        if (!mapa.esTransitable(posObjetivo)) {
            return false;
        }

        // Casilla transitable, pero si la posición está en diagonal a la posición
        // del agente, puede haber obstáculos. Comprobar si la posObjetivo está en
        // diagonal
        int distancia = Math.abs(posObjetivo.x - posAgente.x) + Math.abs(posObjetivo.y - posAgente.y);

        // La posición está en una esquina/diagonal respecto a la posición del agente
        if (distancia == 2) {
            // Obtener las dos celdas diagonales, adyacentes a ambos centro y esquina
            Posicion diagonal1 = new Posicion(posObjetivo.x, posAgente.y);
            Posicion diagonal2 = new Posicion(posAgente.x, posObjetivo.y);

            // Es un muro diagonal, no se puede atravesar
            if (!mapa.esTransitable(diagonal1) && !mapa.esTransitable(diagonal2)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Obtener posiciones accesibles desde la posición actual del agente
     *
     * @return Lista de posiciones a las que se puede mover el agente
     */
    public ArrayList<Posicion> getPosicionesTransitables() {
        ArrayList<Posicion> posiblesPosiciones = new ArrayList<Posicion>();

        for (Posicion posVisible : getPosicionesVisibles()) {
            // No se tiene en cuenta la posición actual del agente
            if (posVisible.esIgualA(posAgente)) {
                continue;
            }

            // Si el agente puede
            if (esLegalMovimiento(posVisible)) {
                posiblesPosiciones.add(posVisible);
            }
        }

        return posiblesPosiciones;
    }

    /**
     * Obtener posiciones visibles desde la posición actual del agente El agente
     * puede ver casillas no transitables
     *
     * @return Lista de posiciones a las que se puede mover el agente
     */
    public ArrayList<Posicion> getPosicionesVisibles() {
        ArrayList<Posicion> posVisibles = new ArrayList<Posicion>();

        for (int i = -1; i <= 1; i++) { // Filas: -1, 0, 1
            for (int j = -1; j <= 1; j++) { // Columnas: -1, 0, 1
                Posicion pos = new Posicion(posAgente.x + i, posAgente.y + j);
                posVisibles.add(pos);
            }
        }

        return posVisibles;
    }

    /**
     * Mover el agente a una posición
     *
     * @param pos Posición a la que mover el agente
     * @return {@code true} si el agente ha podido moverse a dicha posición;
     * {@code false} en caso contrario
     */
    public boolean setPosAgente(Posicion pos) {
        ArrayList<Posicion> posiblesPosiciones = getPosicionesTransitables();

        // Si es posible, mover al agente y devolver true
        for (Posicion posible : posiblesPosiciones) {
            if (pos.esIgualA(posible)) {
                posAgente = pos;
                return true;
            }
        }

        // No es una posible posición, devolver false
        return false;
    }

    @Override
    public String toString() {
        int fils = mapa.getFils();
        int cols = mapa.getCols();
        int valor;
        Posicion pos = new Posicion(-1, -1);
        String msg = "";

        for (int fil = 0; fil < fils; fil++) {
            for (int col = 0; col < cols; col++) {
                pos.setPos(fil, col);
                String celda = "K";
                valor = mapa.getValorPos(pos);

                if (pos.esIgualA(posAgente)) {
                    celda = "P";
                } else if (pos.esIgualA(posMeta)) {
                    celda = "x";
                } else if (valor == -1) {
                    celda = "H";
                } else if (valor == 0) {
                    celda = "·";
                }

                msg += String.format("%2s", celda);
            }
            msg += "\n";
        }

        return msg;
    }

    public String toString(AgenteP3 agente) {
        int fils = mapa.getFils();
        int cols = mapa.getCols();
        int valor;
        Posicion pos = new Posicion(-1, -1);
        String msg = "";

        for (int fil = 0; fil < fils; fil++) {
            for (int col = 0; col < cols; col++) {
                pos.setPos(fil, col);
                String celda = "K";
                valor = mapa.getValorPos(pos);

                if (pos.esIgualA(posMeta)) {
                    celda = "x";
                } else if (valor == -1) {
                    celda = "H";
                } else if (valor == 0) {
                    celda = "·";
                }

                // Agente es null, así que se muestra el mapa simple (sin traza del jugador)
                if (agente != null) {
                    int ocurrencias = Collections.frequency(agente.getCaminoRecorrido(), pos);
                    if (ocurrencias > 0) {
                        celda = Integer.toString(ocurrencias);
                    }
                }
                if (pos.esIgualA(posAgente)) {
                    celda = "P";
                }

                msg += celda;
            }
            msg += "\n";
        }

        return msg;
    }
}
