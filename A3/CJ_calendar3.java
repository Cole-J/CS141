/*
Cole Johnson
3/24/23
CS 141, Calendar Part 3 for Assignment 3

This program creates individual calendars / planners for each month after getting a date from the user. It can print the calendar
to the console or an output file. The calendar can also show an event for each day which can be added with an
input file or by user input while the program is running.

the main mehtod used for drawing each month is the print month method. this method has been changed
since the last assignment can print to different locations, for example the console or the output file.

every method:

main
printMonth, prints the calendar from a given month
printRow, prints 1 row of the calendar
printCalendarBoxLine, prints part of a row ('|   |')
printCalendarEventLine, prints part of the row ('|event |')
printCalendarDateLine, prints part of a row ('|23 |')
printChar, prints a String made up of 1 char and a given length
printCalendarTopRow, prints the very top row of the calendar
displayDate, prints the date below the calendar
validDate, checks if a given date is valid
guiGetUserInput, gets the user input at the start of each loop (e, t, ev, fp,n, p, q)
eventFromDate, gets the event from a given date (mm/dd/ event format)
dayFromDate, gets the day from a given date (mm/dd format)
monthFromDate, gets the month form a given date (mm/dd format)
parseInputFileToArr, creates the event array with the input file (calendarEvents.txt)
getFileName, gets the output file from the user
doesFileExist, checks if a given file exists
createEventArr, creates a 2d array with month axis and days (days per month) axis
commandInstructions, prints the instructions
printBacking, prints the ascii backing

ascii backing was made by making each letter and then using them to make the words

*/ // imports
import java.util.*; // for scanner and printstream
import java.io.*; // for calendar object

class CJ_calendar3 {
   public static void main(String[] args) throws FileNotFoundException {
   
      // import handling
      Scanner input = new Scanner(System.in); // creates scanner and calendar objects
      Calendar calen = Calendar.getInstance();  
      
      // variables 
      int month = 0, day = 0; // defines month and day variables
      int xScale = 16, yScale = 4; // x y scale for th calendar
      
      // arrays
      int daysPerMonth[] = {31,28,31,30,31,30,31,31,30,31,30,31}; // days per month
      int monthOffset[] = {1,-2,-2,-5,0,-3,-5,-1,-4,1,-2,4}; // offset per month
      String[][] eventArr = createEventArr(); // creates a 3d array to save events (3d acting like 2d)
      
      if (!(doesFileExist("calendarEvents.txt"))) {
         System.out.println("<calendarEvents.txt> file not found, no events auto added\n");
      } else {
         eventArr = parseInputFileToArr(eventArr, "calendarEvents.txt");
      } // if statement to check if calendarEvents.txt evists, if it does it adds the events to the event array

      while(true) { // main while loop
      
         String userInput = guiGetUserInput();
         // checks each input then calls the needed methods
         if (userInput.equals("e")) { // enter a date
            System.out.println("enter a date (mm/dd): ");
            String date = input.nextLine(); // gets user date
            System.out.println("");
            if (date.contains("/")) { // first check to ensure entered date is a valid date
               month = monthFromDate(date); // gets month
               day = dayFromDate(date); // gets day
               if (validDate(month,day,daysPerMonth)) { // checks if date is valid (second check)
                  printMonth(month,xScale,yScale, System.out,daysPerMonth,monthOffset, eventArr); // prints calendar month
                  displayDate(month,day,System.out); // prints small display below calendar
                  System.out.println("");  
               } // end of date check
            } else {
               System.out.println("invalid date entered");
            } // end of print user entered date
         } else if (userInput.equals("t")) { // use todays date
            month = 1+(calen.get(Calendar.MONTH)); // gets month and day from cal object
            day = (calen.get(Calendar.DATE));
            printMonth(month,xScale,yScale, System.out,daysPerMonth,monthOffset, eventArr); // same as above but without checker as the date is valid
            displayDate(month,day,System.out);
            System.out.println("");
            // end of print todays date
         } else if (userInput.equals("n")) { // next month
            if (month == 0) { // date has not been entered prev
               System.out.println("please enter a date first (e)");
            } else {
               // date has been entered prev
               if (month == 12) {
                  month = 1; // sets month to 1 if month is 12 / end of year
               } else {
                  month++; // else add 1 to month
               } // end of nested if else
               printMonth(month,xScale,yScale, System.out,daysPerMonth,monthOffset, eventArr);
               displayDate(month,day,System.out);
               System.out.println("");                
            } // end of print next month
         } else if (userInput.equals("p")) { // prev month
            if (month == 0) { // date has not been entered prev
               System.out.println("please enter a date first (e)");
            } else {
               if (month == 1) {
                  month = 12; // sets month to 12 if month is 1 / start of year
               } else {
                  month--; // else minus 1 from month
               } // end of nested if else
               printMonth(month,xScale,yScale, System.out,daysPerMonth,monthOffset, eventArr);
               displayDate(month,day,System.out);
               System.out.println("");  
            } // end of print prev month
         } else if (userInput.equals("ev")) { // add event
            System.out.println("enter an event (format <mm/dd eventName>):");
            String new_date = input.nextLine(); // gets user date
            System.out.println("");
            String event = eventFromDate(new_date); // gets the details of the event
            int eventMonth = monthFromDate(new_date);
            int eventDay = dayFromDate(new_date);
            if (validDate(eventMonth,eventDay,daysPerMonth)) {
               eventArr[eventMonth-1][eventDay-1] = event;
               System.out.println("added " + event + " to the calendar");
            } // end of add event function
         } else if (userInput.equals("fp")) { // print to file
            System.out.println("enter a date / month to print to file:");
            String print_date = input.nextLine(); // gets user date
            int printMonth = monthFromDate(print_date);
            int printDay = dayFromDate(print_date);
            if (validDate(printMonth,printDay,daysPerMonth)) {
               PrintStream fout = new PrintStream(new File(getFileName())); // gets user output file
               printMonth(printMonth,xScale,yScale, fout,daysPerMonth,monthOffset, eventArr); // prints calendar month
               displayDate(printMonth,printDay,fout); // prints small display below calendar
               System.out.println("calendar printed out to file");
            } // end of output to file
         } else if (userInput.equals("q")) { // exit program
            break;
         } // end of input check   
      } // end of main while loop
      System.out.print("user exited the program");
   } // end of main method
   
