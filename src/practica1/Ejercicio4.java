
package practica1;

import java.util.Scanner;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.CyclicBehaviour;

public class Ejercicio4 extends Agent {

  protected int num;
  protected int cont = 0;
  protected double suma = 0,
                   media;

  @Override
  protected void setup() {
    System.out.println("Ejercicio 4: agente que obtiene la media de una cantidad determinada de números");

    System.out.print("Cuántos números se van a introducir: ");
    Scanner scanner = new Scanner(System.in);
    
    num = scanner.nextInt();

    // CyclicBehaviour que ejecuta num veces
    addBehaviour(new CyclicBehaviour() {

      @Override
      public void action() {
        if (cont < num) {
          System.out.print("Introduce número " + Integer.toString(cont + 1) + ": ");
          suma += scanner.nextInt();
          System.out.println();

          cont++;
        } else {
          // Borrar comportamiento cíclico
          myAgent.removeBehaviour(this);

          addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
              media = suma / num;
              System.out.println("La media es " + Double.toString(media));
            }
          });
        }
      }
    });

  }
}
