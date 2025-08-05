
/**
 * Prisoner's Dilemma
 *
 * @author Alex Brook Conway
 * @version 05/08/25
 */

import java.util.Scanner; // Keyboard input
import java.util.Random;

public class PrisonersDilemma {

    Scanner keyboard = new Scanner(System.in); // Initialize scanner for user input
    int AIStrategy;
    int totalRounds;
    int roundsPlayed = 0;
    int playerPointsRealtime;
    int AIPointsRealtime;
    String[] playerHistory;
    String[] AIHistory;
    int[] playerPointsHistory;
    int[] AIPointsHistory;
    boolean grimTriggered;
    boolean playerDefectedLastTwo;
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

            //Step 1 - Reset game variables
            resetGameState();

            // Step 2 - Print Homescreen
            homeScreen();

            // Step 3 - Setup (Choose AI Strategy, number of rounds)
            setup();

            // Step 4 -  Start Game
            startGame();

            // Step 5 - Ask if they want to replay the game

        } while(gameReplayer() == true);

        clearScreen();
        System.out.println("Thanks for playing!");
    }

    public void homeScreen() {
        // run user through game info
        System.out.println("Welcome to a Prisoner's Dilemma recreation!");
        System.out.println();
        System.out.println("This is a simple, text-based activity about choices and trust.");
        System.out.println("You play against an AI opponent over several short rounds.");
        System.out.println("Each round you choose to cooperate or to defect.");
        System.out.println("After both choices are made, you will see the outcome and points.");
        userContinuer(); // get continuation prompt from user

        System.out.println("Disclaimer");
        System.out.println();
        System.out.println("This game is completely fictional, and any mentions of prison or");
        System.out.println("the justice system are abstract and not intended to be representative of the world we live in.");
        userContinuer(); // get continuation prompt from user

        gameTheory();

        instructions();
    }

    public void setup() {
        Random random = new Random();
        AIStrategy = random.nextInt(6); // choose AI based on a random number (gives 0-5)
        /* 0 = Always defect
         * 1 = Always cooperate
         * 2 = Tit-for-tat
         * 3 = Grim trigger
         * 4 = Win–Stay, Lose–Shift
         * 5 = Tit-for-Two-Tats */
        totalRounds =  (int)(Math.random() * (15 - 5 + 1)) + 5; // set random number of rounds between 5-15
        playerHistory = new String[totalRounds];
        AIHistory = new String[totalRounds];
        playerPointsHistory = new int[totalRounds];
        AIPointsHistory  = new int[totalRounds];
    }

    public void startGame() {
        for (int round = 0; round < totalRounds; round++) {
            boolean moveChosen = false;
            String userInput = "";

            clearScreen();
            while (!moveChosen) { // Step 1 - Prompt until we get a valid move (c or d)
                System.out.println("Round " + (round+1) + ":");
                if(round != 0) { // if its not the first round, print the realtime score
                    System.out.println();
                    if (playerPointsRealtime>AIPointsRealtime) {
                        System.out.println("You currently lead by " + playerPointsRealtime + "-" + AIPointsRealtime);
                    } else if (playerPointsRealtime<AIPointsRealtime) {
                        System.out.println("You are currently losing by " + AIPointsRealtime + "-" + playerPointsRealtime);
                    } else {
                        System.out.println("It's currently a draw! " + playerPointsRealtime + "-" + AIPointsRealtime);
                    }
                }
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
                    clearScreen();
                    gameTheory();
                } else if (userInput.equals("i")) {
                    clearScreen();
                    instructions();
                } else if (userInput.equals("h")) {
                    clearScreen();
                    showHistory(roundsPlayed);
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
                    clearScreen();
                    System.out.println("Invalid choice — please try again.");
                }
            }

            // Step 2 - process the chosen move
            playerHistory[round] = userInput; // take user input

            // check for consistent defecting
            boolean consistentDefecting = false;
            if(round>=4) { // if round 4 has passed
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
            clearScreen();
            roundComplete(round);
            roundsPlayed++;
        }
        // After game is finished - show results, history, and stats
        gameResults(); // show who won, and show final score
        showHistory(roundsPlayed); // show round history
        clearScreen();
        showStats(); // show stats
    }

    public boolean gameReplayer() {
        boolean invalidInput = true;
        while(invalidInput) {
            System.out.println("Would you like to play another game?");
            System.out.println("[y] - Yes");
            System.out.println("[n] - No");
            System.out.println();
            try {
                String userInput = keyboard.nextLine().trim().toLowerCase();
                if (userInput.equals("y")) { 
                    // restart game
                    invalidInput = false;
                    return true;
                } else if (userInput.equals("n")) {
                    invalidInput = false;
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
        System.out.println("┌───────────────────  STATS  ───────────────────┐");
        System.out.println("Rounds played: " + roundsPlayed);
        System.out.println("Your score: " + playerPointsRealtime);
        System.out.println("The AI's score: " + AIPointsRealtime);
        System.out.println("────────────────────────────────────────────────");
        System.out.println("You cooperated " + playerCooperateTally + " times.");
        System.out.println("You defected " + playerDefectTally + " times.");
        System.out.println("The AI cooperated " + AICooperateTally + " times.");
        System.out.println("The AI defected " + AIDefectTally + " times.");
        System.out.println("────────────────────────────────────────────────");

        double playerAvg = (roundsPlayed > 0) ? (double) playerPointsRealtime / roundsPlayed : 0.0;
        double aiAvg = (roundsPlayed > 0) ? (double) AIPointsRealtime / roundsPlayed : 0.0;

        System.out.printf("You scored an average of %.1f points per round.%n", playerAvg);
        System.out.printf("The AI scored an average of %.1f points per round.%n", aiAvg);

        System.out.println("└───────────────────────────────────────────────┘");

        userContinuer();
    }

    public void gameResults() {
        if (playerPointsRealtime > AIPointsRealtime) {
            System.out.println("You win!");
            System.out.println();
            System.out.println(playerPointsRealtime + " - " + AIPointsRealtime);
        } else if (playerPointsRealtime < AIPointsRealtime) {
            System.out.println("You lose.");
            System.out.println();
            System.out.println(AIPointsRealtime + " - " + playerPointsRealtime);
        } else {
            System.out.println("It's a draw...");
            System.out.println();
            System.out.println(AIPointsRealtime + " - " + playerPointsRealtime);  
        }

        userContinuer();
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
            case 4:
                winStayLoseShift(round);
                break;
            case 5:
                titForTwoTats(round);
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

    public void winStayLoseShift(int round) {
        // cooperate first, then:
        if (round == 0) {
            AIHistory[round] = "c";
        } else { 

            int lastAIPoints = AIPointsHistory[round - 1];
            String lastAIMove = AIHistory[round - 1];

            // If the AI got 3 or more points last round, repeat last move; else flip it.
            if (lastAIPoints >= 3) {
                // good outcome last round
                AIHistory[round] = lastAIMove; // repeat
            } else {
                // bad outcome last round
                if (lastAIMove.equals("d")) {
                    AIHistory[round] = "c";
                } else {
                    AIHistory[round] = "d";
                }
            }
        }
    }

    public void titForTwoTats(int round) {
        // cooperate first, then defect only after back-to-back defections
        if (round<2) {
            AIHistory[round] = "c";
        } else {
            playerDefectedLastTwo =  playerHistory[round - 1].equals("d") && playerHistory[round - 2].equals("d");
            if (playerDefectedLastTwo) {
                AIHistory[round] = "d";
            } else {
                AIHistory[round] = "c";
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
        if(round==0) {
            AIHistory[round] = "c";
        } else {
            AIHistory[round] = playerHistory[round - 1];
        }
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
            // if player defected on the last round, defect and trigger the grim
            grimTriggered = true;
            AIHistory[round] = "d";
        } else {
            // if player has never defected, cooperate
            AIHistory[round] = "c";
        }
    }

    public void roundComplete(int round) {
        System.out.println("Round " + (round+1) + " results:");
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
        }
        userContinuer();
    }

    public void instructions() {
        System.out.println("How to play:");
        System.out.println();
        System.out.println("You and the AI will play a random number of rounds (5 to 15).");
        System.out.println("On each round choose: [c] cooperate, or [d] defect.");
        System.out.println("Open help at any time with [g] game theory or [i] instructions.");
        System.out.println("View what has happened so far with [h] history.");
        System.out.println("To quit, press [q] and confirm when asked.");
        System.out.println("Aim for the best total by thinking about the long term.");

        userContinuer();
    }

    public void gameTheory() {
        System.out.println("Game theory:");
        System.out.println();
        System.out.println("The Prisoner's Dilemma is a classic example in game theory.");
        System.out.println("Two players choose, without talking, to cooperate or to defect.");
        System.out.println("If both cooperate, both do well. If both defect, both do only a little.");
        System.out.println("If one defects while the other cooperates, the defector does best.");
        System.out.println("Over repeated rounds, trust can raise the score for both players.");

        userContinuer();
    }

    public void resetGameState() {
        playerPointsRealtime = 0;
        AIPointsRealtime = 0;
        roundsPlayed = 0;
        grimTriggered = false;
        playerCooperateTally = 0;
        playerDefectTally = 0;
        AICooperateTally = 0;
        AIDefectTally = 0;
    }

    public void userContinuer() {
        System.out.println();
        System.out.println("Press [ENTER] to continue.");
        keyboard.nextLine();
        clearScreen();
    }

    public void clearScreen() {
        System.out.print('\u000C'); // clear screen (only works in BlueJ)
    }

}