package corso.java.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * CREATE TABLE `camere` (
  `camera_id` int(11) NOT NULL,
  `tipologia` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
 */

@Data
@AllArgsConstructor
public class Camera {

	private int camera_id;
	
	private String tipologia;
	
}
