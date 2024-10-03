/*
Cole Johnson
3/17/23
CS 141, DNA for lab 6

this program takes a given input file (.txt) and prints the output to a given output file (.txt).
the input file have a gene descriptor and the actual gene in the row below it (it can have more than 1 / not max limit).
the output file will then print 4 pieces of information about the given gene.

first is the number of the different nucleotides in the gene (ordered in an array as {a, c, g, t, -}). It does this by
looping through the string and adding 1 to the array when it find the value in the string.

second is the mass of the gene. It does this by taking the amount of each nucleotide and then multiplying them by their mass constant,
and then add all the masses together to get the total mass. Then it divides the mass of one nucleotide by the total to get the
percent mass of each nucleotides.

third is the codon list. this first removes any junk (the '-') from the gene string, and then gets each codon by taking a substring of the
new gene string 3 characters at a time and then adding them to the codon array. It then prints that codon array to the file.

lastly it checks if the gene is a protein coding one by doing the following checks
1. if the first codon is "ATG"
2. if the last codon is "TAA", "TAG", "TGA"
3. if the length of the codon array is greater than 4
4. if the mass % of c and g (index 2 and 3 of totalMassArr) is greater than 30
prints yes if true and no if false using short hand if statements.

*/ // imports
import java.util.*;
import java.io.*;

class CJ_DNA {
   public static void main(String[] args) throws FileNotFoundException {

      System.out.println("This program reports information about DNA");
      System.out.println("nucleotide sequences that may encode proteins.\n");
          
      Scanner fin = new Scanner(new File(getUsersFileName('i'))); // gets file from getUsersFileName and then scans it
      PrintStream fout = new PrintStream(new File(getUsersFileName('o'))); // same as above but for the output file
      while (fin.hasNextLine()) { // goes through each line in the file
         String lineData = fin.nextLine();
         //String lastIndex = (lineData.substring(lineData.length()-3,lineData.length())).toUpperCase();
         if (!(lineData.contains(" "))) { // lastIndex.equals("TAA") || lastIndex.equals("TAG") || lastIndex.equals("TGA")
            // codon line
            int[] amountOfNucleotidesArr = getAmountOfNucleotides(lineData); // total amount of {a, c, g, t, -}
            double[] totalMassArr = getTotalMass(amountOfNucleotidesArr); // gets array of {total mass, %a, %c, %g, %t, %-}
            String codonArr[] = createCodonArr(lineData); // creates an array of the codons without the junk char '-'            
            boolean protein_b = getProteinBool(codonArr, totalMassArr); // gets boolean (if gene is protein coding)    
            // each print needs slightly different for statements  
            // prints nuc string
            fout.println("Nucleotides: " + lineData.toUpperCase());
            // prints nuc count array
            fout.print("Nuc. Counts: [");
            for (int i = 0; i < amountOfNucleotidesArr.length-2; i++) {
               fout.print(amountOfNucleotidesArr[i] + ", ");
            }
            fout.println(amountOfNucleotidesArr[amountOfNucleotidesArr.length-2] + "]");
            // prints total mass % array
            fout.print("Total Mass%: [");
            for (int i = 1; i < totalMassArr.length-2; i++) {
               fout.print(round(totalMassArr[i],1) + ", ");
            }
            fout.println(round(totalMassArr[totalMassArr.length-2],1) +"]");
            // prints codon array
            fout.print("Codons List: [");
            for (int i = 0; i < codonArr.length-1; i++) {
               fout.print(codonArr[i].toUpperCase() + ", ");
            }
            fout.println(codonArr[codonArr.length-1].toUpperCase() + "]");
            // prints protein bool
            String temp = (protein_b) ? "YES" : "NO";
            fout.println("Is Protein?: " + temp + "\n");      
         } else {
            // non codon line
            fout.println("Region Name: " + lineData);
         } // end of if else logic
      } // end of while loop
      fin.close();
      fout.close();
   } // end of main method
   
   // copied from some of my older code
   public static double round(double val, int decimal) { // method which rounds a value to a given number of decimals
      int scale = (int) Math.pow(10, decimal); // 
      return (double) Math.round(val * scale) / scale; // brings val up by power of 10, rounds / removes decimals, brings val back down
   } // end of round method
   
