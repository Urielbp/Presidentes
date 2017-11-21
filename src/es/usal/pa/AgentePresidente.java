package es.usal.pa;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AgentePresidente extends Agent implements Observer {
	private static final long serialVersionUID = 1L;
	private String[] idiomas;
	private String[][] palabras = new String[21][];
	private ArrayList<DFAgentDescription[]> listaAgentesIdiomas;
	private ObservableMensaje mensaje;

	public AgentePresidente() {
		super();
		mensaje = new ObservableMensaje();
		mensaje.addObserver(this);
	}

	public String[] getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(String[] idiomas) {
		this.idiomas = idiomas;
	}


	public void setup() {
		listaAgentesIdiomas = new ArrayList<>();
		DFAgentDescription dfd=new DFAgentDescription();
		dfd.setName(getAID());

		idiomas = Utils.buscarEnFichero(this.getLocalName(),"presidentes.txt");
		if(idiomas.length == 0) {
			System.out.println("No pudimos identificar los idiomas para el agente " + this.getLocalName());
		}
		
		for(int i = 0; i < idiomas.length; i++) {
			String idioma = idiomas[i];	
			palabras[i] = Utils.buscarEnFichero(idioma, "palabras.txt");
		}
		
		for (String idioma : idiomas) {
			ServiceDescription sd=new ServiceDescription();
			sd.setName("Traducción");
			sd.setType(idioma);
			sd.addOntologies("ontologia");
			sd.addLanguages(new SLCodec().getName());
			dfd.addServices(sd);
		}
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		
		ParallelMensaje pm = new ParallelMensaje(this, mensaje);
		addBehaviour(pm);

		System.out.println(this.getLocalName() + "=>");
		
		//this.buscarAgentes();
	}
	
	public void buscarAgentesIdioma() {
		if(this.listaAgentesIdiomas.isEmpty()) {
			for(int i = 0; i < this.idiomas.length; i++) {
				String idioma = this.idiomas[i];
				this.listaAgentesIdiomas.add(i, Utils.buscarAgentes(this, idioma));
			}
			//TODO
			//Depurando
//			for(int i = 0; i < this.listaAgentesIdiomas.size(); i++) {
//				DFAgentDescription[] listaDescripcionAgentes = this.listaAgentesIdiomas.get(i);
//				for(int j = 0; j < listaDescripcionAgentes.length; j++) {
//					DFAgentDescription descripcionAgente = listaDescripcionAgentes[j];
//					System.out.print(descripcionAgente.getName().getLocalName()); 
//				}
//			}
		}	
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(o instanceof ObservableMensaje) {
			//System.out.println("Agente: "+((ObservableMensaje)arg).getContenido());
			
			//ObservableMensaje mensaje = (ObservableMensaje) arg;
			//Si el agente destino habla el idioma y yo también
			//Busco el agente destino en la lista correspondiente de agentes que hablan ese idioma
			if( entregaDirecta((ObservableMensaje)arg) == false){
				//Si no se entrega directamente al destino, buscamos a alguien para traducir
				
				
				
			}
		}
	}

	private boolean entregaDirecta(ObservableMensaje mensaje) {
		for(int i = 0; i < idiomas.length ;i++){
			if(idiomas[i].equalsIgnoreCase(mensaje.idioma) == true) {
				DFAgentDescription[] listaDfd = listaAgentesIdiomas.get(i);
				for(DFAgentDescription descripcion : listaDfd) {
					if(descripcion.getName().getLocalName().equalsIgnoreCase(mensaje.destino) == true) {
						//Le entregamos directamente el mensaje al agente destino
						Utils.enviarMensaje(this, descripcion, (Object) mensaje, "Directo");
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean buscarYEnviar(ObservableMensaje mensaje) {
		for(int i = 0; i < idiomas.length; i++) {
			
		}
		
		
		 return false;
	}
}
