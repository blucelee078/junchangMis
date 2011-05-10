/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package publicmethod;

/**
 *
 * @author developers
 */
import java.util.Calendar;

/**
 *
 * @author Administrator
 */
public class DateCompute {

    public static String getCurrentDate() {
        Calendar rightNow = Calendar.getInstance();
        int day = rightNow.get(Calendar.DAY_OF_MONTH);
        int month = rightNow.get(Calendar.MONTH);
        int year = rightNow.get(Calendar.YEAR);
        //String date = year + "年" + String.valueOf(month + 1) + "月" + day + "日";
        String date = year + "." + String.valueOf(month + 1) + "." + day;
        return date;
    }

    public static String getCurrentDateCHN() {
        Calendar rightNow = Calendar.getInstance();
        int day = rightNow.get(Calendar.DAY_OF_MONTH);
        int month = rightNow.get(Calendar.MONTH);
        int year = rightNow.get(Calendar.YEAR);
        String date = year + "年" + String.valueOf(month + 1) + "月" + day + "日";
        //String date = year + "." + String.valueOf(month + 1) + "." + day;
        return date;
    }

    public static Calendar getCurrentDateObject() {
        return Calendar.getInstance();
    }

    public static String getDay() {
        Calendar rightNow = Calendar.getInstance();
        int day = rightNow.get(Calendar.DAY_OF_MONTH);
        //int month = rightNow.get(Calendar.MONTH);
        //int year = rightNow.get(Calendar.YEAR);
        return Integer.toString(day);
    }

    public static String getMonth() {
        Calendar rightNow = Calendar.getInstance();
        //int day = rightNow.get(Calendar.DAY_OF_MONTH);
        int month = rightNow.get(Calendar.MONTH);
        //int year = rightNow.get(Calendar.YEAR);
        return Integer.toString(month + 1);
    }

    public static void main(String[] args) {
        System.out.println(DateCompute.getCurrentDate());
        System.out.println(DateCompute.getMonth() + " " + DateCompute.getDay());
    }
}
