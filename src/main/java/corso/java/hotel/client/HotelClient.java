package corso.java.hotel.client;

import corso.java.hotel.model.Ospite;
import corso.java.hotel.model.Prenotazione;

import java.sql.Date;
import java.util.List;

public interface HotelClient {

	/*
	 * Inserire nuovi ospiti nella tabella utenti. Effettuare una prenotazione,
	 * specificando l’ospite e la camera. Visualizzare tutte le prenotazioni, con
	 * dettagli su ospiti e camere.
	 * 
	 * Passaggi richiesti
	 * 
	 * Implementare un metodo Java per connettersi al database tramite JDBC. Creare
	 * un’interfaccia CLI che permetta all’utente di scegliere l’operazione da
	 * eseguire. Scrivere query SQL per l’inserimento e il recupero dei dati.
	 */
	
	public int inserisciOspite(String nome, String cognome, String email, String telefono);

	//public void prenota(Prenotazione prenotazione);
	public void prenota(Date checkin_data, Date checkout_data, int camera_id, List<Ospite> ospiti);
	
	public List<Prenotazione> listaPrenotazioni();
	
	public List<Ospite> listaOspiti();
	

}
