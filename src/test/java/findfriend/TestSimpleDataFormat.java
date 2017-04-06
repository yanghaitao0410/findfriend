package findfriend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestSimpleDataFormat {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		Date date = format.parse("2017-04-05 03-13-53");
		System.out.println(date.getTime());
	}
	
}
