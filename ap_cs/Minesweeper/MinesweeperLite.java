/* = MINESWEEPER LITE = */
import java.util.Scanner;

public class MinesweeperLite {
    public static void main(String[] args) {
        System.out.println("   __  ____"); // minus a space because bluej sucks
        System.out.println("   /  |/  (_)___  ___  ______      _____  ___  ____  ___  _____");
        System.out.println("  / /|_/ / / __ \\/ _ \\/ ___/ | /| / / _ \\/ _ \\/ __ \\/ _ \\/ ___/");
        System.out.println(" / /  / / / / / /  __(__  )| |/ |/ /  __/  __/ /_/ /  __/ /    ");
        System.out.println("/_/  /_/_/_/ /_/\\___/____/ |__/|__/\\___/\\___/ .___/\\___/_/     ");
        System.out.println("                                           /_/ by steven :D");
        
        Scanner input = new Scanner(System.in);
        System.out.print("length: ");
        int length = Math.abs(input.nextInt());
        System.out.print("width:  ");
        int width = Math.abs(input.nextInt());
        System.out.print("mines:  ");
        int mines = Math.abs(input.nextInt());
        if(length * width < mines) {
            System.out.println("The amount of mines exceed the space of the board! Filling the whole board with mines...");
            mines = length * width;
        }
        
        MinesweeperBoard game = new MinesweeperBoard(width, length, mines);
        
        System.out.println();
        printGame(game, false);
        
        while(true) {
            boolean flagging = false;
            
            int x = getX();
            switch(x) { // yknow it works
                case -1: // x is "f"
                    flagging = true;
                    System.out.println("Flagging!");
                    x = getX();
                    if(x < 0) {
                        System.out.println("No longer flagging!");  
                        continue;
                    }
                    break;
                case -2: // unknown value
                    System.out.println("Unknown value! Please input a number, or type 'F' to flag a tile..");
                    continue;
                default: break;
            }
            
            int y = getY();
            switch(y) {
                case -2: // unknown value
                    System.out.println("Unknown value! Please input a number.");
                    continue;
                default: break;
            }
            
            char tile = game.update(y, x, flagging);
            
            System.out.println();
            printGame(game, false);
            
            if(tile == game.getMine()) {
                System.out.println("You lose!");
                break;
            } else if(game.isComplete()) {
                System.out.println("You win!");
                break;
            }
        }
    }
    
    private static void printGame(MinesweeperBoard game, boolean doBoard) {
        System.out.println("mines flagged: " + game.getFlags() + "/" + game.getMines());
        
        char[][] board;
        if(doBoard) {
            board = game.getBoard();
        } else {
            board = game.getDisplay();
        }
        
        // display the board
        String spaces = Integer.toString(board.length);
        for(int i = board.length - 1; i >= 0; i--) {
            System.out.print(i + 1); // number on y axis
            
            // generate spaces between number and display to deal with digits
            for(int j = Integer.toString(i + 1).length(); j <= spaces.length(); j++) {
                System.out.print(" ");
            }
            
            // generate display row with respect to x axis numbers
            for(int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                for(int k = 0; k < Integer.toString(board[i].length).length(); k++) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
        
        // for loop to deal with spaces before x axis
        for(int i = 0; i <= spaces.length(); i++) {
            System.out.print(" ");
        }
        
        // now da x axis
        for(int i = 0; i < board[0].length; i++) {
            System.out.print(i + 1);
            for(int j = Integer.toString(i + 1).length(); j <= Integer.toString(board[0].length).length(); j++) {
                System.out.print(" ");
            }
        }

        System.out.println();
    }
    
    private static int getX() {
        Scanner input = new Scanner(System.in);
        System.out.print("x: ");
        String x = input.next();
        int val = 0;
        try {
            val = Math.abs(Integer.parseInt(x));
        } catch(Exception e) {
            if(x.toLowerCase().equals("f")) {
                return -1;
            } else {
                return -2;
            }
        }
        return val - 1;
    }   
    
    private static int getY() {
        Scanner input = new Scanner(System.in);
        System.out.print("y: ");
        String y = input.next();
        int val = 0;
        try {
            val = Math.abs(Integer.parseInt(y));
        } catch(Exception e) {
            return -2;
        }
        return val - 1;
    }
}