/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordsearch;

import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Michael
 */
public class WordSearch { //make instance variables private and no static methods
    private String[] wordList;
    private char[][] crossWord;
    
    
/**
 * The Constructor
 */
    public WordSearch()
    {
      
        int numOfWords = getNumberOfWords();
        crossWord = new char[numOfWords][numOfWords];
        wordList = new String[numOfWords];
    }  
    /**
     * Prompt user for number
     */
    public int getNumberOfWords(){
        
        int validNum = 0;
        while(!(validNum>=5 && validNum<=20)){
            Scanner keyboard = new Scanner(System.in);
            try{     
               System.out.printf("%nPlease enter number of words: ");
               validateNumber(validNum = keyboard.nextInt());
               }catch(InputMismatchException e){
                  System.out.printf("Sorry, the number is not valid. Please enter a number from 5 -20", e);
             }
                catch (IllegalArgumentException ex)
                {
                    System.out.printf(ex.getMessage().replaceAll("%n", " "));
                }
        }
     return validNum;
    }
    public void getWords(){// Get words from user to fill the array
        Scanner keyboard = new Scanner(System.in);
       
        
        int count = 0;
        while(count < wordList.length) {
           try{
               System.out.print("Enter a new word: ");
               String newWord = keyboard.nextLine();
               validateWord(newWord.trim());          
               wordList[count] = newWord.toUpperCase();
               count++;
            }
           catch(IllegalArgumentException e){
               System.out.printf("Sorry, the word is invalid, %s%n", e);
           }
        }
        
    }  
        /**
        * make word list from array to a string
        * make crossword puzzle to a string
        */
    
    @Override
     public String toString(){
         Arrays.sort(wordList);
         String str = "";
         for(String element : wordList){
             str += String.format("%s%n", element);
         }
         
        String grid = "";
        for (char[] crossWord1 : crossWord) {
            for (int col = 0; col<crossWord.length; col++) {
                grid += String.format("%3s", crossWord1[col]);
            }
            grid += String.format("%n");
        }
         return String.format("%s%nHere are the words to find:%n%s",grid, str);
     }   
          
        
    
    /**
     *Validate number entered 
     */
    public static void validateNumber(int valid){
        
        if (!(valid>=5 && valid<=20))
            throw new IllegalArgumentException(
            "The number is not valid%n");
        
    }
    /*
    Validate words entered
    */
    public void validateWord(String word){
            
        
            if(word.length()>wordList.length)
                throw new IllegalArgumentException("The word is too long.");
            if(word.length() == 1)
                throw new IllegalArgumentException("The word is too short");
            if(word.contains(" "))
                throw new IllegalArgumentException("The word has spacing");
            if(wordList.length>0){
              for(int i=0; i<wordList.length; i++)
                if(word.equalsIgnoreCase(wordList[i]))
                  throw new IllegalArgumentException("The word is a duplicate"); 
        }
    }
       
    
    //Create crossword puzzle and fill empty spaces
    
    public void fillEmpties(){
        SecureRandom rng = new SecureRandom();
        char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M',
                          'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        
        //insert words from list to puzzle grid
        for(int i=0; i<wordList.length;i++){
            String word = wordList[i];
            int random;
            if(wordList.length - word.length() == 0)
                random = 0;
            else
                random = rng.nextInt(wordList.length - word.length()+1);
            for(int j=0; j<word.length(); j++){
            crossWord[random][i] = word.charAt(j);
            random++;
        }
        }
        
        //insert alphabet into grid
        for (int row=0; row<crossWord.length;row++){
            for(int col=0;col<crossWord.length;col++){
                 int alpha = rng.nextInt(26);
                if (crossWord[row][col] == 0)
                    crossWord[row][col] = letters[alpha];
                  }
        }
    }         
   
    /**
     * This method will print the crossword puzzle and word list that are
     * converted to string to puzzle.txt
     */
    public void printTofile(){
        String puzzle = toString();
        try{
            Formatter outputStream = new Formatter("puzzle.txt");   
            outputStream.format(puzzle); // put method that has the string
            outputStream.close();
        }catch (FileNotFoundException e){
            System.out.print("Sorry we cannot write to puzzle.txt");
        }
    }
    //main method
    public static void main(String[] args) {
      WordSearch puzzle = new WordSearch();
      puzzle.getWords();
      puzzle.fillEmpties();
     
      System.out.print(puzzle.toString());
      puzzle.printTofile();
    }//end of main method
    
}
