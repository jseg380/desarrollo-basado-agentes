package practica3;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class MensajeElfoAgente extends Behaviour {

    private int step = 0;
    private boolean done = false;

    @Override
    public void action() {
        switch (this.step) {
            case 0 -> {

                // Primera traduccion humano-santa
                ACLMessage msg = myAgent.blockingReceive();
                System.out.println("Elfo recibe: " + msg.getContent());

                if (msg.getPerformative() == ACLMessage.REQUEST) {

                    String contenido = msg.getContent();

                    if (contenido.startsWith("Bro") && contenido.endsWith("en plan")) {
                        String mensaje = contenido.substring(4, contenido.length() - 8).trim();
                        String respuesta = "Rakas Joulupukki " + mensaje + " Kiitos";

                        System.out.println("Elfo envia: " + respuesta);

                        ACLMessage reply = msg.createReply();
                        reply.setConversationId("ConversacionElfo");
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent(respuesta);
                        this.myAgent.send(reply);
                    }
                    this.step = 1;
                }

            }
            case 1 -> {

                // Traduccion santa-humano
                ACLMessage msg = myAgent.blockingReceive();
                System.out.println("Elfo recibe: " + msg.getContent());

                if (msg.getPerformative() == ACLMessage.REQUEST) {

                    String contenido = msg.getContent();

                    if (contenido.startsWith("Hyvää joulua") && contenido.endsWith("Nähdään pian")) {
                        String mensaje = contenido.substring(13, contenido.length() - 13).trim();
                        String respuesta = "Bro " + mensaje + " en plan";

                        System.out.println("Elfo envia: " + respuesta);

                        ACLMessage reply = msg.createReply();
                        reply.setConversationId("ConversacionElfo");
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent(respuesta);
                        this.myAgent.send(reply);
                        if (reply.getContent().equals("Bro Hohoho en plan")) {
                            done = true;
                            myAgent.doDelete();
                        }
                    }
                    this.step = 0;
                }
            }
        }
    }

    @Override
    public boolean done() {
        if (this.done) {
            System.out.println("Done de Elfo");
            myAgent.doDelete();
            return true;
        } else {
            return false;
        }
    }
}
