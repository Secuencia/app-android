package moviles.isaacs.com.isaacs.services;

/**
 * Created by nicolaschaves on 9/10/16.
 */

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class test {


    public static void main(String [] args)
    {
        Calendar c = Calendar.getInstance();
        Date currentTime = c.getTime();

        System.out.println("The current time in Date format is: ");
        System.out.println(currentTime);

        long numericDate = currentTime.getTime();

        System.out.println("The current time as int is: ");
        System.out.println(numericDate);

        System.out.println("Build the date from the int: ");
        System.out.println(new Date(numericDate));
    }

}
