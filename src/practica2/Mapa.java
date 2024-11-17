package practica2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Mapa {
    private int fils = -1;
    private int cols = -1;
    private int[][] mapa = null;

    /**
     * Crear mapa dado un archivo de mapa
     * 
     * <p>El formato del archivo debe ser:
     * <ul>
     * <li>Primera y segunda línea contienen el número de filas y columnas,
     * respectivamente</li>
     * <li>A partir de la 3a línea, cada línea representa una fila, con los valores
     * en cada columna separados por espacio</li>
     * </ul>
     * Ejemplo de archivo:<br>
     * 2<br>
     * 2<br>
     * 0 0<br>
     * -1 0<br>
     * </p>
     * 
     * @param nombre_mapa
     *            Ruta al archivo que contiene el mapa. Puede ser ruta relativa o
     *            absoluta.
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
        mapa = new int[][]
    }

    /**
     * Obtener número de filas del mapa
     * 
     * @return {@code int} número de filas del mapa;<br>
     *         {@code -1} si no ha leído el mapa satisfactoriamente
     */
    public int getFils() {
        return (mapa != null) ? fils : -1;
    }

    /**
     * Obtener número de columnas del mapa
     * 
     * @return {@code int} número de columnas del mapa;<br>
     *         {@code -1} si no ha leído el mapa satisfactoriamente
     */
    public int getCols() {
        return (mapa != null) ? cols : -1;
    }

    /**
     * Obtener mapa
     * 
     * @return {@code int[filas][columnas]} con los valores del mapa si lo ha podido
     *         leer bien;<br>
     *         {@code null} si no ha leído el mapa satisfactoriamente
     */
    public int[][] getMapa() {
        return mapa;
    }

    public void showMap() {
        if (mapa != null) {
            System.out.println("Map:");
            for (int i = 0; i < fils; i++) {
                for (int j = 0; j < cols; j++) {
                    System.out.printf("%" + 3 + "d ", mapa[i][j]);
                    System.out.print(" ");
                }
                System.out.println();
            }
        } else {
            System.out.println("Map is NULL! An error must have occured while reading it");
        }
    }

    public static void main(String[] args) {
        Mapa m = new Mapa("data/practica2/maps/mapWithComplexObstacle2.txt");
        m.showMap();
    }
}