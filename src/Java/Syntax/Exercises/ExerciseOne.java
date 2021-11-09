package Java.Syntax.Exercises;

import java.util.Scanner;

public class ExerciseOne {
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

    public static void main(String[] args) {
        System.out.println("Enter First Number");
        int first_value = validate_integer_input();
        System.out.println("Enter Second Number");
        int second_value = validate_integer_input();
        int big_value = Math.max(first_value,second_value);
        int small_value = Math.min(first_value,second_value);
        while(small_value<big_value){
            System.out.print(small_value+"\t");
            small_value++;
        }

    }
}
