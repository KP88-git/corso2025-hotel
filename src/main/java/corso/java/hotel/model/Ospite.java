package corso.java.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * CREATE TABLE `ospiti` (
  `ospite_id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `cognome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telefono` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
 */

@Data
@AllArgsConstructor
public class Ospite {

	private int ospite_id;
	
	private String nome;
	
	private String cognome;
	
	private String email;
	
	private String telefono;
	
}
