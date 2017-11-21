package es.usal.pa;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import java.util.Scanner;


public class CyclicLeer extends CyclicBehaviour {
	
	private static final long serialVersionUID = 1L;
	Scanner sc;
	AgentePresidente agente = null;
	protected ObservableMensaje mensaje;
	
	
	public CyclicLeer(AgentePresidente agente, ObservableMensaje mensaje) {
		super(agente);
		sc = new Scanner(System.in);
		this.agente = agente;
		this.mensaje = mensaje;
	}

	@Override
	public void action() {
		String linea = sc.nextLine();
		//System.out.println("He recibido:" + mensaje);
		String[] lineaSplit = linea.split(";");
		if(lineaSplit.length == 3) {
			
			if(this.agente != null) {
				agente.buscarAgentesIdioma();
			}
			
			mensaje.setRemitenteInicial(this.agente.getLocalName());
			mensaje.setRemitente(this.agente.getLocalName());
			mensaje.setIdioma(lineaSplit[0]);
			mensaje.setDestino(lineaSplit[1]);
			mensaje.setContenido(lineaSplit[2]);
			
			//System.out.println("Idioma:" + lineaSplit[0] + ",destino:" + lineaSplit[1] + ",contenido:" + lineaSplit[2] + ".");
			
			
			
			//Idioma, presidente destino, mensaje
		}
		else {
			System.out.println("Formato de entrada inválido.");
		}
		
	}
}
