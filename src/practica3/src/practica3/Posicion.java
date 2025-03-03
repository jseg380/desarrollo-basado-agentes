package practica3;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase Posicion Representa un lugar en el mapa.
 */
public class Posicion {

    public int x = 0;
    public int y = 0;

    /**
     * Crea una posición dado x e y
     *
     * @param x Fila de la posición
     * @param y Columna de la posición
     */
    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Establece posición dadas las coordenadas
     *
     * @param x Fila de la posición
     * @param y Columna de la posición
     */
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Comprobar si dos posiciones son iguales
     *
     * @param otraPos Posición con la que comparar
     * @return {@code boolean} Representa si las posiciones son iguales
     */
    public boolean esIgualA(Posicion otraPos) {
        return (this.x == otraPos.x && this.y == otraPos.y);
    }

    /**
     * Implementar método de comparación para objetos Posicion
     *
     * @param o Objeto con el que comparar
     * @return {@code boolean} Representa si las posiciones son iguales
     */
    @Override
    public boolean equals(Object o) {
        Posicion posicion = (Posicion) o;
        return (this.x == posicion.x && this.y == posicion.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Método para convertir un objeto Posicion en String
     *
     * @return {@code String} Objeto Posicion stringified
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * Método para rellenar el objeto Posicion una posicion en String
     *
     * @param str Objeto posicion convertido a String con el método toString()
     */
    public void fromString(String str) {
        String regex = "\\((\\d+),\\s*(\\d+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));

        setPos(x, y);
    }
}
