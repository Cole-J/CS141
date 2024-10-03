/*
Cole Johnson
3/7/23
CS 141, Calendar Part 2 for Assignment 2


This program creates individual calendars for each month after getting a date from the user.

the user in able to input a date a few different ways. they can
1. manually go to a date / month
2. go to todays date / month
3. go to the next month or previous month, based off of the 2 inputs above

the main mehtod used for drawing each month is the print month method. this method has been changed
since the last assignment so that it can have an offset / days or boxes that it creates but does not
give a date.

there are a number of new methods which handle small tasks like getting information or checking inputs,
since we are not suppose to use things like parallel arrays (which could make this less complicated).

every method:

validDate, checks in a date is valid
printMonth, prints 1 calendar month
getDaysInMonth, gets the amount of days in a given month
getMonthOffset, gets the offset for a given month
printRow, prints 1 row of the calendar
printCalendarBoxLine, prints part of a row ('|   |')
printCalendarDateLine, prints part of a row ('|23 |')
displayDate, prints the date below the calendar
dayFromDate, gets the day from a given date (mm/dd format)
monthFromDate, gets the month form a given date (mm/dd format)
printChar, prints a String made up of 1 char and a given length
printCalendarTopRow, prints the very top row of the calendar
commandInstructions, prints the instructions
getUserInput, gets the user input at the start of each loop (e, t, n, p, q)

printBacking and the ones below it are for the backing of the calendar / ascii art
some of them are still a work in progress

*/ // imports
import java.util.*;
class CJ_calendar2 {
   public static void main(String[] args) {
   
      Scanner input = new Scanner(System.in); // creates scanner and calendar objects
      Calendar calen = Calendar.getInstance();   
      int month = 0, day = 0; // defines month and day variables
      int xScale = 12, yScale = 4; // x y scale for th calendar
   
      boolean run = true;
      while(run) {
      
         char userInput = getUserInput(); // gets user input (char)
         switch(userInput) {
            case 'e': // display a date from user input
               System.out.println("enter a date (mm/dd): ");
               String date = input.next(); // gets user date
               System.out.println("");
               month = monthFromDate(date); // gets month
               day = dayFromDate(date); // gets day
               if (validDate(month,day)) { // checks if date is valid
                  printMonth(month,xScale,yScale); // prints calendar month
                  displayDate(month,day); // prints small display below calendar
                  System.out.println("");
               }
               break;
            case 't': // display a date from calendar object
               month = 1+(calen.get(Calendar.MONTH)); // gets month and day from cal object
               day = (calen.get(Calendar.DATE));
               printMonth(month,xScale,yScale); // same as above but without checker as the date is valid
               displayDate(month,day);
               System.out.println("");
               break;
            case 'n': // display next month
               if (month == 0) { // date has not been entered prev
                  System.out.println("please enter a date first (e)");
               } else {
                  // date has been entered prev
                  if (month == 12) {
                     month = 1; // sets month to 1 if month is 12 / end of year
                  } else {
                     month++; // else add 1 to month
                  } // end of nested if else
                  printMonth(month,xScale,yScale);
                  displayDate(month,day);
                  System.out.println("");                
               } // end of if else logic
               break;
            case 'p': // display prev month
               if (month == 0) { // date has not been entered prev
               System.out.println("please enter a date first (e)");
               } else {
                  if (month == 1) {
                     month = 12; // sets month to 12 if month is 1 / start of year
                  } else {
                     month--; // else minus 1 from month
                  } // end of nested if else
                  printMonth(month,xScale,yScale);
                  displayDate(month,day);
                  System.out.println("");  
               } // end of if else logic
               break;
            case 'q': run = false; break; // exits the loop / end the run
         } // end of switch 
      } // end of mian while loop
      System.out.println("user exited the program");
   } // end of main method
   
   public static boolean validDate(int month, int day) { // checks if an inputed date is valid
      if ((month > 0 & month < 13) & (day > 0 & day < getDaysInMonth(month)+1)) {
         return true; // returns true if month is 0 to 12 and day is 0 to amount of days in month
      } else {
         System.out.println("invalid date");
         return false;
      } // end of if logic
   } // end of validDate method
   
