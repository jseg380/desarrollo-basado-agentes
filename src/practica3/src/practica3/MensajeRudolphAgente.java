package practica3;

import java.util.ArrayList;
import java.util.List;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class MensajeRudolphAgente extends Behaviour {

    private int step = 0;
    private String password = "OK JUEGA";
    private List<Posicion> lista;
    private boolean done = false;
    private int renos = 0;

    public MensajeRudolphAgente(List<Posicion> lista) {

        this.lista = new ArrayList<>(lista);
    }

    @Override
    public void action() {
        switch (this.step) {
            case 0 -> {
                ACLMessage msg = myAgent.blockingReceive();
                if (msg.getPerformative() == ACLMessage.REQUEST) {
                    String clave = "Bro " + ((Rudolph) myAgent).getClaveSecreta() + " en plan";
                    if (msg.getContent().equals(clave)) {
                        System.out.println("Que si q soy rodolfo y buscame los renos");
                        ACLMessage reply = msg.createReply(ACLMessage.AGREE);
                        String posicion = lista.get(0).toString();
                        reply.setConversationId("ConversacionRudolph");
                        reply.setContent(posicion);
                        this.myAgent.send(reply);
                        this.step = 1;
                        this.renos++;
                    } else {
                        ACLMessage reply = msg.createReply(ACLMessage.REFUSE);
                        reply.setConversationId("ConversacionRudolph");
                        reply.setContent("Contraseña incorrecta");
                    }
                }
            }
            case 1 -> {
                ACLMessage msg = myAgent.blockingReceive();
                if (msg.getPerformative() == ACLMessage.REQUEST) {
                    ACLMessage reply = msg.createReply(ACLMessage.AGREE);
                    String posicion = lista.get(this.renos).toString();
                    reply.setContent(posicion);
                    reply.setConversationId("ConversacionRudolph");
                    this.myAgent.send(reply);
                    this.renos++;
                    if (this.renos == 8) {
                        this.step = 2;
                    }
                }
            }
            case 2 -> {
                //decir que ha encontrado a todos los renos
                ACLMessage msg = myAgent.blockingReceive();

                if (msg.getPerformative() == ACLMessage.REQUEST) {
                    //REFUSE PARA QUE EL RUDOLPH LE DIGA AL AGENTE QUE YA NO HAY MAS RENOS
                    ACLMessage reply = msg.createReply(ACLMessage.REFUSE);
                    String MensajeReno = "Has encontrado a los 8 renos";

                    reply.setContent(MensajeReno);
                    reply.setConversationId("ConversacionRudolph");
                    this.myAgent.send(reply);
                }

                done = true;
            }
        }
    }

    @Override
    public boolean done() {
        if (this.done) {
            System.out.println("Done de Rudolph");
            myAgent.doDelete();
            return true;
        } else {
            return false;
        }
    }
}
