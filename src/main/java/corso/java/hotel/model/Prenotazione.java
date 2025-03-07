package corso.java.hotel.model;

import java.sql.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/*CREATE TABLE `prenotazioni` (
  `prenotazione_id` int(11) NOT NULL,
  `checkin_data` date NOT NULL,
  `checkout_data` date DEFAULT NULL,
  `numero_persone` int(11) NOT NULL,
  `camera_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
*/

/*ALTER TABLE `prenotazioni`
  ADD CONSTRAINT `prenotazioni_ibfk_1` FOREIGN KEY (`camera_id`) REFERENCES `camere` (`camera_id`);
COMMIT;
*/

/*ALTER TABLE `prenotazione_ospiti`
  ADD CONSTRAINT `prenotazione_ospiti_ibfk_1` FOREIGN KEY (`ospite_id`) REFERENCES `ospiti` (`ospite_id`),
  ADD CONSTRAINT `prenotazione_ospiti_ibfk_2` FOREIGN KEY (`prenotazione_id`) REFERENCES `prenotazioni` (`prenotazione_id`);
*/

@Data
@NoArgsConstructor
public class Prenotazione {

	private int prenotazione_id;

	private Date checkin_data;
	
	private Date checkout_data;
	
	private int numero_persone;
	
	private int camera_id;
	
	private List<Ospite> ospiti;
	
	public Prenotazione(int prenotazione_id, Date checkin_data, Date checkout_data, int numero_persone, int camera_id) {
		this.prenotazione_id = prenotazione_id;
		this.checkin_data = checkin_data;
		this.checkout_data = checkout_data;
		this.numero_persone = numero_persone;
		this.camera_id = camera_id;
	}

}
