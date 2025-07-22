
/**
 * Prisoner's Dilemma
 *
 * @author Alex Brook Conway
 * @version 14/07/25
 */

import java.util.Scanner; // Keyboard input
import java.util.Random;

public class PrisonersDilemma {

    Scanner keyboard = new Scanner(System.in); // Initialize scanner for user input
    int AIStrategy;
    int rounds;
    int playerPointsRealtime;
    int AIPointsRealtime;
    String[] playerHistory;
    String[] AIHistory;
    int[] playerPointsHistory;
    int[] AIPointsHistory;
    boolean invalidInput = true;
    boolean grimTriggered;
    boolean playerWin;
    int playerCooperateTally = 0;
    int playerDefectTally = 0;
    int AICooperateTally = 0;
    int AIDefectTally = 0;

    public PrisonersDilemma() {
        run();
    }

    public void run() {
        do {
            clearScreen();

            // Step 1 - Print Homescreen
            homeScreen();

            // Step 2 - Setup (Choose AI Strategy, number of rounds)
            setup();

            // Step 3 -  Start Game
            startGame();

            // Step 4 - Ask if they want to replay the game

        } while(gameReplayer() == true);

        clearScreen();
        System.out.println("Thanks for playing!");
    }

    public void homeScreen() {
        // run user through game info
        while(invalidInput) { // if invalid input, reprint and let the user try again
            System.out.println("Welcome to a Prisoner's Dilemma recreation!");
            System.out.println();
            System.out.println("The Prisoner's Dilemma is a classic example in game theory where two players must choose to cooperate or betray, ");
            System.out.println("with outcomes depending on the combination of their choices—highlighting the conflict between individual gain and mutual benefit.");
            userContinuer(); // get continuation prompt from user
        }
        invalidInput= true;

        while(invalidInput) { // if invalid input, reprint and let the user try again
            System.out.println("Disclaimer");
            System.out.println();
            System.out.println("This game is completely fictional, and any mentions of prison or");
            System.out.println("the justice system are abstract and not intended to be representative of the world we live in.");
            userContinuer(); // get continuation prompt from user
        }
        invalidInput = true;

        while(invalidInput) { // if invalid input, reprint and let the user try again
            gameTheory();
        }
        invalidInput = true;

        while (invalidInput) { // if invalid input, reprint and let the user try again
            instructions();
        } 
    }

    public void setup() {
        Random random = new Random();
        AIStrategy = 1; //random.nextInt(4); // choose AI based on a random number (gives 0-3)
        /* 0 = Always defect
         * 1 = Always cooperate
         * 2 = Tit-for-tat
         * 3 = Grim trigger */
        rounds = (int)(Math.random() * (15 - 5 + 1)) + 5; // set random number of rounds between 5-15
        playerHistory = new String[rounds];
        AIHistory = new String[rounds];
        playerPointsHistory = new int[rounds];
        AIPointsHistory  = new int[rounds];
    }

    public void startGame() {
        for (int round = 0; round < rounds; round++) {
            boolean moveChosen = false;
            String userInput = "";

            while (!moveChosen) { // Step 1 - Prompt until we get a valid move (c or d)
                clearScreen();
                System.out.println("Round " + (round+1) + ":");
                System.out.println();
                System.out.println("Commands:");
                System.out.println("[g] - Game Theory");
                System.out.println("[i] - Instructions");
                System.out.println();
                System.out.println("[h] - Round History");
                System.out.println();
                System.out.println("Time to make your decision?");
                System.out.println("[c] - Cooperate");
                System.out.println("[d] - Defect");
                System.out.println();
                System.out.println("[q] - Quit at anytime :)");

                userInput = keyboard.nextLine().toLowerCase();
                if (userInput.equals("c") || userInput.equals("d")) {
                    // valid move, we’ll fall out of this loop on the next check
                    moveChosen = true;
                } else if (userInput.equals("g")) {
                    gameTheory();
                } else if (userInput.equals("i")) {
                    instructions();
                } else if (userInput.equals("h")) {
                    clearScreen();
                    showHistory(round);
                } else if (userInput.equals("q")) {
                    clearScreen();
                    System.out.println("Are you sure you want to quit?");
                    System.out.println();
                    System.out.println("[y] = Yes, I want to quit");
                    System.out.println("[any other key] = No, I want to keep playing");
                    String quitResponse = keyboard.nextLine().trim().toLowerCase();

                    if (quitResponse.equals("y")) { // check player is sure
                        clearScreen();
                        System.out.println("Thanks for playing :)");
                        System.exit(0);
                    }
                } else {
                    System.out.println("Invalid choice—try again.");
                }
            }

            // Step 2 - process the chosen move
            playerHistory[round] = userInput; // take user input
            
            // check for consistent defecting
            boolean consistentDefecting = false;
            if(round>4) { // if round 4 has passed
                for(int x=1; x<=4; x++) { // loop through the past 4 rounds
                    if(playerHistory[round-x].equals("d")) {
                        // if player defected, set boolean to true
                        consistentDefecting = true;
                    } else {
                        // if player cooperated, set boolean to false and fall out of loop
                        consistentDefecting = false;
                        x=4;
                    }
                }
            }
            
            

            if(consistentDefecting == true) {
                // if player has consistently defected
                AIHistory[round] = "d";
            } else {
                // otherwise, ask the ai what move to play
                AIChooser(round);
            }
            pointsCalculator(round); // calculate points

            // Step 3 - show round results
            roundComplete(round);
        }
        // After game is finished - show results, history, and stats
        gameResults(); // show who won, and show final score
        showHistory(rounds-1); // show round history
        showStats(); // show stats

        // Ask user if they want to play another round

    }