   // copied from mab libs lab
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
   
   public static boolean getProteinBool(String[] codonArr, double[] massArr) { // checks if string is a protein  coding gene
      if ((codonArr[0].toUpperCase()).equals("ATG")) {
         // checks for valid start codon
         codonArr[codonArr.length-1] = codonArr[codonArr.length-1].toUpperCase();
         if (codonArr[codonArr.length-1].equals("TAA") || codonArr[codonArr.length-1].equals("TAG") || codonArr[codonArr.length-1].equals("TGA")) {
            // checks for valid end codon
            if (codonArr.length > 4) {
               // checks for valid gene length
               if (massArr[2] + massArr[3] >= 30) {
                  // checks for valid mass %
                  return true;
               } else {
                  /*System.out.println("failed mass");*/ return false; 
               }
            } else {
               /*System.out.println("failed length");*/ return false; 
            }
         } else {
            /*System.out.println(" failed end codon");*/ return false; 
         }
      } else {
         /*System.out.println("failed start codon");*/ return false; 
      } // returns false if any checks fail and true if all checks are valid
   } // end of getProteinBool method

   public static String[] createCodonArr(String codonStr) {
      if (codonStr.contains("-")) { // checks if codon string contains junk '-'
         while(codonStr.contains("-")) { // repeats check until junk '-' is removed form the string
            int index = codonStr.indexOf("-"); // gets first index of junk '-'
            codonStr = codonStr.substring(0,index) + codonStr.substring(index+1,codonStr.length());
            codonStr = codonStr.toUpperCase(); // ensures codons are upper case
            // redefines string as the string without the index of the char '-'
         } // end of while loop check
      } // end of if check
      // result is a string without the char '-'
      int trueStrLength = 0;
      for (int i = 0; i < codonStr.length(); i++) { // gets the length of the string without 'junk' or the  '-'
         if (codonStr.charAt(i) != '-') {
            trueStrLength += 1;
         } // end of if 
      } // end of for loop
      int numberOfCodons = trueStrLength/3; // gets the number of codons from the string length / 3
      String[] returnArr = new String[numberOfCodons]; // creates new array with desired size
      for (int i = 0; i < numberOfCodons; i++) { // fills each index of the new array
         String tempCodon = codonStr.substring(i*3, i*3+3);
         returnArr[i] = tempCodon;
      } // end of for loop
      return returnArr; // returns filled array
   } // end of createCodonArr method
   
   public static double[] getTotalMass(int[] nucArr) {
      double[] returnArray = {0,0,0,0,0,0}, nucMass = {135.128,111.103,151.128,125.107,100.000};
      // defines parallel arrays
      for (int i = 0; i < 5; i++) { // gets total mass in index 0
         returnArray[0] += nucArr[i] * nucMass[i];
      } // end of for loop
      for (int i = 1; i <= 5; i++) { // gets total % of mass of a c g and t in index 1 2 3 and 4
         returnArray[i] = (nucArr[i-1]*nucMass[i-1])/(returnArray[0])*100; // uses (e/t)*100 to get % total
      } // end of for loop
      return returnArray;
   } // end of getTotalMass method
       
   public static int[] getAmountOfNucleotides(String str) {
      // chekcs each index in string for its char and then adds 1 to the return array (in the given chars index)
      int[] arr = {0,0,0,0,0}; // order: num of a, num of c, num of g, num of t, num of -
      for (int i = 0; i < str.length(); i++) {
         switch (str.charAt(i)) {
            case 'a':
            case 'A':
               arr[0] = arr[0] + 1;
               break;
            case 'c':
            case 'C':
               arr[1] = arr[1] + 1;
               break;
            case 'g':
            case 'G':
               arr[2] = arr[2] + 1;
               break;
            case 't':
            case 'T':
               arr[3] = arr[3] + 1;
               break;
            case '-':
               arr[4] = arr[4] + 1;
               break;
         } // end of switch
      } // end of for loop
      return arr;
   } // end of getAmountOfNucleotides method
} // end of class