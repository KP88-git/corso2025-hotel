import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import corso.java.hotel.client.HotelClient;
import corso.java.hotel.client.HotelClientV1;
import corso.java.hotel.model.Ospite;

public class TestHotelClientV1_Prenota {

	static Random rnd = new Random();
	
	public static void main(String[] args) {
		
        String url = "jdbc:mysql://localhost:3306/hotel";
        String user = "javatest";
        String password = "testpass";
		
		HotelClientV1 hc = new HotelClientV1(url, user, password);
		
		Date checkin_data = getRandomDate();
		Date checkout_data = addRandomDays(checkin_data);
		int camera_id = getRandomCamera();
		List<Ospite> ospiti = getRandomOspiti(hc);
		
		hc.prenota(checkin_data, checkout_data, camera_id, ospiti);

	}
	
	private static List<Ospite> getRandomOspiti(HotelClient hc) {
		List<Ospite> ospiti = hc.listaOspiti();
		
		int maxOspiti = ospiti.size() > 3 ? 3 : ospiti.size();
		maxOspiti = rnd.nextInt(1, maxOspiti);
		
		while(ospiti.size() > maxOspiti) {
			ospiti.remove(rnd.nextInt(0, ospiti.size()));
		}
		
		return ospiti;
	}

	private static int getRandomCamera() {
		return rnd.nextInt(1,  10);
	}

	static Date getRandomDate() {
		
		long minDay = LocalDate.of(2025, 1, 1).toEpochDay();
		long maxDay = LocalDate.of(2025, 12, 31).toEpochDay();
		long randomDay = rnd.nextLong(minDay, maxDay);
		
		LocalDate randomLocalDate = LocalDate.ofEpochDay(randomDay);
		Date randomDate = Date.valueOf(randomLocalDate);
		
		System.out.println("Random Date generated: " + randomDate.toString());
		return randomDate;
		
	}
	
	static Date addRandomDays(Date dataInput) {
		LocalDate localDate = dataInput.toLocalDate();
		localDate = localDate.plusDays(rnd.nextInt(7));
		Date futureRandomDate = Date.valueOf(localDate);
		System.out.println("Future Date generated: " + futureRandomDate.toString());
		return futureRandomDate;
	}

}
