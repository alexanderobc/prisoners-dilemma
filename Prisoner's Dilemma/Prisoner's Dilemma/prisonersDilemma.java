
/**
 * Prisoner's Dilemma
 *
 * @author Alex Brook Conway
 * @version 09/06/25
 */

import java.util.Scanner; // Keyboard input
import java.util.Random;

public class prisonersDilemma {

    Scanner keyboard = new Scanner(System.in); // Initialize scanner for user input
    int homeScreenLength = 0;
    int AIStrategy;
    int rounds;
    int playerPointsRealtime;
    int AIPointsRealtime;
    String[] playerHistory;
    String[] AIHistory;
    int[] playerPointsHistory;
    int[] AIPointsHistory;
    boolean invalidInput = true;

    public prisonersDilemma() {
        System.out.print('\u000C'); // clear screen

        // Step 1 - Print Homescreen
        homeScreen();

        // Step 2 - Setup (Choose AI Strategy, number of rounds)
        setup();

        // Step 3 -  Start Game
        startGame();
    }

    public void homeScreen() {
        switch(homeScreenLength) { // switch statement to take user through key info one piece at a time.
            case 0: // run user through game info, no breaks so it continues through
                while(invalidInput) { // if invalid input, reprint and let the user try again
                    System.out.println("Welcome to a Prisoner's Dilemma recreation!");
                    System.out.println("");
                    System.out.println("The Prisoner's Dilemma is a classic example in game theory where two players must choose to cooperate or betray, ");
                    System.out.println("with outcomes depending on the combination of their choices—highlighting the conflict between individual gain and mutual benefit.");
                    userContinuer(); // get continuation prompt from user
                }
                invalidInput= true;
            case 1:
                while(invalidInput) { // if invalid input, reprint and let the user try again
                    System.out.println("Disclaimer");
                    System.out.println("");
                    System.out.println("This game is completely fictional, and any mentions of prison or");
                    System.out.println("the justice system are abstract and not intended to be representative of the world we live in.");
                    userContinuer(); // get continuation prompt from user
                }
                invalidInput = true;
            case 2:
                while(invalidInput) { // if invalid input, reprint and let the user try again
                    gameTheory();
                }
                invalidInput = true;
            case 3:
                while (invalidInput) { // if invalid input, reprint and let the user try again
                    instructions();
                }
                invalidInput = true;  
        }
    }

    public void setup() {
        Random random = new Random();
        AIStrategy = random.nextInt(4); // choose AI based on a random number 
        /* 1 = Always defect
         * 2 = Always cooperate
         * 3 = Tit-for-tat
         * 4 = Grim trigger */
        rounds = (int)(Math.random() * (15 - 5 + 1)) + 5; // set random number of rounds between 5-15
        playerHistory = new String[rounds];
        AIHistory = new String[rounds];
    }

    public void startGame() {
        boolean gameOn = true;
        for(int x=0; x<=rounds; x++) { // loop through rounds
            System.out.print('\u000C'); // clear screen

            invalidInput = true;
            while(invalidInput) {
                System.out.println(x);
                System.out.println("Commands:");
                System.out.println("[g] - Game Theory");
                System.out.println("[i] - Instructions");
                System.out.println();
                System.out.println("[h] - Round History");
                System.out.println();
                System.out.println("Time to make your decision?");
                System.out.println("[c] - Cooperate");
                System.out.println("[d] - Defect");
                try {
                    String userInput = keyboard.nextLine().toLowerCase();
                    if (userInput.equals("g")) { // if user selects game theory
                        System.out.print('\u000C'); // clear screen
                        gameTheory();
                        System.out.print('\u000C'); // clear screen
                    } else if (userInput.equals("i")) { // if user selects instructions
                        System.out.print('\u000C'); // clear screen
                        instructions();
                        System.out.print('\u000C'); // clear screen
                    } else if (userInput.equals("h")) { // if user selects history
                        System.out.print('\u000C'); // clear screen
                        showHistory(x);
                        System.out.print('\u000C'); // clear screen
                    } else if (userInput.equals("c")) { // if user selects cooperate
                        System.out.print('\u000C'); // clear screen
                        // Step 1 - Add to player history
                        playerHistory[x] = "c";
                        
                        // Step 2 - Add AI choice
                        AIChooser(x);
                        
                        System.out.print('\u000C'); // clear screen
                    } else if (userInput.equals("d")) { // if user selects defect
                        System.out.print('\u000C'); // clear screen
                        // Step 1 - Add to player history
                        playerHistory[x] = "d";
                        
                        // Step 2 - Add AI choice
                        AIChooser(x);
                        
                        System.out.print('\u000C'); // clear screen
                    } else { // if other text input
                        System.out.print('\u000C'); // clear screen
                        System.out.println("That's not an option...");
                    }
                } catch (Exception e) { // if invalid input (such as a number)
                    System.out.print('\u000C'); // clear screen
                    System.out.println("Invalid entry.");
                }
            }
        }
    }
    