    public boolean gameReplayer() {
        System.out.println("Would you like to play another game?");
        System.out.println("[y] - Yes");
        System.out.println("[n] - No");
        System.out.println();

        invalidInput = true;
        while(invalidInput) {
            try {
                String userInput = keyboard.nextLine().trim().toLowerCase();
                if (userInput.equals("y")) { 
                    // restart game
                    return true;
                } else if (userInput.equals("n")) {
                    return false;
                } else { // if text input other than y or n
                    clearScreen();
                    System.out.println("Invalid entry.");
                }
            } catch (Exception e) { // if invalid input (such as a number)
                clearScreen();
                System.out.println("Invalid entry.");
                keyboard.nextLine(); // clear buffer
            }
        }
        return true;
    }

    public void showStats() {
        clearScreen();

        System.out.println("┌───────────────────  STATS  ───────────────────┐");
        System.out.println("Rounds played: " + rounds);
        System.out.println("Your score: " + playerPointsRealtime);
        System.out.println("The AI's score: " + AIPointsRealtime);
        System.out.println("────────────────────────────────────────────────");
        System.out.println("You cooperated " + playerCooperateTally + " times.");
        System.out.println("You defected " + playerDefectTally + " times.");
        System.out.println("The AI cooperated " + AICooperateTally + " times.");
        System.out.println("The AI defected " + AIDefectTally + " times.");
        System.out.println("────────────────────────────────────────────────");
        System.out.println("You scored an average of " + (playerPointsRealtime/rounds) + " points per round.");
        System.out.println("The AI scored an average of " + (AIPointsRealtime/rounds) + " points per round.");
        System.out.println("└───────────────────────────────────────────────┘");

        userContinuer();
    }

    public void gameResults() {
        if (playerPointsRealtime > AIPointsRealtime) {
            playerWin = true;
            System.out.println("You win.");
            System.out.println();
            System.out.println(playerPointsRealtime + " - " + AIPointsRealtime);
        } else {
            playerWin = false;
            System.out.println("You lose.");
            System.out.println();
            System.out.println(AIPointsRealtime + " - " + playerPointsRealtime);
        }
    }

    public void AIChooser(int round) {
        switch(AIStrategy) {
            case 0: 
                alwaysDefect(round);
                break;
            case 1: 
                alwaysCooperate(round);
                break;
            case 2:
                titForTat(round);
                break;
            case 3:
                grimTrigger(round);
                break;
        }
    }

    public void pointsCalculator(int x) {
        if (playerHistory[x].equals("c")) {
            playerCooperateTally++;
            if (AIHistory[x].equals("c")) {
                // if both cooperate, 3 points to each
                playerPointsHistory[x] = 3;
                AIPointsHistory[x] = 3;
                playerPointsRealtime +=3;
                AIPointsRealtime +=3;
                AICooperateTally++;
            } else {
                // if player cooperates and ai defects, 0 points to player, 5 points to ai
                playerPointsHistory[x] = 0;
                AIPointsHistory[x] = 5;
                playerPointsRealtime +=0;
                AIPointsRealtime +=5;
                AIDefectTally++;
            }
        }

        if (playerHistory[x].equals("d")) {
            playerDefectTally++;
            if (AIHistory[x].equals("d")) {
                // if both defect, 1 point to each
                playerPointsHistory[x] = 1;
                AIPointsHistory[x] = 1;
                playerPointsRealtime +=1;
                AIPointsRealtime +=1;
                AIDefectTally++;
            } else {
                // if player defects and ai cooperates, 5 points to player, 0 points to ai
                playerPointsHistory[x] = 5;
                AIPointsHistory[x] = 0;
                playerPointsRealtime +=5;
                AIPointsRealtime +=0;
                AICooperateTally++;
            }
        }
    }

