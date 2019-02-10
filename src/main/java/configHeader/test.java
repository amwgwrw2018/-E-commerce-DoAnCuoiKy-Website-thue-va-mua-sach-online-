package configHeader;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.apache.axis.encoding.Base64;

public class test {
public static void main(String[] args) throws UnsupportedEncodingException {
String s="123";
String ma=Base64.encode(s.getBytes());
System.out.println(ma);
System.out.println(new String(Base64.decode(ma),"UTF-8"));
}
}
