import java.util.Random;

import corso.java.hotel.client.HotelClientV1;

public class TestHotelClientV1_InserisciOspite {

	public static void main(String[] args) {

		Random rnd = new Random();
		
        String url = "jdbc:mysql://localhost:3306/hotel";
        String user = "javatest";
        String password = "testpass";
		
		HotelClientV1 hc = new HotelClientV1(url, user, password);
		
		int rndInt = rnd.nextInt(10000);
		hc.inserisciOspite(
				"Fabio"+rndInt,
				"Fabioli"+rndInt,
				"fabio.babioli"+rndInt+"@mail.it",
				"329000"+rndInt
				);
	}

}
