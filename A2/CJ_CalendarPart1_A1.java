/* main comments
C J Calendar Part 1 for CS 141 Assignment 1
2/7/23

This program creates 2 blank calendars. 1 is form user input and the other is optained using a Calendar object to get the current day and month.

The drawRow method is used along as a handler for other methods with make each part of the row. printCalendarBase() prints the top and bottom of each
row (the "="). printCalendarBoxLine() and printCalendarDateLine() are similar, the former prints the "|" or the edge of each box, while the latter also
does that along with printing the day number in each box.

the drawMonth(int month, int calendarScaling_width, int calendarScaling_height) method also has a few more parameters. along with the month is has the x and
y scaling options which are passed on to the other methods.

There is also the method drawCalendarBacking(int month, int calWidth, int backingHeight), which draws a simple backing / top for the calendar
along with adding the month number above the calendar.
As for the ascii art, there isnt much but I wanted to implement some type of for loop pattern so I created a simple alternating pattern.

For extra credit it only prints the amout of days in each month (excluding 29 days for febuary on a leap year). This is done by using an array to hold the 
amout of days in each month and putting the user input into that array to get the amout of days. Most of the methods take many more parameters as a result.

*/ // imports
import java.util.*;

// main file
public class CJ_CalendarPart1_A1 {
   public static void main(String[] args) { // start of main



      Scanner input = new Scanner(System.in);
      Calendar calen = Calendar.getInstance();
      
      int xAxisScale = 10;
      int yAxisScale = 2;
      int yAxisBackingScale = 4;
    
      System.out.print("enter a date: ");
      String date = input.next();
      System.out.println("");
      
      int month = monthFromDate(date);
      int day = dayFromDate(date);
      
      drawCalendarBacking(month, xAxisScale, yAxisBackingScale);
      drawMonth(month,xAxisScale,yAxisScale); // int are width and height of squares
      // display desired date
      displayDate(month, day);
      
      int currentMonth = 1+(calen.get(Calendar.MONTH));      
      int currentDay = (calen.get(Calendar.DATE));
      
      System.out.println("\nCurrent Month\n");
      
      drawCalendarBacking(currentMonth, xAxisScale, yAxisBackingScale);
      drawMonth(currentMonth,xAxisScale,yAxisScale);
      // display current date
      displayDate(currentMonth, currentDay);
   
   
   
   } // end of main
   public static void displayDate(int month, int day) {
      System.out.println("month: " + month);
      System.out.println("day: " + day);
   } // end of display date method
   // get date methods
   public static int dayFromDate(String arg_dateString) { // gets int after /
      //mm/dd format
      int dateInt = Integer.parseInt(arg_dateString.substring(arg_dateString.indexOf("/")+1,arg_dateString.length())); 
      return dateInt;
   }
   public static int monthFromDate(String arg_dateString) { // gets int prior to /
      //mm/dd format
      int dateInt = Integer.parseInt(arg_dateString.substring(0,arg_dateString.indexOf("/"))); 
      return dateInt;
   }
   // calendar construction methods
   public static void printCalendarBase(int arg_length) { // prints the top and bottom of the calendar
      for (int i = 0; i < arg_length; i++) {
         System.out.print("=");
      } // end of for loop
      System.out.print("\n");
   } // end of printCalendarBase
   public static void printCalendarDateLine(int arg_boxNumber, int arg_boxWidth, int arg_weekStartDate) {
      int currentDay = arg_weekStartDate; // redefines start day as current day
      System.out.print("|");
      for (int i = 0; i < arg_boxNumber; i++) { // for loop for number of boxes (in a line / week)
         if (currentDay < 10) { // checks the length of current day
            System.out.print(currentDay + " "); // currentDay = 1 digit so day + space
         } else {
            System.out.print(currentDay); // currentDay = 2 digit so no space needed
         }
         for (int j = 0; j < arg_boxWidth-2; j++) { // nested for loop for amount of space between boxes (with 2 less spaces)
            System.out.print(" ");      
         } // end of spacing for loop      
         System.out.print("|");
         currentDay += 1;
      } // end of row for loop
      System.out.print("\n");
   }
   public static void printCalendarBoxLine(int arg_boxNumber, int arg_boxWidth, int arg_height) {
      for (int k = 0; k < arg_height; k++) {
         System.out.print("|");
         for (int i = 0; i < arg_boxNumber; i++) { // for loop for number of boxes (in a line / week)
            for (int j = 0; j < arg_boxWidth; j++) { // nested for loop for amount of space between boxes
               System.out.print(" ");      
            } // end of spacing for loop
            System.out.print("|");
         } // end of row for loop
         System.out.print("\n");
      } // end of height
   }
   // calendar print handler
   
   public static void drawRow(int arg_startDay, int arg_boxNum, int calendarScaling_width, int calendarScaling_height) {
         printCalendarDateLine(arg_boxNum,calendarScaling_width,arg_startDay);
         printCalendarBoxLine(arg_boxNum,calendarScaling_width,calendarScaling_height);
         printCalendarBase(arg_boxNum*(calendarScaling_width+1)+1);
   }
   
   public static void drawMonth(int arg_month, int calendarScaling_width, int calendarScaling_height) {
   
      boolean leapYear = false; // change eventually for leapyear check / does nothing for now
      
      int daysPerMonth[] = {31,28,31,30,31,30,31,31,30,31,30,31};
      
      int fullWeeksInMonth = 4; // always at least 28 day / 4 weeks in month      
      int daysLeft = daysPerMonth[arg_month-1] - (fullWeeksInMonth*7); // gets leftover days
      
      // printing main calendar
      printCalendarBase(7*(calendarScaling_width+1)+1); // prints top line of calendar 
      for (int i = 0; i < fullWeeksInMonth; i++) { // prints the first 28 days of the calendar
         drawRow((i*7)+1, 7, calendarScaling_width, calendarScaling_height);
      } // end of for loop
      // prints days not part of a full week
      if (arg_month != 2 && leapYear == false) { // prints left over days and excludes febuary
         drawRow(29, daysLeft, calendarScaling_width, calendarScaling_height);
      } // end of printing 
   } // end of printMonth method
   // backing method
   public static void drawCalendarBacking(int arg_month, int arg_calWidth, int arg_backingHeight) {
      int calendarLength = ((arg_calWidth+1)*7)+1;
      for (int i = 0; i <= arg_backingHeight-1; i++) { // height for loop
         for (int j = 0; j < calendarLength/2; j++) { // width for loop
            if (i % 2 == 0) { // even check
               System.out.print("\\/");
            } else { // odd check
               System.out.print("/\\");
            }
         }
         System.out.print("\n");
      }
      // prints month number
      
      // makes spacing
      for (int i = 0; i < (calendarLength/2)-2; i++) {
         System.out.print(" ");
      }
      if (arg_month < 10) { // prints num
         System.out.println(" " + arg_month);
      } else {
         System.out.println(arg_month);
      }
   } // end of drawCalendarBacking method
} // end of file   