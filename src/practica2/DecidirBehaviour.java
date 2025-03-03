/* package practica2;

import jade.core.Agent;
import jade.core.Behaviour;

import practica2.*;
import java.util.List;
import java.util.ArrayList;

public class DecidirBehaviour extends Behaviour {

    public void comportamiento_basico(int casilla_inicial_x,int casilla_inicial_y,int casilla_final_x,int casilla_final_y){
        
        //Mapa mapa = new Mapa("url.../mario/medina/lopez");

        Casilla<Integer, Integer> casilla_actual = new Casilla<>(casilla_inicial_x,casilla_inicial_y);

        List<Casilla<Integer,Integer>> camino = new ArrayList<>();

        camino.add(casilla_actual);

        int mover_x = 0;
        int mover_y = 0;


        while(casilla_actual.getCoordX()==casilla_final_x && casilla_actual.getCoordY()==casilla_final_y){
            
            //Control coordenada X
            //Primero decidimos si tenemos que movernos a la izq o a la derecha
            if(casilla_actual.getCoordX()<casilla_final_x){
                mover_x=casilla_actual.getCoordX()-casilla_final_x;
            }
            else if (casilla_actual.getCoordX()>casilla_final_x){
                mover_x = casilla_actual.getCoordX()+casilla_final_x;
            }
            else
                mover_x=0;
            
            //Control coordenada Y
            //Primero decidimos si tenemos que movernos arriba o abajo
            if(casilla_actual.getCoordY()<casilla_final_y){
                mover_y=casilla_actual.getCoordY()-casilla_final_y;
            }
            else if (casilla_actual.getCoordY()>casilla_final_y){
                mover_y = casilla_actual.getCoordY()+casilla_final_y;
            }
            else
                mover_y=0;
            
            //agente.moverXY(mover_x,mover_y);
            //camino.add(Comportamiento.casillaActual);
        }
    }

    public void comportamiento_obstaculos_basicos(){
        // Obtener las coordenadas finales
        int destinoX = casilla_final_x;
        int destinoY = casilla_final_y;
    
        // Obtener las coordenadas actuales
        int actualX = casilla_actual.getCoordX();
        int actualY = casilla_actual.getCoordY();
    
        // Ejecutar mientras no se haya alcanzado la casilla final
        while (actualX != destinoX || actualY != destinoY) {
            
            // Movimiento en Y (arriba o abajo)
            int mover_y = actualY;
            if (actualY < destinoY) {
                mover_y = actualY + 1; // Mover hacia abajo
            } else if (actualY > destinoY) {
                mover_y = actualY - 1; // Mover hacia arriba
            }
            
            // Comprobación de obstáculo en Y
            boolean coord_y = true;
            if (matriz[actualX][mover_y].equals("Obstaculo")) {
                coord_y = false;
            } else {
                actualY = mover_y; // Actualizar coordenada Y si no hay obstáculo
            }
    
            // Movimiento en X (izquierda o derecha)
            int mover_x = actualX;
            if (actualX < destinoX) {
                mover_x = actualX + 1; // Mover hacia la derecha
            } else if (actualX > destinoX) {
                mover_x = actualX - 1; // Mover hacia la izquierda
            }
            
            // Comprobación de obstáculo en X
            boolean coord_x = true;
            if (matriz[mover_x][actualY].equals("Obstaculo")) {
                coord_x = false;
            } else {
                actualX = mover_x; // Actualizar coordenada X si no hay obstáculo
            }
    
            // Si hay obstáculos en ambos ejes, busca una alternativa
            if (!coord_y && !coord_x) {
                // Buscar movimiento alternativo en X o Y que esté libre
                if (mover_x - 1 >= 0 && matriz[mover_x - 1][actualY].equals("Libre")) {
                    mover_x -= 1; // Mover a la izquierda si está libre
                } else if (mover_x + 1 < matriz.length && matriz[mover_x + 1][actualY].equals("Libre")) {
                    mover_x += 1; // Mover a la derecha si está libre
                } else {
                    System.out.println("El agente está bloqueado en su posición actual.");
                    break; // Detener si no hay más opciones de movimiento
                }
            }
    
            // Realizar movimiento del agente a las nuevas coordenadas
            agente.moverXY(actualX, actualY);
            
            // Actualizar posición en el camino recorrido
            camino.add(Comportamiento.casillaActual());
        }
    } 

    Entorno entorno;
    Mapa mapa;
    int filaMovimiento, colMovimiento;
    AgenteP2 agente;

    public DecidirBehaviour(Entorno entorno, AgenteP2 agente){
        this.entorno = entorno;
        this.agente = agente;
    }

    @Override
    public void action(){

        List<String> mov_pos = agente.getMov_pos();

        if(entorno.getFilAgente()>mapa.filaMeta()){
            if(mov_pos.contains("NORTE")){
                //entorno.movimiento("NORTE");
                filaMovimiento=entorno.getFilAgente()-1;
            }
        }else if(entorno.getFilAgente()>mapa.filaMeta()){
            if(mov_pos.contains("SUR")){
                //entorno.movimiento("SUR");


                filaMovimiento=entorno.getFilAgente()+1;
            }
        }else{
            if(entorno.getColAgente()>mapa.columnaMeta()){
                if(mov_pos.contains("OESTE")){
                    //entorno.movimiento("OESTE");
                    colMovimiento=entorno.getColAgente()-1;
                }
            }else if(entorno.getColAgente()>mapa.columnaMeta()){
                if(mov_pos.contains("ESTE")){
                    //entorno.movimiento("ESTE");
                    colMovimiento=entorno.getColAgente()+1;
                }
            }
        }

        ArrayList<Integer> posicion = new ArrayList<Integer>();
        posicion.add(filaMovimiento);
        posicion.add(colMovimiento);
        
        agente.caminoRecorrido.add(posicion);  
    }

    @Override
    public boolean done() {
        if (entorno.getFilAgente() == mapa.filaMeta() && entorno.getColAgente() == mapa.columnaMeta())
            return true;
        else
            return false;
    }
}
 */