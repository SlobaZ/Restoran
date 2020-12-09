package restoran.pomocno;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public interface DatumIVreme {

	public static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
	public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy.");

	// Upisi  String  sadasnji  Datum i Vreme
	public static String UpisiSadasnjiDatumIVremeString() {
		Date date = new Date();
		Timestamp datumIvremeSada = new Timestamp(date.getTime());
		String datumIvremeSadaString = DATE_TIME_FORMAT.format(datumIvremeSada);
		return datumIvremeSadaString;
	}

	// Upisi  String  sadasnji  Datum 
	public static String UpisiSadasnjiDatumString() {
		Date date = Calendar.getInstance().getTime();
		String datum = DATE_FORMAT.format(date);
		return datum;
	}

	
}
