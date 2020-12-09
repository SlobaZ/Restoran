package restoran.pomocno;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface ProveriDatumPocetakIKraj {
	
	public static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
	
	// Provera opsega Ulaz/Izlaz datuma
	
	public static boolean odgovaraOpesegu(String ulaznaGranica, String izlaznaGranica) {
			
		boolean pripada = true;
		Date ulazDatum = null;
		Date izlazDatum = null;
		Date danas = null;
		String danasnjiDatum = null;
	
		Date datumDanas = new Date();
		danasnjiDatum = DATE_TIME_FORMAT.format(datumDanas);
			
		try {
			ulazDatum  = DATE_TIME_FORMAT.parse(ulaznaGranica);
			izlazDatum = DATE_TIME_FORMAT.parse(izlaznaGranica);
			danas = DATE_TIME_FORMAT.parse(danasnjiDatum);
				
			if(ulazDatum.before(izlazDatum) && !ulazDatum.before(danas)){
		        pripada = true;
		    }
			else if(ulazDatum.before(danas)){
				pripada=false;
			}
			else {
				pripada=false;
			}	
			} 
			catch (ParseException e) {	
			}
		
			return pripada;
		}
		

}