   public static void printMonth(int arg_month, int widthScale, int heightScale, PrintStream outType, int daysInMonthArr[], int offsetArr[], String eventArr[][]) {
      // prints a given month to outType
      // lots of parameters which are all passed through the method to other methods
      printBacking(arg_month,outType); // prints backing
      int daysPerMonth = daysInMonthArr[arg_month-1]; // end of loop
      int MonthOffset = offsetArr[arg_month-1]; // start of loop
      int i = MonthOffset; // sets the offset to the count variable
      printChar(7*(widthScale+3)+1,'=',outType);
      outType.println("");
      printCalendarTopRow(widthScale,outType);
      printChar(7*(widthScale+3)+1,'=',outType);
      outType.println("");
      
      while(i <= daysPerMonth-7) { // checks the count variable 1 week ahead
         printRow(i,7,widthScale,heightScale, outType, arg_month, eventArr); // prints row
         i += 7; // goes to the next week
      } // end of while loop
      int daysLeft = daysPerMonth-i+1; // finds the amount of days in the last week
      printRow(i,daysLeft,widthScale,heightScale, outType, arg_month, eventArr); // prints the last week
   } // end of printMonth methods


   public static void printRow(int arg_startDay, int arg_boxAmount, int widthScale, int heightScale, PrintStream outType, int month, String eventArr[][]) {
      // prints 1 row of the calendar
      // lots of parameters which are all passed through the method to other methods
      printCalendarDateLine(arg_boxAmount,widthScale,arg_startDay, outType);
      
      int linesNeeded = printCalendarEventLine(arg_boxAmount,widthScale,arg_startDay,outType,eventArr, month);
      
      printCalendarBoxLine(arg_boxAmount,widthScale,heightScale-linesNeeded, outType);
      printChar(arg_boxAmount*(widthScale+3)+1,'=',outType);
      outType.println("");
   } // end of printRow method
  
   public static void printCalendarBoxLine(int arg_boxAmount, int arg_boxWidth, int arg_height, PrintStream outType) {
      // prints 1 row of the calendar (the box line / defualt line)
      for (int k = 0; k < arg_height; k++) {
         outType.print("|");
         for (int i = 0; i < arg_boxAmount; i++) { // for loop for number of boxes (in a line / week)
            for (int j = 0; j < arg_boxWidth+2; j++) { // nested for loop for amount of space between boxes / 2 spaces by defualt
               outType.print(" ");      
            } // end of spacing for loop
            outType.print("|");
         } // end of row for loop
         outType.print("\n");
      } // end of height
   } // end of printCalendarBoxLine method

