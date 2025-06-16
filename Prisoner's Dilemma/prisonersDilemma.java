
/**
 * Prisoner's Dilemma
 *
 * @author Alex Brook Conway
 * @version 09/06/25
 */

import java.util.Scanner; // Keyboard input

public class prisonersDilemma {

    Scanner keyboard = new Scanner(System.in); // Initialize scanner for user input
    int homeScreenLength = 0;
    boolean invalidInput = true;

    public prisonersDilemma() {
        System.out.print('\u000C'); // clear screen
        homeScreen();
    }

    public void homeScreen() {

        switch(homeScreenLength) { // switch statement to take user through key info one piece at a time.
            case 0:
                while(invalidInput) { // if invalid input, reprint and let the user try again
                    System.out.println("Welcome to Prisoner's Dilemma");
                    System.out.println("");
                    System.out.println("The Prisoner's Dilemma is a classic example in game theory where two players must choose to cooperate or betray, ");
                    System.out.println("with outcomes depending on the combination of their choices—highlighting the conflict between individual gain and mutual benefit");
                    userContinuer(); // get continuation prompt from user
                }
                invalidInput= true;
            case 1:
                while(invalidInput) { // if invalid input, reprint and let the user try again
                    System.out.println("Disclaimeri");
                    System.out.println("");
                    System.out.println("This game is completely fictional, and any mentions of prison or");
                    System.out.println("the justice system are abstract and not intended to be representative of the world we live in.");
                    userContinuer(); // get continuation prompt from user
                }
                invalidInput = true;
            case 2:
                while(invalidInput) { // if invalid input, reprint and let the user try again
                    System.out.println("Game theory:");
                    System.out.println("");
                    System.out.println("The Prisoner's Dilemma is a classic game theory scenario where two players must decide");
                    System.out.println("independently whether to cooperate or betray, without knowing the other's choice.");
                    System.out.println("While mutual cooperation leads to a better outcome for both, the temptation to betray");
                    System.out.println("for personal gain often leads to worse results overall. This dilemma highlights the");
                    System.out.println("conflict between individual rationality and collective benefit.");
                    userContinuer(); // get continuation prompt from user
                }
            case 3:
                while (invalidInput) {
                    System.out.println("Instructions:");
                    System.out.println("Once you start the game there will be two buttons: Cooperate, and defect");
                }
                invalidInput = true;  
        }
    }

    public void userContinuer() {
        System.out.println("Press [i] to continue.");
        try {
            if (keyboard.nextLine().toLowerCase().equals("i")) { // if user continues, move onto next prompt
                invalidInput = false;
                homeScreenLength++;
                System.out.print('\u000C'); // clear screen
            } else { // if text input other than i
                System.out.print('\u000C'); // clear screen
                System.out.println("Invalid entry.");
            }
        } catch (Exception e) { // if invalid input (such as a number)
            System.out.print('\u000C'); // clear screen
            System.out.println("Invalid entry.");
        }
    }

}
