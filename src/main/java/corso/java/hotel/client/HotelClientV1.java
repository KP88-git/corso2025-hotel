package corso.java.hotel.client;

import corso.java.hotel.model.Ospite;
import corso.java.hotel.model.Prenotazione;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HotelClientV1 implements HotelClient {

	Connection conn = null;

	public HotelClientV1(String url, String user, String password) {
		try {
			System.out.printf("Connessione a [%s] con utente [%s] e password [%s].\n", url, user, password);
			this.conn = DriverManager.getConnection(url, user, password);
			System.out.printf("Connessione stabilita.\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int inserisciOspite(String nome, String cognome, String email, String telefono) {

		PreparedStatement pstmt = null;
		ResultSet rsGenId = null;
		
		System.out.printf("Inserisco ospite: %s %s.\n", nome, cognome);

		try {

			String sql = "INSERT INTO ospiti (nome, cognome, email, telefono) VALUES (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, nome);
			pstmt.setString(2, cognome);
			pstmt.setString(3, email);
			pstmt.setString(4, telefono);

			int righeInserite = pstmt.executeUpdate(); // Esegue l'INSERT
			rsGenId = pstmt.getGeneratedKeys();
			rsGenId.first();
			int genId = rsGenId.getInt(1);
			
			if (righeInserite == 1) {
				System.out.println("Ospite inserito con ID: " + genId);
			} else {
				System.out.println("ERRORE! Righe inserite: " + righeInserite);
				// TODO: throw eccezione
			}
			
			return genId;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rsGenId != null)
					rsGenId.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return 0;
	}

	@Override
	public void prenota(Prenotazione p) {

		PreparedStatement pstmt = null;
		ResultSet rsGenId = null;

		int numero_persone = p.getOspiti().size();

		if (numero_persone == 1) {
			System.out.printf("Prenoto camera %s per %s %s.\n", p.getCamera_id(),
					p.getOspiti().getFirst().getNome(), p.getOspiti().getFirst().getCognome());
		} else if (numero_persone > 1) {
			System.out.printf("Prenoto camera %s per %s ospiti.\n", p.getCamera_id(), numero_persone);
		} else {
			System.out.printf("ERRORE! numero ospiti non valido: %s.\n", numero_persone);
			// TODO: lancio una exception?
		}

		// spengo autocommit. committo manualmente quando tutti gli insert sono andati a
		// buon fine.
		try {
			conn.setAutoCommit(false);

			String sql = "INSERT INTO prenotazioni (checkin_data, checkout_data, numero_persone, camera_id) VALUES (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDate(1, p.getCheckin_data());
			pstmt.setDate(2, p.getCheckout_data());
			pstmt.setInt(3, numero_persone);
			pstmt.setInt(4, p.getCamera_id());

			int righeInserite = pstmt.executeUpdate(); // Esegue l'INSERT
			rsGenId = pstmt.getGeneratedKeys();
			rsGenId.first();
			int genId = rsGenId.getInt(1);
			if (righeInserite == 1) {
				System.out.printf("Prenotazione inserita con ID: %s\n", genId);
			} else {
				System.out.println("ERRORE! Righe inserite: " + righeInserite);
				// TODO: throw eccezione
			}

			for (Ospite o : p.getOspiti()) {
				System.out.printf("Aggiungo ospite id %s alla prenotazione id %s.\n", o.getOspite_id(),
						genId);
				String sql2 = "INSERT INTO prenotazione_ospiti (prenotazione_id, ospite_id) VALUES (?, ?)";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, genId);
				pstmt.setInt(2, o.getOspite_id());

				righeInserite = pstmt.executeUpdate(); // Esegue l'INSERT
				if (righeInserite == 1) {
					System.out.println("Riga inserita.");
				} else {
					System.out.println("ERRORE! Righe inserite: " + righeInserite);
					// TODO: throw eccezione
				}
			}

			// committo e riattivo l'autocommit
			conn.commit();
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERRORE! Provo rollback.");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("ERRORE! Rollback fallito.");
			}
		} finally {
			// Chiudere tutte le risorse nel blocco finally per garantirne la chiusura
			try {
				if (rsGenId != null)
					rsGenId.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

	}

	@Override
	public List<Prenotazione> listaPrenotazioni() {

		Statement stmt = null;
		ResultSet rs = null;
		List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
		System.out.println("Prendo lista completa di prenotazioni.");

		try {

			String sql = "SELECT * FROM prenotazioni";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			
			
			while (rs.next()) {
				prenotazioni.add(new Prenotazione(
						rs.getInt("prenotazione_id"),
						rs.getDate("checkin_data"),
						rs.getDate("checkout_data"),
						rs.getInt("numero_persone"),
						rs.getInt("camera_id")
						));
			}
			
			return prenotazioni;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// Chiudere tutte le risorse nel blocco finally per garantirne la chiusura
			try {
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return null;
	}

	public void closeConnection() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

}