   public static int printCalendarEventLine(int boxAmount, int boxWidth, int weekStartDate, PrintStream outType, String eventArr[][], int month) {
      // prints 1 row of the calendar (the line the events are printed on)
      int currentDay = weekStartDate; // redefines start day as current day
      outType.print("|");
      for (int i = 0; i < boxAmount; i++) { // for loop for number of boxes (in a line / week)
         for (int j = 0; j < boxWidth+2; j++) { // for loop for the space in each box
            if (currentDay > 0) { // check for offset days
               String tempStr = eventArr[month-1][currentDay-1]; // gets the event from array
               if (tempStr == null) { // checks if there is an event in the array index
                  outType.print(" "); // if not it prints a space
               } else if (j < tempStr.length()) { // if there is
               // check to make sure the event does not print beyond calendar width
                  tempStr = tempStr.replace("_", " ");
                  outType.print(tempStr.charAt(j));
               } else {
                  outType.print(" ");
               }
            } else {
               outType.print(" ");
            }
         } // end of event print for loop  
         outType.print("|");
         currentDay += 1;
      } // end of row for loop
      outType.print("\n");
      return 1;
   } // end of printCalendarEventLine method

   public static void printCalendarDateLine(int arg_boxAmount, int arg_boxWidth, int arg_weekStartDate, PrintStream outType) { // by defualt boxwidth = 2
      // prints 1 row of the calendar (the line with the day of the month)
      int currentDay = arg_weekStartDate; // redefines start day as current day
      outType.print("|");
      for (int i = 0; i < arg_boxAmount; i++) { // for loop for number of boxes (in a line / week)
         if (currentDay < 1) {
            outType.print("  ");
         } else if (currentDay < 10) { // checks the length of current day
            outType.print(currentDay + " "); // currentDay = 1 digit so day + space
         } else {
            outType.print(currentDay); // currentDay = 2 digit so no space needed
         }
         for (int j = 0; j < arg_boxWidth; j++) { // nested for loop for amount of space between boxes (with 2 less spaces)
            outType.print(" ");      
         } // end of spacing for loop      
         outType.print("|");
         currentDay += 1;
      } // end of row for loop
      outType.print("\n");
   } // end of printCalendarDateLine method

   public static void printChar(int arg_width, char arg_char, PrintStream outType) {
      // prints 1 char a given amount of times
      int i = 0;
      while(i<arg_width) {
         outType.print(arg_char);
         i++;
      } // end of while
   } // end of printSpace method
   
   public static void printCalendarTopRow(int arg_width, PrintStream outType) {
      // prints the week days names
      outType.print("|su");
      printChar(arg_width,' ',outType);
      outType.print("|mo");
      printChar(arg_width,' ',outType);
      outType.print("|tu");
      printChar(arg_width,' ',outType);
      outType.print("|we");
      printChar(arg_width,' ',outType);
      outType.print("|th");
      printChar(arg_width,' ',outType);
      outType.print("|fr");
      printChar(arg_width,' ',outType);
      outType.print("|sa");
      printChar(arg_width,' ',outType);
      outType.println("|");
   } // end of printCalendarTopRow method
   
   public static void displayDate(int month, int day, PrintStream out) {
      // displays date at the bottom of the calendar
      out.println("month: " + month);
      out.println("day: " + day);
   } // end of display date method

   public static boolean validDate(int month, int day, int daysInMonthArr[]) { // checks if an inputed date is valid
      // checks if a date is a valid day, returns boolean
      if (month > 0 & month < 13) { // checks month first to stop error of arr being out of range when month is passed as index
         if (day > 0 & day < daysInMonthArr[month-1]+1) {
            return true; // returns true if month is 0 to 12 and day is 0 to amount of days in month
         } else {
            System.out.println("<invalid date>");
            return false; // false since day is out of index
         }
      } else {
         System.out.println("<invalid date>");
         return false; // false since month is out of index
      } // end of if logic
   } // end of validDate method
   
   public static String guiGetUserInput() {
      // gets user input / main command
      Scanner input = new Scanner(System.in); // creates input object to get user input
      String userInput;
      // checks if user input is valid and returns it, if not it asks for a new input
      while (true) { // main loop
         commandInstructions();
         userInput = input.nextLine(); // gets user input
         if (userInput.equals("e")) { // checks for valid input
            break;
         } else if (userInput.equals("t")) {
            break;
         } else if (userInput.equals("n")) {
            break;
         } else if (userInput.equals("p")) {
            break;
         } else if (userInput.equals("ev")) {
            break;
         } else if (userInput.equals("fp")) {
            break;
         } else if (userInput.equals("q")) {
            break;
         } else {
            System.out.println("<" + userInput + "> invalid input"); // sends error if input is invalid
         } // end of check
      } // end of main while loop
      return userInput; // returns input once user enters valid input
   } // end of guiGetUserInput mthod
   
   // next 3 method get information from a 'date' string
   
