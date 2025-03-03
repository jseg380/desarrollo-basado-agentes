package practica3;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

public class ComunicacionesAgente {

    private int stepElfo;
    private int stepRudolph;
    private int stepSanta;
    private int contadorRenos;
    private String CONVERSATION_ELFO_ID;
    private String CONVERSATION_SANTA_ID;
    private String CONVERSATION_RUDOLPH_ID;

    private boolean enviarMensajeElfo;
    private boolean enviarMensajeRudolph;
    private boolean enviarMensajeSanta;

    private String mensage;
    private String respuesta;
    private String contrasenia;

    private AgenteP3 agente;

    // Mensajes entre agentes
    private ACLMessage msgSanta;
    private ACLMessage msgElfo;
    private ACLMessage msgRudolph;

    public ComunicacionesAgente(AgenteP3 agente) {

        this.contadorRenos = 0;
        this.stepElfo = 0;
        this.stepRudolph = 0;
        this.stepSanta = 0;
        this.CONVERSATION_ELFO_ID = "ConversacionElfo";
        this.CONVERSATION_SANTA_ID = "ConversacionSanta";
        this.CONVERSATION_RUDOLPH_ID = "ConversacionRudolph";

        this.agente = agente;
        this.mensage = "";
        this.respuesta = "";
        this.enviarMensajeElfo = true;
        this.enviarMensajeRudolph = false;
        this.enviarMensajeSanta = false;
        this.msgElfo = new ACLMessage(ACLMessage.REQUEST);
        this.msgRudolph = new ACLMessage(ACLMessage.REQUEST);
        this.msgSanta = new ACLMessage(ACLMessage.PROPOSE);
    }

