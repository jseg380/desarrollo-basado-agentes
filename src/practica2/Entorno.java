package practica2;

import java.util.ArrayList;
import java.util.List;


public class Entorno {
    private Mapa mapa;
    private int filAgente;
    private int colAgente;
    private int map[][];
    

    public Entorno(String nombre_mapa, int filaInicio, int columnaInicio, int filMeta, int colMeta) {
/*         filAgente = filaInicio;
        colAgente = columnaInicio;
        mapa = new Mapa(nombre_mapa, fil_fin, col_fin);
        map = mapa.getMapa(); */
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
        
        if (map[filAgente + 1][colAgente] != -1 && filAgente < mapa.getFilas() - 1) { // SUR
            
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
        
        if (map[filAgente][colAgente + 1] != -1 && colAgente < mapa.getColumnas() - 1) { // ESTE
            
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

    /* public void movimiento (String mov) {

        switch (mov) {
            case "NORTE":
                this.setPosAgente(filAgente - 1, colAgente);
                break;
    
            case "SUR":
                this.setPosAgente(filAgente + 1, colAgente); 
                break;
    
            case "ESTE":
                this.setPosAgente(filAgente, colAgente + 1); 
                break;
    
            case "OESTE":
                this.setPosAgente(filAgente, colAgente - 1); 
                break;
    
            case "NOROESTE":
                this.setPosAgente(filAgente - 1, colAgente - 1); 
                break;
    
            case "NORESTE":
                this.setPosAgente(filAgente - 1, colAgente + 1); 
                break;
    
            case "SUROESTE":
                this.setPosAgente(filAgente + 1, colAgente - 1); 
                break;
                
            case "SURESTE":
                this.setPosAgente(filAgente + 1, colAgente + 1); 
                break;
    
            default:
                break;
        }
    } */
}
