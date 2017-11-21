package es.usal.pa;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class CyclicRecibirMensaje extends CyclicBehaviour {

	private static final long serialVersionUID = 1L;
	protected ObservableMensaje mensaje;
	protected AgentePresidente agente;
	
	public CyclicRecibirMensaje(AgentePresidente agente ,ObservableMensaje mensaje) {
		super(agente);
		this.mensaje = mensaje;
		this.agente = agente;
	}
	
	@Override
	public void action() {
		ACLMessage msg=this.myAgent.blockingReceive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchOntology("Directo")));
		try
		{
			//System.out.println("Mensaje: "+ (String)msg.getContentObject());
			//mensaje.setContenido((String)msg.getContentObject());
			mensaje = (ObservableMensaje) msg.getContentObject();
			System.out.println("[R " 
			+ this.agente.getLocalName() 
			+ "]" 
			+ mensaje.remitenteInicial
			+ ":"
			+ mensaje.remitente
			+ ":"
			+ mensaje.idioma
			+ ":"
			+ mensaje.contenido
			+ ":"
			+ this.agente.getLocalName());
			//Unicamente se considera como destinarario el propio agente, sin traducciones
		}
		catch (UnreadableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
