package es.usal.pa;

import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;

public class ParallelMensaje extends ParallelBehaviour {

	private static final long serialVersionUID = 1L;
	
	protected CyclicLeer cl;
	protected CyclicRecibirMensaje crm;
	protected CyclicIdiomas ci;
	
	public ParallelMensaje(AgentePresidente agente, ObservableMensaje mensaje) {
		super();

		cl = new CyclicLeer(agente, mensaje);
		crm = new CyclicRecibirMensaje(agente, mensaje);
		ci = new CyclicIdiomas(agente, mensaje);
		
		ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();
		addSubBehaviour(tbf.wrap(cl));
		
		tbf = new ThreadedBehaviourFactory();
		addSubBehaviour(tbf.wrap(crm));
		
		tbf = new ThreadedBehaviourFactory();
		addSubBehaviour(tbf.wrap(ci));
		}
	

}