   public static void printMonth(int arg_month, int calendarScaling_width, int calendarScaling_height) {
      printBacking(arg_month); // prints backing
      int daysPerMonth = getDaysInMonth(arg_month); // end of loop
      int MonthOffset = getMonthOffset(arg_month); // start of loop
      int i = MonthOffset; // sets the offset to the count variable
      printChar(7*(calendarScaling_width+3)+1,'=');
      System.out.println("");
      printCalendarTopRow(calendarScaling_width);
      printChar(7*(calendarScaling_width+3)+1,'=');
      System.out.println("");
      
      while(i <= daysPerMonth-7) { // checks the count variable 1 week ahead
         printRow(i,7,calendarScaling_width,calendarScaling_height); // prints row
         i += 7; // goes to the next week
      } // end of while loop
      int daysLeft = daysPerMonth-i+1; // finds the amount of days in the last week
      printRow(i,daysLeft,calendarScaling_width,calendarScaling_height); // prints the last week
   } // end of printMonth methods
      
   public static int getDaysInMonth(int month) { // returns the amount of days in a given month
      int returnValue = 0; // defines return variable
      switch(month) { // combined case block to reduce lines
         case 1: returnValue = 31; break; // jan - dec
         case 2: returnValue = 28; break;         
         case 3: returnValue = 31; break;
         case 4: returnValue = 30; break;
         case 5: returnValue = 31; break;
         case 6: returnValue = 30; break;
         case 7: returnValue = 31; break;
         case 8: returnValue = 31; break;
         case 9: returnValue = 30; break;
         case 10: returnValue = 31; break;
         case 11: returnValue = 30; break;
         case 12: returnValue = 31; break;
      } // end of switch
      return returnValue; // return days in month
   } // end of getDaysInMonth method

   public static int getMonthOffset(int month) { // returns the amount of days in a given month
      int returnValue = 0; // defines return variable
      switch(month) { // combined case block to reduce lines
         case 1: returnValue = 1; break; // jan - dec
         case 2: returnValue = -2; break;         
         case 3: returnValue = -2; break;
         case 4: returnValue = -5; break;
         case 5: returnValue = 0; break;
         case 6: returnValue = -3; break;
         case 7: returnValue = -5; break;
         case 8: returnValue = -1; break;
         case 9: returnValue = -4; break;
         case 10: returnValue = 1; break;
         case 11: returnValue = -2; break;
         case 12: returnValue = -4; break;
      } // end of switch
      return returnValue; // return days in month
   } // end of getDaysInMonth method
   
   public static void printRow(int arg_startDay, int arg_boxAmount, int calendarScaling_width, int calendarScaling_height) {
      // startDay = the day the week starts on (if day < 1 then it will not print), box amount = number of days in the week, generally 7
      // scaling is the addition of space in the x (width) or y (height)
      printCalendarDateLine(arg_boxAmount,calendarScaling_width,arg_startDay);
      printCalendarBoxLine(arg_boxAmount,calendarScaling_width,calendarScaling_height);
      printChar(arg_boxAmount*(calendarScaling_width+3)+1,'=');
      System.out.println("");
   } // end of printRow method

   public static void printCalendarBoxLine(int arg_boxAmount, int arg_boxWidth, int arg_height) {
      for (int k = 0; k < arg_height; k++) {
         System.out.print("|");
         for (int i = 0; i < arg_boxAmount; i++) { // for loop for number of boxes (in a line / week)
            for (int j = 0; j < arg_boxWidth+2; j++) { // nested for loop for amount of space between boxes / 2 spaces by defualt
               System.out.print(" ");      
            } // end of spacing for loop
            System.out.print("|");
         } // end of row for loop
         System.out.print("\n");
      } // end of height
   } // end of printCalendarBoxLine method
   
   public static void printCalendarDateLine(int arg_boxAmount, int arg_boxWidth, int arg_weekStartDate) { // by defualt boxwidth = 2
      int currentDay = arg_weekStartDate; // redefines start day as current day
      System.out.print("|");
      for (int i = 0; i < arg_boxAmount; i++) { // for loop for number of boxes (in a line / week)
         if (currentDay < 1) {
            System.out.print("  ");
         } else if (currentDay < 10) { // checks the length of current day
            System.out.print(currentDay + " "); // currentDay = 1 digit so day + space
         } else {
            System.out.print(currentDay); // currentDay = 2 digit so no space needed
         }
         for (int j = 0; j < arg_boxWidth; j++) { // nested for loop for amount of space between boxes (with 2 less spaces)
            System.out.print(" ");      
         } // end of spacing for loop      
         System.out.print("|");
         currentDay += 1;
      } // end of row for loop
      System.out.print("\n");
   } // end of printCalendarDateLine method
   
