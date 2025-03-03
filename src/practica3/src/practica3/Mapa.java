package practica3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Mapa {

    private int fils;
    private int cols;
    private int[][] mapa = null;

    /**
     * Crear mapa dado un archivo de mapa
     * <p>
     * El formato del archivo debe ser:
     * <ul>
     * <li>Primera y segunda línea contienen el número de filas y columnas,
     * respectivamente</li>
     * <li>A partir de la 3a línea, cada línea representa una fila, con los
     * valores en cada columna separados por espacio</li>
     * </ul>
     * Ejemplo de archivo:<br>
     * 2<br>
     * 2<br>
     * 0 0<br>
     * -1 0<br>
     * </p>
     *
     * @param nombre_mapa Ruta al archivo que contiene el mapa. Puede ser ruta
     * relativa o absoluta.
     */
    public Mapa(String nombre_mapa) {
        try (Scanner scanner = new Scanner(new File(nombre_mapa))) {
            fils = scanner.nextInt();
            cols = scanner.nextInt();

            if (fils < 1 || cols < 1) {
                throw new IllegalArgumentException();
            }

            mapa = new int[fils][cols];

            for (int i = 0; i < fils; i++) {
                for (int j = 0; j < cols; j++) {
                    mapa[i][j] = scanner.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("No se ha encontrado el archivo: " + nombre_mapa);
            mapa = null;
        } catch (IllegalArgumentException e) {
            System.err.println("Las filas y columnas deben ser mayor que 1");
            mapa = null;
        }
    }

    /**
     * Crear mapa, dadas sus dimensiones, con todas las casillas desconocidas
     * Usado como mapa de memoria, donde se almacena aquellas zonas del mapa que
     * han sido exploradas por el agente.
     *
     * @param filas
     * @param columnas
     */
    public Mapa(int filas, int columnas) {
        try {
            fils = filas;
            cols = columnas;

            if (fils < 1 || cols < 1) {
                throw new IllegalArgumentException();
            }

            mapa = new int[fils][cols];

            for (int i = 0; i < fils; i++) {
                for (int j = 0; j < cols; j++) {
                    mapa[i][j] = -25;
                }
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Las filas y columnas deben ser mayor que 1");
            mapa = null;
        }
    }

    /**
     * Obtener número de filas del mapa
     *
     * @return {@code int} número de filas del mapa;<br> {@code -1} si no ha
     * leído el mapa satisfactoriamente
     */
    public int getFils() {
        return (mapa != null) ? fils : -1;
    }

    /**
     * Obtener número de columnas del mapa
     *
     * @return {@code int} número de columnas del mapa; {@code -1} si no ha
     * leído el mapa satisfactoriamente
     */
    public int getCols() {
        return (mapa != null) ? cols : -1;
    }

    /**
     * Obtener valor almacenado en una casilla del mapa Importante: La posición
     * debe ser válida.
     *
     * @param pos Posición de la que obtener el valor
     * @return {@code int} con el valor almacenado en la posición
     */
    public int getValorPos(Posicion pos) {
        return mapa[pos.x][pos.y];
    }

    /**
     * Comprobar si una casilla se puede transitar
     *
     * @param pos Posición a verificar su estado
     * @return {@code true} si la posición es transitable; <br> {@code false} si
     * la posición no es transitable
     */
    public boolean esTransitable(Posicion pos) {
        // Mapa no válido
        if (mapa == null) {
            return false;
        }

        // Posición fuera de los límites
        if (pos.x < 0 || pos.x >= fils || pos.y < 0 || pos.y >= cols) {
            return false;
        }

        // Verificar si la posición tiene un obstáculo
        if (getValorPos(pos) != 0) {
            return false;
        }

        // Si no hay restricciones, la posición es transitable
        return true;
    }
}
