
package practica1;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class Ejercicio3 extends Agent {

  @Override
  protected void setup() {
    System.out.println("Ejercicio 3: agente básico que muestra un mensaje cada 2 segundos.");
    addBehaviour(new TickerBehaviour(this, 2000) {
      @Override
      protected void onTick() {
        System.out.println("Mensaje periódico.");
      }
    });
  }
}