    public void alwaysDefect(int round) {
        // A strategy that always defects
        AIHistory[round] = "d";
    }

    public void alwaysCooperate(int round) {
        // A strategy that always cooperates
        AIHistory[round] = "c";
    }

    public void titForTat(int round) {
        // A strategy that always mirrors what the player put last round, and cooperates on the first round
        AIHistory[round] = playerHistory[round - 1];
    }

    public void grimTrigger(int round) {
        // A strategy that always cooperates, until the player defects once, then always defects
        if (grimTriggered) {
            // if grim has been triggered, defect
            AIHistory[round] = "d";
        } else if (round == 0) {
            // if its the first round, cooperate
            AIHistory[round] = "c";
        } else if (playerHistory[round-1].equals("d")) {
            // if player defected on the last round, defect
            grimTriggered = true;
            AIHistory[round] = "d";
        }
    }

    public void roundComplete(int round) {
        clearScreen();

        System.out.println("Round " + round + " results:");
        System.out.println();

        if(playerHistory[round].equals("c")) { // player history
            System.out.println("You cooperated: " + "+" + playerPointsHistory[round] + " points"); // if player cooperated
        } else {
            System.out.println("You defected: " + "+" + playerPointsHistory[round] + " points"); // if player defected
        }

        if(AIHistory[round].equals("c")) { // ai history
            System.out.println("The AI cooperated: " + "+" + AIPointsHistory[round] + " points"); // if ai cooperated
        } else {
            System.out.println("The AI defected: " + "+" + AIPointsHistory[round] + " points"); // if player defected
        }

        userContinuer();
    }

    public void showHistory(int round) {
        if (round == 0) { // if its still the first round
            System.out.println("There is no history yet! Maybe check back later?");
            userContinuer();
        } else {
            System.out.println("Round History:");
            System.out.println();
            for(int x=0; x<round; x++ ) { // loop through played rounds
                System.out.println("Round " + (x+1) + ":");
                if(playerHistory[x].equals("c")) { // player history
                    System.out.println("You cooperated: " + "+" + playerPointsHistory[x] + " points"); // if player cooperated
                } else {
                    System.out.println("You defected: " + "+" + playerPointsHistory[x] + " points"); // if player defected
                }

                if(AIHistory[x].equals("c")) { // ai history
                    System.out.println("The AI cooperated: " + "+" + AIPointsHistory[x] + " points"); // if ai cooperated
                } else {
                    System.out.println("The AI defected: " + "+" + AIPointsHistory[x] + " points"); // if player defected
                }
                System.out.println();
            }

            userContinuer();
        }

    }

    public void instructions() {
        clearScreen();

        System.out.println("Welcome to a Prisoner's Dilemma recreation!");
        System.out.println();
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
        clearScreen();

        System.out.println("Game theory:");
        System.out.println();
        System.out.println("The Prisoner's Dilemma is a classic game theory scenario where two players must decide");
        System.out.println("independently whether to cooperate or defect, without knowing the other's choice.");
        System.out.println("While mutual cooperation leads to a better outcome for both, the temptation to betray");
        System.out.println("for personal gain often leads to worse results overall. This dilemma highlights the");
        System.out.println("conflict between individual rationality and collective benefit.");

        userContinuer(); // get continuation prompt from user
    }

    public void userContinuer() {
        System.out.println();
        System.out.println("Press [i] to continue.");
        try {

            if (keyboard.nextLine().toLowerCase().equals("i")) { // if user continues, move on
                invalidInput = false;
                clearScreen();clearScreen();
            } else { // if text input other than i
                clearScreen();
                System.out.println("Invalid entry.");
            }
        } catch (Exception e) { // if invalid input (such as a number)
            clearScreen();
            System.out.println("Invalid entry.");
            keyboard.nextLine(); // clear buffer
        }
    }

    public void clearScreen() {
        System.out.print('\u000C'); // clear screen (only works in BlueJ)
    }

}