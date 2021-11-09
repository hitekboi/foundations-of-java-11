package Java.Syntax.Exercises;
import java.util.Random;
import java.util.Scanner;

public class ExerciseTwo {
    static int validate_integer_input(){
        int value = -1;
        do {
            Scanner keyboard = new Scanner(System.in);
            while (!keyboard.hasNextInt()) {
                System.out.println("That's Not a Number");
                keyboard.next();
            }
            value = keyboard.nextInt();
        }while(value <= 0);
        return value;
    }
    static int ChosenValue(int key_value){
        Random generator = new Random();
        return generator.nextInt(key_value*10);
    }
    static int MaxNumberOfTries(int key_value){
        return 100-key_value;
    }
    static void GameLoop(int answer, int number_of_tries){
        int turns_left = number_of_tries;
        int user_answer = -1;
        do {
            System.out.println("Enter Your Guess:");
            user_answer = validate_integer_input();
            turns_left--;
            if(user_answer<answer) System.out.println("The Value is Greater Than Input.\nTurns Left: "+turns_left);
            else if(user_answer>answer) System.out.println("The Value is Lower Than Input.\nTurns Left: "+turns_left);
            else break;
        } while(turns_left > 0);
        if(user_answer!=answer || turns_left == 0){
            System.out.println("You Lost!");
        }
        else{
            System.out.println("You Won!\nNumber of Turns Left: "+turns_left);
        }
    }
    public static void main(String[] args){
        System.out.println("Choose your difficulty level:[1-99]");
        int key_value = validate_integer_input();
        int answer = ChosenValue(key_value);
        int number_of_tries = MaxNumberOfTries(key_value);
        GameLoop(answer, number_of_tries);
    }
}