   public static String eventFromDate(String arg_dateString) { 
      // gets string after ' ' / the event String
      String event;
      if (arg_dateString.contains(" ")) {
         event = arg_dateString.substring(arg_dateString.indexOf(" ")+1,arg_dateString.length());
      } else {
         event = "";
      }
      return event;
   } // end of eventFromDate method
   
   public static int dayFromDate(String arg_dateString) { 
      // gets int after '/' and before ' ' / the event day (int)
      //mm/dd format
      int dateInt;
      if (arg_dateString.contains(" ")) {
         dateInt = Integer.parseInt(arg_dateString.substring(arg_dateString.indexOf("/")+1,arg_dateString.indexOf(" "))); 
      } else {
         dateInt = Integer.parseInt(arg_dateString.substring(arg_dateString.indexOf("/")+1,arg_dateString.length()));
      }
      return dateInt;
   } // end of dayFromDate method
   
   public static int monthFromDate(String arg_dateString) { 
   // gets int prior to '/' / the event month (int)
      //mm/dd format
      int dateInt = Integer.parseInt(arg_dateString.substring(0,arg_dateString.indexOf("/"))); 
      return dateInt;
   } // end of monthFromDate method
   
   public static String[][] parseInputFileToArr(String[][] arr, String inputFile) throws FileNotFoundException {
      // takes input file and returns the complete event array
      Scanner fin = new Scanner(new File(inputFile)); // gets file from getUsersFileName and then scans it
      
      while (fin.hasNextLine()) { // loops through every line (row) of the file
         String lineData = fin.nextLine(); // gets string from next line of the file
         int eventMonth = monthFromDate(lineData); // gets each atribute of the event
         int eventDay = dayFromDate(lineData);
         String event = eventFromDate(lineData); 
         arr[eventMonth-1][eventDay-1] = event; // sets event to its mm/dd index
      } // end of file line loop
      fin.close();
      return arr; // returns arr with file inputs embeded
   } // end of parseInputFileToArr method
   
   public static String getFileName() throws FileNotFoundException {
      // gets the output file from the user
      Scanner input = new Scanner(System.in); // creates input object
      String str; // creates temp string
      
      while (true) {
         System.out.println("print calendar to (.txt file): ");
         str = input.nextLine(); // user input to get file name
         
         if (doesFileExist(str)) {
            break;
         } else {
            System.out.println("<" + str + "> (file) does not exist");
         } // end of if else logic
      } // end of while loop
      return str;
   } //end of getFileName method
   
   public static boolean doesFileExist(String fileName) throws FileNotFoundException { // checks if given fileName exists
      File f = new File(fileName);
      if (f.exists()) {
         return true;
      } else {
         return false;
      } // end of if else logic
   } // end of doesFileExist method
   
   public static String[][] createEventArr() { // creates 2d array, index is [mm][dd]
      int daysPerMonth[] = {31,28,31,30,31,30,31,31,30,31,30,31};
      String[][] arr = new String[12][]; 
      for (int i = 0; i < 12; i++) {
         arr[i] = new String[daysPerMonth[i]];
      } // end of for loop. creates 2d array with month axis, and days axis
      return arr;
   } // end of createEventArr method
   
   public static void commandInstructions() { // prints the instructions when called
      System.out.println("Please type a command");
      System.out.println("\"e\" to enter a date and display the corresponding calendar");
      System.out.println("\"t\" to get todays date and display todays calendar");
      System.out.println("\"n\" to display the next month");
      System.out.println("\"p\" to display the previous month");
      System.out.println("\"ev\" to add an event (<mm/dd event> format)");
      System.out.println("\"fp\" to prints a month to a .txt file");
      System.out.println("\"q\" to quit the program");
   } // end of commandInstructions method
   