   public static void displayDate(int month, int day) {
      System.out.println("month: " + month);
      System.out.println("day: " + day);
   } // end of display date method
   public static int dayFromDate(String arg_dateString) { // gets int after '/'
      //mm/dd format
      int dateInt = Integer.parseInt(arg_dateString.substring(arg_dateString.indexOf("/")+1,arg_dateString.length())); 
      return dateInt;
   } // end of dayFromDate method
   public static int monthFromDate(String arg_dateString) { // gets int prior to '/'
      //mm/dd format
      int dateInt = Integer.parseInt(arg_dateString.substring(0,arg_dateString.indexOf("/"))); 
      return dateInt;
   } // end of monthFromDate method
   public static void printChar(int arg_width, char arg_char) {
      int i = 0;
      while(i<arg_width) {
         System.out.print(arg_char);
         i++;
      } // end of while
   } // end of printSpace method
   public static void printCalendarTopRow(int arg_width) {
      // prints the week days names
      System.out.print("|su");
      printChar(arg_width,' ');
      System.out.print("|mo");
      printChar(arg_width,' ');
      System.out.print("|tu");
      printChar(arg_width,' ');
      System.out.print("|we");
      printChar(arg_width,' ');
      System.out.print("|th");
      printChar(arg_width,' ');
      System.out.print("|fr");
      printChar(arg_width,' ');
      System.out.print("|sa");
      printChar(arg_width,' ');
      System.out.println("|");
   } // end of printCalendarTopRow method
   public static void commandInstructions() { // prints the instructions when called
      System.out.println("Please type a command");
      System.out.println("\"e\" to enter a date and display the corresponding calendar");
      System.out.println("\"t\" to get todays date and display todays calendar");
      System.out.println("\"n\" to display the next month");
      System.out.println("\"p\" to display the previous month");
      System.out.println("\"q\" to quit the program");
   } // end of commandInstructions method
   public static char getUserInput() { //
      Scanner input = new Scanner(System.in); // creates input object to get user input
      String userInput; // defines user input
      char userInput_c; // defines the char that will be returned
      while(true) {
         commandInstructions(); // prints instructions
         userInput = input.nextLine(); // gets user input
         userInput_c = userInput.charAt(0); // sets the return variable to the first index of the string
         // the conversion stops an error if the user inputs more than 1 char
         if (userInput_c =='e'||userInput_c=='t'||userInput_c == 'n'||userInput_c == 'p'||userInput_c == 'q') {
            break; // if the user enters the correct input, the loop stops
         } else { // if not then the loop runs again
            System.out.println("Please enter a valid command. <" + userInput_c + "> is not a valid command.");
         } // end of if logic
      } // end of main while loop
      return userInput_c; // returnds the user input
   } // end of getUserInput method
   
   
   public static void printBacking(int month) {
      switch (month) {
         case 1: // winter
            backing_winter(month); break;
         case 2: // winter
            backing_winter(month); break;
         case 3: // winter
            backing_winter(month); break;
         case 4: // spring
            backing_spring(month); break;
         case 5: // spring
            backing_spring(month); break;
         case 6: // summer
            backing_summer(month); break;
         case 7: // summer
            backing_summer(month); break;
         case 8: // summer
            backing_summer(month); break;
         case 9: // fall
            backing_fall(month); break;
         case 10: // fall
            backing_fall(month); break;
         case 11: // fall
            backing_fall(month); break;
         case 12: // winter
            backing_winter(month); break;
            } // end of switch case
   } // end of printBacking method
   
   // below are ascii methods
   public static void backing_summer(int month) {
      System.out.println("  ,--./,-.  ");
      System.out.println(" /   .-~/   ");
      System.out.println("|   {       ");
      System.out.println(" \\   `--, ");
      System.out.println("  `._,._,'  " + month);
   }
   public static void backing_fall(int month) { // not done yet
      System.out.println(month);
   }
   public static void backing_winter(int month) {
      System.out.println("");
      System.out.println("   _--_");
      System.out.println(" _ (\">)");
      System.out.println("  \\/. \\-,");
      System.out.println("__( :  )__  " + month);
   }
   public static void backing_spring(int month) { // not done yet
      System.out.println(month);
   }
   
   
} // end of class