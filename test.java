// Online Java Compiler
// Use this editor to write, compile and run your Java code online
import java.util.*;
import java.io.*;
class test {
    public static void main(String[] args) {
    Scanner input = new Scanner(System.in); // creates scanner and calendar objects






System.out.println("enter an event (format <mm/dd eventName>):");
String date = input.nextLine(); // gets user date
System.out.println("");
System.out.println(date);


//String date = "1/2 t";

System.out.println(eventFromDate(date));
System.out.println(monthFromDate(date));
System.out.println(dayFromDate(date));







} // end of main


   public static String eventFromDate(String arg_dateString) { // gets string after ' '
      String event;
      if (arg_dateString.contains(" ")) {
         event = arg_dateString.substring(arg_dateString.indexOf(" ")+1,arg_dateString.length());
      } else {
         event = "";
      }
      return event;
   }
   public static int dayFromDate(String arg_dateString) { // gets int after '/' and before ' '
      //mm/dd format
      int dateInt;
      if (arg_dateString.contains(" ")) {
         dateInt = Integer.parseInt(arg_dateString.substring(arg_dateString.indexOf("/")+1,arg_dateString.indexOf(" "))); 
      } else {
         dateInt = Integer.parseInt(arg_dateString.substring(arg_dateString.indexOf("/")+1,arg_dateString.length()));
      }
      return dateInt;
   } // end of dayFromDate method
   public static int monthFromDate(String arg_dateString) { // gets int prior to '/'
      //mm/dd format
      int dateInt = Integer.parseInt(arg_dateString.substring(0,arg_dateString.indexOf("/"))); 
      return dateInt;
   } // end of monthFromDate method

}