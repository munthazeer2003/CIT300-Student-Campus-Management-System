package campus.util;

import java.util.Scanner;

/**
 * Reusable input-validation helpers used throughout the console menu
 * (Requirement 13/14: input validation, invalid input handling).
 */
public class InputValidator {

    public static String readNonEmptyString(Scanner sc, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    public static double readValidMarks(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                double marks = Double.parseDouble(input);
                if (Double.isNaN(marks) || Double.isInfinite(marks) || marks < 0 || marks > 100) {
                    System.out.println("Marks must be between 0 and 100. Please try again.");
                    continue;
                }
                return marks;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a numeric value.");
            }
        }
    }

    public static int readValidInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }
}
