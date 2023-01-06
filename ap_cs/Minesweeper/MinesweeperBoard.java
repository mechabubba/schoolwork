
import java.util.ArrayList;

public class MinesweeperBoard {
    /* the values are counterintuitive but they are relative to how arrays work */
    private int x; // width (y axis)
    private int y; // length (x axis)
    private int mines;
    private int flags;
    private char[][] board;
    private char[][] display;
    
    /* common character values */
    private char bullet = '•'; // • 
    private char mine = '#';   // #
    private char flag = 'F';   // F
    private char zero = ' ';   //  
    
    /* default game values */
    private int _x = 15;
    private int _y = 15;
    private int _mines = 30;
    
    public MinesweeperBoard() {
        x = _x;
        y = _y;
        mines = _mines;
        
        generate();
    }
    
    public MinesweeperBoard(int sX, int sY, int m) throws ArrayIndexOutOfBoundsException {
        if(sX <= 0 || sY <= 0) throw new ArrayIndexOutOfBoundsException("Invalid board bounds - board must exist");
        x = sX;
        y = sY;
        mines = m;
        
        generate();
    }
   
    private void generate() {
        board = new char[y][x];
        display = new char[y][x];
        
        int placedMines = 0;
        while(placedMines < mines) {
            int randomY = (int)Math.floor(Math.random() * y);
            int randomX = (int)Math.floor(Math.random() * x);
            if(board[randomY][randomX] == mine) continue;
            board[randomY][randomX] = mine;
            placedMines++;
        }
        
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                display[i][j] = bullet;
                if(board[i][j] == mine) continue;
                char val = Character.forDigit(selectNearby(i, j, mine).size(), 10);
                if(val == '0') board[i][j] = zero;
                else board[i][j] = val;
            }
        }
    }
    
    public char[][] getDisplay() {
        return display;
    }
    
    public char[][] getBoard() {
        return board;
    }
    
    public char getDisplayValue(int sX, int sY) throws ArrayIndexOutOfBoundsException {
        return display[sX][sY];
    }
    
    public char getBoardValue(int sX, int sY) throws ArrayIndexOutOfBoundsException {
        return display[sX][sY];
    }
    
    public char getMine() {
        return mine;
    }
    
    public int getMines() {
        return mines;
    }
    
    public int getFlags() {
        return flags;
    }
    
    public char update(int sY, int sX) {
        return this.update(sY, sX, false);
    }
    
    public char update(int sY, int sX, boolean doFlag) throws ArrayIndexOutOfBoundsException {
        if(sY < 0 || sY > y || sX < 0 || sX > x) throw new ArrayIndexOutOfBoundsException("Selection was outside of board bounds.");
        if(doFlag) return this.toggleFlag(sY, sX);
        else if(display[sY][sX] == flag) flags--;
        if(board[sY][sX] != zero) {
            display[sY][sX] = board[sY][sX];
            return board[sY][sX];
        }
        this._update(sY, sX);
        return board[sY][sX];
    }
    
    private void _update(int sY, int sX) {
        display[sY][sX] = board[sY][sX];
        ArrayList<Integer[]> nearby;
        if(board[sY][sX] == zero) {
            nearby = this.selectNearby(sY, sX);
        } else {
            nearby = this.selectNearby(sY, sX, zero);
        }
        if(nearby.size() < 1) return; // no nearby values
        for(Integer[] coord : nearby) {
            int nY = coord[0];
            int nX = coord[1]; 
            if(display[nY][nX] != bullet || display[nY][nX] == mine) continue;
            char tile = this.update(nY, nX, false);
        }
        return;
    }
    
    private ArrayList<Integer[]> selectNearby(int sY, int sX) {
        return this.selectNearby(sY, sX, '\0'); // null character
    }
    
    private ArrayList<Integer[]> selectNearby(int sY, int sX, char selector) {
        ArrayList<Integer[]> arr = new ArrayList<Integer[]>();
        
        // 1 2 3
        // 8 • 4
        // 7 6 5
        // if selector is null, it will return every value around
        
        if((sY - 1 >= 0 && sX - 1 >= 0) && (board[sY - 1][sX - 1] == selector || selector == '\0')) // 1
            arr.add(new Integer[]{sY - 1, sX - 1});
        if((sY - 1 >= 0) && (board[sY - 1][sX] == selector || selector == '\0')) // 2
            arr.add(new Integer[]{sY - 1, sX});
        if((sY - 1 >= 0 && sX + 1 < x) && (board[sY - 1][sX + 1] == selector || selector == '\0')) // 3
            arr.add(new Integer[]{sY - 1, sX + 1});
        if((sX + 1 < x) && (board[sY][sX + 1] == selector || selector == '\0')) // 4
            arr.add(new Integer[]{sY, sX + 1});
        if((sY + 1 < y && sX + 1 < x) && (board[sY + 1][sX + 1] == selector || selector == '\0')) // 5
            arr.add(new Integer[]{sY + 1, sX + 1});
        if((sY + 1 < y) && (board[sY + 1][sX] == selector || selector == '\0')) // 6
            arr.add(new Integer[]{sY + 1, sX});
        if((sY + 1 < y && sX - 1 >= 0) && (board[sY + 1][sX - 1] == selector || selector == '\0')) // 7
            arr.add(new Integer[]{sY + 1, sX - 1});
        if((sX - 1 >= 0) && (board[sY][sX - 1] == selector || selector == '\0')) // 8
            arr.add(new Integer[]{sY, sX - 1});
        
        return arr;
    }
    
    public char toggleFlag(int sY, int sX) {
        display[sY][sX] = flag;
        flags++;
        return flag;
    }
    
    public boolean isComplete() {
        return this._isComplete(0, 0);
    }
    
    private boolean _isComplete(int sY, int sX) {
        if(sY >= y) return true;
        else if(sX >= x) {
            sY++;
            sX = 0;
        } else {
            if(board[sY][sX] != display[sY][sX]) {
                if((board[sY][sX] != mine) || (board[sY][sX] == mine && display[sY][sX] != flag)) return false;
            }
            sX++;
        }
        return this._isComplete(sY, sX);
    }
}