   public static void printBacking(int month, PrintStream outType) {
      // prints calendar backing
      // each month has ascii text stored in a 2d array
      // 1 axis is month, other axis is the line of the backing
      String backingArr[][] = { {""}, // <- first index prints nothing (index=0)
      { // jan backing
      "      _                                                ",
      "     | |   __ _   _ __    _   _    __ _   _ __   _   _ ",
      "  _  | |  / _` | | '_ \\  | | | |  / _` | | '__| | | | |",
      " | |_| | | (_| | | | | | | |_| | | (_| | | |    | |_| |",
      "  \\___/   \\__,_| |_| |_|  \\__,_|  \\__,_| |_|     \\__, |",
      "                                                 |___/ "
      },
      { // feb backing
      "  _____          _                                    ",
      " |  ___|   ___  | |__    _   _    __ _   _ __   _   _ ",
      " | |_     / _ \\ | '_ \\  | | | |  / _` | | '__| | | | |",
      " |  _|   |  __/ | |_) | | |_| | | (_| | | |    | |_| |",
      " |_|      \\___| |_.__/   \\__,_|  \\__,_| |_|     \\__, |",
      "                                                |___/ "
      },
      { // mar backing
      "  __  __                         _     ",
      " |  \\/  |   __ _   _ __    ___  | |__  ",
      " | |\\/| |  / _` | | '__|  / __| | '_ \\ ",
      " | |  | | | (_| | | |    | (__  | | | |",
      " |_|  |_|  \\__,_| |_|     \\___| |_| |_|",
      ""
      },
      { // apr backing
      "     _                     _   _ ",
      "    / \\     _ __    _ __  (_) | |",
      "   / _ \\   | '_ \\  | '__| | | | |",
      "  / ___ \\  | |_) | | |    | | | |",
      " /_/   \\_\\ | .__/  |_|    |_| |_|",
      "           |_|   "
      },
      { // may backing
      "  __  __                 ",
      " |  \\/  |   __ _   _   _ ",
      " | |\\/| |  / _` | | | | |",
      " | |  | | | (_| | | |_| |",
      " |_|  |_|  \\__,_|  \\__, |",
      "                   |___/ "
      },
      { // jun backing
      "      _                        ",
      "     | |  _   _   _ __     ___ ",
      "  _  | | | | | | | '_ \\   / _ \\",
      " | |_| | | |_| | | | | | |  __/",
      "  \\___/   \\__,_| |_| |_|  \\___|",
      ""
      },
      { // jul backing
      "      _           _         ",
      "     | |  _   _  | |  _   _ ",
      "  _  | | | | | | | | | | | |",
      " | |_| | | |_| | | | | |_| |",
      "  \\___/   \\__,_| |_|  \\__, |",
      "                      |___/ "
      },
      { // aug backing
      "     _                                    _   ",
      "    / \\     _   _    __ _   _   _   ___  | |_ ",
      "   / _ \\   | | | |  / _` | | | | | / __| | __|",
      "  / ___ \\  | |_| | | (_| | | |_| | \\__ \\ | |_ ",
      " /_/   \\_\\  \\__,_|  \\__, |  \\__,_| |___/  \\__|",
      "                    |___/                     "
      },
      { // sep backing
      "  ____                   _                        _                   ",
      " / ___|    ___   _ __   | |_    ___   _ __ ___   | |__     ___   _ __ ",
      " \\___ \\   / _ \\ | '_ \\  | __|  / _ \\ | '_ ` _ \\  | '_ \\   / _ \\ | '__|",
      "  ___) | |  __/ | |_) | | |_  |  __/ | | | | | | | |_) | |  __/ | |   ",
      " |____/   \\___| | .__/   \\__|  \\___| |_| |_| |_| |_.__/   \\___| |_|   ",
      "                |_|                                                   "
      },
      { // oct backing
      "   ___           _             _                   ",
      "  / _ \\    ___  | |_    ___   | |__     ___   _ __ ",
      " | | | |  / __| | __|  / _ \\  | '_ \\   / _ \\ | '__|",
      " | |_| | | (__  | |_  | (_) | | |_) | |  __/ | |   ",
      "  \\___/   \\___|  \\__|  \\___/  |_.__/   \\___| |_|   ",
      ""
      },
      { // nov backing
      "  _   _                                      _                   ",
      " | \\ | |   ___   __   __   ___   _ __ ___   | |__     ___   _ __ ",
      " |  \\| |  / _ \\  \\ \\ / /  / _ \\ | '_ ` _ \\  | '_ \\   / _ \\ | '__|",
      " | |\\  | | (_) |  \\ V /  |  __/ | | | | | | | |_) | |  __/ | |   ",
      " |_| \\_|  \\___/    \\_/    \\___| |_| |_| |_| |_.__/   \\___| |_|   ",
      ""
      },
      { // dec backing
      "  ____                                     _                   ",
      " |  _ \\    ___    ___    ___   _ __ ___   | |__     ___   _ __ ",
      " | | | |  / _ \\  / __|  / _ \\ | '_ ` _ \\  | '_ \\   / _ \\ | '__|",
      " | |_| | |  __/ | (__  |  __/ | | | | | | | |_) | |  __/ | |   ",
      " |____/   \\___|  \\___|  \\___| |_| |_| |_| |_.__/   \\___| |_|   ",
      ""
      }
      };
      for (int i = 0; i < 6; i++) { // for loop to print the backing
         outType.println(backingArr[month][i]);
      } // end of printing for loop
   } // end of printBacking method
} // end of class