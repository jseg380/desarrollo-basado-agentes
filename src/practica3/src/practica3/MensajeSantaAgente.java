package practica3;

import java.util.Random;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class MensajeSantaAgente extends Behaviour {

    private int step = 0;
    private boolean done = false;

    @Override
    public void action() {
        switch (this.step) {
            case 0 -> {
                // Primera traduccion humano-santa 
                // Esperar el mensaje del agente
                ACLMessage msg = myAgent.blockingReceive();
                System.out.println(msg);
                System.out.println("Santa recibe: " + msg.getContent());

                if (msg.getPerformative() == ACLMessage.PROPOSE) {
                    Random rand = new Random();
                    int n = rand.nextInt(10);

                    if (n < 8) {

                        ACLMessage reply = msg.createReply(ACLMessage.ACCEPT_PROPOSAL);
                        reply.setConversationId("ConversacionSanta");
                        reply.setContent(((Santa) myAgent).formulaRespuesta(((Santa) myAgent).getClaveSecreta()));

                        System.out.println("Santa envia: " + reply.getContent());

                        this.myAgent.send(reply);
                        this.step = 1;

                    } else {
                        ACLMessage reply = msg.createReply(ACLMessage.REJECT_PROPOSAL);
                        reply.setContent(((Santa) myAgent).formulaRespuesta("A bailar loco"));

                        System.out.println("Santa envia: " + reply.getContent());

                        this.myAgent.send(reply);
                        this.myAgent.doDelete();
                    }
                }
            }
            case 1 -> {
                ACLMessage msg = myAgent.blockingReceive();
                System.out.println("Santa recibe: " + msg.getContent());
                if (msg.getPerformative() == ACLMessage.REQUEST) {

                    ACLMessage reply = msg.createReply(ACLMessage.AGREE);
                    reply.setConversationId("ConversacionSanta");

                    String respuesta = ((Santa) myAgent).formulaRespuesta(((Santa) myAgent).getPosicion().toString());
                    reply.setContent(respuesta);

                    System.out.println("Santa envia coordenadas a agente: " + reply.getContent());

                    this.myAgent.send(reply);
                    this.step = 2;
                }
            }
            //devolver mensaje cuando llegue
            case 2 -> {
                ACLMessage msg = myAgent.blockingReceive();
                System.out.println("Ultimo");
                System.out.println("Santa recibe: " + msg.getContent());

                if (msg.getPerformative() == ACLMessage.INFORM_IF) {

                    ACLMessage reply = msg.createReply(ACLMessage.INFORM);

                    reply.setContent(((Santa) myAgent).formulaRespuesta("Hohoho"));
                    reply.setConversationId("ConversacionSanta");
                    System.out.println("Santa envia: " + reply.getContent());

                    this.myAgent.send(reply);
                    done = true;
                    myAgent.doDelete();

                    //Salir del switch
                }
            }
        }
    }

    @Override
    public boolean done() {
        if (this.done) {
            System.out.println("Done de Santa");
            myAgent.doDelete();
            return true;
        } else {
            return false;
        }
    }
}
