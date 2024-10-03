/*
Cole Johnson
2/22/23
CS 141, guessing game

This is a program which runs a guessing game with the user, where the user has to guess a random number as many times until they get it right,
with the system telling them if its too low or too high of a guess. The user can also decide to then play the game again.

Before running the game it prints instructions and afterwards it also prints out the total number of games played, total guesses, average guesses
per game, and the number of guesses in the highest scoring (lowest guesses) game.

There are 3 methods other than the main method used.

the main method calls the other methods as well as handling the main while loop and the variables.

the instructions method uses println to print the instructions to the user.

the singleGameRun method runs 1 loop of the guessing game with the user, and returns the amount of guesses the user took to guess the number.

the printResults method takes in the total guesses, total games, and lowest game, and uses those to print the results to the user.

*/ // import
import java.util.*;

public class CJ_Guess {
    public static void main(String args[]) { // handler
      Scanner input = new Scanner(System.in);
      
      introductions(); // prints instructions
      
      int lowestRun = 0, runs = 0, totalGuesses = 0; // defines needed variables
      String userInput = "y";
    
      while ((userInput.equals("n") || userInput.equals("no") || userInput.equals("nope")) == false) { // start of loop and end conditions, 
      
         runs += 1;
    
         int runAttempts = singleGameRun();
         totalGuesses += runAttempts;
         
         // logic to get lowest guesses
         if (runs == 1) { // checks for 1st run
            lowestRun = runAttempts;
         } else { // not first run
            if (lowestRun > runAttempts) {
               lowestRun = runAttempts;
            } // end of nested
         } // end of if logic
         
         System.out.print("Do you want to play again? ");
         userInput = input.nextLine(); // input statement
         userInput = userInput.toLowerCase();

      } // end of loop
      printResults(totalGuesses, runs, lowestRun); // prints results
    } // end of main
   
    public static void introductions() { // prints instructions using println statement
      System.out.println("This program allows you to play a guessing game.");
      System.out.println("I will think of a number between 1 and");
      System.out.println("100 and will allow you to guess until");
      System.out.println("you get it. For each guess, I will tell you");
      System.out.println("whether the right answer is higher or lower");
      System.out.println("than your guess.");
    } // end of instruction method
    
    public static int singleGameRun() { // runs 1 game, returns total guesses, more below
      /* the loop runing the game stops when the user input and the random number are the same and the returns the attempts */
      
      Random rand = new Random(); 
      Scanner input = new Scanner(System.in);
      int randomNumber = rand.nextInt(100) + 1; // gets random number from 1-100
      
      int attempts = 0, userNumber = 0; // defines variables
      
      while(userNumber != randomNumber) { // start of loop with exit condition
         attempts += 1;
      
         System.out.print("Your guess? ");
         userNumber = input.nextInt(); // input statement
         
         if (userNumber == randomNumber) { // if statements to check if user num is higher or lower than random num
            continue;
         } else if (userNumber < randomNumber) {
            System.out.println("Its higher");
         } else {
            System.out.println("Its lower");
         } // end of if else logic
      } // end of loop
      return attempts;
    } // end of 1 run method
    
    public static void printResults(int arg_totalGuesses, int arg_totalRuns, int arg_lowestRun) { 
      // print total games, total guesses, guesses / games, best game
      System.out.println("\nTotal games = " + arg_totalRuns);
      System.out.println("Total gueses = " + arg_totalGuesses);
      System.out.println("Gueses/games = " + (arg_totalGuesses / arg_totalRuns));
      System.out.println("Best Game = " + arg_lowestRun);
    } // end of print results method
}