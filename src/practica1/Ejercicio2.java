
package practica1;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class Ejercicio2 extends Agent {

  private class Comportamiento2 extends OneShotBehaviour {
    @Override
    public void action() {
      System.out.println("Comportamiento del ejercicio 1: solo un mensaje");
      doDelete();
    }

    protected void takeDown() {
      System.out.println("Comportamiento terminado");
    }
  }
  
  @Override
  protected void setup() {
    System.out.println("Ejercicio 1: agente básico que muestre un mensaje por consola.");
    addBehaviour(new Comportamiento2());
  }
}
