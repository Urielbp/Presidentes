package es.usal.pa;

import java.io.IOException;
import java.util.Iterator;

import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class CyclicIdiomas extends CyclicBehaviour {

	private static final long serialVersionUID = 1L;
	protected ObservableMensaje mensaje;
	protected AgentePresidente agente;
	
	public CyclicIdiomas(AgentePresidente agente ,ObservableMensaje mensaje) {
		super(agente);
		this.mensaje = mensaje;
		this.agente = agente;
	}
	
	@Override
	public void action() {
		ACLMessage msg=this.myAgent.blockingReceive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchOntology("Idiomas")));
		System.out.println("Recibí una petición de los idiomas.");
		//string to AID
		Iterator it = msg.getAllReplyTo();
		while(it.hasNext()) {
			Object ag = it.next();
			ACLMessage msg2 = new ACLMessage(ACLMessage.INFORM);
			msg2.addReceiver((AID)ag);
			msg2.setOntology("Idiomas");
			msg2.setLanguage(new SLCodec().getName());
            //el mensaje se transmita en XML
            msg2.setEnvelope(new Envelope());
			//cambio la codificacion de la carta
			msg2.getEnvelope().setPayloadEncoding("ISO8859_1");
			
			String[]idiomas = this.agente.getIdiomas();
			try {
				msg2.setContentObject(idiomas);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.agente.send(msg2);
		}

	}

}
