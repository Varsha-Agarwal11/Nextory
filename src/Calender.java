import java.util.Calendar;

public class Calender 
{
	public static void main(String args[])
	{
		Calendar c=Calendar.getInstance();
		System.out.println(c);
		String num="716.94";
		String v=num.split("\\.")[0];
		System.out.println(v);
		
		
	}
}
