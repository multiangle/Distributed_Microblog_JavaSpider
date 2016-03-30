/**
 * Created by multiangle on 2016/3/23.
 */
import org.json.* ;


import java.util.*;
import java.util.regex.Matcher ;
import java.util.regex.Pattern ;
import java.util.Date ;
import java.text.SimpleDateFormat ;

public class test {
    public static void main(String[] args){

        long t = System.currentTimeMillis() ;
        long x = 1459309330 ;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss") ;
        String date = sdf.format(new Date(x*1000)) ;
        System.out.println(date);
        System.out.println(t/1000);

    }

}
