package es.usal.pa;

import java.util.Observable;

public class ObservableMensaje extends Observable implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	protected String contenido, idioma, destino, remitenteInicial, remitente;
	
	public ObservableMensaje() {
		super();
	}
	
	public String getRemitenteInicial() {
		return remitenteInicial;
	}
	
	public void setRemitenteInicial(String remitenteInicial) {
		this.remitenteInicial = remitenteInicial;
	}
	
	public String getRemitente() {
		return remitente;
	}
	
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String mensaje) {
		this.contenido = mensaje;
		setChanged();
		notifyObservers(this);
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}
}
