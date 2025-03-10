import corso.java.hotel.client.HotelClientV1;

public class TestHotelClientV1_ListaPrenotazioni {

	public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/hotel";
        String user = "javatest";
        String password = "testpass";
		
		HotelClientV1 hc = new HotelClientV1(url, user, password);
		

		hc.listaPrenotazioni().forEach( p -> System.out.println(p.toString()));

	}

}
