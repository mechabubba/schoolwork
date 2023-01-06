import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class ScoreboardMain {
    /**
     * The main method, which stems into the main game loop.
     * @param args Provided command line arguments.
     */
    public static void main(String[] args) {
        // Start off with initializing the game type.
        System.out.println("Select the type of game;");
        System.out.println("1. Football");
        System.out.println("2. Basketball");
        System.out.println("3. Baseball");
        System.out.println("4. Soccer");
        System.out.println("5. Hockey");
        System.out.print("> ");

        Scanner input = new Scanner(System.in);
        int gameChoice = input.nextInt();

        if(gameChoice > 5 || gameChoice <= 0) {
            System.out.println("Invalid game provided! Terminating.");
            return;
        }

        // **Required line:** Method `nextInt()` does not read the newline after your input, therefore passing it over to the next `nextLine()` method. This fixes that!
        // Source: https://stackoverflow.com/a/13102066
        input.nextLine();

        // Allow the user to input the team names.
        System.out.print("Enter home team: > ");
        String homeTeam = input.nextLine();
        System.out.print("Enter away team: > ");
        String awayTeam = input.nextLine();

        Team home = new Team(homeTeam);
        Team away = new Team(awayTeam);
        
        Game game = null;
        switch(gameChoice) {
            case 1: game = new Football(home, away); break;
            case 2: game = new Basketball(home, away); break;
            case 3: game = new Baseball(home, away); break;
            case 4: game = new Soccer(home, away); break;
            case 5: game = new Hockey(home, away); break;
        }

        System.out.printf("Game: %s\n", game.getClass().getSimpleName());
        game.setState(Game.GameState.IN_PROGRESS); // The game automatically starts out in an unstarted state, so we deliberately set the game to be in progress.

        // The main game loop; just repeatedly runs rounds until we exceed the period limit.
        while(!game.isFinished()) {
            runRound(game);
        }

        // The game is now over! Display some stats.
        System.out.println("The game is now over!");

        if(home.getScore() > away.getScore()) {
            System.out.printf("Result: %s WINS!\n", home.getName());
        } else if(home.getScore() < away.getScore()) {
            System.out.printf("Result: %s WINS!\n", away.getName());
        } else {
            System.out.println("Result: TIE?!"); // Authors note: This is an unacceptable result. Play again.
        }

        System.out.println("\nReplay:");
        LinkedList<ScoringHistoryEntry> history = game.getHistory();
        for(ScoringHistoryEntry entry : history) {
            System.out.printf("In %s %d: %s scored a %s!\n", game.getPeriodName(), entry.getPeriod() + 1, entry.getTeamName(), entry.getScoringMethod().getName());
        }
    }

    /**
     * Runs a round in the game. In this case, a round is any duration in which either team makes a score.
     * @param game - The game we're playing. Taken from the main function.
     */
    private static void runRound(Game game) {
        Scanner input = new Scanner(System.in);

        Team home = game.getTeam1();
        Team away = game.getTeam2();

        System.out.printf("%s vs %s (%d - %d)\n", home.getName(), away.getName(), home.getScore(), away.getScore());
        System.out.printf("In %s %d.\n\n", game.getPeriodName(), game.getPeriodNumber() + 1);
        
        ArrayList<ScoringMethod> methods = game.getScoringMethods();

        // Print menu options to the terminal.
        System.out.println("Menu:");
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < methods.size(); j++) {
                if(i == 0) {
                    System.out.printf("%d. %s %s\n", j + 1, home.getName(), methods.get(j).getName());
                } else {
                    System.out.printf("%d. %s %s\n", (j + (i * methods.size()) + 1), away.getName(), methods.get(j).getName());
                }
            }
        }
        System.out.printf("%d. End %s\n", (methods.size() * 2) + 1, game.getPeriodName()); // One more to end the period of the game.

        System.out.println("Please choose an option.");
        System.out.print("> ");
        int menuChoice = input.nextInt();

        // We can do some simple arithmetic to dynamically get which score to apply to which team.
        // We test some bounds to see if the user makes a score for the home or away team (or if they wish to end the period).
        if(menuChoice < methods.size()) {
            if(menuChoice <= 0) {
                // The user chose something out of bounds
                System.out.println("Out of bounds choice made!");
            }
            // Home team choice.
            menuChoice = menuChoice - 1;
            ScoringMethod method = methods.get(menuChoice);
            System.out.printf("Team %s has scored a %s!\n", home.getName(), method.getName());
            game.addScore(home, method);
        } else {
            int finalElement = ((methods.size() * 2) + 1); // The final selectable element 
            // Away team choice.
            if(menuChoice == finalElement) {
                // Don't apply a score to any team; the user wants to end the period.
                System.out.printf("Ending the %s!\n", game.getPeriodName());
                game.setPeriodNumber(game.getPeriodNumber() + 1);
            } else if(menuChoice > finalElement) {
                // The user chose something out of bounds
                System.out.println("Out of bounds choice made!");
            } else {
                // Apply score to away team.
                menuChoice = (menuChoice - 1) % methods.size(); // This is the actual index of the scoring method.
                ScoringMethod method = methods.get(menuChoice);
                System.out.printf("Team %s has scored %s!\n\n", away.getName(), method.getName());
                game.addScore(away, method);
            }
        }
        // At the end of all this choice making, we return to the main method.
        // If we haven't reached the end of the game, we'll just start back out at the top.
    }
}
