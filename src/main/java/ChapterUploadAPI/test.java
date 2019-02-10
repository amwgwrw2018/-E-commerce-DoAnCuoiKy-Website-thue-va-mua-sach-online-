package ChapterUploadAPI;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class test {
public static void main(String[] args) throws ParseException {
//	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
//	  Calendar c = Calendar.getInstance();
//	  Calendar c2= Calendar.getInstance();
//      c.setTime(sdf.parse("2018-11-29"));
//      c2.setTime(c2.getTime());
//      c.add(Calendar.DAY_OF_YEAR, 60);
//      System.out.println(c.getTime());
//      System.out.println(c2.getTime());
//  
//
//System.out.println( c.get(Calendar.DATE)- c2.get(Calendar.DATE));
//System.out.println(c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH));
//	LocalDate lc=LocalDate.of(2018, 11, 30);
//
//	System.out.println(lc.getMonth());
//	LocalDate lc2=LocalDate.now();
//	System.out.println(lc.isAfter(lc2));
//	System.out.println(lc2.getYear()+"-"+lc2.getMonthValue()+"-"+lc2.getDayOfMonth());
	Random rd=new Random();
	int numberRd=0;
	String newPassword="";
	char charrd;
	for (int i = 0; i < 8; i++) {
	
		
		if(i%2==0) {
			numberRd=rd.nextInt(10)+48;
			newPassword+=(char)numberRd;
		}else {
			charrd=(char) (rd.nextInt(25)+65);
			newPassword+=charrd;
		}
	}
	System.out.println(newPassword);
}
}
