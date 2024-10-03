import java.util.Random;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
public class Hangman
{
    public static void main(String[]args) throws FileNotFoundException, IOException 
    {
        Random random = new Random();
        List<String>words = getwords("words.txt");
        int index = random.nextInt(words.size());
        String mysteryword = words.get(index);
        System.out.println("Guess the mystery word.");
        String filledstring = "_".repeat((mysteryword.length()));
        Set<Character> filled = new HashSet<>();
        Set<Character> incorrect = new HashSet<>();
        int strikes = 0;
        String outcome = guess(filledstring, mysteryword, filled, incorrect, strikes);
        System.out.println(outcome);
        
    }
    private static List<String> getwords(String filename) throws FileNotFoundException, IOException
    {
        List<String>words = new ArrayList<>();
        try(BufferedReader wordreader = new BufferedReader(new FileReader(filename)))
        {
            String word;
            while((word = wordreader.readLine())!=null)
            {
                word = word.strip();
                words.add(word);
            }
        }
        catch(Exception e)
        {
            System.out.println();
        }
        return words;
    }
    private static String guess(String filledString, String mysteryword, Set<Character>filled, Set<Character>incorrect, int strikes)
    {
        if(strikes == 6)
        {
            return "You've lost! The word was "+mysteryword;   
        }
        else if(filledString.equals(mysteryword))
        {
            return "Congratulations! You guessed the word was "+mysteryword;
        }
        else
        {
            Scanner scanner = new Scanner(System.in);
            char input = ' ';
            while(true)
            {
                String currentInput = "";
                Boolean validornot = false;
                while(!validornot)
                {
                    System.out.println("Enter a single letter or guess the word please");
                    currentInput = scanner.nextLine();
                    if(currentInput.length() == 1 && Character.isLetter(currentInput.charAt(0)))
                    {
                        input = currentInput.charAt(0);
                        if(mysteryword.contains(String.valueOf(input)))
                        {
                            if(filled.contains(input))
                            {
                                System.out.println("The letter is already filled out. Try again");
                            }
                            else
                            {
                                System.out.println("Nice job! "+input+ " is on the mystery word!");
                                filled.add(input);
                                validornot = true;
                            }
                        }
                        else
                        {
                            System.out.println(input+" is not on the mystery word");
                            validornot = true;
                            strikes+=1;
                            if(incorrect.contains(input))
                            {
                                System.out.println("The letter is already filled out. Try again");
                            }
                            
                        }
                        
                    }
                    else
                    {
                        if(currentInput.equals(mysteryword))
                        {
                            filledString = currentInput;
                            validornot = true;
                        }
                        else
                        {
                            System.out.println("Sorry that is incorrect");
                            validornot = true;
                            strikes+=1;
                        }
                    }
                }
                String results = "";
                if(!(filledString.equals(mysteryword)))
                {
                    for(int i = 0; i < mysteryword.length(); i++)
                    {
                        if(filled.contains(mysteryword.charAt(i)))
                        {
                            results += String.valueOf(mysteryword.charAt(i));
                        }
                        else
                        {
                            results += "_";
                        }
                    }
                    filledString = results;
                }
               
                System.out.println("Word: "+filledString);
                return guess(filledString, mysteryword, filled, incorrect, strikes);
            }
            
            
        }
    }
    

}