    public void AIChooser(int x) {
        switch(AIStrategy) {
            case 1: 
                alwaysDefect(x);
                break;
            case 2: 
                alwaysCooperate(x);
                break;
            case 3:
                titForTat(x);
                break;
            case 4:
                grimTrigger(x);
                break;
        }
    }
    
    public void pointsCalculator(int x) {
        if (playerHistory[x] == "c") {
            if (AIHistory[x] == "c") {
                // if both cooperate, 3 points to each
                playerPointsHistory[x] = 3;
                AIPointsHistory[x] = 3;
                playerPointsRealtime +=3;
                AIPointsRealtime +=3;
            } else {
                
            }
        } else {
            
        }
    }
    
    public void alwaysDefect(int x) {
        
    }
    
    public void alwaysCooperate(int x) {
        
    }
    
    public void titForTat(int x) {
        
    }
    
    public void grimTrigger(int x) {
        
    }

    public void showHistory(int x) {
        if (x==0) { // if its still the first round
            System.out.println("There is no history yet! Maybe check back later?");
            userContinuer();
        } else {
            System.out.println("Round History:");
            System.out.println();
            for(int y=0; y<=x; y++ ) { // loop through played rounds
                System.out.println("Round " + x + ":");
                if(playerHistory[x].equals("c")) { // player history
                    System.out.println("You cooperated - " + "+" + playerPointsHistory[x] + " points"); // if player cooperated
                } else {
                    System.out.println("You defected - " + "+" + playerPointsHistory[x] + " points"); // if player defected
                }

                if(AIHistory[x].equals("c")) { // ai history
                    System.out.println("The AI cooperated - " + "+" + playerPointsHistory[x] + " points"); // if ai cooperated
                } else {
                    System.out.println("The AI defected - " + "+" + playerPointsHistory[x] + " points"); // if player defected
                }
            }

            userContinuer();
            invalidInput = true;
        }

    }

    public void instructions() {
        System.out.println("Welcome to a Prisoner's Dilemma recreation!");
        System.out.println("");
        System.out.println("You and the AI have both been arrested. Each round, you must choose to 'cooperate' or 'defect'.");
        System.out.println("If you both cooperate, you both get 3 points. If you both defect, you both get 1 point.");
        System.out.println("If one defects and the other cooperates, the defector gets 5 points, and the cooperator gets 0.");
        System.out.println("Each round, enter your choice: type 'C' to cooperate or 'D' to defect.");
        System.out.println("The game will run for a random number of rounds (between 5 and 15).");
        System.out.println("Your goal is to score as many points as possible over all rounds.");
        System.out.println("To quit the game early, type 'Q' at any time.");
        System.out.println("You will be able to see round history before you make your decisions.");
        System.out.println("The game begins now. Good luck!");
        
        userContinuer(); // get continuation prompt from user
    }

    public void gameTheory() {
        System.out.println("Game theory:");
        System.out.println("");
        System.out.println("The Prisoner's Dilemma is a classic game theory scenario where two players must decide");
        System.out.println("independently whether to cooperate or defect, without knowing the other's choice.");
        System.out.println("While mutual cooperation leads to a better outcome for both, the temptation to betray");
        System.out.println("for personal gain often leads to worse results overall. This dilemma highlights the");
        System.out.println("conflict between individual rationality and collective benefit.");
        
        userContinuer(); // get continuation prompt from user
    }

    public void userContinuer() {
        System.out.println("Press [i] to continue.");
        try {
            if (keyboard.nextLine().toLowerCase().equals("i")) { // if user continues, move on
                invalidInput = false;
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
