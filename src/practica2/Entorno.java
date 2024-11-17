package practica2;

import java.util.ArrayList;
import java.util.List;

public class Entorno {
    private Mapa mapa;
    private int filAgente;
    private int colAgente;
    private int map[][];
    private int filMeta;
    private int colMeta;

    public Entorno(String nombre_mapa, int filaInicio, int columnaInicio, int filaMeta, int columnaMeta) {
        filAgente = filaInicio;
        colAgente = columnaInicio;
        mapa = new Mapa(nombre_mapa);
        map = mapa.getMapa();
        filMeta = filaMeta;
        colMeta = columnaMeta;
    }

    public void setPosAgente(int filAgente, int colAgente) {
        this.filAgente = filAgente;
        this.colAgente = colAgente;
    }

    public int getFilAgente() {
        return filAgente;
    }

    public int getColAgente() {
        return colAgente;
    }

    public int getFilMeta() {
        return -1;
        // return filMeta;
    }

    public int getColMeta() {
        return -1;
        // return colMeta;
    }

    public List<String> posiblesMovimientos () {

        List<String> mov_pos = new ArrayList<String>();
        
        if(map[filAgente - 1][colAgente] != -1 && filAgente > 0) { // NORTE
            
            mov_pos.add("NORTE");

            if(map[filAgente - 1][colAgente + 1] != -1) { // NORESTE
                mov_pos.add("NORESTE");
            }
            
            if (map[filAgente - 1][colAgente - 1] != -1) { // NOROESTE
                mov_pos.add("NOROESTE");
            }
            
        } 
        
        if (map[filAgente + 1][colAgente] != -1 && filAgente < mapa.getFils() - 1) { // SUR
            
            mov_pos.add("SUR");

            if(map[filAgente + 1][colAgente + 1] != -1) { // SURESTE
                mov_pos.add("SURESTE");
            }
            
            if (map[filAgente + 1][colAgente - 1] != -1) { // SUROESTE
                mov_pos.add("SUROESTE");
            }

        }
        
        if (map[filAgente][colAgente - 1] != -1 && colAgente > 0) { // OESTE
            
            mov_pos.add("OESTE");

            if (map[filAgente - 1][colAgente - 1] != -1) { // NOROESTE
                mov_pos.add("NOROESTE");
            }

            if (map[filAgente + 1][colAgente - 1] != -1) { // SUROESTE
                mov_pos.add("SUROESTE");
            }

        }
        
        if (map[filAgente][colAgente + 1] != -1 && colAgente < mapa.getCols() - 1) { // ESTE
            
            mov_pos.add("ESTE");

            if(map[filAgente - 1][colAgente + 1] != -1) { // NORESTE
                mov_pos.add("NORESTE");
            } 
            
            if(map[filAgente + 1][colAgente + 1] != -1) { // SURESTE
                mov_pos.add("SURESTE");
            }
        }

        return mov_pos;
    }
}
