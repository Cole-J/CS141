/*
Cole Johnson
2/27/23
CS 141, mad libs

this program can be used to create and view mad libs.

the user is able to use the program / gui to create mad libs from templtates, and then output them to an output file, or view output files, 
and be able to do those two things an infinite number of times.

the program first prints instructions, and then asks the user for their input on what they would like to do.
if they want to view a file, it gets the file name and then prints each line of the txt file line by line.
if they want to create a file, it gets an input (template) and output file, and then uses user input to fill out the mad lib template.

the program uses a number of methods other than main, those are,

getUsersFileName(char arg_textType), gets the file the user wants to use as an input or output, makes sure the file exists and can be used, and if so
returns the file. this is called by other methods to both get the file the user wants to use, while also making sure it exists. the argument just specifies
if the program wants an input or output file to the user.

viewExistingFile() uses getUsersFileName() to get a file, and then prints it line by line until there are no more lines (rows) of the txt file.

createNewFile() is the most complicated method. it gets the template file and then outputs it to a new file, however it lets the user change the values of the
placeholders (<placeholder>) in the template file.

header() just prints some basic instructions to the user at the beginning of main.

gui() returns a users input, after asking which part of the program the user wants to use which is then used in main to call the correct method.

*/ // import
import java.util.*;
import java.io.*;

public class CJ_MadLibs {
   public static void main(String args[]) throws FileNotFoundException { // mian handler for the other methods
   
      header(); // prints the instructions at the top of the output
      
      char userInput = 'a';
      
      while (userInput != 'q') { // main while loop / gets user input, then calls correct method
      
         userInput = gui(); // gets the user input (char variable)
         
         switch (userInput) { // logic to call methods
            case 'v': viewExistingFile(); // calls view file method
               break;
            case 'c': createNewFile(); // calls create file method
               break;
            default: continue; // if userInput is 'q' then it continues and breaks at the beginning of the next loop
         } // end of switch logic
      } // end of while loop
      System.out.print("thanks for playing!");
   } // end of main method
   
   public static String getUsersFileName(char arg_textType) { // takes user input and checks to make sure the file exists, if it does it returns the file name
      Scanner input = new Scanner(System.in);
      String userInput;
      while (true) {
         switch (arg_textType) { // switch for variable starting statement
            case 'i': System.out.print("Input file name: ");
            break;
            case 'o': System.out.print("Output file name: ");
            break;
         } // end of switch
         userInput = input.nextLine(); // user input to get file name
         
         File fileObject = new File(userInput);
         
         if (fileObject.exists()) { // makes sure the file exists
            break;
         } else {
            System.out.println("file does not exist, try again."); // user error statement
         } // end of file check
      } // end of file check while loop
      return userInput;
   } // end of get file method
   
   public static void viewExistingFile() throws FileNotFoundException { // gets a users file using user input, then pints its contents line by line
      
      Scanner f = new Scanner(new File(getUsersFileName('i'))); // gets file from get file name  method and then scans it
      
      while (f.hasNextLine()) { // while loop to print contents of file line by line
         String lineData = f.nextLine();
         System.out.println(lineData);
      } // end of printing while loop
      f.close();
   } //end of view existing file method
   
   public static void createNewFile() throws FileNotFoundException {
      // this method opens a template file, then going line by line it searches for placeholders, and prompts the user to fill out the placeholders with their
      // own input. the method then outputs all of these changes to the template to a seperate output file
   
      Scanner f = new Scanner(new File(getUsersFileName('i'))); // gets file from get file name  method and then scans it, this is the input mad lib file
      PrintStream output = new PrintStream(new File(getUsersFileName('o'))); // same as above, this is the ouput mad lib file
      Scanner input = new Scanner(System.in); // creates input object to get user input
      
      //System.out.println("t");
      
      while (f.hasNextLine()) { // loops through each line of the file given by the user
         
         String data = f.nextLine(); // gets the string for 1 line of the file / 1 line of data
         //output.println(lineData);
         
         int i = 0; // while loop count variable
         while (i < data.length()) { // nested while loop that loops through each line of the input file
         
            if (data.charAt(i) == '<') { // finds beginning of the placeholder
               String subData = data.substring(i+1,data.length());
               
               if (subData.contains(">")) { // makes sure that there is an end to the placeholder
                  // main in and out scope for placeholder
                  
                  int endIndex = i + subData.indexOf(">");
                  String placeHolder = data.substring(i+1,endIndex+1);
                 
                  char placeHolderFirstIndex = (placeHolder.toLowerCase()).charAt(0); // gets first letter of the placeholder
                  if ((placeHolderFirstIndex == 'a') & (placeHolderFirstIndex == 'e') & (placeHolderFirstIndex == 'i' & (placeHolderFirstIndex == 'o'))) {
                     System.out.println("Please type an " + placeHolder); // changes from "a" to "an" depending on the first letter in placeHolder string
                  } else {
                     System.out.println("Please type a " + placeHolder);
                  }
                  String userInput = input.nextLine(); // gets user input
                  
                  output.print(userInput); // outputs user input to ouput file
                  
                  i = endIndex + 1; // sets index to the end of the placeholder
                  
               } else { // if no end to placeholder, then there is no placeholder
                  output.print(data.charAt(i));
               }
            } else { // if no beginning to placeholder, then there is no placeholder
               output.print(data.charAt(i));
            }
            i++; // increase nested loop count variable
         } // end of nested while loop
         output.println(""); // goes to next line
      } // end of primary while
      f.close();
      System.out.println("Mad lib has been created.");
   } // end of create new file method
   
   public static void header() { // prints the heading / instructions at the top of the output
      System.out.println("Welcome to the game of Mad Libs.\nI will ask you to provide various words");
      System.out.println("and phrases to fill in a story.\nThe result will be written to an output file.");
   } // end of header method
   
   public static char gui() { // this method runs the main gui, its called in main to get user input and returns a char variable
      Scanner input = new Scanner(System.in);
      String userInput;
   
      while(true) { // while loop to make sure the user input is correct
         System.out.print("(C)reate mad-lib, (V)iew mad-lib, (Q)uit: ");
         userInput = input.nextLine(); // gets user input
         userInput = userInput.toLowerCase();
         
         if (userInput.equals("c") || userInput.equals("v") || userInput.equals("q")) { // break condition
            break;
         } // end of break condition
         System.out.println("<" + userInput + "> is an invalid input."); // error message to user
      } // end of while loop
      return userInput.charAt(0);
   } // end of gui method
} // end of class