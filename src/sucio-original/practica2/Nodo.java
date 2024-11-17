package sucio-original.practica2;

// Nodo para representar el estado actual en A*
class Nodo implements Comparable<Nodo> {
    int x, y;
    int gCost, hCost, fCost; // g: coste actual, h: heurística, f: g + h
    Nodo parent;

    public Nodo(int x, int y, int gCost, int hCost, Nodo parent) {
        this.x = x;
        this.y = y;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = gCost + hCost;
        this.parent = parent;
    }

    @Override
    public int compareTo(Nodo other) {
        return Integer.compare(this.fCost, other.fCost);
    }

}