    public String getMensage() {
        return mensage;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setMensage(String mensage) {
        this.mensage = mensage;
    }

    // Metodos set y get para mensajes de Rudolf, Elfo y Santa
    public void setEnviarMensajeRudolf(boolean estado) {
        this.enviarMensajeRudolph = estado;
    }

    public void setEnviarMensajeElfo(boolean estado) {
        this.enviarMensajeElfo = estado;
    }

    public void setEnviarMensajeSanta(boolean estado) {
        this.enviarMensajeSanta = estado;
    }

    public boolean getEnviarMensajeElfo() {
        return enviarMensajeElfo;
    }

    public boolean getEnviarMensajeRudolf() {
        return enviarMensajeRudolph;
    }

    public boolean getEnviarMensajeSanta() {
        return enviarMensajeSanta;
    }

    public void comunicacion() {

        if (this.enviarMensajeElfo) {

            switch (this.stepElfo) {

                // Enviamos el mensaje a elfo
                case 0 -> {
                    msgElfo.addReceiver(new AID("Elfo", AID.ISLOCALNAME));
                    msgElfo.setConversationId(CONVERSATION_ELFO_ID);
                    msgElfo.setContent("Bro puedo jugar en plan");
                    agente.send(msgElfo);
                    System.out.println("Primer mensaje enviado al elfo traduciendo solicitud de confianza a santa");
                    this.stepElfo = 1;
                }

                // Recibimos el mensaje traducido del elfo
                case 1 -> {
                    ACLMessage msg = agente.blockingReceive();
                    if (msg.getConversationId().equals(CONVERSATION_ELFO_ID)
                            && msg.getPerformative() == ACLMessage.INFORM) {

                        respuesta = msg.getContent();
                        this.stepElfo = 2;
                        System.out.println("Mensaje recibido del elfo con la solicitud para pedir confianza de santa");
                        this.setEnviarMensajeElfo(false);
                        this.setEnviarMensajeSanta(true);
                    }
                }

                // Enviar a traducir la respuesta de santa
                case 2 -> {
                    System.out.println("Agente envia a traducir mensaje de Santa: " + this.respuesta);
                    ACLMessage msg = msgElfo.createReply();
                    msg.setConversationId(CONVERSATION_ELFO_ID);
                    msg.addReceiver(new AID("Elfo", AID.ISLOCALNAME));
                    msg.setContent(this.respuesta);
                    agente.send(msg);
                    System.out.println("Primera respuesta de santa enviada al elfo a ver si nos ha dado la contraseña");
                    this.stepElfo = 3;
                }

                // Recibir traduccion del mensaje de santa
                // Hay que editarlo para que tambien sea las coordenadas del santa
                case 3 -> {
                    ACLMessage msg = agente.blockingReceive();
                    if (msg.getConversationId().equals(CONVERSATION_ELFO_ID)
                            && msg.getPerformative() == ACLMessage.INFORM) {

                        respuesta = msg.getContent();
                        this.stepElfo = 4;

                        String contrasenia = respuesta.substring(4, respuesta.length() - 8).trim();

                        this.setContrasenia(contrasenia);
                        System.out.println("Recepción de la contraseña traducida desde el elfo(respuesta): " + respuesta);
                        String prueba_resp = "Bro " + getContrasenia() + " en plan";
                        System.out.println("Recepción de la contraseña traducida desde el elfo: " + prueba_resp);
                        if (respuesta.equals(prueba_resp)) {
                            System.out.println("Vamos por buen camino ");
                            this.setEnviarMensajeElfo(false);
                            this.setEnviarMensajeRudolf(true);
                        } else {
                            System.out.println("Vamos por mal camino ");
                            this.setEnviarMensajeElfo(false);
                            this.setEnviarMensajeSanta(false);
                            this.setEnviarMensajeRudolf(false);
                        }
                    }
                }

                // Le envio al elfo : "Dime coordenadas santa"
                case 4 -> {
                    ACLMessage msg = msgElfo.createReply();
                    msg.setConversationId(CONVERSATION_ELFO_ID);
                    msg.addReceiver(new AID("Elfo", AID.ISLOCALNAME));
                    msg.setContent("Bro santa dime tus coordenadas en plan");
                    System.out.println("Mensaje enviado al elfo para que nos traduzca el mensaje: Bro santa dime tus coordenadas en plan ");
                    agente.send(msg);
                    this.stepElfo = 5;
                }
                case 5 -> {
                    ACLMessage msg = agente.blockingReceive();
                    if (msg.getConversationId().equals(CONVERSATION_ELFO_ID)
                            && msg.getPerformative() == ACLMessage.INFORM) {

                        respuesta = msg.getContent();
                        this.stepElfo = 6;

                        this.setEnviarMensajeElfo(false);
                        this.setEnviarMensajeSanta(true);
                    }
                }
                case 6 -> {
                    ACLMessage msg = msgElfo.createReply();
                    msg.addReceiver(new AID("Elfo", AID.ISLOCALNAME));
                    msg.setConversationId(CONVERSATION_ELFO_ID);
                    msg.setContent(respuesta);
                    agente.send(msg);
                    this.stepElfo = 7;
                }
                case 7 -> {
                    ACLMessage msg = agente.blockingReceive();
                    if (msg.getConversationId().equals(CONVERSATION_ELFO_ID)
                            && msg.getPerformative() == ACLMessage.INFORM) {

                        respuesta = msg.getContent();
                        // this.stepElfo = 0;

                        String mensaje = respuesta.substring(4, respuesta.length() - 8).trim();
                        System.out.println("Recibimos las coordenadas de santa: " + mensaje);
                        String[] partes = mensaje.substring(1, mensaje.length() - 1).split(",");

                        // Convertir las partes a enteros
                        int x = Integer.parseInt(partes[0]);
                        int y = Integer.parseInt(partes[1]);
                        System.out.println("Las coordenadas de santa son: " + x + "," + y);
                        Posicion coords = new Posicion(x, y);
                        agente.setCoordenadas(coords);
                        this.stepElfo = 8;

                        agente.setComunicar(false);
                    }

                }

                case 8 -> {
                    ACLMessage msg = msgElfo.createReply();
                    msg.addReceiver(new AID("Elfo", AID.ISLOCALNAME));
                    msg.setConversationId(CONVERSATION_ELFO_ID);
                    msg.setContent("Bro santa llegué en plan");
                    agente.send(msg);
                    this.stepElfo = 9;
                }
                case 9 -> {
                    ACLMessage msg = agente.blockingReceive();
                    if (msg.getConversationId().equals(CONVERSATION_ELFO_ID)
                            && msg.getPerformative() == ACLMessage.INFORM) {

                        respuesta = msg.getContent();

                        this.setEnviarMensajeElfo(false);
                        this.setEnviarMensajeSanta(true);
                        System.out.println("Caso 9 elfo");
                        this.stepElfo = 10;
                    }

                }
                case 10 -> {
                    ACLMessage msg = msgElfo.createReply();
                    msg.addReceiver(new AID("Elfo", AID.ISLOCALNAME));
                    msg.setConversationId(CONVERSATION_ELFO_ID);
                    msg.setContent(respuesta);
                    System.out.println("Caso 10 elfo");
                    System.out.println("Enviamos la ultima traduccion al elfo " + msg.getContent());
                    agente.send(msg);
                    this.stepElfo = 11;
                }
                case 11 -> {
                    ACLMessage msg = agente.blockingReceive();
                    if (msg.getConversationId().equals(CONVERSATION_ELFO_ID)
                            && msg.getPerformative() == ACLMessage.INFORM) {

                        respuesta = msg.getContent();
                        System.out.println("Acabamoooos: " + msg.getContent());
                        if (respuesta.equals("Bro Hohoho en plan")) {
                            agente.setFinalizado(true);
                            System.out.println("Acabamoooos");
                        }

                    }
                }
                default -> {
                }
            }

        } else if (this.enviarMensajeRudolph) {

            switch (this.stepRudolph) {
                case 0 -> {
                    msgRudolph.addReceiver(new AID("Rudolph", AID.ISLOCALNAME));
                    msgRudolph.setConversationId(CONVERSATION_RUDOLPH_ID);
                    msgRudolph.setContent("Bro " + this.getContrasenia() + " en plan");
                    System.out.println("Enviamos primer mensaje a rodolfo " + msgRudolph.getContent());
                    agente.send(msgRudolph);
                    this.stepRudolph = 1;
                }
                case 1 -> {
                    ACLMessage msg = agente.blockingReceive();
                    if (msg.getPerformative() == ACLMessage.AGREE) {
                        String coordenadas = msg.getContent();

                        String[] partes = coordenadas.substring(1, coordenadas.length() - 1).split(",");

                        // Convertir las partes a enteros
                        int x = Integer.parseInt(partes[0]);
                        int y = Integer.parseInt(partes[1]);
                        Posicion coords = new Posicion(x, y);
                        agente.setCoordenadas(coords);
                        this.contadorRenos++;
                        System.out.println("Buscamoo reno:  " + coords.toString());
                        /*if (contadorRenos == 8) {
                            System.out.println("Fin renos");
                            this.setEnviarMensajeRudolf(false);
                            this.setEnviarMensajeElfo(true);

                        } else {
                            this.stepRudolph = 2;
                        }*/

                        this.stepRudolph = 2;

                        agente.setComunicar(false);

                    }
                }

                case 2 -> {
                    ACLMessage msg = msgRudolph.createReply();
                    msg.addReceiver(new AID("Rudolph", AID.ISLOCALNAME));
                    msg.setConversationId(CONVERSATION_RUDOLPH_ID);
                    msg.setContent("Bro dame el siguiente en plan");
                    agente.send(msg);
                    if (contadorRenos == 8) {
                        System.out.println("Fin renos");
                        this.setEnviarMensajeRudolf(false);
                        this.setEnviarMensajeElfo(true);
                    } else {
                        this.stepRudolph = 1;
                    }
                }

                default -> {
                }
            }

        } else if (this.enviarMensajeSanta) {

            switch (this.stepSanta) {
                case 0 -> {
                    msgSanta.addReceiver(new AID("Santa", AID.ISLOCALNAME));
                    msgSanta.setConversationId(CONVERSATION_SANTA_ID);
                    msgSanta.setContent(respuesta);
                    agente.send(msgSanta);
                    this.stepSanta = 1;
                }

                case 1 -> {
                    ACLMessage msg = agente.blockingReceive();
                    if (msg.getConversationId().equals(CONVERSATION_SANTA_ID)
                            && msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {

                        System.out.println("Agente recibe respuesta de santa: " + msg.getContent());
                        this.respuesta = msg.getContent();
                        this.stepSanta = 2;

                        this.setEnviarMensajeElfo(true);
                        this.setEnviarMensajeSanta(false);
                    }
                }
                case 2 -> {
                    ACLMessage msg = msgSanta.createReply(ACLMessage.REQUEST);
                    msg.addReceiver(new AID("Santa", AID.ISLOCALNAME));
                    msg.setConversationId(CONVERSATION_SANTA_ID);
                    msg.setContent(respuesta);
                    System.out.println("Agente encuentra los renos y avisa a santa: " + msg.getContent());
                    agente.send(msg);
                    this.stepSanta = 3;
                }
                case 3 -> {
                    ACLMessage msg = agente.blockingReceive();
                    if (msg.getConversationId().equals(CONVERSATION_SANTA_ID)
                            && msg.getPerformative() == ACLMessage.AGREE) {

                        respuesta = msg.getContent();
                        System.out.println("Agente recibe coords de santa: " + msg.getContent());
                        this.stepSanta = 4;

                        this.setEnviarMensajeElfo(true);
                        this.setEnviarMensajeSanta(false);
                    }
                }

                case 4 -> {
                    ACLMessage msg = msgSanta.createReply(ACLMessage.INFORM_IF);
                    msg.addReceiver(new AID("Santa", AID.ISLOCALNAME));
                    msg.setConversationId(CONVERSATION_SANTA_ID);
                    msg.setContent(respuesta);
                    System.out.println("Agente avisa a santa que lo ha encontrado: " + msg.getContent());
                    agente.send(msg);
                    this.stepSanta = 5;
                }
                case 5 -> {
                    ACLMessage msg = agente.blockingReceive();
                    if (msg.getConversationId().equals(CONVERSATION_SANTA_ID)
                            && msg.getPerformative() == ACLMessage.INFORM) {

                        respuesta = msg.getContent();
                        System.out.println("Santa avisa a agente que ha sido encontrado (falta traducir): " + msg.getContent());

                        this.setEnviarMensajeElfo(true);
                        System.out.println(this.enviarMensajeElfo);
                        this.setEnviarMensajeSanta(false);
                        System.out.println(this.enviarMensajeSanta);
                    }
                }
                default -> {
                }
            }

        } else {

        }
    }
}
